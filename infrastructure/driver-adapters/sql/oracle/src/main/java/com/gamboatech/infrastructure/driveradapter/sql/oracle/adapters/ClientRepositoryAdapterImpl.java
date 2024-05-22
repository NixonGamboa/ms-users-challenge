package com.gamboatech.infrastructure.driveradapter.sql.oracle.adapters;

import com.gamboatech.application.adapters.driveradapters.sql.ClientRepositoryAdapter;
import com.gamboatech.domain.commons.BusinessException;
import com.gamboatech.domain.commons.ErrorCodes;
import com.gamboatech.domain.commons.Gender;
import com.gamboatech.domain.model.Client;
import com.gamboatech.infrastructure.driveradapter.sql.oracle.entities.ClientEntity;
import com.gamboatech.infrastructure.driveradapter.sql.oracle.repositories.ClientEntityRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ClientRepositoryAdapterImpl implements ClientRepositoryAdapter {

    private final ClientEntityRepository repository;

    public ClientRepositoryAdapterImpl(ClientEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public Client save(Client client) {
        Optional<ClientEntity> clientExist = repository.findByIdentificationNumber(client.getIdentificationNumber());
        if(clientExist.isPresent())
            throw new BusinessException(ErrorCodes.DUPLICATED, String.format("El cliente con numero de identificacion %s ya existe",client.getIdentificationNumber()));

        ClientEntity newClient = repository.save(toEntity(client));
        return toModel(newClient);
    }

    @Override
    public Client update(Client client) {

        Optional<ClientEntity> exist = repository.findById(client.getId());
        if(exist.isPresent()){
            return toModel(repository.save(toEntity(client)));
        }
        throw new BusinessException(
                ErrorCodes.NOT_FOUND,
                String.format("El cliente con id %d no existe", client.getId()));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Client findByIdentificationNumber(String identificationNumber) {
        Optional<ClientEntity> optionalClient = repository.findByIdentificationNumber(identificationNumber);
        if(optionalClient.isPresent()) return toModel(optionalClient.get());
        throw new BusinessException(
                ErrorCodes.NOT_FOUND,
                String.format("El cliente con numero de identificación %s no existe", identificationNumber));
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


