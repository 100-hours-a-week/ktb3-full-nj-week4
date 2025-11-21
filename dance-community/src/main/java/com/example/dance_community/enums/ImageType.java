package com.example.dance_community.enums;

public enum ImageType {

    PROFILE("users", true, "/images/default-user.png"),
    CLUB("clubs", true, "/images/default-club.png"),
    POST("posts", false, null),
    EVENT("events", false, null);

    private final String directory;
    private final boolean allowDefault;
    private final String defaultImageUrl;

    ImageType(String directory, boolean allowDefault, String defaultImageUrl) {
        this.directory = directory;
        this.allowDefault = allowDefault;
        this.defaultImageUrl = defaultImageUrl;
    }

    public String getDirectory() {
        return directory;
    }

    public boolean allowDefault() {
        return allowDefault;
    }

    public String defaultImageUrl() {
        return defaultImageUrl;
    }

    public String getTypeName() {
        return name().toLowerCase();
    }
}