package com.cavcav.Eventify.user.model.enums;

public enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    PREFER_NOT_TO_SAY("Prefer_Not_To_Say");
    private final String value;
    private Gender(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
