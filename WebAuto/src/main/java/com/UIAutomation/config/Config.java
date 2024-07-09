package com.UIAutomation.config;

import org.aeonbits.owner.ConfigCache;

public class Config {

    private Config() {
    }

    public static Configuration getConfigs() {
        return ConfigCache.getOrCreate(Configuration.class);

    }

}
