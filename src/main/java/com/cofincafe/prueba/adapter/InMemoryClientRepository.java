package com.cofincafe.prueba.adapter;

import com.cofincafe.prueba.model.Client;
import com.cofincafe.prueba.port.ClientRepository;

import java.util.List;

public class InMemoryClientRepository implements ClientRepository {

    private final List<Client> seed;

    public InMemoryClientRepository(List<Client> seed) {
        this.seed = List.copyOf(seed);
    }

    @Override
    public List<Client> findAll() {
        return List.copyOf(seed);
    }
}
