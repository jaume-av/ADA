package ada.empresa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Departaments")
public class Departament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdDepartament")
    private Integer id;

    @Column(name = "NomDepartament", nullable = false, length = 100)
    private String nom;

    @Column(name = "Responsable", length = 100)
    private String responsable;

    protected Departament() {
    }

    public String getNom() {
        return nom;
    }
}
