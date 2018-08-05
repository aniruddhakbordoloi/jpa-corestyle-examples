package com.codesmack.jpa;

import com.codesmack.jpa.entity.callback.EmailSender;
import com.codesmack.jpa.entity.callback.Product;
import com.codesmack.jpa.entity.callback.Programmer;
import org.junit.*;
import org.junit.rules.ExpectedException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Aniruddha on 04-08-2018.
 */
public class CallbackTest {

    private static EntityManager entityManager;
    private static Connection connection;
    private static Statement statement;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
        statement.execute("delete from programmer_callback_product_callback");
        statement.execute("delete from product_callback");
        statement.execute("delete from programmer_callback");
        statement.execute("drop table programmer_callback_product_callback");
        statement.execute("drop table product_callback");
        statement.execute("drop table programmer_callback");
        statement.close();
        connection.close();
    }

    @After
    public void handleTransaction() {
        if(transaction().isActive()) {
            transaction().rollback();
        }
    }

    private EntityTransaction transaction() {
        return entityManager.getTransaction();
    }

    @Test
    public void thatCallbackAnnotationSuccessfullyAppliedOnPrePersistEvent() {
        System.out.println("****************Test Started for Pre-Persist Callback event****************");
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Date of Birth can not be null.");
        final Programmer programmer = new Programmer("Codesmack", "Programmer by choice", null, "Object Oriented");
        final Product product = new Product("Fusion Collections", "My Library", new Float(20.0), new Float(20.0), "Modern");
        final Set<Product> products = new HashSet<Product>();
        products.add(product);
        products.add(product);
        programmer.setProducts(products);
        transaction().begin();
        entityManager.persist(programmer);
        transaction().commit();
        System.out.println("****************Test Ended for Pre-Persist Callback event****************");
    }

    @Test
    public void thatCallbackAnnotationSuccessfullyAppliedOnPostPersistEvent() {
        System.out.println("****************Test Started for Post-Persist Callback event****************");
        final Programmer programmer = new Programmer("Codesmack", "Programmer by choice", "1987-10-22", "Object Oriented");
        final Product product = new Product("Fusion Collections", "My Library", new Float(20.0), new Float(20.0), "Modern");
        final Set<Product> products = new HashSet<Product>();
        products.add(product);
        products.add(product);
        programmer.setProducts(products);
        transaction().begin();
        entityManager.persist(programmer);
        transaction().commit();
        final Programmer retrievedProgrammer = entityManager.find(Programmer.class, programmer.getId());
        assertThat(retrievedProgrammer.getDob(), is("1987-10-22"));
        assertThat(EmailSender.getEmailContent(), is("Email Sent for persistence"));
        System.out.println("****************Test Ended for Post-Persist Callback event****************");
    }

    @Test
    public void thatCallbackAnnotationSuccessfullyAppliedOnLoadEvent() {
        System.out.println("****************Test Started for Post Load Callback event****************");
        final Programmer programmer = new Programmer("Codesmack", "Programmer by choice", "1987-10-22", "Object Oriented");
        final Product product = new Product("Fusion Collections", "My Library", new Float(20.0), new Float(20.0), "Modern");
        final Set<Product> products = new HashSet<Product>();
        products.add(product);
        products.add(product);
        programmer.setProducts(products);
        transaction().begin();
        entityManager.persist(programmer);
        transaction().commit();

        final Programmer retrievedProgrammer = entityManager.find(Programmer.class, programmer.getId());
        //post load needs a refresh
        entityManager.refresh(retrievedProgrammer);
        assertThat(retrievedProgrammer.getAge(), is(30));
        System.out.println("****************Test Ended for Post Load Callback event****************");
    }

    @Test
    public void thatCallbackAnnotationSuccessfullyAppliedOnPreUpdateEvent() {
        System.out.println("****************Test Started for Pre-Update Callback event****************");
        final Programmer programmer = new Programmer("Codesmack", "Programmer by choice", "1987-10-22", "Object Oriented");
        final Product product = new Product("Fusion Collections", "My Library", new Float(20.0), new Float(20.0), "Modern");
        final Set<Product> products = new HashSet<Product>();
        products.add(product);
        products.add(product);
        programmer.setProducts(products);
        transaction().begin();
        entityManager.persist(programmer);
        transaction().commit();

        thrown.expectCause(new CauseMatcher(RuntimeException.class, "Date of Birth can not be null while updating."));
        transaction().begin();
        final Programmer retrievedProgrammer = entityManager.find(Programmer.class, programmer.getId());
        retrievedProgrammer.setDob(null);
        transaction().commit();
        System.out.println("****************Test Ended for Pre-Update Callback event****************");
    }

    @Test
    public void thatCallbackAnnotationSuccessfullyAppliedOnPostUpdateEvent() {
        System.out.println("****************Test Started for Post-Update Callback event****************");
        final Programmer programmer = new Programmer("Codesmack", "Programmer by choice", "1987-10-22", "Object Oriented");
        final Product product = new Product("Fusion Collections", "My Library", new Float(20.0), new Float(20.0), "Modern");
        final Set<Product> products = new HashSet<Product>();
        products.add(product);
        products.add(product);
        programmer.setProducts(products);
        transaction().begin();
        entityManager.persist(programmer);
        transaction().commit();

        transaction().begin();
        final Programmer ooProgrammer = entityManager.find(Programmer.class, programmer.getId());
        ooProgrammer.setLanguage("Functional");
        transaction().commit();

        transaction().begin();
        final Programmer retrievedProgrammer = entityManager.find(Programmer.class, programmer.getId());
        assertThat(retrievedProgrammer.getLanguage(), is("Functional"));
        assertThat(retrievedProgrammer.getDob(), is("1987-10-21"));//per post update event
        System.out.println("****************Test Ended for Post-Update Callback event****************");
    }

    @Test
    public void thatCallbackAnnotationSuccessfullyAppliedOnPreRemoveEvent() {
        System.out.println("****************Test Started for Pre-Remove Callback event****************");
        final Programmer programmer = new Programmer("Codesmack", "Programmer by choice", "1987-10-22", "Object Oriented");
        final Product product = new Product("Fusion Collections", "My Library", new Float(20.0), new Float(20.0), "Modern");
        final Set<Product> products = new HashSet<Product>();
        products.add(product);
        products.add(product);
        programmer.setProducts(products);
        transaction().begin();
        entityManager.persist(programmer);
        transaction().commit();

        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Primary Id can't be null when performing a remove operation.");
        transaction().begin();
        final Programmer ooProgrammer = entityManager.find(Programmer.class, programmer.getId());
        ooProgrammer.setId(null);
        entityManager.remove(ooProgrammer);
        System.out.println("****************Test Ended for Pre-Remove Callback event****************");
    }

    @Test
    public void thatCallbackAnnotationSuccessfullyAppliedOnPostRemoveEvent() {
        System.out.println("****************Test Started for Post-Remove Callback event****************");
        final Programmer programmer = new Programmer("Codesmack", "Programmer by choice", "1987-10-22", "Object Oriented");
        final Product product = new Product("Fusion Collections", "My Library", new Float(20.0), new Float(20.0), "Modern");
        final Set<Product> products = new HashSet<Product>();
        products.add(product);
        products.add(product);
        programmer.setProducts(products);
        transaction().begin();
        entityManager.persist(programmer);
        transaction().commit();

        transaction().begin();
        final Programmer ooProgrammer = entityManager.find(Programmer.class, programmer.getId());
        entityManager.remove(ooProgrammer);
        transaction().commit();

        assertThat(EmailSender.getEmailContent(), is("Programmer remove notification sent"));
        System.out.println("****************Test Ended for Post-Remove Callback event****************");
    }
}
