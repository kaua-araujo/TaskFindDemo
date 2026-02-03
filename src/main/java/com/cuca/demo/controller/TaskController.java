package com.cuca.demo.controller;

import com.cuca.demo.model.Task;
import com.cuca.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    @Autowired
    private TaskRepository repository;

    @PostMapping
    public Task criarTarefa(@RequestBody Task task) {
        task.setStatus("PENDENTE");
        return repository.save(task);
    }

    @GetMapping
    public List<Task> listarTarefas() {
        return repository.findAll();
    }

    @PutMapping("/{id}/status")
    public Task atualizarStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        return repository.findById(id).map(task -> {
            task.setStatus(status);
            return repository.save(task);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public String excluirTarefa(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Tarefa removida com sucesso";
        }
        return "Tarefa não encontrada";
    }
}
