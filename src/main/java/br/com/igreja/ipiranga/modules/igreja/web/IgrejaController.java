package br.com.igreja.ipiranga.modules.igreja.web;

import br.com.igreja.ipiranga.modules.igreja.application.IgrejaApplicationService;
import br.com.igreja.ipiranga.modules.igreja.domain.model.Igreja;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Controller de Igreja
 * Camada: Interface (Web)
 *
 * Expõe as funcionalidades de cadastro de Igrejas.
 */
@RestController
@RequestMapping("/api/igrejas")
@RequiredArgsConstructor
public class IgrejaController {

    private final IgrejaApplicationService service;

    @GetMapping
    public ResponseEntity<List<Igreja>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Igreja> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Igreja> create(@RequestBody Igreja igreja) {
        Igreja saved = service.save(igreja);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Igreja> update(@PathVariable Long id, @RequestBody Igreja igreja) {
        igreja.setId(id);
        return ResponseEntity.ok(service.save(igreja));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
