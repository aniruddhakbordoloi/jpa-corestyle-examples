package com.codesmack.jpa.entity;

import javax.persistence.*;

/**
 * Created by Aniruddha on 14-07-2018.
 */
@Entity
@Table(name = "BookEntityWithGenerator")
public class BookEntityWithGenerator {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @Column(name = "unit_cost")
    private Float unitCost;
    private String isbn;
    @Column(name = "nbr_of_page")
    private Integer nbrOfPage;

    public BookEntityWithGenerator() {
    }

    public BookEntityWithGenerator(final String title, final String description, final Float unitCost, final String isbn, final Integer nbrOfPage) {
        this.title = title;
        this.description = description;
        this.unitCost = unitCost;
        this.isbn = isbn;
        this.nbrOfPage = nbrOfPage;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Float getUnitCost() {
        return unitCost;
    }

    public String getIsbn() {
        return isbn;
    }

    public Integer getNbrOfPage() {
        return nbrOfPage;
    }

    public BookEntityWithGenerator(final Long id) {
        this.id = id;
    }
}
