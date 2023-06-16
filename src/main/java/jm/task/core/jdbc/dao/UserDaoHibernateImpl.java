package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getConnection2();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        Session session = sessionFactory.openSession();
        try  {
            transaction = session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS User" +
                    "(id BIGINT not null auto_increment," +
                    " name TEXT, " +
                    "lastname TEXT, " +
                    "age INT, " +
                    "PRIMARY KEY (id))";
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            System.out.println("не созда");
            if (transaction != null) {
                transaction.rollback();
            }
        }
        finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        Session session = sessionFactory.openSession();
        try  {
            transaction = session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS User";
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            System.out.println("не удал все");
            if (transaction != null) {
                transaction.rollback();
            }
        }
        finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        Session session = sessionFactory.openSession();
        try  {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (HibernateException e) {
            System.out.println("не добавл");
            if (transaction != null) {
                transaction.rollback();
            }
        }
        finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        Session session = sessionFactory.openSession();
        try {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
        } catch (HibernateException e) {
            System.out.println("не удал");
            if (transaction != null) {
                transaction.rollback();
            }
        }
        finally {
            session.close();
        }

    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        Session session = sessionFactory.openSession();
        List<User> users = null;
        try {
            transaction = session.beginTransaction();
            users = session.createQuery("select p from User p", User.class)
                    .getResultList();
        } catch (HibernateException e) {
            System.out.println("не выводит всех");
            if (transaction != null) {
                transaction.rollback();
            }
        }
        finally {
            session.close();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        Session session = sessionFactory.openSession();
        try  {
            String sql = "TRUNCATE TABLE User";
            transaction = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            System.out.println("не очищ");
            if (transaction != null) {
                transaction.rollback();
            }
        }
        finally {
            session.close();
        }
    }
}
