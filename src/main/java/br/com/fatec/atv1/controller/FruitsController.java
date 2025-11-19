package br.com.fatec.atv1.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/fruits", produces = MediaType.APPLICATION_JSON_VALUE)
public class FruitsController {

    private final Map<Integer, Map<String, String>> fruits = new HashMap<>(Map.of(
        1, Map.of("name", "Lichia", "origin", "China", "season", "Verão", "calories", "66"),
        2, Map.of("name", "Uva", "origin", "Mediterrâneo", "season", "Verão/Outono", "calories", "69"),
        3, Map.of("name", "Manga", "origin", "Índia", "season", "Verão", "calories", "60")
    ));

    // GET ALL
    @GetMapping
    public ResponseEntity<Map<Integer, Map<String, String>>> getFruits() {
        return ResponseEntity.ok(fruits);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, String>> getFruitById(@PathVariable Integer id) {

        Map<String, String> fruit = fruits.get(id);

        if (fruit == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                    "message", "Fruta não encontrada",
                    "id", id.toString()
                ));
        }

        return ResponseEntity.ok(
            Map.of(
                "id", id.toString(),
                "name", fruit.get("name"),
                "origin", fruit.get("origin"),
                "season", fruit.get("season"),
                "calories", fruit.get("calories")
            )
        );
    }

    // POST - ADD FRUIT
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> addFruit(
            @RequestBody Map<String, String> requestFruit) {

        Integer cod = Integer.parseInt(requestFruit.get("id"));
        String name = requestFruit.get("name");
        String origin = requestFruit.get("origin");
        String season = requestFruit.get("season");
        String calories = requestFruit.get("calories");

        fruits.put(cod, Map.of(
            "name", name,
            "origin", origin,
            "season", season,
            "calories", calories
        ));

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(Map.of(
                "id", cod,
                "name", name,
                "origin", origin,
                "season", season,
                "calories", calories,
                "message", "Fruta adicionada com sucesso"
            ));
    }
}