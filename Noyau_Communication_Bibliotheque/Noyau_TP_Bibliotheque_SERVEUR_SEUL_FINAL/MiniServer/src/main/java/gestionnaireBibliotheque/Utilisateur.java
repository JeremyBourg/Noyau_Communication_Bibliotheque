package gestionnaireBibliotheque;

public abstract class Utilisateur {
    private int id;
    private String nom;
    private ListeEmprunts empruntsEnCours;
    private ListeEmprunts empruntsTermines;
    private FilePrioriteReservations reservations;
    public static final int MAX_RESERVATIONS = 10;

    public Utilisateur(int id, String nom) {
        this.id = id;
        this.nom = nom;
        this.empruntsEnCours = new ListeEmprunts();
        this.empruntsTermines = new ListeEmprunts();
        this.reservations = new FilePrioriteReservations();
    }

    public void ajouterEmpruntEnCours(Emprunt emprunt){
        if (emprunt.getStatut() == StatutEmprunt.EN_COURS) {
            empruntsEnCours.ajouter(emprunt);
        } else {
            System.out.println("L'emprunt n'est pas en cours : " + emprunt.getLivre() + emprunt.getStatut());
        }
    }
    public void ajouterEmpruntTermine(Emprunt emprunt){
        if (emprunt.getStatut() == StatutEmprunt.RETOURNE || emprunt.getStatut() == StatutEmprunt.EN_RETARD) {
            empruntsTermines.ajouter(emprunt);
        } else {
            System.out.println("L'emprunt n'est pas retourné : " + emprunt.getLivre() + emprunt.getStatut());
        }
    }

    public Emprunt supprimerEmpruntEnCours(int idEmprunt) {
        if (empruntsEnCours.contient(idEmprunt)) {
            return empruntsEnCours.supprimer(idEmprunt);
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
        if (empruntsEnCours.contient(idEmprunt)) {
            return empruntsEnCours.rechercher(idEmprunt);
        } else {
            return null;
        }
    }

    public Emprunt rechercherEmpruntTermine(int idEmprunt){
        if (empruntsTermines.contient(idEmprunt)){
            return empruntsTermines.rechercher(idEmprunt);
        }
        return null;
    }
    public boolean aDesEmpruntsEnCours() {
        if (empruntsEnCours.estVide()) {
            return false;
        } else {
            return true;
        }
    }
    public abstract int nombreMaxEmprunts();
    public boolean peutEmprunter(){
        if (empruntsEnCours.taille() < nombreMaxEmprunts()) {
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
        return empruntsEnCours;
    }
    public ListeEmprunts getEmpruntsTermines() {
        return empruntsTermines;
    }
    public FilePrioriteReservations getReservations() {
        return reservations;
    }

    @Override
    public String toString() {
        return "Utilisateur{ type: " + getTypeUtilisateur() +
                " ,id= " + id +
                " ,nom= '" + nom + '\'' +
                " ,empruntsEnCours= " + empruntsEnCours.taille() +
                " ,empruntsTermines= " + empruntsTermines.taille() +
                " ,reservations= " + reservations.taille() +
                '}';
    }
}
