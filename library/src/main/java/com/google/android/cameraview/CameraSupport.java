package com.google.android.cameraview;

import android.annotation.TargetApi;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.content.Context;

public class CameraSupport {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public boolean supportCamera2(@NonNull final Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP || isProblematicDeviceOnCamera2()) {
            return false;
        }
        try {
            final CameraManager manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
            String[] idList = manager.getCameraIdList();
            boolean hasSupport = true;
            if (idList.length == 0) {
                hasSupport = false;
            } else {
                for (final String id : idList) {
                    if (id == null || id.trim().isEmpty()) {
                        hasSupport = false;
                        break;
                    }
                    final CameraCharacteristics characteristics = manager.getCameraCharacteristics(id);
                    //noinspection ConstantConditions
                    final int supportLevel = characteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
                    if (supportLevel == CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY) {
                        hasSupport = false;
                        break;
                    }
                }
            }
            return hasSupport;
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }
    }

    private boolean isProblematicDeviceOnCamera2() {
        if (Build.MANUFACTURER == null || Build.PRODUCT == null) {
            return true;
        }
        if ("Huawei".equals(Build.MANUFACTURER) &&
                "angler".equals(Build.PRODUCT)) {
            return true;
        } else if ("LGE".equals(Build.MANUFACTURER) &&
                "occam".equals(Build.PRODUCT)) {
            return true;
        } else if ("Amazon".equals(Build.MANUFACTURER) &&
                "full_ford".equals(Build.PRODUCT)) {
            return true;
        } else if ("Amazon".equals(Build.MANUFACTURER) &&
                "full_thebes".equals(Build.PRODUCT)) {
            return true;
        } else if ("HTC".equals(Build.MANUFACTURER) &&
                "m7_google".equals(Build.PRODUCT)) {
            return true;
        } else if ("HTC".equals(Build.MANUFACTURER) &&
                "hiaeuhl_00709".equals(Build.PRODUCT)) {
            return true;
        } else if ("Wileyfox".equals(Build.MANUFACTURER) &&
                "Swift".equals(Build.PRODUCT)) {
            return true;
        } else if ("Sony".equals(Build.MANUFACTURER) &&
                "C6603".equals(Build.PRODUCT)) {
            return true;
        } else if ("Sony".equals(Build.MANUFACTURER) &&
                "C6802".equals(Build.PRODUCT)) {
            return true;
        } else if ("samsung".equals(Build.MANUFACTURER) &&
                "zerofltexx".equals(Build.PRODUCT)) {
            return true;
        } else if ("OnePlus".equals(Build.MANUFACTURER) &&
                Build.MODEL.startsWith("ONE E100")) {
            return true;
        } else if ("LGE".equals(Build.MANUFACTURER) &&
                Build.MODEL.equals("LGUS991")) {
            return true;
        } else if ("LGE".equals(Build.MANUFACTURER) &&
                Build.MODEL.equals("LG-H815")) {
            return true;
        }
        return false;
    }
}
