package io.github.testwork.utils;

import io.github.testwork.dto.NumberDto;
import io.github.testwork.entity.NumberEntity;

public class DataUtils {
    public static NumberEntity getNumberTransient(Long number) {
        return NumberEntity.builder()
                .number(number)
                .build();
    }

    public static NumberEntity getNumberPersisted(Long id, Long number) {
        return NumberEntity.builder()
                .id(id)
                .number(number)
                .build();
    }

    public static NumberDto getNumberDto(Long number) {
        return new NumberDto(number);
    }
}
