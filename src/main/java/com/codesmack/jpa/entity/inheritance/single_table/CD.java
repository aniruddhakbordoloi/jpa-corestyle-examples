package com.codesmack.jpa.entity.inheritance.single_table;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by Aniruddha on 23-07-2018.
 */
@Entity(name = "CD_ITEM_SINGLE_TABLE")
public class CD extends Item {

    @Column(name = "total_duration")
    private Float totalDuration;

    private String genre;

    public CD() {
    }

    public CD (final String title, final String description, final Float unitCost, final Float totalDuration, final String genre) {
        super(title, description, unitCost);
        this.totalDuration = totalDuration;
        this.genre = genre;
    }

    public Float getTotalDuration() {
        return totalDuration;
    }
}
