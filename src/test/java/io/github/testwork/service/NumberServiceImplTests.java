package io.github.testwork.service;

import io.github.testwork.dto.NumberDto;
import io.github.testwork.entity.NumberEntity;
import io.github.testwork.mapper.NumberMapper;
import io.github.testwork.repository.NumberRepository;
import io.github.testwork.utils.DataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class NumberServiceImplTests {
    @Spy
    private final NumberMapper mapper = Mappers.getMapper(NumberMapper.class);
    @Mock
    private NumberRepository repository;
    @InjectMocks
    private NumberServiceImpl serviceUnderTest;

    @Test
    @DisplayName("Test append number functionality")
    public void givenPersistedAndTransientNumbers_whenAppendTransientNumber_thenReturnAllPersistedNumbers() {
        //given
        NumberDto numberToAppend = DataUtils.getNumberDto(21L);
        NumberEntity persistedNumber16 = DataUtils.getNumberPersisted(1L, 16L);
        NumberEntity persistedNumber7 = DataUtils.getNumberPersisted(2L, 7L);
        NumberEntity persistedNumber21 = DataUtils.getNumberPersisted(3L, 21L);
        List<NumberEntity> persistedNumbers = List.of(persistedNumber7, persistedNumber16, persistedNumber21);
        BDDMockito.given(repository.findAll(any(Sort.class)))
                .willReturn(persistedNumbers);
        BDDMockito.given(repository.save(any(NumberEntity.class)))
                .willReturn(persistedNumber21);
        //when
        List<NumberDto> obtainedNumbers = serviceUnderTest.appendNumber(numberToAppend);
        //then
        assertThat(CollectionUtils.isEmpty(obtainedNumbers)).isFalse();
        assertThat(obtainedNumbers.size()).isEqualTo(persistedNumbers.size());
        verify(repository, times(1)).findAll(any(Sort.class));
        verify(repository, times(1)).save(any(NumberEntity.class));
    }
}
