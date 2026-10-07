package gestionnaireBibliotheque;

import gestionnaireBibliotheque.TypeUtilisateur;
import gestionnaireBibliotheque.StatutReservation;

public class Reservation implements Comparable<Reservation> {
	private int id;
	private Livre livre;
	private int idUtilisateur;
	private TypeUtilisateur typeUtilisateur;
	private int ordreReservation;
	private StatutReservation statutReservation = StatutReservation.EN_ATTENTE;

	private static int idActuel = 1;
	private static int ordreReservationActuel = 1;

	private static int prochainID() {
		return idActuel++;
	}
	private static int prochainOrdreReservation() {
		return ordreReservationActuel++;
	}

	public Reservation(Livre livre, int idUtilisateur, TypeUtilisateur typeUtilisateur) {
		this.id = prochainID();
		this.livre = livre;
		this.idUtilisateur = idUtilisateur;
		this.typeUtilisateur = typeUtilisateur;
		this.ordreReservation = prochainOrdreReservation();
		this.statutReservation = StatutReservation.EN_ATTENTE;
	}

	public int getOrdreReservation() {
	    return ordreReservation;
	}
	public TypeUtilisateur getTypeUtilisateur() {
	    return typeUtilisateur;
	}

	// converti les TypeUtilisateur en int utilisables dans une comparaison,
	// donnant une valeur de priorité pour chaque type
	// 0 ne devrait jamais arriver.
	private int priorite(TypeUtilisateur t) {
		switch (t) {
			case PROFESSEUR:
				return 3;
			case PERSONNEL:
				return 2;
			case ETUDIANT:
				return 1;
			default:
				return 0;
		}
	}

	/*
	 * Retourne 1 si autre est prioritaire,
	 * -1 dans le cas contraire
	 * et 0 s'ils sont égaux
	*/
	@Override
	public int compareTo(Reservation autre) {
		if(priorite(autre.getTypeUtilisateur()) == priorite(this.typeUtilisateur)) {
			if(this.ordreReservation == autre.getOrdreReservation())
				return 0; // même ordre
			else {
				// ordre plus grand => moins prioritaire
				return this.ordreReservation > autre.getOrdreReservation()
					? 1
					: -1;
			}
		}
		else if(priorite(autre.getTypeUtilisateur()) > priorite(this.typeUtilisateur))
			return 1;
		else
			return -1;
	}

	@Override
	public String toString() {
		return "ID de la réservation: " + id + "\n"
			+ "ID du livre: " + livre.getId() + "\n"
			+ "ID de l'utilisaleur: " + idUtilisateur + "\n"
			+ "Type d'utilisateur: " + typeUtilisateur.toString() + "\n"
			+ "Ordre de réservation: " + ordreReservation + "\n"
			+ "Statut de la réservation: " + statutReservation.toString();
	}
}
