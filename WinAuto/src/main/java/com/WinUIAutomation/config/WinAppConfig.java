package com.WinUIAutomation.config;

import org.aeonbits.owner.ConfigCache;

public class WinAppConfig {

    private WinAppConfig() {
    }

    public static WinAppConfiguration getConfigs() {
        return ConfigCache.getOrCreate(WinAppConfiguration.class);

    }

}
