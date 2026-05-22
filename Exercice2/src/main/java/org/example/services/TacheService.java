package org.example.services;

import org.example.model.EmployeTache;
import org.example.model.Tache;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class TacheService extends AbstractService<Tache> {

    public TacheService() {
        super(Tache.class);
    }

    public List<Tache> findByPrixSuperieurA1000() {
        Session session = null;
        List<Tache> taches = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Tache> query = session.createNamedQuery("Tache.findByPrixSuperieur", Tache.class);
            query.setParameter("prix", 1000.0);
            taches = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return taches;
    }


    public List<Tache> findTachesRealiseesEntreDates(Date dateDebut, Date dateFin) {
        Session session = null;
        List<Tache> taches = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Tache> query = session.createQuery(
                    "SELECT DISTINCT et.tache FROM EmployeTache et " +
                            "WHERE et.dateDebutReelle >= :debut AND et.dateFinReelle <= :fin", Tache.class);
            query.setParameter("debut", dateDebut);
            query.setParameter("fin", dateFin);
            taches = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return taches;
    }
}