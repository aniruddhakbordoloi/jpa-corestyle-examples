package com.codesmack.jpa;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class ProductProgrammerTest {
    private static EntityManager entityManager;
    private static Connection connection;
    private static Statement statement;

    @BeforeClass
    public static void setUp() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jpa_core_style", "root", "root");
        statement = connection.createStatement();
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpaCoreStyle");
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterClass
    public static void destroyAll() throws SQLException {
        statement.execute("drop table programmer_product");
        statement.execute("drop table Product");
        statement.execute("drop table Programmer");
        statement.execute("drop table product_unidir_join_column");
        statement.execute("drop table programmer_unidir_join_column");
        statement.close();
        connection.close();
    }

    @Test
    public void thatUnidirectionalOneToManyInsertionIsSuccessful() throws SQLException {
        final com.codesmack.jpa.entity.unidirectional.onetomany.jointable.Programmer programmer =
                new com.codesmack.jpa.entity.unidirectional.onetomany.jointable.Programmer("Codesmack", "Programmer by choice", "1987-10-22", "Object Oriented");
        final com.codesmack.jpa.entity.unidirectional.onetomany.jointable.Product product =
                new com.codesmack.jpa.entity.unidirectional.onetomany.jointable.Product("Fusion Collections", "My Library", new Float(20.0), new Float(20.0), "Modern");
        final Set<com.codesmack.jpa.entity.unidirectional.onetomany.jointable.Product> products =
                new HashSet<com.codesmack.jpa.entity.unidirectional.onetomany.jointable.Product>();
        products.add(product);
        products.add(product);
        programmer.setProducts(products);
        entityManager.getTransaction().begin();
        entityManager.persist(programmer);
        entityManager.getTransaction().commit();
        final com.codesmack.jpa.entity.unidirectional.onetomany.jointable.Programmer retrievedProgrammer
                = entityManager.find(com.codesmack.jpa.entity.unidirectional.onetomany.jointable.Programmer.class, programmer.getId());
        assertTrue(retrievedProgrammer.getProducts().size()> 0);
    }

    @Test
    public void thatUnidirectionalOneToManyInsertionIsSuccessfulForJoinColumn() throws SQLException {
        final com.codesmack.jpa.entity.unidirectional.onetomany.joincolumn.Programmer programmer =
                new com.codesmack.jpa.entity.unidirectional.onetomany.joincolumn.Programmer("Codesmack", "Programmer by choice", "1987-10-22", "Object Oriented");
        final com.codesmack.jpa.entity.unidirectional.onetomany.joincolumn.Product product =
                new com.codesmack.jpa.entity.unidirectional.onetomany.joincolumn.Product("Fusion Collections", "My Library", new Float(20.0), new Float(20.0), "Modern");
        final Set<com.codesmack.jpa.entity.unidirectional.onetomany.joincolumn.Product> products =
                new HashSet<com.codesmack.jpa.entity.unidirectional.onetomany.joincolumn.Product>();
        products.add(product);
        products.add(product);
        programmer.setProducts(products);
        entityManager.getTransaction().begin();
        entityManager.persist(programmer);
        entityManager.getTransaction().commit();
        final com.codesmack.jpa.entity.unidirectional.onetomany.joincolumn.Programmer retrievedProgrammer
                = entityManager.find(com.codesmack.jpa.entity.unidirectional.onetomany.joincolumn.Programmer.class, programmer.getId());
        assertTrue(retrievedProgrammer.getProducts().size()> 0);
    }
}
