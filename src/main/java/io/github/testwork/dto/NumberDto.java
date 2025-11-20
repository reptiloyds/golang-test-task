package io.github.testwork.dto;

import jakarta.validation.constraints.NotNull;

public record NumberDto(@NotNull Long number) {
}
