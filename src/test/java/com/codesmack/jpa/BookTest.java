package com.codesmack.jpa;

import com.codesmack.jpa.entity.BookEntity;
import com.codesmack.jpa.entity.BookEntityWithGenerator;
import com.codesmack.jpa.entity.BookNative;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Aniruddha on 14-07-2018.
 */
public class BookTest {

    private Connection connection;
    private Statement statement;
    private EntityManager entityManager;

    @Before
    public void setUp() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jpa_core_style", "root", "root");
        statement = connection.createStatement();
        //the following snippet looks for persistence.xml by default.
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpaCoreStyle");
        entityManager = entityManagerFactory.createEntityManager();
    }

    @After
    public void destroy() throws SQLException {
        statement.close();
        connection.close();
    }

    @Test
    public void thatJDBCInsertionSuccessful() throws SQLException {
        final String createQuery = "create table Book(ID bigint(255) not null auto_increment, TITLE VARCHAR(40), DESCRIPTION varchar(1000), UNIT_COST float(100,4), ISBN varchar(1000), NBR_OF_PAGE bigint(255), primary key (ID));";
        statement.execute(createQuery);
        final String insertQuery = "insert into book (id, title, description, unit_cost, isbn, nbr_of_page) values " +
                "(?, ?, ?, ?, ?, ?)";
        final BookNative book = new BookNative(new Long(1), "Codesmack's", "My Library", new Float(10), "123", 200);
        getPreparedStatement(insertQuery, book).executeUpdate();
        final ResultSet rs = statement.executeQuery("select * from book");
        while (rs.next()) {
            assertNotNull(rs);
            System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
        }
        statement.execute("delete from book");
        statement.execute("drop table book");
    }

    @Test
    public void thatEntityInsertionIsSuccessful() throws SQLException {
        final BookEntity bookEntity = new BookEntity(new Long(1), "Codesmack's", "My Library", new Float(10), "123", 200);
        entityManager.getTransaction().begin();
        entityManager.persist(bookEntity);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        //final BookEntity retrievedBook = entityManager.find(BookEntity.class, bookEntity);This doesn't work.
        final BookEntity retrievedBook = entityManager.find(BookEntity.class, bookEntity.getId());
        assertEquals("My Library", retrievedBook.getDescription());
        entityManager.remove(retrievedBook);
        entityManager.getTransaction().commit();
        statement.execute("drop table BookEntity");
    }

    @Test
    public void thatEntityInsertionIsSuccessfulForGeneratorType() throws SQLException {//this needs an auto increment PK in the DB.
        final BookEntityWithGenerator bookEntity = new BookEntityWithGenerator("Codesmack's", "My Library", new Float(10), "123", 200);
        entityManager.getTransaction().begin();
        entityManager.persist(bookEntity);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        final BookEntityWithGenerator retrievedBook = entityManager.find(BookEntityWithGenerator.class, bookEntity.getId());
        assertEquals("My Library", retrievedBook.getDescription());
        entityManager.remove(retrievedBook);
        entityManager.getTransaction().commit();
        statement.execute("drop table BookEntityWithGenerator");
    }

    @Test
    public void thatDetachedEntityDeletionIsSuccessful() throws SQLException {
        final BookEntity bookEntity = new BookEntity(new Long(1), "Codesmack's", "My Library", new Float(10), "123", 200);
        entityManager.getTransaction().begin();
        entityManager.persist(bookEntity);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        final BookEntity bookEntityToMerge = new BookEntity(new Long(1), "Codesmack's", "My Library", new Float(10), "123", 200);
        final BookEntity bookToBeDeleted = entityManager.merge(bookEntityToMerge);
        entityManager.remove(bookToBeDeleted);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        final BookEntity retrievedBook = entityManager.find(BookEntity.class, bookEntity.getId());
        assertNull(retrievedBook);
        entityManager.getTransaction().commit();
        statement.execute("drop table BookEntity");
    }

    private PreparedStatement getPreparedStatement(final String insertQuery, final BookNative book) throws SQLException {
        final PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
        preparedStatement.setLong(1, book.getId());
        preparedStatement.setString(2, book.getTitle());
        preparedStatement.setString(3, book.getDescription());
        preparedStatement.setFloat(4, book.getUnitCost());
        preparedStatement.setString(5, book.getIsbn());
        preparedStatement.setInt(6, book.getNbrOfPage());
        return preparedStatement;
    }
}
