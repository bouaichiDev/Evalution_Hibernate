package org.example;

import org.example.model.*;
import org.example.services.*;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.text.SimpleDateFormat;

public class App {

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            tx = session.beginTransaction();
            // Services
            EmployeService employeService = new EmployeService();
            ProjetService projetService = new ProjetService();
            TacheService tacheService = new TacheService();
            EmployeTacheService employeTacheService = new EmployeTacheService();

            // ===== CRÉATION EMPLOYÉS =====
            System.out.println("=== Création des employés ===");
            Employe emp1 = new Employe("Alami", "Ahmed", "0612345678");
            Employe emp2 = new Employe("Benani", "Sara", "0698765432");
            employeService.create(emp1);
            employeService.create(emp2);

            // ===== CRÉATION PROJET =====
            System.out.println("\n=== Création du projet ===");
            Projet projet = new Projet("Gestion de stock",
                    sdf.parse("2013-01-14"), sdf.parse("2013-06-14"));
            projet.setChefDeProjet(emp1);
            projetService.create(projet);

            // ===== CRÉATION TÂCHES =====
            System.out.println("\n=== Création des tâches ===");
            Tache t1 = new Tache("Analyse",
                    sdf.parse("2013-02-01"), sdf.parse("2013-02-20"), 800);
            t1.setProjet(projet);

            Tache t2 = new Tache("Conception",
                    sdf.parse("2013-03-01"), sdf.parse("2013-03-15"), 1200);
            t2.setProjet(projet);

            Tache t3 = new Tache("Développement",
                    sdf.parse("2013-04-01"), sdf.parse("2013-04-25"), 1500);
            t3.setProjet(projet);

            tacheService.create(t1);
            tacheService.create(t2);
            tacheService.create(t3);

            // ===== CRÉATION EMPLOYÉ-TÂCHE (dates réelles) =====
            System.out.println("\n=== Création des affectations ===");
            EmployeTache et1 = new EmployeTache(sdf.parse("2013-02-10"), sdf.parse("2013-02-20"));
            et1.setEmploye(emp1);
            et1.setTache(t1);

            EmployeTache et2 = new EmployeTache(sdf.parse("2013-03-10"), sdf.parse("2013-03-15"));
            et2.setEmploye(emp2);
            et2.setTache(t2);

            EmployeTache et3 = new EmployeTache(sdf.parse("2013-04-10"), sdf.parse("2013-04-25"));
            et3.setEmploye(emp1);
            et3.setTache(t3);

            employeTacheService.create(et1);
            employeTacheService.create(et2);
            employeTacheService.create(et3);

            // ===== TESTS =====

            // 1. Tâches réalisées par un employé
            System.out.println("\n========================================");
            System.out.println("TEST 1: Tâches réalisées par l'employé");
            employeService.afficherTachesRealisees(emp1);

            // 2. Projets gérés par un employé
            System.out.println("\n========================================");
            System.out.println("TEST 2: Projets gérés par l'employé");
            employeService.afficherProjetsGeres(emp1);

            // 3. Tâches planifiées pour un projet
            System.out.println("\n========================================");
            System.out.println("TEST 3: Tâches planifiées du projet");
            projetService.afficherTachesPlanifiees(projet);

            // 4. Tâches réalisées avec dates réelles (affichage attendu)
            System.out.println("\n========================================");
            System.out.println("TEST 4: Tâches réalisées avec dates réelles");
            projetService.afficherTachesRealiseesAvecDatesReelles(projet);

            // 5. Tâches avec prix > 1000 DH (requête nommée)
            System.out.println("\n========================================");
            System.out.println("TEST 5: Tâches avec prix > 1000 DH");
            for (Tache t : tacheService.findByPrixSuperieurA1000()) {
                System.out.println(t.getId() + " - " + t.getNom() + " - " + t.getPrix() + " DH");
            }

            // 6. Tâches réalisées entre deux dates
            System.out.println("\n========================================");
            System.out.println("TEST 6: Tâches réalisées entre 01/02/2013 et 31/03/2013");
            for (Tache t : tacheService.findTachesRealiseesEntreDates(
                    sdf.parse("2013-02-01"), sdf.parse("2013-03-31"))) {
                System.out.println(t.getId() + " - " + t.getNom());
            }

            System.out.println("succès !");
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            System.err.println( e.getMessage());
            e.printStackTrace();
        } finally {
            HibernateUtil.getSessionFactory().close();

        }
    }
}