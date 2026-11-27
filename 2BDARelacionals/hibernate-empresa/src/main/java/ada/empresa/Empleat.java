package ada.empresa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Empleats")
public class Empleat {
    @Id
    @Column(name = "NIF", length = 9)
    private String nif;

    @Column(name = "Nom", length = 100)
    private String nom;

    @Column(name = "Cognoms", length = 100)
    private String cognoms;

    @Column(name = "Salari")
    private Double salari;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdDepartament")
    private Departament departament;

    protected Empleat() {
    }

    public Empleat(String nif, String nom, String cognoms, Double salari, Departament departament) {
        this.nif = nif;
        this.nom = nom;
        this.cognoms = cognoms;
        this.salari = salari;
        this.departament = departament;
    }

    public String getNom() {
        return nom;
    }

    public Departament getDepartament() {
        return departament;
    }
}
