package com.gamboatech.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Client extends Person{

    private String password;
    private Boolean status;
    private String clientId;
}
