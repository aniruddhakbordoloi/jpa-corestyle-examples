package com.codesmack.jpa;

import com.codesmack.jpa.entity.unidirectional.onetoone.Product;
import com.codesmack.jpa.entity.unidirectional.onetoone.Programmer;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Date;

public class ProductProgrammerTest {
    private EntityManager entityManager;

    @Before
    public void setUp() {
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpaCoreStyle");
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Test
    public void thatProductInsertionIsSuccessful() {
        //insert
        final Product product = new Product("Fusion Collections", "My Library", new Float(20.0), new Float(20.0), "Modern");
        entityManager.getTransaction().begin();
        entityManager.persist(product);
        entityManager.getTransaction().commit();
        //clean up
        final Product retrievedProduct = entityManager.find(Product.class, product.getId());
        entityManager.getTransaction().begin();
        entityManager.remove(retrievedProduct);
        entityManager.getTransaction().commit();
    }

    @Test
    public void thatProgrammerInsertionIsSuccessful() {
        //insert
        final Programmer programmer = new Programmer("Codesmack", "Programmer by choice", "1987-10-22", "Object Oriented");
        entityManager.getTransaction().begin();
        entityManager.persist(programmer);
        entityManager.getTransaction().commit();
        //clean up
        final Programmer retrievedProgrammer = entityManager.find(Programmer.class, programmer.getId());
        entityManager.getTransaction().begin();
        entityManager.remove(retrievedProgrammer);
        entityManager.getTransaction().commit();
    }
}
