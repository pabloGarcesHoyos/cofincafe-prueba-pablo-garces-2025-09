package com.cofincafe.prueba;

import com.cofincafe.prueba.adapter.ClientJson;
import com.cofincafe.prueba.model.Client;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientJsonTest {
    @Test
    void toJson_ok() {
        var json = new ClientJson();
        var s = json.toJson(List.of(new Client(1,"A", 10)));
        assertTrue(s.contains("\"id\":1"));
        assertTrue(s.contains("\"name\":\"A\""));
        assertTrue(s.contains("\"balance\":10.0"));
    }
}
