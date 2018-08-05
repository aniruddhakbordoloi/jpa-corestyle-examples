package com.codesmack.jpa.entity.callback;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Programmer_Callback")
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

    @Transient
    private int age;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Product> products = new HashSet<Product>();

    public Programmer() {}
    public Programmer(final String name, final String bio, final String dob, final String language) {
        this.name = name;
        this.bio = bio;
        this.dob = dob;
        this.language = language;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDob() {
        return dob;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public int getAge() {
        return age;
    }

    @PrePersist
    private void prePersistCheck() throws DateOfBirthNotFoundException {
        System.out.println("Pre persist called");
        if (null == this.dob) {
            throw new DateOfBirthNotFoundException("Date of Birth can not be null.");
        }
    }

    @PostPersist
    public void postPersistCheck() {
        System.out.println("Post Persist called");
        EmailSender.sendEmail("Email Sent for persistence");
    }

    @PostLoad
    public void postLoadCheck() {
        System.out.println("Post Load called");
        final LocalDate birthDate = LocalDate.parse(this.dob, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        final LocalDate todaysDate = LocalDate.now();
        final Period period = Period.between(birthDate, todaysDate);
        this.age = period.getYears();
    }

    @PreUpdate
    public void preUpdateCheck() throws DateOfBirthNotFoundException {
        System.out.println("Pre-update called");
        if (null == this.dob) {
            throw new DateOfBirthNotFoundException("Date of Birth can not be null while updating.");
        }
    }

    @PostUpdate
    public void postUpdateCheck() {
        System.out.println("Post-update called");
        this.dob = "1987-10-21";
    }

    @PreRemove
    public void preRemoveCheck() throws IllegalAccessException {
        System.out.println("Pre-remove called");
        if (null == this.id) {
            throw new IllegalAccessException("Primary Id can't be null when performing a remove operation.");
        }
    }

    @PostRemove
    public void postRemoveCheck() {
        System.out.println("Post-remove called");
        EmailSender.sendEmail("Programmer remove notification sent");
    }
}
