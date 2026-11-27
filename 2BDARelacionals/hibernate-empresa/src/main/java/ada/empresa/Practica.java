package ada.empresa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Practica {
    private static final String NIF = "555555555";

    public static void main(String[] args) throws IOException {
        preparaBaseDeDades();

        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("empresa")) {
            guardaEmpleat(emf);
            mostraEmpleat(emf);
        }
    }

    private static void preparaBaseDeDades() throws IOException {
        Path baseDeDades = Path.of("empresa.sqlite");
        if (Files.notExists(baseDeDades)) {
            try (InputStream origen = Practica.class.getResourceAsStream("/empresa-inicial.sqlite")) {
                if (origen == null) {
                    throw new IOException("Falta el recurs empresa-inicial.sqlite");
                }
                Files.copy(origen, baseDeDades);
            }
        }
    }

    private static void guardaEmpleat(EntityManagerFactory emf) {
        try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            try {
                if (em.find(Empleat.class, NIF) == null) {
                    Departament departament = em.find(Departament.class, 2);
                    if (departament == null) {
                        throw new IllegalStateException("Falta el departament amb identificador 2");
                    }

                    Empleat empleat = new Empleat(NIF, "Aina", "Ferrandis Roig", 2200.0, departament);
                    em.persist(empleat);
                }
                tx.commit();
            } catch (RuntimeException error) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                throw error;
            }
        }
    }

    private static void mostraEmpleat(EntityManagerFactory emf) {
        try (EntityManager em = emf.createEntityManager()) {
            Empleat empleat = em.find(Empleat.class, NIF);
            Departament departament = empleat.getDepartament();
            String nomDepartament = departament == null ? "Sense departament" : departament.getNom();
            System.out.printf("%s treballa a %s%n", empleat.getNom(), nomDepartament);
        }
    }
}
