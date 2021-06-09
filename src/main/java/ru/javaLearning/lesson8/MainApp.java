package ru.javaLearning.lesson8;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class MainApp {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        Session session = null;
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();

            var studentsRepository = new Repository<>(Student.class, session);

            var student = new Student("Bob");

            studentsRepository.save(student);

            student.print();

            student = studentsRepository.get(1L);

            student.print();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (factory != null) {
                factory.close();
            }

            if (session != null) {
                session.close();
            }
        }
    }
}
