package org.example.services;

import org.example.model.Homme;
import org.example.model.Mariage;
import org.example.dao.IDao;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class HommeService implements IDao<Homme> {

@Override
public boolean create(Homme o) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    session.save(o);
    session.getTransaction().commit();
    session.close();
    return true;
}

@Override
public boolean update(Homme o) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    session.update(o);
    session.getTransaction().commit();
    session.close();
    return true;
}

@Override
public boolean delete(Homme o) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    session.delete(o);
    session.getTransaction().commit();
    session.close();
    return true;
}

@Override
public Homme findById(int id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Homme homme = session.get(Homme.class, id);
    session.close();
    return homme;
}

@Override
public List<Homme> findAll() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    List<Homme> list = session.createQuery("FROM Homme", Homme.class).list();
    session.close();
    return list;
}

public List<Mariage> findEpousesEntreDates(Homme homme, Date dateDebut, Date dateFin) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query<Mariage> query = session.createQuery(
            "FROM Mariage m WHERE m.homme.id = :hommeId AND m.dateDebut BETWEEN :debut AND :fin", Mariage.class);
    query.setParameter("hommeId", homme.getId());
    query.setParameter("debut", dateDebut);
    query.setParameter("fin", dateFin);
    List<Mariage> mariages = query.list();
    session.close();
    return mariages;
}

public void afficherMariages(Homme homme) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Homme h = session.get(Homme.class, homme.getId());

    System.out.println("Nom : " + h.getNom() + " " + h.getPrenom());

    System.out.println("Mariages En Cours :");
    int i = 1;
    for (Mariage m : h.getMariages()) {
        if (m.getDateFin() == null) {
            System.out.println(i + ". Femme : " + m.getFemme().getNom() + " " + m.getFemme().getPrenom() +
                    "   Date Début : " + m.getDateDebut() + "    Nbr Enfants : " + m.getNbrEnfant());
            i++;
        }
    }

    System.out.println("\nMariages échoués :");
    i = 1;
    for (Mariage m : h.getMariages()) {
        if (m.getDateFin() != null) {
            System.out.println(i + ". Femme : " + m.getFemme().getNom() + " " + m.getFemme().getPrenom() +
                    "  Date Début : " + m.getDateDebut() +
                    "    Date Fin : " + m.getDateFin() + "    Nbr Enfants : " + m.getNbrEnfant());
            i++;
        }
    }
    session.close();
}
}