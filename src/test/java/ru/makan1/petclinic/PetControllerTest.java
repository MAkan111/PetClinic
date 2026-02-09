package ru.makan1.petclinic;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.makan1.petclinic.model.PetDto;
import ru.makan1.petclinic.model.UserDto;
import ru.makan1.petclinic.service.PetService;
import ru.makan1.petclinic.service.UserService;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PetService petService;

    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldReturnNotFoundUser() throws Exception {
        var pet = new PetDto(
                null, "barsik", null
        );

        String userDtoJson = new ObjectMapper().writeValueAsString(pet);

        String createPetJson = mockMvc.perform(post("/api/v1/pets/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson)
                ).andExpect(status().is(404))
                .andReturn()
                .getResponse()
                .getContentAsString();

        PetDto petDtoResponse = objectMapper.readValue(createPetJson, PetDto.class);

        Assertions.assertNotNull(petDtoResponse);
    }

    @Test
    void shouldReturnNotFoundWhenPetNotPresent() throws Exception {
        mockMvc.perform(get("/api/v1/pets/{id}", Integer.MAX_VALUE))
                .andExpect(status().is(404));
    }

    @Test
    void shouldReturnValidExceptionWhenUserDtoIsInvalid() throws Exception {
        var pet = new PetDto(
                null, "", null
        );

        Set<ConstraintViolation<PetDto>> violations = validator.validate(pet);

        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertEquals(1, violations.size());
    }
}
