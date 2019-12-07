package net.wakamesoba98.rtmdengoadapter;

import org.lwjgl.input.Controller;

public class TwoHandlesTrainController implements TrainController {
    private int booleanToInt(boolean b) {
        return b ? 1 : 0;
    }

    public int getNotch(Controller control, int lastNotchLevel) {
        int notchLevel;
        int notchAxis = (int) control.getXAxisValue();
        boolean notchButton = control.isButtonPressed(0);
        if (notchAxis == -1) {
            if (notchButton) {
                notchLevel = 3;
            } else {
                if (lastNotchLevel == 3
                        || lastNotchLevel == 4
                        || lastNotchLevel == 5) {
                    notchLevel = 4;
                } else {
                    notchLevel = 0;
                }
            }
        } else if (notchAxis == 1) {
            if (notchButton) {
                notchLevel = 1;
            } else {
                notchLevel = 2;
            }
        } else if (notchAxis == 0) {
            if (notchButton) {
                notchLevel = 5;
            } else {
                notchLevel = lastNotchLevel;
            }
        } else {
            notchLevel = lastNotchLevel;
        }
        return notchLevel;
    }

    public int getBrake(Controller control, int lastBrakeLevel) {
        int brakeLevel;
        int brakeButton = booleanToInt(control.isButtonPressed(6))
                        + booleanToInt(control.isButtonPressed(4)) * 2
                        + booleanToInt(control.isButtonPressed(7)) * 4
                        + booleanToInt(control.isButtonPressed(5)) * 8;
        switch (brakeButton) {
            case 15:
                brakeLevel = lastBrakeLevel;
                break;
            case 14:
                brakeLevel = 0;
                break;
            case 13:
                brakeLevel = -1;
                break;
            case 12:
                brakeLevel = -2;
                break;
            case 11:
                brakeLevel = -3;
                break;
            case 10:
                brakeLevel = -4;
                break;
            case 9:
                brakeLevel = -5;
                break;
            case 8:
                brakeLevel = -6;
                break;
            case 7:
                brakeLevel = -7;
                break;
            case 6:
                brakeLevel = -8;
                break;
            default:
                brakeLevel = -8;
                break;
        }
        return brakeLevel;
    }
}
