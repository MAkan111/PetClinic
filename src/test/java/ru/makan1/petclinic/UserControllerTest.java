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
import ru.makan1.petclinic.model.UserDto;
import ru.makan1.petclinic.service.UserService;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldSuccessCreateUser() throws Exception {
        var user = new UserDto(
                null, "alex", "example@mail.com", 25, List.of()
        );

        String userDtoJson = new ObjectMapper().writeValueAsString(user);

        String createUserJson = mockMvc.perform(post("/api/v1/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userDtoJson)
        ).andExpect(status().is(201))
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto userDtoResponse = objectMapper.readValue(createUserJson, UserDto.class);

        Assertions.assertNotNull(userDtoResponse);
        Assertions.assertNotNull(userDtoResponse.getId());
    }

    @Test
    void shouldReturnNotFoundWhenUserNotPresent() throws Exception {
        mockMvc.perform(get("/api/v1/user/{id}", Integer.MAX_VALUE))
                .andExpect(status().is(404));
    }

    @Test
    void shouldReturnValidExceptionWhenUserDtoIsInvalid() throws Exception {
        var user = new UserDto(
                null, "", "example@mail.com", 25, List.of()
        );

        Set<ConstraintViolation<UserDto>> violations = validator.validate(user);

        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertEquals(1, violations.size());
    }
}
