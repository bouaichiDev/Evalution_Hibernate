package org.example;

import org.example.model.Produit;
import org.example.services.ProduitService;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class App {

    public static void main(String[] args) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

//            // === CATÉGORIES ===
//            Categorie cat1 = new Categorie("CAT01", "Ordinateurs");
//            Categorie cat2 = new Categorie("CAT02", "Périphériques");
//            session.save(cat1);
//            session.save(cat2);
//
//            // === PRODUITS ===
//            Produit p1 = new Produit("ES12", 120f);
//            p1.setCategorie(cat1);
//
//            Produit p2 = new Produit("ZR85", 100f);
//            p2.setCategorie(cat2);
//
//            Produit p3 = new Produit("EE85", 200f);
//            p3.setCategorie(cat1);
//
//            session.save(p1);
//            session.save(p2);
//            session.save(p3);
//
//            // === COMMANDE ===
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            Commande cmd = new Commande(sdf.parse("2013-03-14"));
//            session.save(cmd);
//
//            // === LIGNES DE COMMANDE ===
//            LigneCommandeProduit lcp1 = new LigneCommandeProduit(7);
//            lcp1.setProduit(p1);
//            lcp1.setCommande(cmd);
//
//            LigneCommandeProduit lcp2 = new LigneCommandeProduit(14);
//            lcp2.setProduit(p2);
//            lcp2.setCommande(cmd);
//
//            LigneCommandeProduit lcp3 = new LigneCommandeProduit(5);
//            lcp3.setProduit(p3);
//            lcp3.setCommande(cmd);
//
//            session.save(lcp1);
//            session.save(lcp2);
//            session.save(lcp3);
            ProduitService produitService =new ProduitService();
           Produit p= produitService.findByReference("ES12");
            System.out.println("============");
            System.out.println(p);
            System.out.println("============");
            for(Produit e : produitService.findByPrix(100))
                System.out.println(e);
            tx.commit();
            System.out.println("✅ Tables créées et données insérées avec succès !");

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            System.err.println("❌ Erreur : " + e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
            sessionFactory.close();
        }
    }
}