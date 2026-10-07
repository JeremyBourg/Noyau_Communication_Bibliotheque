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

	private static int prochainID() {
		return idActuel++;
	}

	public Livre(String titre, String auteur, String categorie) {
		this.id = prochainID();
		this.titre = titre;
		this.auteur = auteur;
		this.categorie = categorie;
		this.statut = StatutLivre.DISPONIBLE;
	}

	public int getId() {
		return this.id;
	}

	@Override
	public String toString() {
		String statutString;
		switch (this.statut) {
			case DISPONIBLE:
				statutString = "Disponible";
				break;
			case EMPRUNTE:
				statutString = "Emprunte";
				break;
			case RESERVE:
				statutString = "Reserve";
				break;
			case PERDU:
				statutString = "Perdu";
				break;
			default:
				statutString = "Statut invalide";
				break;
		}

		return String.format("LIVRE %d: %s - %s (%s): %s",
				id,
				titre,
				auteur,
				categorie,
				statutString);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == this) return true;
		if(obj == null) return false;

		if(obj instanceof Livre) {
			Livre l = (Livre) obj;
			return this.id == l.getId();
		}

		return false;
	}
}
