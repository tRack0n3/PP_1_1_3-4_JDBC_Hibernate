package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;
import org.hibernate.Session;
import javax.persistence.criteria.CriteriaQuery;


import java.util.List;


public class UserDaoHibernateImpl extends Util implements UserDao {
    
    private final SessionFactory sessionFactory = Util.getSessionFactory();


    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {

        String sqlQuery = "CREATE TABLE IF NOT EXISTS users " +
                "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(64), lastname VARCHAR(64), age TINYINT)";
        
        Transaction transaction = null;

        try (Session openSession = sessionFactory.openSession()) {
            transaction = openSession.beginTransaction();
            openSession.createNativeQuery(sqlQuery).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {

        String sqlQuery = "DROP TABLE IF EXISTS users";
        Transaction transaction = null;

        try (Session openSession = sessionFactory.openSession()) {
            transaction = openSession.beginTransaction();
            openSession.createNativeQuery(sqlQuery).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        Transaction transaction = null;

        try (Session openSession = sessionFactory.openSession()) {
            transaction = openSession.beginTransaction();
            openSession.save(new User(name, lastName, age));
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeUserById(long id) {

        Transaction transaction = null;

        try (Session openSession = sessionFactory.openSession()) {
            transaction = openSession.beginTransaction();
            openSession.delete(openSession.get(User.class, id));
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {

        Session openSession = sessionFactory.openSession();
        CriteriaQuery<User> criteriaQuery = openSession.getCriteriaBuilder().createQuery(User.class);
        criteriaQuery.from(User.class);
        Transaction transaction = openSession.beginTransaction();
        List<User> list = openSession.createQuery(criteriaQuery).getResultList();

        try {
            transaction.commit();
            return list;
        } catch (HibernateException e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            openSession.close();
        }
        return list;

    }

    @Override
    public void cleanUsersTable() {

        String sqlQuery = "TRUNCATE users";
        Transaction transaction = null;

        try (Session openSession = sessionFactory.openSession()) {
            transaction = openSession.beginTransaction();
            openSession.createNativeQuery(sqlQuery).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}

