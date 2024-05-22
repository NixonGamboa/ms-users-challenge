package com.gamboatech.infrastructure.entrypoints.rest.dto;

import com.gamboatech.domain.commons.Gender;
import com.gamboatech.domain.model.Client;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
    private Long id;

    @NotBlank
    private String name;
    @NotNull
    private String identificationNumber;
    @NotNull
    private String address;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String password;
    @NotNull
    @Min(18)
    private Integer age;
    private String gender;
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
