package net.wakamesoba98.rtmdengoadapter;

import org.lwjgl.input.Controller;

public class DPadAsButtonGamePadAdapter extends GamePadAdapter {
    public int getNotch(Controller control, int lastNotchLevel) {
        int notchLevel;
        int brakeButton = booleanToInt(control.isButtonPressed(0))
                + booleanToInt(control.isButtonPressed(15)) * 2
                + booleanToInt(control.isButtonPressed(13)) * 4;
        switch (brakeButton) {
            case 6:
                notchLevel = 0;
                break;
            case 5:
                notchLevel = 1;
                break;
            case 4:
                notchLevel = 2;
                break;
            case 3:
                notchLevel = 3;
                break;
            case 2:
                notchLevel = 4;
                break;
            case 1:
                notchLevel = 5;
                break;
            default:
                notchLevel = lastNotchLevel;
                break;
        }
        return notchLevel;
    }
}
