package br.com.igreja.ipiranga.modules.igreja.web;

import br.com.igreja.ipiranga.modules.igreja.application.IgrejaApplicationService;
import br.com.igreja.ipiranga.modules.igreja.domain.model.Igreja;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

/**
 * Controlador REST para administração de Igrejas.
 * <p>
 * Fornece endpoints para CRUD completo das unidades (Matriz/Filiais).
 * Geralmente restrito a usuários com perfil administrativo global.
 * </p>
 *
 * @author Sistema Igreja
 * @version 1.0
 */
@RestController
@RequestMapping("/api/igrejas")
@RequiredArgsConstructor
public class IgrejaController {

    private final IgrejaApplicationService service;

    /**
     * Recupera a lista de todas as igrejas.
     *
     * @return ResponseEntity com a lista de igrejas.
     */
    @GetMapping
    public ResponseEntity<List<Igreja>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * Busca os dados de uma igreja pelo ID.
     *
     * @param id ID da igreja.
     * @return ResponseEntity com os dados da igreja.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Igreja> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * Cadastra uma nova igreja.
     *
     * @param igreja Objeto JSON com dados da nova igreja.
     * @return ResponseEntity com status 201 Created e Location header.
     */
    @PostMapping
    public ResponseEntity<Igreja> create(@RequestBody Igreja igreja) {
        Igreja saved = service.save(igreja);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    /**
     * Atualiza os dados de uma igreja existente.
     *
     * @param id     ID da igreja a atualizar.
     * @param igreja Objeto JSON com os novos dados.
     * @return ResponseEntity com a igreja atualizada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Igreja> update(@PathVariable UUID id, @RequestBody Igreja igreja) {
        igreja.setId(id);
        return ResponseEntity.ok(service.save(igreja));
    }

    /**
     * Exclui uma igreja.
     *
     * @param id ID da igreja a excluir.
     * @return ResponseEntity sem conteúdo (204).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
