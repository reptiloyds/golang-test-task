package io.github.testwork.service;

import io.github.testwork.dto.NumberDto;

import java.util.List;

public interface NumberService {
    List<NumberDto> appendNumber(NumberDto numberDto);
}
