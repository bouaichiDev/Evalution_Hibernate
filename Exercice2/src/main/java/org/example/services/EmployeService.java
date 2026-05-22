package org.example.services;

import org.example.model.Employe;
import org.example.model.EmployeTache;
import org.example.model.Projet;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class EmployeService extends AbstractService<Employe> {

public EmployeService() {
    super(Employe.class);
}

public void afficherTachesRealisees(Employe employe) {
    Session session = null;

    try {
        session = HibernateUtil.getSessionFactory().openSession();
        Employe emp = session.get(Employe.class, employe.getId());

        System.out.println("Employé : " + emp.getNom() + " " + emp.getPrenom());
        System.out.println("Liste des tâches réalisées :");
        System.out.println("Num  Nom           Date Début Réelle   Date Fin Réelle");

        for (EmployeTache et : emp.getEmployeTaches()) {
            System.out.printf("%-4d %-13s %-20s %s%n",
                    et.getTache().getId(),
                    et.getTache().getNom(),
                    et.getDateDebutReelle(),
                    et.getDateFinReelle());
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (session != null) session.close();
    }
}

public void afficherProjetsGeres(Employe employe) {
    Session session = null;

    try {
        session = HibernateUtil.getSessionFactory().openSession();
        Employe emp = session.get(Employe.class, employe.getId());

        System.out.println("Employé : " + emp.getNom() + " " + emp.getPrenom());
        System.out.println("Liste des projets gérés :");
        System.out.println("Num  Nom                 Date Début   Date Fin");

        for (Projet p : emp.getProjetsGeres()) {
            System.out.printf("%-4d %-20s %-12s %s%n",
                    p.getId(),
                    p.getNom(),
                    p.getDateDebut(),
                    p.getDateFin());
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (session != null) session.close();
    }
}
}