package edu.eci.dosw.techcup.integration;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void loginShouldReturnJwtToken() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"admin@escuelaing.edu.co\",\"password\":\"admin123\"}")
                .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    public void unauthenticatedUsersCannotAccessUsersEndpoint() throws Exception {
        mockMvc.perform(get("/api/users"))
            .andExpect(status().isForbidden());
    }

    @Test
    public void onlyAdminCanAccessUserManagement() throws Exception {
        String adminToken = obtainToken("admin@escuelaing.edu.co", "admin123");
        mockMvc.perform(get("/api/users")
                .header("Authorization", "Bearer " + adminToken))
            .andExpect(status().isOk());

        String playerToken = obtainToken("jugador@gmail.com", "jugador123");
        mockMvc.perform(get("/api/users")
                .header("Authorization", "Bearer " + playerToken))
            .andExpect(status().isForbidden());
    }

    @Test
    public void onlyAdminOrOrganizerCanCreateTournaments() throws Exception {
        String adminToken = obtainToken("admin@escuelaing.edu.co", "admin123");
        mockMvc.perform(post("/api/tournaments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Champions Cup\",\"initialDate\":\"2026-05-01\",\"finalDate\":\"2026-05-10\",\"teamsNumber\":8,\"inscriptionCost\":150}")
                .header("Authorization", "Bearer " + adminToken)
                .with(csrf()))
            .andExpect(status().isCreated());

        String playerToken = obtainToken("jugador@gmail.com", "jugador123");
        mockMvc.perform(post("/api/tournaments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Blocked Cup\",\"initialDate\":\"2026-05-01\",\"finalDate\":\"2026-05-10\",\"teamsNumber\":4,\"inscriptionCost\":100}")
                .header("Authorization", "Bearer " + playerToken)
                .with(csrf()))
            .andExpect(status().isForbidden());
    }

    @Test
    public void securityHeadersArePresent() throws Exception {
        mockMvc.perform(get("/api/auth/csrf"))
            .andExpect(status().isOk())
            .andExpect(header().string("X-Frame-Options", "SAMEORIGIN"))
            .andExpect(header().string("Content-Security-Policy", containsString("script-src 'self'")))
            .andExpect(header().exists("X-XSS-Protection"));
    }

    private String obtainToken(String email, String password) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}")
                .with(csrf()))
            .andExpect(status().isOk())
            .andReturn();

        return objectMapper.readTree(result.getResponse().getContentAsString())
                .get("token").asText();
    }
}
