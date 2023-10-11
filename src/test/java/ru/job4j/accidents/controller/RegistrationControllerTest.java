package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.service.UserDataService;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDataService userService;

    @Test
    @WithMockUser
    public void whenTestUserRegistrationGetMethod() throws Exception {
        mockMvc.perform(get("/reg"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("reg"));
    }

    @Test
    public void whenTestUserRegistrationPostMethod() throws Exception {
        when(userService.addUser(any(User.class))).thenReturn(Optional.of(new User()));
        mockMvc.perform(post("/reg")
                        .param("username", "username111")
                        .param("password", "password111"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"));
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).addUser(userArgumentCaptor.capture());
        assertThat(userArgumentCaptor.getValue().getUsername()).isEqualTo("username111");
        assertThat(BCrypt.checkpw("password111", userArgumentCaptor.getValue().getPassword())).isTrue();
    }

    @Test
    public void whenTestUserRegistrationPostMethodAndGetError() throws Exception {
        when(userService.addUser(any(User.class))).thenReturn(Optional.empty());
        mockMvc.perform(post("/reg")
                        .param("username", "username111")
                        .param("password", "password111"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("reg"));
    }
}