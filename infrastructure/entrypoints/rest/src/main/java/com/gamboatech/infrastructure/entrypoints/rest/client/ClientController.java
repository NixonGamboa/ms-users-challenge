package com.gamboatech.infrastructure.entrypoints.rest.client;

import com.gamboatech.domain.model.Client;
import com.gamboatech.domain.usecase.client.ClientUseCase;
import com.gamboatech.infrastructure.entrypoints.rest.dto.ClientDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
@Validated
public class ClientController {
    private final ClientUseCase clientUseCase;

    public ClientController(ClientUseCase clientUseCase){
        this.clientUseCase = clientUseCase;
    }

    @PostMapping
    public ResponseEntity<ClientDto> create(@Valid @RequestBody ClientDto clientDto){
        Client client = clientUseCase.create(clientDto.toModel());
        return ResponseEntity.ok(ClientDto.modelToDto(client));
    }

    @PutMapping
    public ResponseEntity<ClientDto> update(@Valid @RequestBody ClientDto clientDto){
        ClientDto updatedClient = ClientDto.modelToDto(clientUseCase.update(clientDto.toModel()));
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Positive @PathVariable("id") Long id){
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
