package com.codesmack.jpa.entity.inheritance.mapped_superclass;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Date;

/**
 * Created by Aniruddha on 23-07-2018.
 */
@Entity(name = "BOOK_MAPPED_TABLE")
public class Book extends Item {

    private String isbn;

    @Column(name = "nbr_of_page")
    private Integer nbrOfPage;

    @Column(name = "publication_date")
    private Date publicationDate;

    public Book() {
    }

    public Book(final String title, final String description, final Float unitCost, final String isbn, final Integer nbrOfPage, final Date publicationDate) {
        super(title, description, unitCost);
        this.isbn = isbn;
        this.nbrOfPage = nbrOfPage;
        this.publicationDate = publicationDate;
    }

    public String getIsbn() {
        return isbn;
    }
}
