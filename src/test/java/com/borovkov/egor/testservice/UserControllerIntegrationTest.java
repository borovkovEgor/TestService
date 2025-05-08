package com.borovkov.egor.testservice;

import com.borovkov.egor.testservice.dto.request.UserRequestDto;
import com.borovkov.egor.testservice.model.SubName;
import com.borovkov.egor.testservice.model.User;
import com.borovkov.egor.testservice.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("testdb")
            .withUsername("admin")
            .withPassword("12345");

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void testCreateUser() throws Exception {
        UserRequestDto request = new UserRequestDto("Egor", "Borovkov", 24, "egor@example.com");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("egor@example.com")));
    }

    @Test
    void testGetUserById() throws Exception {
        User user = new User();
        user.setFirstName("Test");
        user.setLastName("User");
        user.setAge(25);
        user.setEmail("test@example.com");
        User saved = userRepository.save(user);

        mockMvc.perform(get("/users/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("test@example.com")));
    }

    @Test
    void testUpdateUser() throws Exception {
        User user = new User();
        user.setFirstName("Old");
        user.setLastName("Name");
        user.setAge(40);
        user.setEmail("old@example.com");
        User saved = userRepository.save(user);

        UserRequestDto update = new UserRequestDto("New", "Name", 35, "new@example.com");

        mockMvc.perform(put("/users/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("new@example.com")));
    }

    @Test
    void testDeleteUser() throws Exception {
        User user = new User();
        user.setFirstName("To");
        user.setLastName("Delete");
        user.setAge(22);
        user.setEmail("delete@example.com");
        User saved = userRepository.save(user);

        mockMvc.perform(delete("/users/" + saved.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void testAddAndRemoveSubscription() throws Exception {

        User user = new User();
        user.setFirstName("Sub");
        user.setLastName("User");
        user.setAge(28);
        user.setEmail("sub@example.com");
        user = userRepository.save(user);

        mockMvc.perform(post("/users/" + user.getId() + "/subscriptions")
                        .param("subName", String.valueOf(SubName.YOUTUBE)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subscriptions", hasSize(1)))
                .andExpect(jsonPath("$.subscriptions[0]", is("YouTube Premium")));

        MvcResult result = mockMvc.perform(get("/users/" + user.getId() + "/subscriptions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].displayName", is(SubName.YOUTUBE.getDisplayName())))
                        .andReturn();

        String json = result.getResponse().getContentAsString();
        JsonNode jsonArray = new ObjectMapper().readTree(json);
        long subId = jsonArray.get(0).get("id").asLong();

        mockMvc.perform(delete("/users/" + user.getId() + "/subscriptions/" + subId))
                .andExpect(status().isOk());

        mockMvc.perform(get("/users/" + user.getId() + "/subscriptions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
