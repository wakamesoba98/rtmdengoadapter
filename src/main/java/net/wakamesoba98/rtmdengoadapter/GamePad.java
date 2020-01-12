package net.wakamesoba98.rtmdengoadapter;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;

public class GamePad {
    private Controller control;
    private int lastNotchLevel, lastBrakeLevel;
    private int currentLevel = 0;
    private GamePadAdapter gamePadAdapter;

    public GamePad() throws LWJGLException, NoSuchFieldException {
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
        if (gamePadAdapter == null) {
            return;
        }
        KeyboardBuffer.init();
    }

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        tick();
    }

    private void tick() {
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
        if (currentLevel < level) {
            currentLevel++;
            KeyboardBuffer.press(Keyboard.KEY_S);
        } else if (currentLevel > level) {
            currentLevel--;
            KeyboardBuffer.press(Keyboard.KEY_W);
        } else {
            KeyboardBuffer.release(Keyboard.KEY_S);
            KeyboardBuffer.release(Keyboard.KEY_W);
        }
    }
}
