package com.alura.forum.domain.dtos;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record TopicoDto(
    Long id,
    String titulo,
    String mensagem,
    LocalDateTime dataCriacao
) {}
