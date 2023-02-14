package com.saludtools.fullstackchallengehexagonalarchitecture.infrastracture.repository;

import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Gender;

public enum GenderEnumEntity {

    MALE("Masculino","MALE"), FEMALE("Femenino","FEMALE");

    private String name;
    private String value;

    GenderEnumEntity(String name, String value) {

        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public static GenderEnumEntity findByValue(String value) {
        for (GenderEnumEntity gender : values()) {
            if (gender.getValue().equalsIgnoreCase(value)) {
                return gender;
            }
        }
        return null;
    }

    public static GenderEnumEntity toGenderEntity(String value){
        return GenderEnumEntity.findByValue(value);
    }

    public static Gender toGender(GenderEnumEntity value){
        if(value == null) {
            return null;
        }
       return Gender.findByValue(value.toString());
    }


}
