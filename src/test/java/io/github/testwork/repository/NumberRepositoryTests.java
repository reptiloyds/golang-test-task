package io.github.testwork.repository;

import io.github.testwork.entity.NumberEntity;
import io.github.testwork.template.PostgresTestTemplate;
import io.github.testwork.utils.DataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
public class NumberRepositoryTests extends PostgresTestTemplate {
    @Autowired
    private NumberRepository repository;

    @BeforeEach
    public void setup() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Test save number functionality")
    public void givenNumberObject_whenSave_thenNumberIsCreated() {
        //given
        NumberEntity numberToSave = DataUtils.getNumberTransient(1L);
        //when
        NumberEntity savedNumber = repository.save(numberToSave);
        //then
        assertThat(savedNumber).isNotNull();
        assertThat(savedNumber.getId()).isNotNull();
    }

    @Test
    @DisplayName("Test get number by Id functionality")
    public void givenNumberCreated_whenGetById_thenNumberIsReturned() {
        //given
        NumberEntity numberToSave = DataUtils.getNumberTransient(1L);
        repository.save(numberToSave);
        //when
        NumberEntity obtainedNumber = repository.findById(numberToSave.getId())
                .orElse(null);
        //then
        assertThat(obtainedNumber).isNotNull();
        assertThat(obtainedNumber.getNumber()).isEqualTo(numberToSave.getNumber());
    }

    @Test
    @DisplayName("Test get sorted numbers functionality")
    public void givenNumbersCreated_whenGetSortedNumbers_thenNumbersAreReturnedInOrder() {
        //given
        NumberEntity numberToSave1 = DataUtils.getNumberTransient(1L);
        NumberEntity numberToSave257 = DataUtils.getNumberTransient(257L);
        NumberEntity numberToSave700 = DataUtils.getNumberTransient(700L);
        repository.save(numberToSave700);
        repository.save(numberToSave1);
        repository.save(numberToSave257);
        //when
        List<NumberEntity> obtainedNumbers = repository.findAll(Sort.by(Sort.Direction.ASC, NumberEntity.Fields.number));
        //then
        assertThat(CollectionUtils.isEmpty(obtainedNumbers)).isFalse();
        assertThat(obtainedNumbers.get(0).getNumber()).isEqualTo(numberToSave1.getNumber());
        assertThat(obtainedNumbers.get(1).getNumber()).isEqualTo(numberToSave257.getNumber());
        assertThat(obtainedNumbers.get(2).getNumber()).isEqualTo(numberToSave700.getNumber());
    }
}
