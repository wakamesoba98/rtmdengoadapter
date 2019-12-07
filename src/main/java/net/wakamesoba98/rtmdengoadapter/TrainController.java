package net.wakamesoba98.rtmdengoadapter;

import org.lwjgl.input.Controller;

public interface TrainController {
    int getNotch(Controller control, int lastNotchLevel);
    int getBrake(Controller control, int lastBrakeLevel);
}
