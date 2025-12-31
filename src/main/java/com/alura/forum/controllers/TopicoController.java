package com.alura.forum.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable; 

import lombok.AllArgsConstructor;

import com.alura.forum.domain.dtos.TopicoDto;
import com.alura.forum.domain.dtos.TopicoForSaveDto;
import com.alura.forum.services.TopicoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/topicos")
@AllArgsConstructor
public class TopicoController {
    private final TopicoService topicoService;
    
    @GetMapping
    public ResponseEntity<List<TopicoDto>> getTopicos() {
        return ResponseEntity.ok(topicoService.listAll());
    }

    @PostMapping
    public ResponseEntity<TopicoDto> saveTopicoString(@RequestBody TopicoForSaveDto topicoForSaveDto) {
        topicoService.saveTopico(topicoForSaveDto);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopico(@PathVariable Long id) {
        topicoService.deleteTopico(id);
        return ResponseEntity.noContent().build(); 
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicoDto> modifyTopico(@PathVariable Long id, @RequestBody TopicoForSaveDto topicoForSaveDto) {
        try {
            topicoService.modifyTopico(id, topicoForSaveDto);
            return ResponseEntity.ok().build();
        
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }

    }
    
}
