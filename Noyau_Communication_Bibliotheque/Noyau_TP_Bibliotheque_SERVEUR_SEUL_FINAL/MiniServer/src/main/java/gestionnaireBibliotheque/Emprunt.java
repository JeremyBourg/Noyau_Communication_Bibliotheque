package gestionnaireBibliotheque;
public class Emprunt {
    private int id; //id de l'emprunt
    private Livre livre;
    private int idUtilisateur;
    private int jourEmprunt;
    private int jourRetourPrevu; // Jour prévu du retours, avant qu'il soit en retard
    private int jourRetour; // Jour reel du retour
    private StatutEmprunt statut = StatutEmprunt.EN_COURS;
    private static int idEmprunt = 0;
    public Emprunt(Livre livre, int idUtilisateur, int jourEmprunt) {
        this.id = prochainID();
        this.livre = livre;
        this.idUtilisateur = idUtilisateur;
        this.jourEmprunt = jourEmprunt;
        this.jourRetourPrevu = jourEmprunt + Livre.DUREE_MAX_EMPRUNT;
    }
    public static int prochainID(){
        idEmprunt++;
        return idEmprunt;
    }
    public boolean estEnRetard(int jourActuel){
        if (jourActuel > jourRetourPrevu) {
            return true;
        } else {
            return false;
        }
    }
    public int calculerJoursRetard(int jourActuel){
        if (jourActuel > jourRetourPrevu) {
            int jourRetard = jourActuel - jourRetourPrevu;
            return jourRetard;
        }
        else
        {
            return 0;
        }
    }
    public void retourner(int jourRetour){
        this.jourRetour = jourRetour;
        if (jourRetour <= jourRetourPrevu) {
            statut = StatutEmprunt.RETOURNE;
        } else {
            statut = StatutEmprunt.EN_RETARD;
        }
    }
    @Override
    public String toString() {
        return "Emprunt{" +
                "id=" + id +
                ",\t livre emprunte=" + livre +
                ", idUtilisateur=" + idUtilisateur +
                ", jourEmprunt=" + jourEmprunt +
                ", jourRetourPrevu=" + jourRetourPrevu +
                ", jourRetour=" + jourRetour +
                ", status=" + statut +
                '}';
    }
}