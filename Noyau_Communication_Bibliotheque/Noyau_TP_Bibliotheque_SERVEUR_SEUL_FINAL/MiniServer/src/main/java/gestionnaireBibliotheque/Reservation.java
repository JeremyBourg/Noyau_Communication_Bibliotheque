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
}
