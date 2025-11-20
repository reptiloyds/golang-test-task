package io.github.testwork.service;

import io.github.testwork.dto.NumberDto;
import io.github.testwork.entity.NumberEntity;
import io.github.testwork.mapper.NumberMapper;
import io.github.testwork.repository.NumberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NumberServiceImpl implements NumberService {
    private final NumberRepository repository;
    private final NumberMapper mapper;

    @Override
    @Transactional
    public List<NumberDto> appendNumber(NumberDto numberDto) {
        NumberEntity entityToSave = mapper.toEntity(numberDto);
        repository.save(entityToSave);
        List<NumberEntity> persistedEntities = repository.findAll(Sort.by(Sort.Direction.ASC, NumberEntity.Fields.number));

        return mapper.toDto(persistedEntities);
    }
}
