package jp.kaiz.rtmdengoadapter;

import org.lwjgl.input.Controller;

public class DPadAsButtonGamePadAdapter extends GamePadAdapter {

    @Override
    public boolean isHorn(Controller control) {
        return control.isButtonPressed(3);
    }

    @Override
    public boolean isDoorR(Controller control) {
        return control.isButtonPressed(8);
    }

    @Override
    public boolean isDoorL(Controller control) {
        return control.isButtonPressed(9);
    }

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
