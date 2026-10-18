package gestionnaireBibliotheque;
import java.util.PriorityQueue;

public class FilePrioriteReservations {
    private PriorityQueue<Reservation> file;

    public FilePrioriteReservations() {
        file = new PriorityQueue<Reservation>();
    }

    public boolean ajouter(Reservation reservation) {
        return file.add(reservation);
    }
    public Reservation retirer() {
        return file.poll();
    }
    public Reservation consulter() {
        return file.peek();
    }
    public boolean estVide() {
        return file.isEmpty();
    }
    public int taille() {
        return file.size();
    }
    public Reservation retirerPourLivre(int idLivre) {
        Reservation meilleure = null;
        for (Reservation r : file) {
            if (r.getLivre().getId() == idLivre) {
                if (meilleure == null || r.compareTo(meilleure) < 0) {
                    meilleure = r;
                }
            }
        }
        if (meilleure != null) {
            file.remove(meilleure);
        }
        return meilleure;
    }

    public boolean contientLivre(int idLivre) { // Extra methode pour comparer les livres hors de la class Utilisateur
        for (Reservation r: file){
            if (r.getId() == idLivre){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String resultat = "FilePrioriteReservations (" + file.size() + "){";
        for (Reservation r : file) {
            resultat += "\n\t" + r;
        }
        resultat += "\n}";
        return resultat;
    }
}
