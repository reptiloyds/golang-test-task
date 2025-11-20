package io.github.testwork.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

@Data
@FieldNameConstants
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "numbers")
public class NumberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;
    @Column()
    private Long number;
}