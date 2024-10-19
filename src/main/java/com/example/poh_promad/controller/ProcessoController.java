package com.example.poh_promad.controller;

import com.example.poh_promad.model.Processo;
import com.example.poh_promad.service.ProcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/processos")
public class ProcessoController {

    @Autowired
    private ProcessoService processoService;

    @GetMapping
    public List<Processo> findAll() {
        return processoService.findAll();
    }

    @GetMapping("/{id}")
    public Processo findById(@PathVariable Long id){
        Optional<Processo> processo = processoService.findById(id);
        return processo.orElse(null);
    }

    @PostMapping
    public Processo save(@RequestBody Processo processo){

        return processoService.save(processo).getProcesso();

    }


    @DeleteMapping("/id")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        if (processoService.findById(id).isPresent()){
            processoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return  ResponseEntity.notFound().build();
        }
    }

}
