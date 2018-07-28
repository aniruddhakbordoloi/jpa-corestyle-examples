package com.codesmack.jpa.entity.inheritance.table_per_concrete_class;

import javax.persistence.*;

/**
 * Created by Aniruddha on 23-07-2018.
 */
@Entity(name = "ITEM_CONCRETE_TABLE")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
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
