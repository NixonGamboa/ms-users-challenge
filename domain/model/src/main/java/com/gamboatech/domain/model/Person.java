package com.gamboatech.domain.model;

import com.gamboatech.domain.commons.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Person {
    private Long id;
    private String name;
    private Gender gender;
    private Integer age;
    private String identificationNumber;
    private String address;
    private String phoneNumber;
}
