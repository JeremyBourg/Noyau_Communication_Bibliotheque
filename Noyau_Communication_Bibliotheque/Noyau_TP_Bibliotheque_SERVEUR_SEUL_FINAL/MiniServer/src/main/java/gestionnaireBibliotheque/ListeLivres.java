package gestionnaireBibliotheque;

import java.util.ArrayList;
import java.util.Iterator;

public class ListeLivres implements IListeLivres, Iterable<Livre> {
    private ArrayList<Livre> livres;

    public ListeLivres() {
        livres = new ArrayList<>();
    }

    @Override
    public boolean ajouter(Livre livre) {
        return livres.add(livre);
    }

    @Override
    public Livre supprimer(int idLivre) {
        Livre livre = rechercher(idLivre);
        if (livre != null) {
            livres.remove(livre);
        }
        return livre;
    }

    @Override
    public Livre rechercher(int idLivre) {
        for (Livre livre : livres) {
            if (livre.getId() == idLivre) {
                return livre;
            }
        }
        return null;
    }

    @Override
    public boolean contient(int idLivre) {
        return rechercher(idLivre) != null;
    }

    @Override
    public int taille() {
        return livres.size();
    }

    @Override
    public boolean estVide() {
        return livres.isEmpty();
    }

    @Override
    public Iterator<Livre> iterator() {
        return livres.iterator();
    }

    @Override
    public String toString() {
        String resultat = "ListeLivres (" + livres.size() + "){";
        for (Livre livre : livres) {
            resultat += "\n\t" + livre;
        }
        resultat += "\n}";
        return resultat;
    }
}
