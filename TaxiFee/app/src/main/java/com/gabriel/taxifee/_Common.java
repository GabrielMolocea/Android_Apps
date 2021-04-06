package com.gabriel.taxifee;

import com.gabriel.taxifee.model.DriverInfoModel;

public class _common {
    public static final String DRIVER_INFO_REFERENCE = "DriverInfo";
    public static final String DRIVER_LOCATION_REFERENCES = "DriverLocation";

    public static DriverInfoModel currentUser;

    public static String buildWelcomeMessage() {
        if (_common.currentUser != null) {
            return new StringBuilder("Welcome ")
                    .append(_common.currentUser.getFirstName())
                    .append(" ")
                    .append(_common.currentUser.getLastName()).toString();
        } else {
            return "";
        }
    }
}
