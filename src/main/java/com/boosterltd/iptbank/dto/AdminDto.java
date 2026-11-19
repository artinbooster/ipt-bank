package com.boosterltd.iptbank.dto;

import lombok.Data;

@Data
public class AdminDto {
    private final Long id;
    private final String hashedPassword;
    private final String plainPassword;
    private final String username;
}
