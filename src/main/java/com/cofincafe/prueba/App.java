package com.cofincafe.prueba;

import com.cofincafe.prueba.adapter.ClientJson;
import com.cofincafe.prueba.adapter.InMemoryClientRepository;
import com.cofincafe.prueba.model.Client;
import com.cofincafe.prueba.service.ClientService;

import java.util.List;

public class App {
    public static void main(String[] args) {
        var seed = List.of(
                new Client(1, "Juan",       1200),
                new Client(2, "Camilo",      -50),
                new Client(3, "Maria Jose", 9800),
                new Client(4, "Sofia",       300),
                new Client(5, "Pablo",      -10),
                new Client(6, "Ana",        9800)
        );

        var repo = new InMemoryClientRepository(seed);
        var service = new ClientService(repo);
        var json = new ClientJson();

        var negatives = service.findClientsWithNegativeBalance();
        var top3 = service.findTop3ByBalance();

        System.out.println("Negativos: " + json.toJson(negatives));
        System.out.println("Top 3:     " + json.toJson(top3));
    }
}
