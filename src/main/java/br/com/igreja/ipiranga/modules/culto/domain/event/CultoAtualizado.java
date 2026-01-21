package br.com.igreja.ipiranga.modules.culto.domain.event;

import br.com.igreja.ipiranga.modules.culto.domain.model.Culto;
import br.com.igreja.ipiranga.shared.domain.DomainEvent;
import lombok.Getter;

/**
 * Evento de Domínio: Culto Atualizado
 * Disparado quando um culto é criado ou sofre atualizações em seus dados básicos.
 */
@Getter
public class CultoAtualizado extends DomainEvent {
    private final Culto culto;

    public CultoAtualizado(Culto culto) {
        super();
        this.culto = culto;
    }
}
