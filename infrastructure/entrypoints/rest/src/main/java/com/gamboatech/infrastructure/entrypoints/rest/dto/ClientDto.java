package com.gamboatech.infrastructure.entrypoints.rest.dto;

import ch.qos.logback.core.model.Model;
import com.gamboatech.domain.commons.Gender;
import com.gamboatech.domain.model.Client;
import com.gamboatech.domain.model.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
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

    public Client toModel(){
        return (Client) new Client()
                .setPassword(this.password)
                .setStatus(this.status)
                .setClientId(this.clientId)
                .setId(this.id)
                .setName(this.name)
                .setGender(Gender.valueOf(this.gender))
                .setAge(this.age)
                .setIdentificationNumber(this.identificationNumber)
                .setAddress(this.address)
                .setPhoneNumber(this.phoneNumber);
    }
    public static ClientDto modelToDto(Client model){
        return new ClientDto()
                .setPassword(model.getPassword())
                .setStatus(model.getStatus())
                .setClientId(model.getClientId())
                .setId(model.getId())
                .setName(model.getName())
                .setGender(model.getGender().toString())
                .setAge(model.getAge())
                .setIdentificationNumber(model.getIdentificationNumber())
                .setAddress(model.getAddress())
                .setPhoneNumber(model.getPhoneNumber());
    }
}
