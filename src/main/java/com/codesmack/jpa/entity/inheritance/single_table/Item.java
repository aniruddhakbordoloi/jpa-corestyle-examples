package com.codesmack.jpa.entity.inheritance.single_table;

import javax.persistence.*;

/**
 * Created by Aniruddha on 23-07-2018.
 */
@Entity(name = "ITEM_SINGLE_TABLE")
@DiscriminatorColumn(name = "RECORD_TYPE")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @Column(name = "unit_cost")
    private Float unitCost;

    public Item() {
    }

    public Item(final String title, final String description, final Float unitCost) {
        this.title = title;
        this.description = description;
        this.unitCost = unitCost;
    }

    public String getTitle() {
        return title;
    }

    public Long getId() {
        return id;
    }
}
