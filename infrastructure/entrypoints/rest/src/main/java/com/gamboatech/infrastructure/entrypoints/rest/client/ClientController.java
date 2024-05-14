package com.gamboatech.infrastructure.entrypoints.rest.client;

import com.gamboatech.domain.model.Client;
import com.gamboatech.domain.usecase.client.ClientUseCase;
import com.gamboatech.infrastructure.entrypoints.rest.dto.ClientDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClientController {
    private final ClientUseCase clientUseCase;

    public ClientController(ClientUseCase clientUseCase){
        this.clientUseCase = clientUseCase;
    }

    @PostMapping
    public ClientDto create(@RequestBody ClientDto clientDto){
        Client client = clientUseCase.create(clientDto.toModel());
        return ClientDto.modelToDto(client);
    }

    @PutMapping
    public ClientDto update(@RequestBody ClientDto clientDto){
        return ClientDto.modelToDto(clientUseCase.update(clientDto.toModel()));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        clientUseCase.delete(id);
    }

    @GetMapping("/{identificationNumber}")
    public ClientDto getByIdentificationNumber(@PathVariable("identificationNumber") String identificationNumber) {
        return ClientDto.modelToDto(clientUseCase.getByIdentificationNumber(identificationNumber));
    }
}
