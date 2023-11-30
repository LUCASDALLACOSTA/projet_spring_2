package fr.projet.declarationfrais.model;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "restauration")
public class Restauration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private Float montant;

    @Column(name = "donnees_fichier", nullable = false)
    private byte[] donnees_fichier;

    @Column(name = "nom_fichier", nullable = false)
    private String nom_fichier;

    @Column(name = "type_fichier", nullable = false)
    private String type_fichier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "declaration_id")
    private Declaration declaration;
}
