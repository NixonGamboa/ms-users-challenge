package com.gamboatech.infrastructure.driveradapter.sql.oracle.adapters;

import com.gamboatech.application.adapters.driveradapters.sql.ClientRepositoryAdapter;
import com.gamboatech.domain.commons.Gender;
import com.gamboatech.domain.model.Client;
import com.gamboatech.infrastructure.driveradapter.sql.oracle.entities.ClientEntity;
import com.gamboatech.infrastructure.driveradapter.sql.oracle.repositories.ClientEntityRepository;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.Optional;

@Component
public class ClientRepositoryAdapterImpl implements ClientRepositoryAdapter {

    private final ClientEntityRepository repository;

    public ClientRepositoryAdapterImpl(ClientEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public Client save(Client client) {

        try {
            ClientEntity newClient = repository.save(toEntity(client));
            return toModel(newClient);

        } catch (DataAccessResourceFailureException exception) {
            throw  exception;
        }
    }

    @Override
    public Client update(Client client) {

        Optional<ClientEntity> exist = repository.findById(client.getId());
        if(exist.isPresent()){
            return toModel(repository.save(toEntity(client)));
        }
        throw new RuntimeException("Client Not Found");
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }



    private Client toModel(ClientEntity entity){
        return (Client) new Client()
                .setPassword(entity.getPassword())
                .setStatus(entity.getStatus())
                .setClientId(entity.getClientId())
                .setId(entity.getId())
                .setName(entity.getName())
                .setGender(Gender.valueOf(entity.getGender()))
                .setAge(entity.getAge())
                .setIdentificationNumber(entity.getIdentificationNumber())
                .setAddress(entity.getAddress())
                .setPhoneNumber(entity.getPhoneNumber());
    }
    private ClientEntity toEntity(Client model){
        return new ClientEntity()
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


