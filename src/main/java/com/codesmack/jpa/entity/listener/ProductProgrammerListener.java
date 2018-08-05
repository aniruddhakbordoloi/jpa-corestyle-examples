package com.codesmack.jpa.entity.listener;

import com.codesmack.jpa.entity.callback.DateOfBirthNotFoundException;
import com.codesmack.jpa.entity.callback.EmailSender;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * Created by Aniruddha on 06-08-2018.
 */
public class ProductProgrammerListener {

    @PrePersist
    private void prePersistCheck(final Programmer programmer) throws DateOfBirthNotFoundException {
        System.out.println("Pre persist called in Listener");
        if (null == programmer.getDob()) {
            throw new DateOfBirthNotFoundException("Date of Birth can not be null.");
        }
    }

    @PostPersist
    public void postPersistCheck(final Programmer programmer) {
        System.out.println("Post Persist called in Listener");
        EmailSender.sendEmail("Email Sent for persistence");
    }

    @PostLoad
    public void postLoadCheck(final Programmer programmer) {
        System.out.println("Post Load called in Listener");
        final LocalDate birthDate = LocalDate.parse(programmer.getDob(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        final LocalDate todaysDate = LocalDate.now();
        final Period period = Period.between(birthDate, todaysDate);
        programmer.setAge(period.getYears());
    }

    @PreUpdate
    public void preUpdateCheck(final Programmer programmer) throws DateOfBirthNotFoundException {
        System.out.println("Pre-update called in Listener");
        if (null == programmer.getDob()) {
            throw new DateOfBirthNotFoundException("Date of Birth can not be null while updating.");
        }
    }

    @PostUpdate
    public void postUpdateCheck(final Programmer programmer) {
        System.out.println("Post-update called in Listener");
        programmer.setDob("1987-10-21");
    }

    @PreRemove
    public void preRemoveCheck(final Programmer programmer) throws IllegalAccessException {
        System.out.println("Pre-remove called in Listener");
        if (null == programmer.getId()) {
            throw new IllegalAccessException("Primary Id can't be null when performing a remove operation.");
        }
    }

    @PostRemove
    public void postRemoveCheck(final Programmer programmer) {
        System.out.println("Post-remove called in Listener");
        EmailSender.sendEmail("Programmer remove notification sent");
    }
}
