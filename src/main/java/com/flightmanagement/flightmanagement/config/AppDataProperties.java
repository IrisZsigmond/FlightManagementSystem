package com.flightmanagement.flightmanagement.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/// Application data properties (data directory, auto-seed).
/// Mapped from application.properties with prefix "fms".
/// Only used for the InFileRepository implementations.
///
@Component
@ConfigurationProperties(prefix = "fms")
public class AppDataProperties {
    /**
     * Writable runtime data directory (./data).
     */
    private String dataDir = "./data";

    /**
     * If true, missing runtime files will be auto-created from classpath:/data/*.json.
     * For dev/test: true. For prod: false (fail fast).
     */
    private boolean autoSeed = true;

    public String getDataDir() { return dataDir; }
    public void setDataDir(String dataDir) { this.dataDir = dataDir; }

    public boolean isAutoSeed() { return autoSeed; }
    public void setAutoSeed(boolean autoSeed) { this.autoSeed = autoSeed; }
}
