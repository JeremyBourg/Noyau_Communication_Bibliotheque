package gestionnaireBibliotheque;

import java.util.Objects;
import java.util.Scanner;

public class Demo {
    public static void main(String[] args) {
        System.out.println("===== Exemple de l'enonce =====");
        Livre livre = new Livre("Java", "Deitel", "Informatique");
        Etudiant etudiant = new Etudiant(101, "Amine");
        Emprunt emprunt = new Emprunt(livre, etudiant.getId(), 5);
        System.out.println(emprunt);
        System.out.println("Jours de retard : " + emprunt.calculerJoursRetard(50));
        System.out.println();

        System.out.println("===== Livres =====");
        Livre cleanCode = new Livre("CleanCode", "Martin", "Informatique");
        Livre dune = new Livre("Dune", "Herbert", "Roman");
        System.out.println(livre);
        System.out.println(cleanCode);
        System.out.println(dune);
        System.out.println("livre.equals(livre) : " + livre.equals(livre) + " (attendu : true)");
        System.out.println("livre.equals(dune) : " + livre.equals(dune) + " (attendu : false)");

        System.out.println();
        System.out.println("===== Emprunt : retour prevu et retard =====");
        System.out.println("Retour prevu : " + emprunt.getJourRetourPrevu() + " (attendu : 45)");
        System.out.println("En retard au jour 30 : " + emprunt.estEnRetard(30) + " (attendu : false)");
        System.out.println("En retard au jour 50 : " + emprunt.estEnRetard(50) + " (attendu : true)");
        System.out.println("Jours de retard au jour 50 : " + emprunt.calculerJoursRetard(50) + " (attendu : 5)");

        Emprunt aTemps = new Emprunt(cleanCode, 101, 10);
        aTemps.retourner(40);
        System.out.println("Retour au jour 40 : " + aTemps.getStatut() + " (attendu : RETOURNE)");
        Emprunt enRetard = new Emprunt(dune, 101, 10);
        enRetard.retourner(60);
        System.out.println("Retour au jour 60 : " + enRetard.getStatut() + " (attendu : EN_RETARD)");

        System.out.println();
        System.out.println("===== Utilisateurs et penalites =====");
        Personnel personnel = new Personnel(201, "Julie");
        Professeur professeur = new Professeur(301, "Tremblay");
        System.out.println("Etudiant : max " + etudiant.nombreMaxEmprunts() + ", 10 jours = "
                + etudiant.calculerPenalite(10) + " $ (attendu : 5, 2.5)");
        System.out.println("Personnel : max " + personnel.nombreMaxEmprunts() + ", 10 jours = "
                + personnel.calculerPenalite(10) + " $ (attendu : 7, 5.0)");
        System.out.println("Professeur : max " + professeur.nombreMaxEmprunts() + ", 10 jours = "
                + professeur.calculerPenalite(10) + " $ (attendu : 10, 7.5)");
        System.out.println(etudiant.getTypeUtilisateur() + " " + personnel.getTypeUtilisateur()
                + " " + professeur.getTypeUtilisateur());
        System.out.println(etudiant);

        System.out.println();
        System.out.println("===== Reservations : ordre de priorite =====");
        Reservation rEtudiant = new Reservation(livre, 101, TypeUtilisateur.ETUDIANT);
        Reservation rPersonnel = new Reservation(livre, 201, TypeUtilisateur.PERSONNEL);
        Reservation rProf = new Reservation(livre, 301, TypeUtilisateur.PROFESSEUR);
        Reservation rProf2 = new Reservation(cleanCode, 302, TypeUtilisateur.PROFESSEUR);
        System.out.println("Prof compare a etudiant : " + rProf.compareTo(rEtudiant) + " (attendu : -1)");
        System.out.println("Etudiant compare a personnel : " + rEtudiant.compareTo(rPersonnel) + " (attendu : 1)");
        System.out.println("Prof compare a prof plus recent : " + rProf.compareTo(rProf2) + " (attendu : -1)");

        FilePrioriteReservations file = new FilePrioriteReservations();
        System.out.println("File vide au depart : " + file.estVide() + " (attendu : true)");
        file.ajouter(rEtudiant);
        file.ajouter(rProf2);
        file.ajouter(rPersonnel);
        file.ajouter(rProf);
        System.out.println("Taille : " + file.taille() + " (attendu : 4)");
        System.out.println(file);
        System.out.println("Consulter : utilisateur " + file.consulter().getIdUtilisateur() + " (attendu : 301)");
        System.out.println("Retirer pour le livre 1 : " + file.retirerPourLivre(livre.getId()).getIdUtilisateur() + " (attendu : 301)");
        System.out.println("Retirer pour le livre 1 : " + file.retirerPourLivre(livre.getId()).getIdUtilisateur() + " (attendu : 201)");
        System.out.println("Retirer pour le livre 1 : " + file.retirerPourLivre(livre.getId()).getIdUtilisateur() + " (attendu : 101)");
        System.out.println("Retirer pour le livre 1 : " + file.retirerPourLivre(livre.getId()) + " (attendu : null)");
        System.out.println("Retirer : utilisateur " + file.retirer().getIdUtilisateur() + " (attendu : 302)");
        System.out.println("File vide a la fin : " + file.estVide() + " (attendu : true)");

        System.out.println();
        System.out.println("===== ListeLivres =====");
        ListeLivres liste = new ListeLivres();
        System.out.println("Vide au depart : " + liste.estVide() + " (attendu : true)");
        liste.ajouter(livre);
        liste.ajouter(cleanCode);
        liste.ajouter(dune);
        System.out.println(liste);
        System.out.println("Taille : " + liste.taille() + " (attendu : 3)");
        System.out.println("Contient Dune : " + liste.contient(dune.getId()) + " (attendu : true)");
        System.out.println("Rechercher id 2 : " + liste.rechercher(2));
        System.out.println("Rechercher id 999 : " + liste.rechercher(999) + " (attendu : null)");
        System.out.println("Parcours avec for-each :");
        for (Livre l : liste) {
            System.out.println("  - " + l.getTitre());
        }
        System.out.println("Supprimer CleanCode : " + liste.supprimer(cleanCode.getId()));
        System.out.println("Taille apres suppression : " + liste.taille() + " (attendu : 2)");

        System.out.println();
        System.out.println("===== ListeEmprunts =====");
        ListeEmprunts listeEmprunts = new ListeEmprunts();
        listeEmprunts.ajouter(emprunt);
        listeEmprunts.ajouter(aTemps);
        System.out.println(listeEmprunts);
        System.out.println("Taille : " + listeEmprunts.taille() + " (attendu : 2)");
        System.out.println("Contient l'emprunt " + aTemps.getId() + " : " + listeEmprunts.contient(aTemps.getId()) + " (attendu : true)");
        System.out.println("Parcours avec for-each :");
        for (Emprunt e : listeEmprunts) {
            System.out.println("  - emprunt " + e.getId() + " : " + e.getLivre().getTitre());
        }
        System.out.println("Supprimer l'emprunt " + emprunt.getId() + " : " + (listeEmprunts.supprimer(emprunt.getId()) != null) + " (attendu : true)");
        System.out.println("Rechercher id 999 : " + listeEmprunts.rechercher(999) + " (attendu : null)");
        System.out.println(listeEmprunts);


        System.out.println();
        System.out.println("===== Partie II : commandes =====");
        GestionnaireBibliotheque gestionBiblio = new GestionnaireBibliotheque();
        ListeLivres catalogue = new ListeLivres();
        Livre algo = new Livre("Algorithmes", "Cormen", "Informatique");
        Livre reseaux = new Livre("Reseaux", "Tanenbaum", "Informatique");
        Livre petitPrince = new Livre("PetitPrince", "SaintExupery", "Roman");
        catalogue.ajouter(algo);
        catalogue.ajouter(reseaux);
        catalogue.ajouter(petitPrince);
        gestionBiblio.setLivres(catalogue);

        // Tests
        tester(gestionBiblio.traiterCommande(1, "BOOKS", null), "BOOKS 3 {4 Algorithmes Cormen Informatique DISPONIBLE} {5 Reseaux Tanenbaum Informatique DISPONIBLE} {6 PetitPrince SaintExupery Roman DISPONIBLE}");
        tester(gestionBiblio.traiterCommande(1, "HELLO", null), "COMMAND_ERROR");

        // REGISTER
        tester(gestionBiblio.getUtilisateur(120), null);
        tester(gestionBiblio.traiterCommande(1, "REGISTER", "120 ETUDIANT Sara"), "REGISTERED 120 Sara");
        tester(gestionBiblio.getUtilisateur(120).getNom(), "Sara");
        tester(gestionBiblio.traiterCommande(1, "REGISTER", "201 PERSONNEL Julie"), "REGISTERED 201 Julie");
        tester(gestionBiblio.traiterCommande(1, "REGISTER", "301 PROFESSEUR Tremblay"), "REGISTERED 301 Tremblay");
        tester(gestionBiblio.traiterCommande(1, "REGISTER", "120 ETUDIANT Autre"), "BAD_ARGUMENT_ERROR");
        tester(gestionBiblio.traiterCommande(1, "REGISTER", "abc ETUDIANT Sara"), "BAD_ARGUMENT_ERROR");
        tester(gestionBiblio.traiterCommande(1, "REGISTER", "130 DIRECTEUR Paul"), "BAD_ARGUMENT_ERROR");

        // ID
        tester(gestionBiblio.traiterCommande(1, "BORROW", "4 10"), "AUTHENTICATION_ERROR");
        tester(gestionBiblio.traiterCommande(1, "ID", "999"), "BAD_ARGUMENT_ERROR");
        tester(gestionBiblio.traiterCommande(1, "ID", "120"), "AUTHORIZED 120 Sara");
        tester(gestionBiblio.traiterCommande(1, "ID", "201"), "ALREADY_AUTHENTIFIED_ERROR");
        tester(gestionBiblio.traiterCommande(2, "ID", "120"), "TOO_MANY_CONNECTIONS_ERROR");
        tester(gestionBiblio.traiterCommande(2, "ID", "201"), "AUTHORIZED 201 Julie");
        tester(gestionBiblio.traiterCommande(3, "ID", "301"), "AUTHORIZED 301 Tremblay");

        // BORROW
        tester(gestionBiblio.traiterCommande(1, "BORROW", "4 10"), "BORROW_OK 4 4 50");
        tester(gestionBiblio.traiterCommande(2, "BORROW", "4 11"), "BOOK_NOT_AVAILABLE_ERROR");
        tester(gestionBiblio.traiterCommande(1, "BORROW", "99 10"), "BOOK_NOT_FOUND_ERROR");
        tester(gestionBiblio.traiterCommande(1, "BORROW", "5"), "BAD_ARGUMENT_ERROR");
        tester(gestionBiblio.traiterCommande(1, "BORROW", "5 12"), "BORROW_OK 5 5 52");

        // INFO
        tester(gestionBiblio.traiterCommande(1, "INFO", null), "INFO 2 {4 4 10 50 EN_COURS} {5 5 12 52 EN_COURS}");
        tester(gestionBiblio.traiterCommande(1, "INFO", "5"), "INFO 1 {5 5 12 52 EN_COURS}");
        tester(gestionBiblio.traiterCommande(2, "INFO", "5"), "BAD_BORROW_ERROR");
        tester(gestionBiblio.traiterCommande(1, "INFO", "x"), "BAD_ARGUMENT_ERROR");

        // RESERVE
        tester(gestionBiblio.traiterCommande(2, "RESERVE", "6"), "BOOK_ALREADY_AVAILABLE_ERROR");
        tester(gestionBiblio.traiterCommande(2, "RESERVE", "99"), "BOOK_NOT_FOUND_ERROR");
        tester(gestionBiblio.traiterCommande(2, "RESERVE", "4"), "RESERVATION_OK 5 4");
        tester(gestionBiblio.traiterCommande(2, "RESERVE", "4"), "BAD_ARGUMENT_ERROR");
        tester(gestionBiblio.traiterCommande(3, "RESERVE", "4"), "RESERVATION_OK 6 4");

        // RETURN
        tester(gestionBiblio.traiterCommande(2, "RETURN", "4 40"), "BAD_BORROW_ERROR");
        tester(gestionBiblio.traiterCommande(1, "RETURN", "4 60"), "RESERVATION_ASSIGNED 6 4");
        tester(gestionBiblio.traiterCommande(3, "INFO", null), "INFO 1 {6 4 60 100 EN_COURS}");
        tester(gestionBiblio.traiterCommande(3, "RETURN", "6 70"), "RESERVATION_ASSIGNED 7 4");
        tester(gestionBiblio.traiterCommande(2, "RETURN", "7 80"), "RETURN_OK 7 4");
        tester(gestionBiblio.traiterCommande(1, "BOOKS", null), "BOOKS 3 {4 Algorithmes Cormen Informatique DISPONIBLE} {5 Reseaux Tanenbaum Informatique EMPRUNTE} {6 PetitPrince SaintExupery Roman DISPONIBLE}");

        // PENALTY
        tester(gestionBiblio.traiterCommande(1, "PENALTY", "4 100"), "PENALTY 2.50 10");
        tester(gestionBiblio.traiterCommande(1, "PENALTY", "5 60"), "PENALTY 2.00 8");
        tester(gestionBiblio.traiterCommande(1, "PENALTY", "5 30"), "PENALTY 0.00 0");
        tester(gestionBiblio.traiterCommande(2, "PENALTY", "5 60"), "BAD_BORROW_ERROR");

        // EXIT
        tester(gestionBiblio.traiterCommande(1, "EXIT", null), "END");
        tester(gestionBiblio.traiterCommande(1, "EXIT", null), "AUTHENTICATION_ERROR");
        tester(gestionBiblio.traiterCommande(1, "INFO", null), "AUTHENTICATION_ERROR");
        tester(gestionBiblio.traiterCommande(4, "ID", "120"), "AUTHORIZED 120 Sara");

        System.out.println("Tous les tests ont passés");

        System.out.println();
        System.out.println("=== Demo du client (aucune intégration serveur) ===");
        System.out.println("Saisissez EXIT pour quitter:");
        Scanner clavier = new Scanner(System.in);
        String saisie;
        int idConnexion = 9999;
        System.out.print("> ");
        saisie = clavier.nextLine();
        while(! saisie.toUpperCase().equals("EXIT")) {
            int pos = saisie.indexOf(' ');
            String cmd = pos != -1 ? saisie.substring(0, pos) : saisie;
            String arg = pos != -1 ? saisie.substring(pos + 1) : null;

            System.out.println(gestionBiblio.traiterCommande(idConnexion, cmd, arg));
            System.out.print("> ");
            saisie = clavier.nextLine();
        }
    }

    private static <T> void tester(T recu, T attendu) {
        if(!Objects.equals(recu, attendu)) {
            throw new AssertionError(
                    "Attendu: " + attendu + ", mais reçu: " + recu
            );
        }
    }
}
