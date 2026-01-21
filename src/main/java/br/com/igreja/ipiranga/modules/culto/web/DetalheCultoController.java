package br.com.igreja.ipiranga.modules.culto.web;

import br.com.igreja.ipiranga.modules.culto.application.DetalheCultoApplicationService;
import br.com.igreja.ipiranga.modules.culto.application.dto.CultoDashboardDTO;
import br.com.igreja.ipiranga.modules.culto.domain.model.*;
import br.com.igreja.ipiranga.modules.financeiro.domain.model.Dizimo;
import br.com.igreja.ipiranga.modules.financeiro.domain.model.Oferta;
import br.com.igreja.ipiranga.modules.financeiro.domain.model.TesoureiroConferencia;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller de Detalhes do Culto
 * Camada: Interface (Web)
 * 
 * Gerencia a inserção de itens específicos (louvores, ofertas, dízimos, pessoas) associados a um culto.
 * Atua como ponto de entrada para operações que enriquecem o agregado Culto com detalhes operacionais e financeiros.
 * 
 * Endpoints principais:
 * - GET /api/cultos/{cultoId}/dashboard: Retorna uma visão consolidada do culto.
 * - POST /api/cultos/{cultoId}/*: Adiciona itens específicos ao culto.
 */
@RestController
@RequestMapping("/api/cultos/{cultoId}")
@RequiredArgsConstructor
public class DetalheCultoController {

    private final DetalheCultoApplicationService service;

    @GetMapping("/dashboard")
    public ResponseEntity<CultoDashboardDTO> getDashboard(@PathVariable Long cultoId) {
        return ResponseEntity.ok(service.getDashboard(cultoId));
    }

    // Louvores
    @PostMapping("/louvores")
    public ResponseEntity<Louvor> addLouvor(@PathVariable Long cultoId, @RequestBody Louvor louvor) {
        louvor.setCulto(Culto.builder().id(cultoId).build());
        return ResponseEntity.ok(service.addLouvor(louvor));
    }

    // Dízimos
    @PostMapping("/dizimos")
    public ResponseEntity<Dizimo> addDizimo(@PathVariable Long cultoId, @RequestBody Dizimo dizimo) {
        dizimo.setCulto(Culto.builder().id(cultoId).build());
        return ResponseEntity.ok(service.addDizimo(dizimo));
    }

    // Ofertas
    @PostMapping("/ofertas")
    public ResponseEntity<Oferta> addOferta(@PathVariable Long cultoId, @RequestBody Oferta oferta) {
        oferta.setCulto(Culto.builder().id(cultoId).build());
        return ResponseEntity.ok(service.addOferta(oferta));
    }

    // Cooperadores
    @PostMapping("/cooperadores")
    public ResponseEntity<Cooperador> addCooperador(@PathVariable Long cultoId, @RequestBody Cooperador cooperador) {
        cooperador.setCulto(Culto.builder().id(cultoId).build());
        return ResponseEntity.ok(service.addCooperador(cooperador));
    }

    // Músicos
    @PostMapping("/musicos")
    public ResponseEntity<Musico> addMusico(@PathVariable Long cultoId, @RequestBody Musico musico) {
        musico.setCulto(Culto.builder().id(cultoId).build());
        return ResponseEntity.ok(service.addMusico(musico));
    }

    // Visitantes
    @PostMapping("/visitantes")
    public ResponseEntity<Visitante> addVisitante(@PathVariable Long cultoId, @RequestBody Visitante visitante) {
        visitante.setCulto(Culto.builder().id(cultoId).build());
        return ResponseEntity.ok(service.addVisitante(visitante));
    }

    // Presbíteros
    @PostMapping("/presbiteros")
    public ResponseEntity<Presbitero> addPresbitero(@PathVariable Long cultoId, @RequestBody Presbitero presbitero) {
        presbitero.setCulto(Culto.builder().id(cultoId).build());
        return ResponseEntity.ok(service.addPresbitero(presbitero));
    }

    // Conferência de Tesouraria
    @PostMapping("/conferencia")
    public ResponseEntity<TesoureiroConferencia> conferir(@PathVariable Long cultoId, @RequestBody TesoureiroConferencia conferencia) {
        conferencia.setCulto(Culto.builder().id(cultoId).build());
        return ResponseEntity.ok(service.addConferencia(conferencia));
    }
}
