package io.github.testwork.controller;

import io.github.testwork.dto.NumberDto;
import io.github.testwork.dto.AppendNumberResponse;
import io.github.testwork.service.NumberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/numbers")
@RequiredArgsConstructor
public class NumberRestControllerV1 {
    private final NumberService numberService;

    @PostMapping()
    public ResponseEntity<AppendNumberResponse> appendNumber(@Valid @RequestBody NumberDto request) {
        List<NumberDto> totalNumbers = numberService.appendNumber(request);
        AppendNumberResponse response = new AppendNumberResponse(totalNumbers);

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
