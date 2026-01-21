package br.com.igreja.ipiranga.modules.igreja.application;

import br.com.igreja.ipiranga.modules.igreja.domain.model.Igreja;
import br.com.igreja.ipiranga.modules.igreja.domain.repository.IgrejaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Serviço de aplicação para gestão do cadastro de Igrejas.
 * <p>
 * Responsável pelas regras de negócio envolvidas na criação e manutenção
 * das unidades (Matriz e Filiais).
 * </p>
 *
 * @author Sistema Igreja
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class IgrejaApplicationService {

    private final IgrejaRepository repository;

    /**
     * Lista todas as igrejas cadastradas no sistema.
     *
     * @return Lista completa de igrejas.
     */
    public List<Igreja> findAll() {
        return repository.findAll();
    }

    /**
     * Busca os detalhes de uma igreja específica.
     *
     * @param id ID da igreja.
     * @return A entidade Igreja encontrada.
     * @throws RuntimeException se não encontrada.
     */
    public Igreja findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Igreja não encontrada"));
    }

    /**
     * Cria ou atualiza o cadastro de uma igreja.
     *
     * @param igreja Objeto com os dados a serem salvos.
     * @return A entidade persistida.
     */
    @Transactional
    public Igreja save(Igreja igreja) {
        return repository.save(igreja);
    }

    /**
     * Remove uma igreja do sistema.
     * <p>
     * Cuidado: Isso pode ter impactos em cascata se houver integridade referencial
     * não tratada,
     * embora o ideal seja exclusão lógica.
     * </p>
     *
     * @param id ID da igreja a remover.
     */
    @Transactional
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
