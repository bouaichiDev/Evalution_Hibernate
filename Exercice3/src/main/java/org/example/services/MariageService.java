package org.example.services;

import org.example.model.Mariage;
import org.example.dao.IDao;
import org.example.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class MariageService implements IDao<Mariage> {

@Override
public boolean create(Mariage o) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    session.save(o);
    session.getTransaction().commit();
    session.close();
    return true;
}

@Override
public boolean update(Mariage o) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    session.update(o);
    session.getTransaction().commit();
    session.close();
    return true;
}

@Override
public boolean delete(Mariage o) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    session.delete(o);
    session.getTransaction().commit();
    session.close();
    return true;
}

@Override
public Mariage findById(int id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Mariage mariage = session.get(Mariage.class, id);
    session.close();
    return mariage;
}

@Override
public List<Mariage> findAll() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    List<Mariage> list = session.createQuery("FROM Mariage", Mariage.class).list();
    session.close();
    return list;
}
}