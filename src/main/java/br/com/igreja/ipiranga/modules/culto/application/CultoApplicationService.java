package br.com.igreja.ipiranga.modules.culto.application;

import br.com.igreja.ipiranga.modules.culto.domain.model.Culto;
import br.com.igreja.ipiranga.modules.culto.domain.repository.CultoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Serviço de aplicação principal para gestão do ciclo de vida dos Cultos.
 * <p>
 * Responsável pelas operações de cadastro, atualização e remoção de cultos,
 * funcionando como
 * a barreira transacional inicial.
 * </p>
 * <p>
 * Integra-se com o Apache Kafka para emitir eventos de domínio (ex:
 * "culto-criado", "culto-deletado"),
 * permitindo que outros microserviços ou componentes (como WebSocket) reajam a
 * essas mudanças.
 * </p>
 *
 * @author Sistema Igreja
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class CultoApplicationService {

    private final CultoRepository repository;
    // Kafka agora é desacoplado via Domain Event Listener
    // private final KafkaTemplate<String, Object> kafkaTemplate;

    public List<Culto> findAll() {
        return repository.findAll();
    }

    public Culto findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Culto não encontrado"));
    }

    @Transactional
    public Culto save(Culto culto) {
        if (culto.getId() == null) {
            culto.registrarAtualizacao();
        }
        return repository.save(culto);
    }

    @Transactional
    public void delete(UUID id) {
        repository.deleteById(id);
        // ...
    }
}
