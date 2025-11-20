package io.github.testwork.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.testwork.dto.NumberDto;
import io.github.testwork.exceptionhandler.ErrorCode;
import io.github.testwork.repository.NumberRepository;
import io.github.testwork.template.PostgresTestTemplate;
import io.github.testwork.utils.DataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItNumberRestControllerV1Tests extends PostgresTestTemplate {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private NumberRepository repository;

    @BeforeEach
    public void setup() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Test append number functionality")
    public void givenNumberDto_whenAppendNumber_thenSuccessResponse() throws Exception {
        //given
        NumberDto dtoToAppend = DataUtils.getNumberDto(24L);

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/numbers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoToAppend)));
        //then
        resultActions
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numbers", notNullValue()))
                .andExpect(jsonPath("$.numbers[0].number", is(dtoToAppend.number().intValue())));
    }

    @Test
    @DisplayName("Test append empty number functionality")
    public void givenNothing_whenAppendNumber_thenErrorResponse() throws Exception {
        //given

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/numbers")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_STATE_OR_ARGUMENT.toString())));
    }
}
