package io.github.testwork.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.testwork.config.ExceptionHandlerProperties;
import io.github.testwork.dto.NumberDto;
import io.github.testwork.entity.NumberEntity;
import io.github.testwork.exceptionhandler.ErrorCode;
import io.github.testwork.service.NumberService;
import io.github.testwork.utils.DataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = NumberRestControllerV1.class)
public class NumberRestControllerV1Tests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private ExceptionHandlerProperties exceptionHandlerProperties;
    @MockitoBean
    private NumberService service;

    @Test
    @DisplayName("Test append number functionality")
    public void givenNumberDto_whenAppendNumber_thenSuccessResponse() throws Exception {
        //given
        NumberDto dtoToAppend = DataUtils.getNumberDto(24L);
        NumberDto persistedNumber8 = DataUtils.getNumberDto(8L);
        NumberDto persistedNumber60 = DataUtils.getNumberDto(60L);
        BDDMockito.given(service.appendNumber(any(NumberDto.class)))
                .willReturn(List.of(persistedNumber8, dtoToAppend, persistedNumber60));
        //when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/numbers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoToAppend)));
        //then
        resultActions
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numbers", notNullValue()))
                .andExpect(jsonPath("$.numbers[0].number", is(persistedNumber8.number().intValue())))
                .andExpect(jsonPath("$.numbers[1].number", is(dtoToAppend.number().intValue())))
                .andExpect(jsonPath("$.numbers[2].number", is(persistedNumber60.number().intValue())));
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
