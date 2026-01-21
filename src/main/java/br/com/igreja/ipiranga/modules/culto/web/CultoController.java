package br.com.igreja.ipiranga.modules.culto.web;

import br.com.igreja.ipiranga.modules.culto.application.CultoApplicationService;
import br.com.igreja.ipiranga.modules.culto.domain.model.Culto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST API para operações CRUD no recurso /api/cultos.
 * <p>
 * Ponto de entrada para criação e gerenciamento básico dos cultos.
 * </p>
 *
 * @author Sistema Igreja
 * @version 1.0
 */
@RestController
@RequestMapping("/api/cultos")
@RequiredArgsConstructor
public class CultoController {

    private final CultoApplicationService service;

    /**
     * Obtém a lista completa de cultos registrados.
     *
     * @return Lista de cultos.
     */
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
