package com.gamboatech.infrastructure.driveradapter.sql.oracle.entities;

import com.gamboatech.domain.commons.Gender;
import com.gamboatech.domain.model.Client;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "clients")
public class ClientEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    private String gender;
    private Integer age;
    private String identificationNumber;
    private String address;
    private String phoneNumber;
    private String password;
    private Boolean status;
    private String clientId;
}
