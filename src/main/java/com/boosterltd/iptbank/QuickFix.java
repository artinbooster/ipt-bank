package com.boosterltd.iptbank;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class QuickFix implements CommandLineRunner {
    @Override
    public void run(String... args) {
        System.out.println("QuickFix's run method runned succesfully.");
    }
}
