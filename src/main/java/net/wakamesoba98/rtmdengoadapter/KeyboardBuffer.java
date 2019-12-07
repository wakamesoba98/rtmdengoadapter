package net.wakamesoba98.rtmdengoadapter;

import org.apache.logging.log4j.Level;
import org.lwjgl.input.Keyboard;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

public class KeyboardBuffer {
    private static Field keyDownBufferField, readBufferField;
    private static Byte[] keyState;
    private static boolean initialized = false;

    private KeyboardBuffer() {
    }

    public static void init() throws NoSuchFieldException {
        if (initialized) {
            return;
        }
        readBufferField = Keyboard.class.getDeclaredField("readBuffer");
        keyDownBufferField = Keyboard.class.getDeclaredField("keyDownBuffer");
        readBufferField.setAccessible(true);
        keyDownBufferField.setAccessible(true);
        keyState = new Byte[Keyboard.KEYBOARD_SIZE];
        for (int i = 0; i < Keyboard.KEYBOARD_SIZE; i++) {
            keyState[i] = 0;
        }
        initialized = true;
    }

    private static boolean isValidKey(int keycode) {
        return keycode >= 0 && keycode <= Keyboard.KEYBOARD_SIZE;
    }

    private static void writeNext(int keycode, byte state) {
        if (readBufferField == null || keyDownBufferField == null) {
            return;
        }
        try {
            // https://github.com/LWJGL/lwjgl/blob/master/src/java/org/lwjgl/input/Keyboard.java#L510
            ByteBuffer readBuffer = (ByteBuffer) readBufferField.get(null);
            readBuffer.compact();
            readBuffer.putInt(keycode);
            readBuffer.put(state);
            readBuffer.putInt(keycode);
            readBuffer.putLong(500);
            readBuffer.put((byte) 0);
            readBuffer.flip();

            ByteBuffer keyDownBuffer = (ByteBuffer) keyDownBufferField.get(null);
            keyDownBuffer.put(keycode, state);

            keyState[keycode] = state;
        } catch (IllegalAccessException e) {
            RTMDengoAdapter.logger.log(Level.ERROR, e);
        }
    }

    public static void press(int keycode) {
        if (initialized && isValidKey(keycode)) {
            writeNext(keycode, (byte) 1);
        }
    }

    public static void release(int keycode) {
        if (initialized && isValidKey(keycode) && keyState[keycode] == 1) {
            writeNext(keycode, (byte) 0);
        }
    }
}
