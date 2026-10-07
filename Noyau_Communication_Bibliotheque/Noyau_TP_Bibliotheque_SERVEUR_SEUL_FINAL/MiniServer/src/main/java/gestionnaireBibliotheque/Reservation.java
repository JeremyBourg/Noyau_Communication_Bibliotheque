package gestionnaireBibliotheque;

import gestionnaireBibliotheque.TypeUtilisateur;
import gestionnaireBibliotheque.StatutReservation;

public class Reservation {
	int id;
	Livre livre;
	int idUtilisateur;
	TypeUtilisateur typeUtilisateur;
	int ordreReservation;
	StatutReservation statutReservation = StatutReservation.EN_ATTENTE;

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
}
