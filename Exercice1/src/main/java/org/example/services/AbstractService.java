package org.example.services;


import java.util.List;

import org.example.dao.IDao;
import org.example.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public abstract class AbstractService<T> implements org.example.dao.IDao<T> {

    private final Class<T> entityClass;

    public AbstractService(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public boolean create(T o) {
        Session session = null;
        Transaction tx = null;
        boolean etat = false;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            session.save(o);

            tx.commit();
            etat = true;
        } catch (HibernateException exception) {
            if (tx != null) {
                tx.rollback();
            }
            exception.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return etat;
    }

    @Override
    public boolean delete(T o) {
        Session session = null;
        Transaction tx = null;
        boolean etat = false;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            session.delete(o);

            tx.commit();
            etat = true;
        } catch (HibernateException exception) {
            if (tx != null) {
                tx.rollback();
            }
            exception.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return etat;
    }

    @Override
    public boolean update(T o) {
        Session session = null;
        Transaction tx = null;
        boolean etat = false;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            session.update(o);

            tx.commit();
            etat = true;
        } catch (HibernateException exception) {
            if (tx != null) {
                tx.rollback();
            }
            exception.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return etat;
    }

    @Override
    public T findById(int id) {
        Session session = null;
        Transaction tx = null;
        T object = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            object = (T) session.get(entityClass, id);
            tx.commit();
        } catch (HibernateException exception) {
            if (tx != null) {
                tx.rollback();
            }
            exception.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return object;
    }

    @Override
    public List<T> findAll() {
        Session session = null;
        Transaction tx = null;
        List<T> objects = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            objects = session.createQuery("from " + entityClass.getSimpleName()).list();

            tx.commit();
        } catch (HibernateException exception) {
            if (tx != null) {
                tx.rollback();
            }
            exception.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return objects;
    }
}