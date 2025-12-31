package com.alura.forum.services;

import java.util.stream.Collectors;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import com.alura.forum.domain.dtos.TopicoDto;
import com.alura.forum.domain.dtos.TopicoForSaveDto;
import com.alura.forum.domain.models.Topico;
import com.alura.forum.repository.TopicoRepository;

import jakarta.transaction.Transactional;

import com.alura.forum.mappers.TopicoMapper;


@Service
@AllArgsConstructor
public class TopicoService {
    private final TopicoRepository topicoRepository;

    public List<TopicoDto> listAll(){
        return topicoRepository
        .findAll()
        .stream()
        .map(TopicoMapper::modelToDto)
        .collect(Collectors.toList());
    }

    @Transactional
    public boolean saveTopico(TopicoForSaveDto topicoForSaveDto){
        topicoRepository.save(
            TopicoMapper.dtoForSaveToModel(topicoForSaveDto)
        );
        return true;
    }
    
    @Transactional
    public void deleteTopico(Long id){
        topicoRepository.deleteById(id);
    }

    @Transactional
    public TopicoDto modifyTopico(Long id, TopicoForSaveDto topicoForSaveDto){
        Topico topico = topicoRepository
        .findById(id)
        .orElseThrow(
            () -> new RuntimeException("Tópico não encontrado")
        );

        topico.setTitulo(topicoForSaveDto.titulo());
        topico.setMensagem(topicoForSaveDto.mensagem());
        topicoRepository.save(topico);

        return TopicoMapper.modelToDto(topico);
    }
}
