package net.wakamesoba98.rtmdengoadapter;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;

@Mod(modid = RTMDengoAdapter.MODID, name = RTMDengoAdapter.NAME, version = RTMDengoAdapter.VERSION)
public class RTMDengoAdapter {
    public static final String MODID = "rtmdengoadapter";
    public static final String NAME = "RTM Dengo Adapter";
    public static final String VERSION = "0.1";

    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        try {
            MinecraftForge.EVENT_BUS.register(new GamePad());
        } catch (LWJGLException e) {
            logger.log(Level.WARN, "Failed to initialize Controllers");
        } catch (NoSuchFieldException e) {
            logger.log(Level.WARN, "Failed to initialize KeyboardBuffer");
        }
    }
}
