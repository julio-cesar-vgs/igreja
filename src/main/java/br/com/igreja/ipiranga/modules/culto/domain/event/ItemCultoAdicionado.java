package br.com.igreja.ipiranga.modules.culto.domain.event;

import br.com.igreja.ipiranga.shared.domain.DomainEvent;
import lombok.Getter;

/**
 * Evento de Domínio: Item Adicionado ao Culto
 * Disparado quando um item (Louvor, Dízimo, etc) é adicionado.
 */
@Getter
public class ItemCultoAdicionado extends DomainEvent {
    private final String tipoItem; // ex: LOUVOR, DIZIMO
    private final Long itemId;
    private final Long cultoId;

    public ItemCultoAdicionado(String tipoItem, Long itemId, Long cultoId) {
        super();
        this.tipoItem = tipoItem;
        this.itemId = itemId;
        this.cultoId = cultoId;
    }
}
