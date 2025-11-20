package io.github.testwork.mapper;

import io.github.testwork.dto.NumberDto;
import io.github.testwork.entity.NumberEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NumberMapper {
    NumberDto toDto(NumberEntity number);
    List<NumberDto> toDto(List<NumberEntity> numbers);
    @Mapping(target = "id", ignore = true)
    NumberEntity toEntity(NumberDto number);
    List<NumberEntity> toEntity(List<NumberDto> numbers);
}
