/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.services;

 import org.example.model.Produit;
 import org.example.util.HibernateUtil;
 import org.hibernate.HibernateException;
 import org.hibernate.Session;
 import org.hibernate.Transaction;

 import java.util.List;

/**
 *
 * @author X1 YOGA
 */
public class ProduitService extends AbstractService<Produit>{

    public ProduitService() {
        super(Produit.class);
    }
    public Produit findByReference(String reference) {
        Session session = null;
        Transaction tx = null;
        Produit produit = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            produit = (Produit) session.getNamedQuery("findByReference").setParameter("reference", reference).uniqueResult();

            tx.commit();
        }catch(HibernateException exception){
            exception.printStackTrace();
            if(tx != null)
                tx.rollback();
        }finally{
            if(session != null)
                session.close();
        }
        return produit;
    }
    public List<Produit> findByPrix(float prix) {
        Session session = null;
        Transaction tx = null;
        List<Produit> produits = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            produits =  session.createNativeQuery("Select * from produit where prix = :prix",Produit.class).setParameter("prix",prix).getResultList();

            tx.commit();
        }catch(HibernateException exception){
            exception.printStackTrace();
            if(tx != null)
                tx.rollback();
        }finally{
            if(session != null)
                session.close();
        }
        return produits;
    }
    
}
