package com.cofincafe.prueba.port;

import com.cofincafe.prueba.model.Client;

import java.util.List;

public interface ClientRepository {
    List<Client> findAll();
}
