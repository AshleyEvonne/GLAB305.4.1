package Controller;

import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.xml.transform.Source;

public class UserController {
    public static void main(String[] args){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();

//        addUser(factory,session);
//        findUser(factory,session,1);
//        updateUser(session,3);
        deleteUser(session,4);

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
        User uFour = new User("Christ", "Christ@gmail.com","147852", 35, 35000.3, "NJ");
        User uFive= new User("Sid","Sid","s258",29,4000.36,"LA");

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
    public static void findUser(SessionFactory factory,Session session, int userId){
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
    public static void updateUser(Session session, int userId){

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
    public static void deleteUser(Session session, int userId){

        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Transaction tx = session.beginTransaction();
        User u = new User();
        u.setId(userId);
        session.remove(u);
        tx.commit();
        session.close();
        factory.close();
    }



}
