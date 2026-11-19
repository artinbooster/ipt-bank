package com.boosterltd.iptbank.dto;

import lombok.Data;

@Data
public class ClientDto {
    @Data
    public static class Name {
        private final String givenName;
        private final String surname;
        private final String surnameSuffix;
        private final String fullName;
    }
   
    private final Long id;
    private final String emailAddress;
    private final String gender;
    private final Name name;
    private final String hashedPassword;
    private final String plainPassword;
}
