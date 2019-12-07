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
    private TrainController trainController;

    public GamePad() throws LWJGLException, NoSuchFieldException {
        Controllers.create();
        if (Controllers.getControllerCount() > 0) {
            control = Controllers.getController(0);
        }
        KeyboardBuffer.init();
        trainController = new TwoHandlesTrainController();
    }

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        tick();
    }

    private void tick() {
        if (control == null) {
            return;
        }
        control.poll();

        int notchLevel = trainController.getNotch(control, lastNotchLevel);
        if (lastNotchLevel != notchLevel) {
            lastNotchLevel = notchLevel;
        }

        int brakeLevel = trainController.getBrake(control, lastBrakeLevel);
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
