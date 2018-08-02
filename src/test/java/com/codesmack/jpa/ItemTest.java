package com.codesmack.jpa;

import com.codesmack.jpa.entity.inheritance.single_table.Book;
import com.codesmack.jpa.entity.inheritance.single_table.CD;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.sql.*;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Aniruddha on 23-07-2018.
 */
public class ItemTest {

    private static EntityManager entityManager;
    private static EntityTransaction transaction;
    private static Connection connection;
    private static Statement statement;

    @BeforeClass
    public static void setUp() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jpa_core_style", "root", "root");
        statement = connection.createStatement();
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpaCoreStyle");
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
    }

    @AfterClass
    public static void destroyAll() throws SQLException {
        statement.execute("drop table item_single_table");
        statement.execute("drop table cd_item_concrete_table");
        statement.execute("drop table book_item_concrete_table");
        statement.execute("drop table item_concrete_table");
        statement.execute("drop table cd_item_joined_table");
        statement.execute("drop table book_item_joined_table");
        statement.execute("drop table item_joined_table");
        statement.execute("drop table hibernate_sequences");
        statement.execute("drop table cd_mapped_table");
        statement.execute("drop table book_mapped_table");
        statement.close();
        connection.close();
    }

    @Test
    public void thatCDInsertionWorksWithSingleTableStrategy() {
        final CD cd = new CD("Digitally encoded", "Watch to decode!", new Float(33.50),
                new Float(20), "GEN-Y");
        transaction.begin();
        entityManager.persist(cd);
        transaction.commit();
        final CD retrievedCD = entityManager.find(CD.class, cd.getId());
        assertNotNull(retrievedCD.getTotalDuration());
    }

    @Test
    public void thatBookInsertionWorksWithSingleTableStrategy() {
        final Book book = new Book("Program Detoxification", "A cheat code!", new Float(30), "cdsmck007", 300, new Date(System.currentTimeMillis()));
        transaction.begin();
        entityManager.persist(book);
        transaction.commit();
        final Book retrievedBook = entityManager.find(Book.class, book.getId());
        assertNotNull(retrievedBook.getIsbn());
    }

    @Test
    public void thatCDInsertionWorksWithConcreteTableStrategy() {
        final com.codesmack.jpa.entity.inheritance.table_per_concrete_class.CD cd =
                new com.codesmack.jpa.entity.inheritance.table_per_concrete_class.CD("Digitally encoded", "Watch to decode!", new Float(33.50),
                        new Float(20), "GEN-Y");
        transaction.begin();
        entityManager.persist(cd);
        transaction.commit();
        final com.codesmack.jpa.entity.inheritance.table_per_concrete_class.CD retrievedCD =
                entityManager.find(com.codesmack.jpa.entity.inheritance.table_per_concrete_class.CD.class, cd.getId());
        assertNotNull(retrievedCD.getTotalDuration());
    }

    @Test
    public void thatBookInsertionWorksWithConcreteTableStrategy() {
        final com.codesmack.jpa.entity.inheritance.table_per_concrete_class.Book book =
                new com.codesmack.jpa.entity.inheritance.table_per_concrete_class.Book("Program Detoxification", "A cheat code!", new Float(30), "cdsmck007", 300, new Date(System.currentTimeMillis()));
        transaction.begin();
        entityManager.persist(book);
        transaction.commit();
        final com.codesmack.jpa.entity.inheritance.table_per_concrete_class.Book retrievedBook =
                entityManager.find(com.codesmack.jpa.entity.inheritance.table_per_concrete_class.Book.class, book.getId());
        assertNotNull(retrievedBook.getIsbn());
    }

    @Test
    public void thatCDInsertionWorksWithJoinedSubclassStrategy() {
        final com.codesmack.jpa.entity.inheritance.joined_subclass.CD cd =
                new com.codesmack.jpa.entity.inheritance.joined_subclass.CD("Digitally encoded", "Watch to decode!", new Float(33.50),
                        new Float(20), "GEN-Y");
        transaction.begin();
        entityManager.persist(cd);
        transaction.commit();
        final com.codesmack.jpa.entity.inheritance.joined_subclass.CD retrievedCD =
                entityManager.find(com.codesmack.jpa.entity.inheritance.joined_subclass.CD.class, cd.getId());
        assertNotNull(retrievedCD.getTotalDuration());
    }

    @Test
    public void thatBookInsertionWorksWithJoinedSubclassStrategy() {
        final com.codesmack.jpa.entity.inheritance.joined_subclass.Book book =
                new com.codesmack.jpa.entity.inheritance.joined_subclass.Book("Program Detoxification", "A cheat code!", new Float(30), "cdsmck007", 300, new Date(System.currentTimeMillis()));
        transaction.begin();
        entityManager.persist(book);
        transaction.commit();
        final com.codesmack.jpa.entity.inheritance.joined_subclass.Book retrievedBook =
                entityManager.find(com.codesmack.jpa.entity.inheritance.joined_subclass.Book.class, book.getId());
        assertNotNull(retrievedBook.getIsbn());
    }

    @Test
    public void thatCDInsertionWorksWithMappedSuperClass() {
        final com.codesmack.jpa.entity.inheritance.mapped_superclass.CD cd =
                new com.codesmack.jpa.entity.inheritance.mapped_superclass.CD("Digitally encoded", "Watch to decode!", new Float(33.50),
                        new Float(20), "GEN-Y");
        transaction.begin();
        entityManager.persist(cd);
        transaction.commit();
        final com.codesmack.jpa.entity.inheritance.mapped_superclass.CD retrievedCD =
                entityManager.find(com.codesmack.jpa.entity.inheritance.mapped_superclass.CD.class, cd.getId());
        assertNotNull(retrievedCD.getTotalDuration());
    }

    @Test
    public void thatBookInsertionWorksWithMappedSuperClass() {
        final com.codesmack.jpa.entity.inheritance.mapped_superclass.Book book =
                new com.codesmack.jpa.entity.inheritance.mapped_superclass.Book("Program Detoxification", "A cheat code!", new Float(30), "cdsmck007", 300, new Date(System.currentTimeMillis()));
        transaction.begin();
        entityManager.persist(book);
        transaction.commit();
        final com.codesmack.jpa.entity.inheritance.mapped_superclass.Book retrievedBook =
                entityManager.find(com.codesmack.jpa.entity.inheritance.mapped_superclass.Book.class, book.getId());
        assertNotNull(retrievedBook.getIsbn());
    }
}
