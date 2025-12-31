package com.alura.forum.mappers;

import java.time.LocalDateTime;

import com.alura.forum.domain.dtos.TopicoDto;
import com.alura.forum.domain.dtos.TopicoForSaveDto;
import com.alura.forum.domain.models.Topico;

public class TopicoMapper {
    public static TopicoDto modelToDto(Topico topico) {
        return TopicoDto.builder()
            .id(topico.getId())
            .titulo(topico.getTitulo())
            .mensagem(topico.getMensagem())
            .dataCriacao(topico.getDataCriacao())
            .build();
    }

    public static Topico dtoForSaveToModel(TopicoForSaveDto topicoDto) {
        return Topico
            .builder()
            .titulo(topicoDto.titulo())
            .mensagem(topicoDto.mensagem())
            .dataCriacao(LocalDateTime.now())
            .build();
    }
}
