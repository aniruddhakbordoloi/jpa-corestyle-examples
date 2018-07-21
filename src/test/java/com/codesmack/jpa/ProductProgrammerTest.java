package com.codesmack.jpa;

import com.codesmack.jpa.entity.Product;
import com.codesmack.jpa.entity.Programmer;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertNotNull;

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

    /*@After
    public void destroy() throws SQLException {
        statement.close();
        connection.close();
    }*/

    @AfterClass
    public static void destroyAll() throws SQLException {
        statement.execute("drop table programmer_product");
        statement.execute("drop table Product");
        statement.execute("drop table Programmer");
        statement.close();
        connection.close();
    }

    @Test
    public void thatProductInsertionIsSuccessful() throws SQLException {
        //insert
        final Product product = new Product("Fusion Collections", "My Library", new Float(20.0), new Float(20.0), "Modern");
        entityManager.getTransaction().begin();
        entityManager.persist(product);
        entityManager.getTransaction().commit();
        //clean up
        final Product retrievedProduct = entityManager.find(Product.class, product.getId());
        assertNotNull(retrievedProduct);
        entityManager.getTransaction().begin();
        entityManager.remove(retrievedProduct);
        entityManager.getTransaction().commit();
        //statement.execute("drop table Product");
    }

    @Test
    public void thatProgrammerInsertionIsSuccessful() throws SQLException {
        //insert
        final Programmer programmer = new Programmer("Codesmack", "Programmer by choice", "1987-10-22", "Object Oriented");
        entityManager.getTransaction().begin();
        entityManager.persist(programmer);
        entityManager.getTransaction().commit();
        //clean up
        final Programmer retrievedProgrammer = entityManager.find(Programmer.class, programmer.getId());
        assertNotNull(retrievedProgrammer);
        entityManager.getTransaction().begin();
        entityManager.remove(retrievedProgrammer);
        entityManager.getTransaction().commit();
        //statement.execute("drop table Programmer");
    }

    @Test
    public void thatUnidirectionalOneToManyInsertionIsSuccessful() throws SQLException {
        final com.codesmack.jpa.entity.com.codesmack.jpa.entity.unidirectional.onetomany.Programmer programmer =
                new com.codesmack.jpa.entity.com.codesmack.jpa.entity.unidirectional.onetomany.Programmer("Codesmack", "Programmer by choice", "1987-10-22", "Object Oriented");
        final com.codesmack.jpa.entity.com.codesmack.jpa.entity.unidirectional.onetomany.Product product =
                new com.codesmack.jpa.entity.com.codesmack.jpa.entity.unidirectional.onetomany.Product("Fusion Collections", "My Library", new Float(20.0), new Float(20.0), "Modern");
        final Set<com.codesmack.jpa.entity.com.codesmack.jpa.entity.unidirectional.onetomany.Product> products =
                new HashSet<com.codesmack.jpa.entity.com.codesmack.jpa.entity.unidirectional.onetomany.Product>();
        products.add(product);
        products.add(product);
        programmer.setProducts(products);
        //insert
        entityManager.getTransaction().begin();
        entityManager.persist(programmer);
        entityManager.getTransaction().commit();
        System.out.println(programmer);
        //clean up
        /*final com.codesmack.jpa.entity.com.codesmack.jpa.entity.unidirectional.onetomany.Programmer retrievedProgrammer =
                entityManager.find(com.codesmack.jpa.entity.com.codesmack.jpa.entity.unidirectional.onetomany.Programmer.class, programmer.getId());
        assertNotNull(retrievedProgrammer);
        entityManager.getTransaction().begin();
        entityManager.remove(retrievedProgrammer);
        entityManager.getTransaction().commit();
        statement.execute("drop table Programmer");*/
    }
}
