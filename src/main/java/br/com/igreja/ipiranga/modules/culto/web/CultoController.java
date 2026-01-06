package br.com.igreja.ipiranga.modules.culto.web;

import br.com.igreja.ipiranga.modules.culto.application.CultoApplicationService;
import br.com.igreja.ipiranga.modules.culto.domain.model.Culto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller de Culto
 * Camada: Interface (Web)
 * 
 * Expõe as funcionalidades do contexto de Culto para o mundo externo via REST.
 */
@RestController
@RequestMapping("/api/cultos")
@RequiredArgsConstructor
public class CultoController {

    private final CultoApplicationService service;

    @GetMapping
    public ResponseEntity<List<Culto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Culto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Culto> create(@RequestBody Culto culto) {
        return ResponseEntity.ok(service.save(culto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Culto> update(@PathVariable Long id, @RequestBody Culto culto) {
        culto.setId(id);
        return ResponseEntity.ok(service.save(culto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
