package gestionnaireBibliotheque;

public abstract class Utilisateur {
    private int id;
    private String nom;
    private ListeEmprunts listeEmpruntsEnCours;
    private ListeEmprunts listeEmpruntsTermines;
    private FilePrioriteReservations reservations;
    public static final int MAX_RESERVATIONS = 10;

    public Utilisateur(int id, String nom) {
        this.id = id;
        this.nom = nom;
        this.listeEmpruntsEnCours = new ListeEmprunts();
        this.listeEmpruntsTermines = new ListeEmprunts();
        this.reservations = new FilePrioriteReservations();
    }

    public void ajouterEmpruntEnCours(Emprunt emprunt){
        if (emprunt.getStatut() == StatutEmprunt.EN_COURS) {
            listeEmpruntsEnCours.ajouter(emprunt);
        } else {
            System.out.println("L'emprunt n'est pas en cours : " + emprunt.getLivre() + emprunt.getStatut());
        }
    }
    public void ajouterEmpruntTermine(Emprunt emprunt){
        if (emprunt.getStatut() == StatutEmprunt.RETOURNE) {
            listeEmpruntsTermines.ajouter(emprunt);
        } else {
            System.out.println("L'emprunt n'est pas retourné : " + emprunt.getLivre() + emprunt.getStatut());
        }
    }

    public Emprunt supprimerEmpruntEnCours(int idEmprunt) {
        if (listeEmpruntsEnCours.contient(idEmprunt)) {
            return listeEmpruntsEnCours.supprimer(idEmprunt);
        } else {
            return null;
        }
    }
//    public Emprunt supprimerEmpruntEnCours(int idEmprunt){
//        Livre testLivre = new Livre("ok", "test", "Fiction");
//        Emprunt emprunt = new Emprunt(testLivre, 2,2);
//        return emprunt;
//    }


    public Emprunt rechercherEmpruntEnCours(int idEmprunt) {
        if (listeEmpruntsEnCours.contient(idEmprunt)) {
            return listeEmpruntsEnCours.rechercher(idEmprunt);
        } else {
            return null;
        }
    }

    public Emprunt rechercherEmpruntTermine(int idEmprunt){
        if (listeEmpruntsTermines.contient(idEmprunt)){
            return listeEmpruntsTermines.rechercher(idEmprunt);
        }
        return null;
    }
    public boolean aDesEmpruntsEnCours() {
        if (listeEmpruntsEnCours.estVide()) {
            return false;
        } else {
            return true;
        }
    }
    public abstract int nombreMaxEmprunts();
    public boolean peutEmprunter(){
        if (listeEmpruntsEnCours.taille() < nombreMaxEmprunts()) {
            return true;
        }
        return false;
    }

    public boolean possedeReservationEnAttentePourLivre (int idLivre){
        if (reservations.contientLivre(idLivre)) {
            return true;
        }
        return false;
    }
    public boolean ajouterReservation(Reservation reservation){
        if (reservations.taille() < MAX_RESERVATIONS){
            reservations.ajouter(reservation);
            return true;
        }
        return false;
    }
    public abstract double calculerPenalite(int joursRetard);
    public abstract TypeUtilisateur getTypeUtilisateur();

    public int getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public ListeEmprunts getEmpruntsEnCours() {
        return listeEmpruntsEnCours;
    }
    public ListeEmprunts getEmpruntsTermines() {
        return listeEmpruntsTermines;
    }
    public FilePrioriteReservations getReservations() {
        return reservations;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
				", type=" + getTypeUtilisateur() +
                ", id=" + id +
                ", nom='" + nom + '\'' +
                ", listeEmpruntsEnCours=" + listeEmpruntsEnCours.taille() +
                ", ListeEmpruntsTermines=" + listeEmpruntsTermines.taille() +
                ", reservations=" + reservations.taille() +
                '}';
    }
}
