package com.cofincafe.prueba.service;

import com.cofincafe.prueba.model.Client;
import com.cofincafe.prueba.port.ClientRepository;

import java.util.Comparator;
import java.util.List;

public class ClientService {

    private static final Comparator<Client> BY_BALANCE_DESC_NAME_ID =
            Comparator.comparingDouble(Client::balance).reversed()
                    .thenComparing(Client::name)
                    .thenComparingLong(Client::id);

    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    /** 1) Clientes con balance negativo */
    public List<Client> findClientsWithNegativeBalance() {
        return repository.findAll().stream()
                .filter(c -> c.balance() < 0)
                .toList();
    }

    /** 2) Top 3 por mayor balance (desempates deterministas por nombre y luego id) */
    public List<Client> findTop3ByBalance() {
        return repository.findAll().stream()
                .sorted(BY_BALANCE_DESC_NAME_ID)
                .limit(3)
                .toList();
    }
}
