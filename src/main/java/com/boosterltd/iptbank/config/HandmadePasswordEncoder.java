package com.boosterltd.iptbank.config;

import org.jspecify.annotations.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.boosterltd.lib.exception.HandmadeIllegalArgException;
import com.boosterltd.lib.security.Security;

public class HandmadePasswordEncoder implements PasswordEncoder {
    @Override
    public @Nullable String encode(@Nullable CharSequence rawPassword) {
            // Validate the arguments.
        if (rawPassword == null) {
            throw new HandmadeIllegalArgException(
                "CharSequence", "rawPassword", rawPassword,
                "You have passed a null argument for this parameter which is not valid."
            );
        }

        return Security.getHashedVersion(rawPassword.toString());
    }

    @Override
    public boolean matches(@Nullable CharSequence rawPassword, @Nullable String encodedPassword) {
            // Validate the arguments.
        if (rawPassword == null) {
            throw new HandmadeIllegalArgException(
                "CharSequence", "rawPassword", rawPassword,
                "You have passed a null argument for this parameter which is not valid."
            );
        }
        if (encodedPassword == null) {
            throw new HandmadeIllegalArgException(
                "String", "encodedPassword", encodedPassword,
                "You have passed a null argument for this parameter which is not valid."
            );
        }

        return Security.isInputPasswordCorrect(rawPassword.toString(), encodedPassword);
    }
}
