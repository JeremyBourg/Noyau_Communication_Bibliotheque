package gestionnaireBibliotheque;

import gestionnaireBibliotheque.StatutLivre;

public class Livre {
	private int id;
	private String titre;
	private String auteur;
	private String categorie;
	StatutLivre statut = StatutLivre.DISPONIBLE;

	public static final int DUREE_MAX_EMPRUNT = 40;
	private static int idActuel = 1;

	public static int prochainID() {
		return idActuel++;
	}

	public Livre(String titre, String auteur, String categorie) {
		this.id = prochainID();
		this.titre = titre;
		this.auteur = auteur;
		this.categorie = categorie;
		this.statut = StatutLivre.DISPONIBLE;
	}
}
