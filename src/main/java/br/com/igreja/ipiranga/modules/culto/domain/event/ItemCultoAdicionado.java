package br.com.igreja.ipiranga.modules.culto.domain.event;

import br.com.igreja.ipiranga.shared.domain.DomainEvent;
import lombok.Getter;

import java.util.UUID;

/**
 * Evento de Domínio: Item Adicionado ao Culto
 * Disparado quando um item (Louvor, Dízimo, etc) é adicionado.
 */
@Getter
public class ItemCultoAdicionado extends DomainEvent {
    private final String tipoItem; // ex: LOUVOR, DIZIMO
    private final UUID itemId;
    private final UUID cultoId;

    public ItemCultoAdicionado(String tipoItem, UUID itemId, UUID cultoId) {
        super();
        this.tipoItem = tipoItem;
        this.itemId = itemId;
        this.cultoId = cultoId;
    }
}
