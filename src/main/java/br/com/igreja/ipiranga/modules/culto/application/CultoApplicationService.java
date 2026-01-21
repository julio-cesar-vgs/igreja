package br.com.igreja.ipiranga.modules.culto.application;

import br.com.igreja.ipiranga.modules.culto.domain.model.Culto;
import br.com.igreja.ipiranga.modules.culto.domain.repository.CultoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Serviço de aplicação principal para gestão do ciclo de vida dos Cultos.
 * <p>
 * Responsável pelas operações de cadastro, atualização e remoção de cultos, funcionando como
 * a barreira transacional inicial.
 * </p>
 * <p>
 * Integra-se com o Apache Kafka para emitir eventos de domínio (ex: "culto-criado", "culto-deletado"),
 * permitindo que outros microserviços ou componentes (como WebSocket) reajam a essas mudanças.
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

    public Culto findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Culto não encontrado"));
    }

    /**
     * Salva ou atualiza um culto.
     * <p>
     * Se o culto já possuir ID, será uma atualização. Caso contrário, um insert.
     * Após persistir com sucesso, envia uma mensagem para o tópico Kafka 'culto-updates',
     * contendo o objeto salvo serializado.
     * </p>
     *
     * @param culto A entidade a ser salva.
     * @return O culto persistido e atualizado.
     */
    @Transactional
    public Culto save(Culto culto) {
        // Registra o evento de domínio antes de salvar.
        // O Spring Data JPA publicará automaticamente após o commit da transação (ou flush).
        culto.registrarAtualizacao();
        
        return repository.save(culto);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
        // Para deleção, o ideal seria carregar a entidade, registrar evento e deletar,
        // ou usar um evento manual. Simplificação: Deixamos sem evento por enquanto ou 
        // implementamos um ApplicationEventPublisher manual se necessário.
    }
}
