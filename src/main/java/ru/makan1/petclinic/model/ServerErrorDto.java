package ru.makan1.petclinic.model;

import java.time.LocalDateTime;

public record ServerErrorDto(String message,
                             String detailedMessage,
                             LocalDateTime timestamp
) {
}
