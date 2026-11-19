package com.boosterltd.iptbank.helper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter @RequiredArgsConstructor
public enum ResponseStatusExceptionMessage {
    UNAUTHORIZED("You haven't given valid evidence to us about having the permission to access this service."),
    NOT_FOUND("There is no %1$s in our database having your given %2$s (%3$s).");

    private final String message;
}
