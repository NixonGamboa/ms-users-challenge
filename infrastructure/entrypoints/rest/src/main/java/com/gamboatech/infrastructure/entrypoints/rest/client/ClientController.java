package com.gamboatech.infrastructure.entrypoints.rest.client;

import com.gamboatech.domain.model.Client;
import com.gamboatech.domain.usecase.client.ClientUseCase;
import com.gamboatech.infrastructure.entrypoints.rest.dto.ClientDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClientController {
    private final ClientUseCase clientUseCase;

    public ClientController(ClientUseCase clientUseCase){
        this.clientUseCase = clientUseCase;
    }

    @PostMapping
    public ResponseEntity<ClientDto> create(@RequestBody ClientDto clientDto){
        Client client = clientUseCase.create(clientDto.toModel());
        return ResponseEntity.ok(ClientDto.modelToDto(client));
    }

    @PutMapping
    public ResponseEntity<ClientDto> update(@RequestBody ClientDto clientDto){
        ClientDto updatedClient = ClientDto.modelToDto(clientUseCase.update(clientDto.toModel()));
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        clientUseCase.delete(id);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{identificationNumber}")
    public ResponseEntity<ClientDto> getByIdentificationNumber(@PathVariable("identificationNumber") String identificationNumber) {
        ClientDto clientDto = ClientDto.modelToDto(
                clientUseCase.getByIdentificationNumber(identificationNumber));
        return ResponseEntity.ok(clientDto);
    }
}
