package com.gamboatech.application.adapters.driveradapters.sql;

import com.gamboatech.domain.model.Client;

public interface ClientRepositoryAdapter {
    Client save (Client client);

    Client update (Client client);

    void delete (Long id);
}
