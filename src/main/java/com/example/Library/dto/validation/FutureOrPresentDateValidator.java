package com.example.Library.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class FutureOrPresentDateValidator implements ConstraintValidator<FutureOrPresentDate, LocalDate> {
    @Override
    public void initialize(FutureOrPresentDate futureOrPresentDate) {
    }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
        return !date.isBefore(LocalDate.now());
    }
}
