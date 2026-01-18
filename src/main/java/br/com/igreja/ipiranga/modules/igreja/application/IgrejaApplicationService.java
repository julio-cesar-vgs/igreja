package br.com.igreja.ipiranga.modules.igreja.application;

import br.com.igreja.ipiranga.modules.igreja.domain.model.Igreja;
import br.com.igreja.ipiranga.modules.igreja.domain.repository.IgrejaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service de Aplicação: IgrejaApplicationService
 * Camada: Application
 *
 * Gerencia as operações de negócio para o contexto de Igreja (Cadastro de Matriz e Filiais).
 */
@Service
@RequiredArgsConstructor
public class IgrejaApplicationService {

    private final IgrejaRepository repository;

    public List<Igreja> findAll() {
        return repository.findAll();
    }

    public Igreja findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Igreja não encontrada"));
    }

    @Transactional
    public Igreja save(Igreja igreja) {
        return repository.save(igreja);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
