package gestionnaireBibliotheque;

import java.util.ArrayList;
import java.util.Iterator;

public class ListeEmprunts implements IListeEmprunts, Iterable<Emprunt> {
    private ArrayList<Emprunt> emprunts;

    public ListeEmprunts() {
        emprunts = new ArrayList<>();
    }

    @Override
    public boolean ajouter(Emprunt emprunt) {
        return emprunts.add(emprunt);
    }

    @Override
    public Emprunt supprimer(int idEmprunt) {
        Emprunt emprunt = rechercher(idEmprunt);
        if (emprunt != null) {
            emprunts.remove(emprunt);
        }
        return emprunt;
    }

    @Override
    public Emprunt rechercher(int idEmprunt) {
        for (Emprunt emprunt : emprunts) {
            if (emprunt.getId() == idEmprunt) {
                return emprunt;
            }
        }
        return null;
    }

    @Override
    public boolean contient(int idEmprunt) {
        return rechercher(idEmprunt) != null;
    }

    @Override
    public int taille() {
        return emprunts.size();
    }

    @Override
    public boolean estVide() {
        return emprunts.isEmpty();
    }

    @Override
    public Iterator<Emprunt> iterator() {
        return emprunts.iterator();
    }

    @Override
    public String toString() {
        String resultat = "Emprunts (" + emprunts.size() + ") :";
        for (Emprunt emprunt : emprunts) {
            resultat += "\n" + emprunt;
        }
        return resultat;
    }
}
