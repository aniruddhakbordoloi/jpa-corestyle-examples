package com.codesmack.jpa.entity;

import javax.persistence.*;

@Entity
@Table(name = "Programmer")
public class Programmer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "biography")
    private String bio;
    @Column(name = "date_Of_Birth")
    private String dob;
    private String language;

    public Programmer() {}
    public Programmer(final String name, final String bio, final String dob, final String language) {
        this.name = name;
        this.bio = bio;
        this.dob = dob;
        this.language = language;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

}
