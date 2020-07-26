package jp.kaiz.rtmdengoadapter;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import jp.kaiz.atsassistmod.api.ControlTrain;
import jp.ngt.rtm.RTMCore;
import jp.ngt.rtm.entity.train.EntityTrainBase;
import jp.ngt.rtm.event.RTMKeyHandlerClient;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GamePad {
    private Controller control;
    private int lastNotchLevel, lastBrakeLevel;
    private int currentLevel = 0;
    private GamePadAdapter gamePadAdapter;

    private boolean isPressHorn, isPressDoorR, isPressDoorL;

    public GamePad() throws LWJGLException {
        Controllers.create();

        label:
        for (int i = 0; i < Controllers.getControllerCount(); i++) {
            Controller controller = Controllers.getController(i);
            String controllerName = controller.getName();
            System.out.println("Detected controller(" + i + "): " + controller.getName());
            switch (controllerName) {
                case "ELECOM JC-PS201U series":
                    control = controller;
                    gamePadAdapter = new DPadAsButtonGamePadAdapter();
                    break label;
                case "ELECOM JC-PS101U series":
                    control = controller;
                    gamePadAdapter = new DPadAsAxisGamePadAdapter();
                    break label;
                case "Generic   USB  Joystick  ":
                    control = controller;
                    gamePadAdapter = new SanYingGamePadAdapter();
                    break label;
            }
        }
    }

    @SubscribeEvent
    public void playerTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            if (control == null || gamePadAdapter == null) {
                return;
            }
            control.poll();

            int notchLevel = gamePadAdapter.getNotch(control, lastNotchLevel);
            if (lastNotchLevel != notchLevel) {
                lastNotchLevel = notchLevel;
            }

            int brakeLevel = gamePadAdapter.getBrake(control, lastBrakeLevel);
            if (lastBrakeLevel != brakeLevel) {
                lastBrakeLevel = brakeLevel;
            }

            int level = (brakeLevel < 0) ? brakeLevel : notchLevel;
            if (currentLevel != level) {
                currentLevel = level;
                TrainNotchController.setNotch(level);
            }

            EntityTrainBase train = this.getEntityTrainBase();

            if (train == null) {
                isPressHorn = false;
                isPressDoorR = false;
                isPressDoorL = false;
                return;
            }

            if (gamePadAdapter.isHorn(control)) {
                if (!isPressHorn) {
                    try {
                        isPressHorn = true;
                        Method m = RTMKeyHandlerClient.class.getDeclaredMethod("playSound", EntityPlayer.class, byte.class);
                        m.setAccessible(true);
                        m.invoke(RTMKeyHandlerClient.INSTANCE, Minecraft.getMinecraft().thePlayer, RTMCore.KEY_Horn);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                isPressHorn = false;
            }

            int nowDoorStateData = train.getTrainStateData(4);
            int newDoorStateData = nowDoorStateData;

            if (gamePadAdapter.isDoorR(control)) {
                if (!isPressDoorR) {
                    isPressDoorR = true;
                    if (nowDoorStateData == 0 || nowDoorStateData == 2) {
                        newDoorStateData += 1;
                    } else {
                        newDoorStateData -= 1;
                    }
                }
            } else {
                isPressDoorR = false;
            }

            if (gamePadAdapter.isDoorL(control)) {
                if (!isPressDoorL) {
                    isPressDoorL = true;
                    if (nowDoorStateData == 0 || nowDoorStateData == 1) {
                        newDoorStateData += 2;
                    } else {
                        newDoorStateData -= 2;
                    }
                }
            } else {
                isPressDoorL = false;
            }

            if (nowDoorStateData != newDoorStateData) {
                ControlTrain.setTrainState(4, (byte) newDoorStateData);
            }
        }
    }

    private EntityTrainBase getEntityTrainBase() {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (player == null) {
            return null;
        }

        Entity entity = player.ridingEntity;
        if (entity instanceof EntityTrainBase) {
            return (EntityTrainBase) entity;
        }

        return null;
    }
}
