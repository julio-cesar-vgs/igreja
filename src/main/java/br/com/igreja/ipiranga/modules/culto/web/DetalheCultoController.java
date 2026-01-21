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
import java.util.UUID;

/**
 * Controlador REST API para gerenciamento dos itens detalhados de um culto
 * específico.
 * <p>
 * Todos os endpoints aqui são sub-recursos de um culto, identificados path
 * {@code /api/cultos/{cultoId}/...}.
 * Permite adicionar louvores, ofertas, dízimos, músicos e obter o dashboard
 * consolidado.
 * </p>
 *
 * @author Sistema Igreja
 * @version 1.0
 */
@RestController
@RequestMapping("/api/cultos/{cultoId}")
@RequiredArgsConstructor
public class DetalheCultoController {

    private final DetalheCultoApplicationService service;

    /**
     * Recupera o Dashboard completo com todos os dados do culto.
     *
     * @param cultoId ID do culto.
     * @return DTO contendo louvores, finanças, pessoas, etc.
     */
    @GetMapping("/dashboard")
    public ResponseEntity<CultoDashboardDTO> getDashboard(@PathVariable UUID cultoId) {
        return ResponseEntity.ok(service.getDashboard(cultoId));
    }

    // Louvores
    /**
     * Adiciona um registro de louvor ao culto.
     *
     * @param cultoId ID do culto pai.
     * @param louvor  Dados do louvor.
     * @return Louvor salvo.
     */
    @PostMapping("/louvores")
    public ResponseEntity<Louvor> addLouvor(@PathVariable UUID cultoId, @RequestBody Louvor louvor) {
        louvor.setCulto(Culto.builder().id(cultoId).build());
        return ResponseEntity.ok(service.addLouvor(louvor));
    }

    // Dízimos
    @PostMapping("/dizimos")
    public ResponseEntity<Dizimo> addDizimo(@PathVariable UUID cultoId, @RequestBody Dizimo dizimo) {
        dizimo.setCulto(Culto.builder().id(cultoId).build());
        return ResponseEntity.ok(service.addDizimo(dizimo));
    }

    // Ofertas
    @PostMapping("/ofertas")
    public ResponseEntity<Oferta> addOferta(@PathVariable UUID cultoId, @RequestBody Oferta oferta) {
        oferta.setCulto(Culto.builder().id(cultoId).build());
        return ResponseEntity.ok(service.addOferta(oferta));
    }

    // Cooperadores
    @PostMapping("/cooperadores")
    public ResponseEntity<Cooperador> addCooperador(@PathVariable UUID cultoId, @RequestBody Cooperador cooperador) {
        cooperador.setCulto(Culto.builder().id(cultoId).build());
        return ResponseEntity.ok(service.addCooperador(cooperador));
    }

    // Músicos
    @PostMapping("/musicos")
    public ResponseEntity<Musico> addMusico(@PathVariable UUID cultoId, @RequestBody Musico musico) {
        musico.setCulto(Culto.builder().id(cultoId).build());
        return ResponseEntity.ok(service.addMusico(musico));
    }

    // Visitantes
    @PostMapping("/visitantes")
    public ResponseEntity<Visitante> addVisitante(@PathVariable UUID cultoId, @RequestBody Visitante visitante) {
        visitante.setCulto(Culto.builder().id(cultoId).build());
        return ResponseEntity.ok(service.addVisitante(visitante));
    }

    // Presbíteros
    @PostMapping("/presbiteros")
    public ResponseEntity<Presbitero> addPresbitero(@PathVariable UUID cultoId, @RequestBody Presbitero presbitero) {
        presbitero.setCulto(Culto.builder().id(cultoId).build());
        return ResponseEntity.ok(service.addPresbitero(presbitero));
    }

    // Conferência de Tesouraria
    @PostMapping("/conferencia")
    public ResponseEntity<TesoureiroConferencia> conferir(@PathVariable UUID cultoId,
            @RequestBody TesoureiroConferencia conferencia) {
        conferencia.setCulto(Culto.builder().id(cultoId).build());
        return ResponseEntity.ok(service.addConferencia(conferencia));
    }
}
