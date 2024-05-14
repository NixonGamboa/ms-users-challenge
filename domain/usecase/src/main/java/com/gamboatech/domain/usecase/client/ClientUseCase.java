package com.gamboatech.domain.usecase.client;

import com.gamboatech.domain.model.Client;

public interface ClientUseCase {

    Client create (Client client);

    Client update (Client client);

    void delete (Long id);

    Client getByIdentificationNumber(String identificationNumber);
}
