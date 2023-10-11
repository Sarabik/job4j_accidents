package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.service.AccidentService;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AccidentControllerTest {

    @MockBean
    private AccidentService accidentService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void whenTestCreateAccidentGetMethod() throws Exception {
        mockMvc.perform(get("/accidents/createAccident"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accidents/createAccident"));
    }

    @Test
    @WithMockUser
    public void whenTestSaveAccidentPostMethod() throws Exception {
        Accident accident = new Accident(
                0,
                "Accident1",
                "Description1",
                "Salnas str.",
                new AccidentType(1, null),
                new HashSet<>()
        );

        mockMvc.perform(post("/accidents/saveAccident")
                    .param("name", accident.getName())
                    .param("text", accident.getText())
                    .param("address", accident.getAddress())
                    .param("type.id", String.valueOf(accident.getType().getId()))
                    .param("rIds", "1", "2")
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/index"));

        ArgumentCaptor<Accident> accidentCaptor = ArgumentCaptor.forClass(Accident.class);
        ArgumentCaptor<String[]> arrCaptor = ArgumentCaptor.forClass(String[].class);
        verify(accidentService).addAccident(accidentCaptor.capture(), arrCaptor.capture());
        assertThat(accidentCaptor.getValue()).usingRecursiveComparison().isEqualTo(accident);

    }

    @Test
    @WithMockUser
    public void whenTestEditAccidentGetMethod() throws Exception {
        Accident accident = new Accident(
                1,
                "Accident1",
                "Description1",
                "Salnas str.",
                new AccidentType(1, null),
                new HashSet<>()
        );
        when(accidentService.getAccident(1)).thenReturn(Optional.of(accident));
        mockMvc.perform(get("/accidents/editAccident")
                        .param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accidents/editAccident"));
    }

    @Test
    @WithMockUser
    public void whenTestEditAccidentGetMethodAndGetError() throws Exception {
        mockMvc.perform(get("/accidents/editAccident")
                        .param("id", "100"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("errors/404"))
                .andExpect(content().string(containsString("Accident is not found")));
    }

    @Test
    @WithMockUser
    public void whenTestEditAccidentPostMethod() throws Exception {
        when(accidentService.editAccident(any(Accident.class), any(String[].class))).thenReturn(true);
        mockMvc.perform(post("/accidents/editAccident")
                        .param("id", "1")
                        .param("name", "Accident2")
                        .param("text", "Description2")
                        .param("address", "Salnas str.")
                        .param("type.id", "1")
                        .param("rIds", "1", "2")
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/index"));

        ArgumentCaptor<Accident> accidentCaptor = ArgumentCaptor.forClass(Accident.class);
        ArgumentCaptor<String[]> arrCaptor = ArgumentCaptor.forClass(String[].class);
        verify(accidentService).editAccident(accidentCaptor.capture(), arrCaptor.capture());
        assertThat(accidentCaptor.getValue().getName()).isEqualTo("Accident2");
        assertThat(accidentCaptor.getValue().getText()).isEqualTo("Description2");
    }

    @Test
    @WithMockUser
    public void whenTestEditAccidentPostMethodAndGetError() throws Exception {
        when(accidentService.editAccident(any(Accident.class), any(String[].class))).thenReturn(false);
        mockMvc.perform(post("/accidents/editAccident")
                        .param("id", "1")
                        .param("name", "Accident2")
                        .param("text", "Description2")
                        .param("address", "Salnas str.")
                        .param("type.id", "1")
                        .param("rIds", "1", "2")
                )
                .andDo(print())
                .andExpect(view().name("errors/404"))
                .andExpect(content().string(containsString("Accident is not found")));
    }

}