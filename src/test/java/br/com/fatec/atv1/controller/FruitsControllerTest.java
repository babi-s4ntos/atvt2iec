package br.com.fatec.atv1.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FruitsController.class)
public class FruitsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Teste GET ALL
    @Test
    public void testGetAllFruits() throws Exception {
        mockMvc.perform(get("/fruits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.1.name").value("Lichia"))
                .andExpect(jsonPath("$.2.name").value("Uva"))
                .andExpect(jsonPath("$.3.name").value("Manga"));
    }

    // Teste GET BY ID - Sucesso
    @Test
    public void testGetFruitById_Success() throws Exception {
        mockMvc.perform(get("/fruits/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Lichia"))
                .andExpect(jsonPath("$.origin").value("China"))
                .andExpect(jsonPath("$.season").value("Verão"))
                .andExpect(jsonPath("$.calories").value("66"));
    }

    // Teste GET BY ID - Não encontrado
    @Test
    public void testGetFruitById_NotFound() throws Exception {
        mockMvc.perform(get("/fruits/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Fruta não encontrada"))
                .andExpect(jsonPath("$.id").value("999"));
    }

    // Teste POST - Adicionar nova fruta
    @Test
    public void testAddFruit() throws Exception {
        String fruitJson = """
            {
                "id": "4",
                "name": "Morango",
                "origin": "Europa",
                "season": "Primavera",
                "calories": "32"
            }
        """;

        mockMvc.perform(post("/fruits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(fruitJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.name").value("Morango"))
                .andExpect(jsonPath("$.origin").value("Europa"))
                .andExpect(jsonPath("$.season").value("Primavera"))
                .andExpect(jsonPath("$.calories").value("32"))
                .andExpect(jsonPath("$.message").value("Fruta adicionada com sucesso"));
    }
}