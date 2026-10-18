package gestionnaireBibliotheque;

import java.util.Map;
import java.util.HashMap;
import java.util.Locale;

public class GestionnaireBibliotheque {
    private Map<Integer, Utilisateur> utilisateurs;
    private Map<Integer, Utilisateur> utilisateursAuthentifies;
    private ListeLivres livres;
    private FilePrioriteReservations reservationsEnAttente;

    private static final String AUTHENTICATION_ERROR = "AUTHENTICATION_ERROR";
    private static final String ALREADY_AUTHENTIFIED_ERROR = "ALREADY_AUTHENTIFIED_ERROR";
    private static final String TOO_MANY_CONNECTIONS_ERROR = "TOO_MANY_CONNECTIONS_ERROR";
    private static final String BAD_ARGUMENT_ERROR = "BAD_ARGUMENT_ERROR";
    private static final String COMMAND_ERROR = "COMMAND_ERROR";
    private static final String BOOK_NOT_FOUND_ERROR = "BOOK_NOT_FOUND_ERROR";
    private static final String BOOK_NOT_AVAILABLE_ERROR = "BOOK_NOT_AVAILABLE_ERROR";
    private static final String BORROW_LIMIT_ERROR = "BORROW_LIMIT_ERROR";
    private static final String RESERVATION_LIMIT_ERROR = "RESERVATION_LIMIT_ERROR";
    private static final String BAD_BORROW_ERROR = "BAD_BORROW_ERROR";
    private static final String BOOK_ALREADY_AVAILABLE_ERROR = "BOOK_ALREADY_AVAILABLE_ERROR";

    public GestionnaireBibliotheque() {
        this.utilisateurs = new HashMap<>();
        this.utilisateursAuthentifies = new HashMap<>();
        this.livres =  new ListeLivres();
        this.reservationsEnAttente = new FilePrioriteReservations();
    }

    public String traiterCommande(int idConnexion, String typeCommande, String argument){
        if (typeCommande == null) {
            return COMMAND_ERROR;
        }
        switch (typeCommande.toUpperCase()){
            case "REGISTER":
                return traiterREGISTER(argument);
            case "ID":
                return traiterID(idConnexion,argument);
            case "BOOKS":
                return traiterBOOKS();
            case "BORROW":
                return traiterBORROW(idConnexion,argument);
            case "RETURN":
                return traiterRETURN(idConnexion,argument);
            case "RESERVE":
                return traiterRESERVE(idConnexion,argument);
            case "INFO":
                return traiterINFO(idConnexion,argument);
            case "PENALTY":
                return traiterPENALTY(idConnexion,argument);
            case "EXIT":
                return traiterEXIT(idConnexion);
            default:
                return COMMAND_ERROR;
        }
    }

