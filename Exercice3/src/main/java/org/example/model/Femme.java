package org.example.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "femme")
@DiscriminatorValue("F")
@NamedQueries({
        @NamedQuery(name = "Femme.findMarieesDeuxFoisOuPlus",
                query = "SELECT f FROM Femme f WHERE SIZE(f.mariages) >= 2"),
        @NamedQuery(name = "Femme.countEnfantsEntreDates",
                query = "SELECT SUM(m.nbrEnfant) FROM Mariage m WHERE m.femme.id = :femmeId AND m.dateDebut BETWEEN :debut AND :fin")
})
@NamedNativeQueries({
        @NamedNativeQuery(name = "Femme.countEnfantsNative",
                query = "SELECT SUM(m.nbr_enfant) as total FROM mariage m WHERE m.femme_id = ? AND m.date_debut BETWEEN ? AND ?",
                resultSetMapping = "countEnfantsMapping")
})
@SqlResultSetMapping(name = "countEnfantsMapping",
        columns = {@ColumnResult(name = "total")})
public class Femme extends Personne {

    @OneToMany(mappedBy = "femme", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Mariage> mariages;

    public Femme() {

    }

    public Femme(String nom, String prenom, String telephone, String adresse, java.util.Date dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    }

    public List<Mariage> getMariages() {
        return mariages;
    }

    public void setMariages(List<Mariage> mariages) {
        this.mariages = mariages;
    }
}