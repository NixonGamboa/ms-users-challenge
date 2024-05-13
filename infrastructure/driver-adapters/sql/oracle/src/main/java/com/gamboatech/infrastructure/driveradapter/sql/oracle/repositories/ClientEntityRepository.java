package com.gamboatech.infrastructure.driveradapter.sql.oracle.repositories;

import com.gamboatech.infrastructure.driveradapter.sql.oracle.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientEntityRepository extends JpaRepository<ClientEntity,Long> {

}
