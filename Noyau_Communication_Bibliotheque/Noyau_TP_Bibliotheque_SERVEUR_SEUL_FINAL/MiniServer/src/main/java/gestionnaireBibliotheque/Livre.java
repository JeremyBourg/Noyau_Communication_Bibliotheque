package gestionnaireBibliotheque;

import gestionnaireBibliotheque.StatutLivre;

public class Livre {
	private int id;
	private String titre;
	private String auteur;
	private String categorie;
	StatutLivre statut = StatutLivre.DISPONIBLE;
}
