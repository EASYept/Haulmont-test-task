package com.easyept.entity;

import javax.persistence.*;
import java.util.UUID;

/**
 * FIXME : I DID NOT USED THIS!
 * But tried. Put it here just for showing my approach.
 * Why? Because if i gone apply this to all my entities,
 * i would have weird bug(or i'm not understanding)
 * where Vaadin's grid at multiSelectMode would not GRID.select(Item item)
 * even if it's literally the same object i just set in grid.
 */
@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue
    @Column(unique = true, updatable = false)
    private UUID uuid;

    public UUID getUuid() {
        return uuid;
    }
}
