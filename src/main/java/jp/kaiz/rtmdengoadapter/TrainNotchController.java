package jp.kaiz.rtmdengoadapter;

import jp.kaiz.atsassistmod.api.ControlTrain;
import jp.ngt.rtm.entity.train.EntityTrainBase;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class TrainNotchController {
    public static void setNotch(int notch) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;

        if (player == null || !player.isRiding()) {
            return;
        }
        Entity entity = player.ridingEntity;
        if (entity == null) {
            return;
        }

        if (entity instanceof EntityTrainBase) {
            EntityTrainBase train = (EntityTrainBase) entity;
            if (train.isControlCar()) {
                ControlTrain.setControllerNotch(notch);
            }
        }
    }
}
