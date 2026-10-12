package gestionnaireBibliotheque;

public class  Reservation implements Comparable<Reservation> {

    private int id;
    private Livre livre;
    private int idUtilisateur;
    private  TypeUtilisateur typeUtilisateur;
    private  int ordreReservation;
    private StatutReservation statutReservation;
    private static int idReservation = 0;
    private static int compteurOrdre = 0;

    public Reservation(Livre livre, int idUtilisateur, TypeUtilisateur typeUtilisateur) {
        this.livre = livre;
        this.idUtilisateur = idUtilisateur;
        this.typeUtilisateur = typeUtilisateur;
        this.id = prochainID();
        this.ordreReservation = prochainOrdreReservation();
        this.statutReservation = StatutReservation.EN_ATTENTE;
    }

    public static int prochainID(){
        idReservation++;
        return idReservation;
    }
    public static int prochainOrdreReservation(){
        compteurOrdre++;
        return compteurOrdre;
    }


    @Override
    public int compareTo(Reservation autre) { // Compare 2 objet Reservation
        int prioriteCourant = 0;
        int prioriteAutre = 0;

            switch (this.typeUtilisateur) {
                case PROFESSEUR:
                    prioriteCourant = 3;
                    break;
                case PERSONNEL:
                    prioriteCourant = 2;
                    break;
                case ETUDIANT:
                    prioriteCourant = 1;
                    break;
            }
            switch (autre.typeUtilisateur) {
                case PROFESSEUR:
                    prioriteAutre = 3;
                    break;
                case PERSONNEL:
                    prioriteAutre = 2;
                    break;
                case ETUDIANT:
                    prioriteAutre = 1;
                    break;
            }

            if (prioriteCourant > prioriteAutre){
                return -1;
            } else if (prioriteCourant < prioriteAutre) {
                return 1;
            }
            // Si la reservation courante a la même priorité, mais différente ordreReservation:
            else if (prioriteCourant == prioriteAutre && this.ordreReservation > autre.ordreReservation) {
                return 1; // Reservation courante est moins prioritaire (plus haut nb reservation)
            } else if (prioriteCourant == prioriteAutre && this.ordreReservation < autre.ordreReservation) {
                return -1; // Reservation courante est plus prioritaire (moins haute nb reservation)
            }


        return 0; // Si tout autre if statement n'est pas trouvé
        // Reservation doit etre même priorité & même nb ordreReservation = 0;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", livre=" + livre +
                ", idUtilisateur=" + idUtilisateur +
                ", typeUtilisateur=" + typeUtilisateur +
                ", ordreReservation=" + ordreReservation +
                ", statutReservation=" + statutReservation +
                '}';
    }
}
