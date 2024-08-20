package Controller;

import jakarta.persistence.TypedQuery;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.xml.transform.Source;
import java.util.List;

public class UserController {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();

//        addUser(factory,session);
//        findUser(factory,session,1);
//        findUserHql(factory,session);
//        updateUser(session,3);
//        deleteUser(session,4);
//        getRecordById(factory,session);
//        getRecords(session);
        //getMaxSalary(session);
//        getMaxSalaryGroupBy();
        namedQueryExample(session);

    }

    public static void addUser(SessionFactory factory, Session session) {

        Transaction transaction = session.beginTransaction();
        User uOne = new User();
        uOne.setEmail("haseeb@gmail.com");
        uOne.setFullName("Moh Haseeb");
        uOne.setPassword("has123");
        uOne.setSalary(2000.69);
        uOne.setAge(20);
        uOne.setCity("NYC");


        User uTwo = new User();
        uOne.setEmail("james@gmail.com");
        uOne.setFullName("James Santana");
        uOne.setPassword("james123");
        uOne.setSalary(2060.69);
        uOne.setAge(25);
        uOne.setCity("Dallas");

        User uThree = new User();
        uThree.setEmail("shahparan@gmail.com");
        uThree.setFullName("AH Shahparan");
        uThree.setPassword("Shahparan123");
        uThree.setSalary(2060.69);
        uThree.setAge(30);
        uThree.setCity("Chicago");

        // Pass values by using a constructor
        User uFour = new User("Christ", "Christ@gmail.com", "147852", 35, 35000.3, "NJ");
        User uFive = new User("Sid", "Sid", "s258", 29, 4000.36, "LA");

        //Integer userid = null;
        session.persist(uOne);
        session.persist(uTwo);
        session.persist(uThree);
        session.persist(uFour);
        session.persist(uFive);

        transaction.commit();
        System.out.println("Successfully saved");
        factory.close();
        session.close();
    }

    public static void findUser(SessionFactory factory, Session session, int userId) {
        Transaction tx = session.beginTransaction();

        User u = session.get(User.class, userId);
        System.out.println("FullNAme: " + u.getFullName());
        System.out.println("Email: " + u.getEmail());
        System.out.println("password: " + u.getPassword());

        // CLose resources
        tx.commit();
        factory.close();
        session.close();

    }

    public static void updateUser(Session session, int userId) {

        Transaction tx = session.beginTransaction();
        User u = new User();
        u.setId(userId);
        u.setEmail("mhaseeb@perscholas");
        u.setFullName("M Haseeb");
        u.setPassword("123456");
        session.merge(u);
        session.getTransaction().commit();
        session.close();
    }

    public static void deleteUser(Session session, int userId) {

        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Transaction tx = session.beginTransaction();
        User u = new User();
        u.setId(userId);
        session.remove(u);
        tx.commit();
        session.close();
        factory.close();
    }

    public static void findUserHql(SessionFactory factory, Session session) {
        String hqlFrom = "FROM User"; //Example of HQL to get all records of user class

        String hqlSelect = "SELECT u FROM User u";
        TypedQuery<User> query = session.createQuery(hqlSelect, User.class);
        List<User> results = query.getResultList();

        System.out.printf("%s%13s%17s%34s%n", "|User Id", "|Full name", "|Email", "|Password");

        for (User u : results) {
            System.out.printf("%-10d %-20s %-30s %s %n", u.getID(), u.getFullName(), u.getEmail(), u.getPassword());
        }
    }

    public static void getRecordById(SessionFactory factory, Session session) {
        String hql = "FROM User u Where u.id > 2 ORDER BY u.salary DESC";
        TypedQuery<User> query = session.createQuery(hql, User.class);
        List<User> results = query.getResultList();

        System.out.printf("%s%13s%17s%34s%21s%n", "|User Id", "|Full name", "|Email", "|Password", "|Salary");

        for (User u : results) {
            System.out.printf(" %-10d %-20s %-30s %-23s %s %n", u.getID(), u.getFullName(), u.getEmail(), u.getPassword(), u.getSalary());
        }
    }

    public static void getRecords(Session session) {
        TypedQuery<Object[]> query = session.createQuery(
                "SELECT U.salary, U.fullName FROM User AS U", Object[].class);
        List<Object[]> results = query.getResultList();
        System.out.printf("%s%13s%n", "Salary", "City");
        for (Object[] a : results) {
            System.out.printf("%-16s%s%n", a[0], a[1]);
        }
    }

    public static void getMaxSalary(Session session) {
        String hql = "SELECT max(U.salary) FROM User U";
        TypedQuery<Object> query = session.createQuery(hql, Object.class);
        Object result = query.getSingleResult();
        System.out.printf("%s%s", "Maximum Salary:", result);
    }

//        String hqlCount = "SELECT COUNT(*) FROM User U";
//        List<Object> results = session.createQuery(hqlCount, Object.class).getResultList();
//        System.out.println("Count:" + results);
//    }
//

    public static void   getMaxSalaryGroupBy() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        String hql = "SELECT SUM(U.salary), U.city FROM User U GROUP BY U.city";
        TypedQuery query = session.createQuery(hql);
        List<Object[]> result =query.getResultList();
        for (Object[] o : result) {
            System.out.println("Total salary " +o[0] +" | city: "+ o[1] );
        }
    }

    public static void namedQueryExample(Session session) {
        String hql = "FROM User u WHERE u.id = :id";
        TypedQuery<User> query = session.createQuery(hql, User.class);
        query.setParameter("id", 1);
        List<User> result = query.getResultList();

        System.out.printf("%s%13s%17s%34s%21s%n", "|User Id", "|Full name", "|Email", "|Password", "|Salary");
        for (User u : result) {
            System.out.printf(" %-10d %-20s %-30s %-23s %s %n", u.getID(), u.getFullName(), u.getEmail(), u.getPassword(), u.getSalary());
        }
    }


}
