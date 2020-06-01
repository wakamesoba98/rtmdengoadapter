package jp.kaiz.rtmdengoadapter;

import org.lwjgl.input.Controller;

public class SanYingGamePadAdpter extends GamePadAdapter {

    public int getNotch(Controller control, int lastNotchLevel) {
        int notchLevel;
        int notchButton = booleanToInt(control.isButtonPressed(9))
                + booleanToInt(control.isButtonPressed(8)) * 2
                + booleanToInt(control.isButtonPressed(7)) * 4
                + 8;

        switch (notchButton) {
            case 10:
                notchLevel = 0;
                break;
            case 11:
                notchLevel = 1;
                break;
            case 12:
                notchLevel = 2;
                break;
            case 13:
                notchLevel = 3;
                break;
            case 14:
                notchLevel = 4;
                break;
            case 15:
                notchLevel = 5;
                break;
            default:
                notchLevel = lastNotchLevel;
                break;
        }

        return notchLevel;
    }

    public int getBrake(Controller control, int lastBrakeLevel) {
        int brakeLevel;
        int brakeButton = booleanToInt(control.isButtonPressed(9))
                + booleanToInt(control.isButtonPressed(8)) * 2
                + booleanToInt(control.isButtonPressed(7)) * 4
                + booleanToInt(control.isButtonPressed(6)) * 8;
        switch (brakeButton) {
            case 0:
                brakeLevel = lastBrakeLevel;
                break;
            case 1:
                brakeLevel = -8;
                break;
            case 2:
            case 3:
                brakeLevel = -7;
                break;
            case 4:
                brakeLevel = -6;
                break;
            case 5:
                brakeLevel = -5;
                break;
            case 6:
                brakeLevel = -4;
                break;
            case 7:
                brakeLevel = -3;
                break;
            case 8:
                brakeLevel = -2;
                break;
            case 9:
                brakeLevel = -1;
                break;
            default:
                brakeLevel = 0;
                break;
        }

        return brakeLevel;
    }

}