    private int[] lireEntiers(String argument, int nombre) {
        if (argument == null) {
            return null;
        }
        String[] morceaux = argument.trim().split(" ");
        if (morceaux.length != nombre) {
            return null;
        }
        int[] valeurs = new int[nombre];
        try {
            for (int i = 0; i < nombre; i++) {
                valeurs[i] = Integer.parseInt(morceaux[i]);
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return valeurs;
    }

    private String formaterEmprunt(Emprunt emprunt) {
        return " {" + emprunt.getId() +
                " " + emprunt.getLivre().getId() +
                " " + emprunt.getJourEmprunt() +
                " " + emprunt.getJourRetourPrevu() +
                " " + emprunt.getStatut() + "}";
    }

    public String traiterID(int idConnexion, String argument){
        int[] valeurs = lireEntiers(argument, 1);
        if (valeurs == null || !utilisateurs.containsKey(valeurs[0])) {
            return BAD_ARGUMENT_ERROR;
        }
        if (utilisateursAuthentifies.containsKey(idConnexion)) {
            return ALREADY_AUTHENTIFIED_ERROR;
        }
        Utilisateur utilisateur = utilisateurs.get(valeurs[0]);
        if (utilisateursAuthentifies.containsValue(utilisateur)) {
            return TOO_MANY_CONNECTIONS_ERROR;
        }
        utilisateursAuthentifies.put(idConnexion, utilisateur);
        return "AUTHORIZED " + utilisateur.getId() + " " + utilisateur.getNom();
    }

    public String traiterBOOKS(){
        String reponse = "BOOKS " + livres.taille();
        for (Livre livre : livres){
            reponse += " {" + livre.getId() +
                    " " + livre.getTitre() +
                    " " + livre.getAuteur() +
                    " " + livre.getCategorie() +
                    " " + livre.getStatut() + "}";
        }
        return reponse;
    }

    public String traiterBORROW(int idConnexion, String argument) {
        Utilisateur utilisateur = utilisateursAuthentifies.get(idConnexion);
        if (utilisateur == null) {
            return AUTHENTICATION_ERROR;
        }
        int[] valeurs = lireEntiers(argument, 2);
        if (valeurs == null) {
            return BAD_ARGUMENT_ERROR;
        }
        Livre livre = livres.rechercher(valeurs[0]);
        if (livre == null) {
            return BOOK_NOT_FOUND_ERROR;
        }
        if (livre.getStatut() != StatutLivre.DISPONIBLE) {
            return BOOK_NOT_AVAILABLE_ERROR;
        }
        if (!utilisateur.peutEmprunter()) {
            return BORROW_LIMIT_ERROR;
        }
        Emprunt emprunt = new Emprunt(livre, utilisateur.getId(), valeurs[1]);
        utilisateur.ajouterEmpruntEnCours(emprunt);
        livre.setStatut(StatutLivre.EMPRUNTE);
        return "BORROW_OK " + emprunt.getId() + " " + livre.getId() + " " + emprunt.getJourRetourPrevu();
    }

    public String traiterRETURN(int idConnexion, String argument){
        Utilisateur utilisateur = utilisateursAuthentifies.get(idConnexion);
        if (utilisateur == null) {
            return AUTHENTICATION_ERROR;
        }
        int[] valeurs = lireEntiers(argument, 2);
        if (valeurs == null) {
            return BAD_ARGUMENT_ERROR;
        }
        Emprunt emprunt = utilisateur.rechercherEmpruntEnCours(valeurs[0]);
        if (emprunt == null) {
            return BAD_BORROW_ERROR;
        }
        int jour = valeurs[1];
        emprunt.retourner(jour);
        utilisateur.supprimerEmpruntEnCours(emprunt.getId());
        utilisateur.ajouterEmpruntTermine(emprunt);

        Livre livre = emprunt.getLivre();
        Reservation reservation = reservationsEnAttente.retirerPourLivre(livre.getId());
        if (reservation == null) {
            livre.setStatut(StatutLivre.DISPONIBLE);
            return "RETURN_OK " + emprunt.getId() + " " + livre.getId();
        }

        reservation.setStatut(StatutReservation.ATTRIBUEE);
        Utilisateur prioritaire = utilisateurs.get(reservation.getIdUtilisateur());
        prioritaire.getReservations().retirerPourLivre(livre.getId());
        Emprunt nouvelEmprunt = new Emprunt(livre, prioritaire.getId(), jour);
        prioritaire.ajouterEmpruntEnCours(nouvelEmprunt);
        livre.setStatut(StatutLivre.EMPRUNTE);
        return "RESERVATION_ASSIGNED " + nouvelEmprunt.getId() + " " + livre.getId();
    }

    public String traiterRESERVE(int idConnexion, String argument){
        Utilisateur utilisateur = utilisateursAuthentifies.get(idConnexion);
        if (utilisateur == null) {
            return AUTHENTICATION_ERROR;
        }
        int[] valeurs = lireEntiers(argument, 1);
        if (valeurs == null) {
            return BAD_ARGUMENT_ERROR;
        }
        Livre livre = livres.rechercher(valeurs[0]);
        if (livre == null) {
            return BOOK_NOT_FOUND_ERROR;
        }
        if (livre.getStatut() == StatutLivre.DISPONIBLE) {
            return BOOK_ALREADY_AVAILABLE_ERROR;
        }
        if (utilisateur.getReservations().taille() >= Utilisateur.MAX_RESERVATIONS) {
            return RESERVATION_LIMIT_ERROR;
        }
        if (utilisateur.possedeReservationEnAttentePourLivre(livre.getId())) {
            return BAD_ARGUMENT_ERROR;
        }
        Reservation reservation = new Reservation(livre, utilisateur.getId(), utilisateur.getTypeUtilisateur());
        reservationsEnAttente.ajouter(reservation);
        utilisateur.ajouterReservation(reservation);
        livre.setStatut(StatutLivre.RESERVE);
        return "RESERVATION_OK " + reservation.getId() + " " + livre.getId();
    }

    public String traiterINFO(int idConnexion, String argument){
        Utilisateur utilisateur = utilisateursAuthentifies.get(idConnexion);
        if (utilisateur == null) {
            return AUTHENTICATION_ERROR;
        }
        if (argument == null || argument.trim().isEmpty()) {
            String reponse = "INFO " + utilisateur.getEmpruntsEnCours().taille();
            for (Emprunt emprunt : utilisateur.getEmpruntsEnCours()) {
                reponse += formaterEmprunt(emprunt);
            }
            return reponse;
        }
        int[] valeurs = lireEntiers(argument, 1);
        if (valeurs == null) {
            return BAD_ARGUMENT_ERROR;
        }
        Emprunt emprunt = utilisateur.rechercherEmpruntEnCours(valeurs[0]);
        if (emprunt == null) {
            return BAD_BORROW_ERROR;
        }
        return "INFO 1" + formaterEmprunt(emprunt);
    }

    public String traiterPENALTY(int idConnexion, String argument){
        Utilisateur utilisateur = utilisateursAuthentifies.get(idConnexion);
        if (utilisateur == null) {
            return AUTHENTICATION_ERROR;
        }
        int[] valeurs = lireEntiers(argument, 2);
        if (valeurs == null) {
            return BAD_ARGUMENT_ERROR;
        }
        Emprunt emprunt = utilisateur.rechercherEmpruntEnCours(valeurs[0]);
        if (emprunt == null) {
            emprunt = utilisateur.rechercherEmpruntTermine(valeurs[0]);
        }
        if (emprunt == null) {
            return BAD_BORROW_ERROR;
        }
        int jour = valeurs[1];
        if (emprunt.getStatut() != StatutEmprunt.EN_COURS) {
            jour = emprunt.getJourRetour();
        }
        int joursRetard = emprunt.calculerJoursRetard(jour);
        double montant = utilisateur.calculerPenalite(joursRetard);
        return "PENALTY " + String.format(Locale.US, "%.2f", montant) + " " + joursRetard;
    }

    public String traiterREGISTER(String argument) {
        if (argument == null) {
            return BAD_ARGUMENT_ERROR;
        }
        String[] argumentInformation = argument.split(" ",3); //" " cherche les espaces dans le string
        if (argumentInformation.length != 3){
            return BAD_ARGUMENT_ERROR;
        }

        int idRegister;
        try {
            idRegister  = Integer.parseInt(argumentInformation[0]);
        } catch (NumberFormatException e){
            return BAD_ARGUMENT_ERROR;
        }
        String typeUtilisateurRegister = argumentInformation[1];
        String nomUtilisateurRegister = argumentInformation[2];

        if (utilisateurs.containsKey(idRegister)){
            return BAD_ARGUMENT_ERROR;
        }

        switch (typeUtilisateurRegister) {
            case "PERSONNEL":
                Personnel personnel = new Personnel(idRegister, nomUtilisateurRegister);
                utilisateurs.put(idRegister,personnel);
                break;
            case "ETUDIANT":
                Etudiant etudiant = new Etudiant(idRegister, nomUtilisateurRegister);
                utilisateurs.put(idRegister,etudiant);
                break;
            case "PROFESSEUR":
                Professeur professeur = new Professeur(idRegister, nomUtilisateurRegister);
                utilisateurs.put(idRegister,professeur);
                break;
            default:
                return BAD_ARGUMENT_ERROR;
        }
        return "REGISTERED " + idRegister + " " + nomUtilisateurRegister;
    }

    public String traiterEXIT(int idConnexion){
        if (!utilisateursAuthentifies.containsKey(idConnexion)) {
            return AUTHENTICATION_ERROR;
        }
        utilisateursAuthentifies.remove(idConnexion);
        return "END";
    }

    public void setLivres(ListeLivres livres) {
        this.livres = livres;
    }
}
