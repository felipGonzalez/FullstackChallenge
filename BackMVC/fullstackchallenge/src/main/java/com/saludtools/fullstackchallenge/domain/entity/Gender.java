package com.saludtools.fullstackchallenge.domain.entity;

public enum Gender {

    MALE("Masculino","MALE"), FEMALE("Femenino","FEMALE");

    private String name;
    private String value;

    Gender(String name,String value) {

        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public static Gender findByValue(String value) {
        for (Gender gender : values()) {
            if (gender.getValue().equalsIgnoreCase(value)) {
                return gender;
            }
        }
        return null;
    }
}
