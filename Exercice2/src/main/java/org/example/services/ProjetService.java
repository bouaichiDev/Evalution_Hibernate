package org.example.services;

import org.example.model.EmployeTache;
import org.example.model.Projet;
import org.example.model.Tache;
import org.example.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class ProjetService extends AbstractService<Projet> {

public ProjetService() {
    super(Projet.class);
}

public void afficherTachesPlanifiees(Projet projet) {
    Session session = null;

    try {
        session = HibernateUtil.getSessionFactory().openSession();
        Projet proj = session.get(Projet.class, projet.getId());

        System.out.println("Projet : " + proj.getId() + "     Nom : " + proj.getNom());
        System.out.println("Date début : " + proj.getDateDebut() + "   Date fin : " + proj.getDateFin());
        System.out.println("Liste des tâches planifiées :");
        System.out.println("Num  Nom           Date Début   Date Fin     Prix");

        for (Tache t : proj.getTaches()) {
            System.out.printf("%-4d %-13s %-12s %-12s %.2f DH%n",
                    t.getId(),
                    t.getNom(),
                    t.getDateDebut(),
                    t.getDateFin(),
                    t.getPrix());
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (session != null) session.close();
    }
}

public void afficherTachesRealiseesAvecDatesReelles(Projet projet) {
    Session session = null;

    try {
        session = HibernateUtil.getSessionFactory().openSession();
        Projet proj = session.get(Projet.class, projet.getId());

        System.out.println("Projet : " + proj.getId() +
                "     Nom : " + proj.getNom() +
                "     Date début : " + proj.getDateDebut());
        System.out.println("Liste des tâches:");
        System.out.println("Num  Nom           Date Début Réelle   Date Fin Réelle");

        for (Tache t : proj.getTaches()) {
            for (EmployeTache et : t.getEmployeTaches()) {
                System.out.printf("%-4d %-13s %-20s %s%n",
                        t.getId(),
                        t.getNom(),
                        et.getDateDebutReelle(),
                        et.getDateFinReelle());
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (session != null) session.close();
    }
}
}