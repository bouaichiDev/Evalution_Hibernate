package org.example;

import org.example.model.Femme;
import org.example.model.Homme;
import org.example.model.Mariage;
import org.example.services.FemmeService;
import org.example.services.HommeService;
import org.example.services.MariageService;
import org.example.util.HibernateUtil;

import java.text.SimpleDateFormat;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            HommeService hommeService = new HommeService();
            FemmeService femmeService = new FemmeService();
            MariageService mariageService = new MariageService();

            // ===== CRÉATION 10 FEMMES =====
            System.out.println("=== Création des femmes ===");
            Femme f1 = new Femme("SAFI", "SALMA", "0611111111", "Casablanca", sdf.parse("15/05/1970"));
            Femme f2 = new Femme("ALAOUI", "WAFA", "0622222222", "Rabat", sdf.parse("20/08/1975"));
            Femme f3 = new Femme("RAMI", "SALIMA", "0633333333", "Fès", sdf.parse("10/03/1965"));
            Femme f4 = new Femme("ALI", "AMAL", "0644444444", "Marrakech", sdf.parse("25/12/1972"));
            Femme f5 = new Femme("ALAMI", "KARIMA", "0655555555", "Tanger", sdf.parse("05/01/1960"));
            Femme f6 = new Femme("BENANI", "LATIFA", "0666666666", "Agadir", sdf.parse("18/07/1980"));
            Femme f7 = new Femme("CHRAIBI", "NOURA", "0677777777", "Oujda", sdf.parse("30/09/1978"));
            Femme f8 = new Femme("DAHBI", "SAMIRA", "0688888888", "Tétouan", sdf.parse("12/04/1982"));
            Femme f9 = new Femme("EL FASSI", "FATIMA", "0699999999", "Salé", sdf.parse("08/11/1968"));
            Femme f10 = new Femme("FILALI", "HIND", "0600000000", "Meknès", sdf.parse("22/06/1976"));

            femmeService.create(f1); femmeService.create(f2); femmeService.create(f3);
            femmeService.create(f4); femmeService.create(f5); femmeService.create(f6);
            femmeService.create(f7); femmeService.create(f8); femmeService.create(f9);
            femmeService.create(f10);

            // ===== CRÉATION 5 HOMMES =====
            System.out.println("=== Création des hommes ===");
            Homme h1 = new Homme("SAFI", "SAID", "0611111111", "Casablanca", sdf.parse("10/01/1960"));
            Homme h2 = new Homme("ALAMI", "OMAR", "0622222222", "Rabat", sdf.parse("15/03/1962"));
            Homme h3 = new Homme("BENNANI", "KARIM", "0633333333", "Fès", sdf.parse("20/05/1958"));
            Homme h4 = new Homme("CHRAIBI", "YOUSSEF", "0644444444", "Marrakech", sdf.parse("08/09/1965"));
            Homme h5 = new Homme("DAHBI", "MOHAMED", "0655555555", "Tanger", sdf.parse("12/07/1970"));

            hommeService.create(h1); hommeService.create(h2); hommeService.create(h3);
            hommeService.create(h4); hommeService.create(h5);

            // ===== CRÉATION MARIAGES =====
            System.out.println("=== Création des mariages ===");

            // Homme 1 - SAFI SAID : 3 mariages en cours + 1 échoué
            Mariage m1 = new Mariage(sdf.parse("03/09/1990"), null, 4);
            m1.setHomme(h1); m1.setFemme(f3); mariageService.create(m1);

            Mariage m2 = new Mariage(sdf.parse("03/09/1995"), null, 2);
            m2.setHomme(h1); m2.setFemme(f4); mariageService.create(m2);

            Mariage m3 = new Mariage(sdf.parse("04/11/2000"), null, 3);
            m3.setHomme(h1); m3.setFemme(f2); mariageService.create(m3);

            Mariage m4 = new Mariage(sdf.parse("03/09/1989"), sdf.parse("03/09/1990"), 0);
            m4.setHomme(h1); m4.setFemme(f5); mariageService.create(m4);

            // Homme 2 - 4 mariages pour test Criteria
            Mariage m5 = new Mariage(sdf.parse("01/01/1990"), null, 2);
            m5.setHomme(h2); m5.setFemme(f1); mariageService.create(m5);

            Mariage m6 = new Mariage(sdf.parse("01/01/1992"), null, 3);
            m6.setHomme(h2); m6.setFemme(f6); mariageService.create(m6);

            Mariage m7 = new Mariage(sdf.parse("01/01/1995"), null, 1);
            m7.setHomme(h2); m7.setFemme(f7); mariageService.create(m7);

            Mariage m8 = new Mariage(sdf.parse("01/01/1998"), null, 2);
            m8.setHomme(h2); m8.setFemme(f8); mariageService.create(m8);

            // Femme mariée 2 fois (f1)
            Mariage m9 = new Mariage(sdf.parse("01/01/1985"), sdf.parse("01/01/1988"), 1);
            m9.setHomme(h3); m9.setFemme(f1); mariageService.create(m9);

            // ===== TESTS =====

            // 1. Liste des femmes
            System.out.println("\n========================================");
            System.out.println("1. LISTE DES FEMMES");
            for (Femme f : femmeService.findAll()) {
                System.out.println(f.getId() + " - " + f.getNom() + " " + f.getPrenom());
            }

            // 2. Femme la plus âgée
            System.out.println("\n========================================");
            System.out.println("2. FEMME LA PLUS ÂGÉE");
            Femme plusAgee = femmeService.findFemmePlusAgee();
            System.out.println(plusAgee.getNom() + " " + plusAgee.getPrenom() +
                    " - Née le : " + plusAgee.getDateNaissance());

            // 3. Épouses d'un homme entre deux dates
            System.out.println("\n========================================");
            System.out.println("3. ÉPOUSES DE SAFI SAID (1988-2005)");
            for (Mariage m : hommeService.findEpousesEntreDates(h1,
                    sdf.parse("01/01/1988"), sdf.parse("31/12/2005"))) {
                System.out.println(m.getFemme().getNom() + " " + m.getFemme().getPrenom() +
                        " - Début : " + m.getDateDebut());
            }

            // 4. Nombre d'enfants d'une femme entre deux dates
            System.out.println("\n========================================");
            System.out.println("4. NOMBRE D'ENFANTS DE SALIMA RAMI (1988-2005)");
            Long enfants = femmeService.countEnfantsNative(f3,
                    sdf.parse("01/01/1988"), sdf.parse("31/12/2005"));
            System.out.println("Total enfants : " + enfants);

            // 5. Femmes mariées 2 fois ou plus
            System.out.println("\n========================================");
            System.out.println("5. FEMMES MARIÉES 2 FOIS OU PLUS");
            for (Femme f : femmeService.findFemmesMarieesDeuxFoisOuPlus()) {
                System.out.println(f.getNom() + " " + f.getPrenom());
            }

            // 6. Hommes mariés à 4 femmes (Criteria)
            System.out.println("\n========================================");
            System.out.println("6. HOMMES MARIÉS À 4 FEMMES (1988-2005)");
            for (Object[] row : femmeService.findHommesMariesAQuatreFemmes(
                    sdf.parse("01/01/1988"), sdf.parse("31/12/2005"))) {
                System.out.println("ID: " + row[0] + " - " + row[1] + " " + row[2] +
                        " - Nbr femmes: " + row[3]);
            }

            // 7. Mariages d'un homme (affichage détaillé)
            System.out.println("\n========================================");
            System.out.println("7. DÉTAIL DES MARIAGES");
            hommeService.afficherMariages(h1);

            System.out.println("\n✅ Tous les tests exécutés avec succès !");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.getSessionFactory().close();
        }
    }
}