package com.example.luna.utils;

import android.os.SystemProperties;

public class PropertiesUtil {

    /**
     * Either a changelist number, or a label like "M4-rc20".
     */
    public static final String ID = getString("ro.build.id");

    /**
     * A build ID string meant for displaying to the user
     */
    public static final String DISPLAY = getString("ro.build.display.id");

    /**
     * The name of the overall product.
     */
    public static final String PRODUCT = getString("ro.product.name");
    public static final String PRODUCT_S = "ro.product.name";

    /**
     * The name of the industrial design.
     */
    public static final String DEVICE = getString("ro.product.device");

    /**
     * The name of the underlying board, like "goldfish".
     */
    public static final String BOARD = getString("ro.product.board");

    /**
     * The manufacturer of the product/hardware.
     */
    public static final String MANUFACTURER = getString("ro.product.manufacturer");

    /**
     * The consumer-visible brand with which the product/hardware will be associated, if any.
     */
    public static final String BRAND = getString("ro.product.brand");

    /**
     * The end-user-visible name for the end product.
     */
    public static final String MODEL = getString("ro.product.model");
    public static final String TIME_ZONE = getString("persist.sys.timezone");
    /**
     * The system bootloader version number.
     */
    public static final String BOOTLOADER = getString("ro.bootloader");

    public static String getString(String name) {
        return SystemProperties.get(name, "unknown");
    }
}
