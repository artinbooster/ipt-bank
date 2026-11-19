package com.boosterltd.iptbank.helper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter @RequiredArgsConstructor
public enum ResponseEntityMessage {
    SUCCESSFUL_CREATE("The %1$s got created successfully with this %2$s (%3$s)."),
    SUCCESSFUL_UPDATE("The %1$s having your given %2$s (%3$s) got updated successfully."),
    SUCCESSFUL_DELETE("The %1$s having your given %2$s (%3$s) got deleted successfully.");

    private final String message;
}
