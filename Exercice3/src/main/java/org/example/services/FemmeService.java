package org.example.services;

import org.example.model.Femme;
import org.example.model.Homme;
import org.example.model.Mariage;
import org.example.dao.IDao;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;

public class FemmeService implements IDao<Femme> {

@Override
public boolean create(Femme o) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    session.save(o);
    session.getTransaction().commit();
    session.close();
    return true;
}

@Override
public boolean update(Femme o) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    session.update(o);
    session.getTransaction().commit();
    session.close();
    return true;
}

@Override
public boolean delete(Femme o) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    session.delete(o);
    session.getTransaction().commit();
    session.close();
    return true;
}

@Override
public Femme findById(int id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Femme femme = session.get(Femme.class, id);
    session.close();
    return femme;
}

@Override
public List<Femme> findAll() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    List<Femme> list = session.createQuery("FROM Femme", Femme.class).list();
    session.close();
    return list;
}

public Long countEnfantsNative(Femme femme, Date dateDebut, Date dateFin) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = session.getNamedQuery("Femme.countEnfantsNative");
    query.setParameter(1, femme.getId());
    query.setParameter(2, dateDebut);
    query.setParameter(3, dateFin);
    Number result = (Number) query.uniqueResult();
    session.close();
    return result != null ? result.longValue() : 0L;
}

public List<Femme> findFemmesMarieesDeuxFoisOuPlus() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query<Femme> query = session.createNamedQuery("Femme.findMarieesDeuxFoisOuPlus", Femme.class);
    List<Femme> femmes = query.list();
    session.close();
    return femmes;
}

public List<Object[]> findHommesMariesAQuatreFemmes(Date dateDebut, Date dateFin) {
    Session session = HibernateUtil.getSessionFactory().openSession();

    CriteriaBuilder cb = session.getCriteriaBuilder();
    CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

    Root<Mariage> mariage = cq.from(Mariage.class);
    Join<Mariage, Homme> homme = mariage.join("homme");

    cq.multiselect(
            homme.get("id"),
            homme.get("nom"),
            homme.get("prenom"),
            cb.count(mariage.get("femme"))
    );

    cq.where(
            cb.between(mariage.get("dateDebut"), dateDebut, dateFin)
    );

    cq.groupBy(homme.get("id"));
    cq.having(cb.equal(cb.count(mariage.get("femme")), 4));

    List<Object[]> result = session.createQuery(cq).list();
    session.close();
    return result;
}

public Femme findFemmePlusAgee() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query<Femme> query = session.createQuery(
            "FROM Femme f ORDER BY f.dateNaissance ASC", Femme.class);
    query.setMaxResults(1);
    Femme femme = query.uniqueResult();
    session.close();
    return femme;
}
}