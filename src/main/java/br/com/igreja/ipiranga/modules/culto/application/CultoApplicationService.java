package br.com.igreja.ipiranga.modules.culto.application;

import br.com.igreja.ipiranga.modules.culto.domain.model.Culto;
import br.com.igreja.ipiranga.modules.culto.domain.repository.CultoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service de Aplicação: CultoApplicationService
 * Camada: Application
 * 
 * Orquestra as operações de negócio para o contexto de Culto.
 * Esta camada é responsável por coordenar as tarefas e delegar a lógica para o domínio.
 * Também gerencia a publicação de eventos para o Kafka (Event-Driven).
 */
@Service
@RequiredArgsConstructor
public class CultoApplicationService {

    private final CultoRepository repository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public List<Culto> findAll() {
        return repository.findAll();
    }

    public Culto findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Culto não encontrado"));
    }

    @Transactional
    public Culto save(Culto culto) {
        Culto saved = repository.save(culto);
        // Event-Driven: Publicação de evento de integração para outros contextos
        kafkaTemplate.send("culto-updates", saved);
        return saved;
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
        // Event-Driven: Notificação de deleção
        kafkaTemplate.send("culto-updates", "DELETED:" + id);
    }
}
