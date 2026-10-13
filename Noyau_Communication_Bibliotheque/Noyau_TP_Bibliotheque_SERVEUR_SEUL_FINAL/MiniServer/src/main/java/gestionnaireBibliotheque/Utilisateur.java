package gestionnaireBibliotheque;

public abstract class Utilisateur {
    private int id; //Id utilisateur
    private String nom;
    private ListeEmprunts listeEmpruntEnCours; // = ListeEmprunts.EnCours;
    private ListeEmprunts ListeEmpruntsTermines; // = listeEmprunts.Termines;
    private FilePrioriteReservations reservations;
    public static final int MAX_RESERVATIONS = 10;

    public Utilisateur(int id, String nom) {
        this.id = id;
        this.nom = nom;
        //  Les listes d’emprunts et la file de réservations doivent être initialisées (non
    }

    public void ajouterEmpruntEnCours(Emprunt emprunt){

    }
    public void ajouterEmpruntTermine(Emprunt emprunt){

    }
    public Emprunt supprimerEmpruntEnCours(int idEmprunt){
        Livre testLivre = new Livre("ok", "test", "Fiction");
        Emprunt emprunt = new Emprunt(testLivre, 2,2);
        return emprunt;
    }
    public Emprunt rechercherEmpruntEnCours(int idEmprunt){

        return null;
    }
    public Emprunt rechercherEmpruntTermine(int idEmprunt){
        return null;
    }
    public boolean aDesEmpruntsEnCours(){
        return true;
    }

    public abstract int nombreMaxEmprunts();
    public boolean peutEmprunter(){
        return true;
    }

    public boolean possedeReservationEnAttentePourLivre (int idLivre){
        return true;
    }
    public boolean ajouterReservation(Reservation reservation){
        return true;
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
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", listeEmpruntEnCours=" + listeEmpruntEnCours +
                ", ListeEmpruntsTermines=" + ListeEmpruntsTermines +
                ", reservations=" + reservations +
                '}';
    }
}
