package jp.kaiz.rtmdengoadapter;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;

public class GamePad {
    private Controller control;
    private int lastNotchLevel, lastBrakeLevel;
    private int currentLevel = 0;
    private GamePadAdapter gamePadAdapter;

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
                    gamePadAdapter = new SanYingGamePadAdpter();
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
        }
    }
}
