package net.wakamesoba98.rtmdengoadapter;

import jp.ngt.rtm.entity.train.EntityTrainBase;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;

public class GamePad {
    private Controller control;
    private int lastNotchLevel, lastBrakeLevel;
    private GamePadAdapter gamePadAdapter;

    public GamePad() throws LWJGLException {
        Controllers.create();
        for (int i = 0; i < Controllers.getControllerCount(); i++) {
            Controller controller = Controllers.getController(i);
            String controllerName = controller.getName();
            if (controllerName.equals("ELECOM JC-PS201U series")) {
                control = controller;
                gamePadAdapter = new DPadAsButtonGamePadAdapter();
                break;
            } else if (controllerName.equals("ELECOM JC-PS101U series")) {
                control = controller;
                gamePadAdapter = new DPadAsAxisGamePadAdapter();
                break;
            }
        }
    }

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        tick();
    }

    private void tick() {
        if (control == null || gamePadAdapter == null) {
            return;
        }
        EntityTrainBase train = this.getTrain();
        if (train == null || !train.isControlCar()) {
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
        int currentNotch = train.getNotch();
        if (level != currentNotch) {
            train.syncNotch(level - currentNotch);
        }
    }

    public EntityTrainBase getTrain() {
        EntityPlayer player = Minecraft.getMinecraft().player;
        if (player != null) {
            Entity entity = player.getRidingEntity();
            if (entity instanceof EntityTrainBase) {
                return (EntityTrainBase) entity;
            }
        }
        return null;
    }
}
