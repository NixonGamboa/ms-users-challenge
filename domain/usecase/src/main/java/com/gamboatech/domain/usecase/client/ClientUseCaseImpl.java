package com.gamboatech.domain.usecase.client;

import com.gamboatech.application.adapters.driveradapters.sql.ClientRepositoryAdapter;
import com.gamboatech.domain.model.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ClientUseCaseImpl implements ClientUseCase{

    private final ClientRepositoryAdapter repositoryAdapter;

    public ClientUseCaseImpl(ClientRepositoryAdapter repositoryAdapter) {
        this.repositoryAdapter = repositoryAdapter;
    }

    @Override
    public Client create(Client client) {
        log.info("new client received: "+ client.getName());
        return repositoryAdapter.save(client);
    }

    @Override
    public Client update(Client client) {
        return repositoryAdapter.update(client);
    }

    @Override
    public void delete(Long id) {
        repositoryAdapter.delete(id);
    }
}
