package com.cofincafe.prueba;

import com.cofincafe.prueba.adapter.InMemoryClientRepository;
import com.cofincafe.prueba.model.Client;
import com.cofincafe.prueba.service.ClientService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientServiceTest {

    @Test
    void negativeBalance_ok() {
        var repo = new InMemoryClientRepository(List.of(
                new Client(1,"A",-1),
                new Client(2,"B", 0),
                new Client(3,"C", 2),
                new Client(4,"D",-0.01)
        ));
        var svc = new ClientService(repo);
        var out = svc.findClientsWithNegativeBalance();

        assertEquals(2, out.size());
        assertTrue(out.stream().allMatch(c -> c.balance() < 0));
    }

    @Test
    void top3_deterministic_ties_ok() {
        var repo = new InMemoryClientRepository(List.of(
                new Client(1,"Juan",   5),
                new Client(2,"Camilo",   9),
                new Client(3,"Maria Jose",  1),
                new Client(4,"Sofia",  9),
                new Client(5,"Pablo",  7),
                new Client(6,"Ana",   9)
        ));
        var svc = new ClientService(repo);
        var out = svc.findTop3ByBalance();

        assertEquals(3, out.size());
        assertEquals(9.0, out.get(0).balance());
        assertEquals(9.0, out.get(1).balance());
        assertEquals(9.0, out.get(2).balance());


        assertEquals(2, out.get(0).id());
        assertEquals(6, out.get(1).id());
        assertEquals(4, out.get(2).id());
    }
}
