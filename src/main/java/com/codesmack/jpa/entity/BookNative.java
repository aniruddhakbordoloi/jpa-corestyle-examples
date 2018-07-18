package com.codesmack.jpa.entity;

/**
 * Created by Aniruddha on 14-07-2018.
 */

public class BookNative {

    private Long id;
    private String title;
    private String description;
    private Float unitCost;
    private String isbn;

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

    private Integer nbrOfPage;

    public BookNative(final Long id) {
        this.id = id;
    }

    public BookNative(final Long id, final String title, final String description, final Float unitCost, final String isbn, final Integer nbrOfPage) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.unitCost = unitCost;
        this.isbn = isbn;
        this.nbrOfPage = nbrOfPage;
    }
}
