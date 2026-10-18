package gestionnaireBibliotheque;

import java.util.Map;
import java.util.HashMap;

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


    public String traiterID(int idConnexion, String argument){
        int idArg;
        try {
            idArg = Integer.parseInt(argument);
        } catch (NumberFormatException e){
            return BAD_ARGUMENT_ERROR;
        }

        if (! (utilisateurs.containsKey(idArg)))
            return BAD_ARGUMENT_ERROR;

        if (utilisateursAuthentifies.containsKey(idConnexion))
            return ALREADY_AUTHENTIFIED_ERROR;

        if (utilisateursAuthentifies.containsKey(idArg))
            return TOO_MANY_CONNECTIONS_ERROR;

        utilisateursAuthentifies.put(idArg, utilisateurs.get(idArg));
        return String.format("AUTHORIZED %d %s", idArg, utilisateurs.get(idArg).getNom());
    }
    public String traiterBOOKS(){
        String reponse = "BOOKS <" + livres.taille() + "> ";
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
        return argument;
    }
    public String traiterRETURN(int idConnexion, String argument){
        return argument;
    }

    public String traiterRESERVE(int idConnexion, String argument){
        return argument;
    }
    public String traiterINFO(int idConnexion, String argument){
        return argument;
    }
    public String traiterPENALTY(int idConnexion, String argument){
        return argument;
    }
    public String traiterREGISTER(String argument) {
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
        return "END";
    }

    public void setLivres(ListeLivres livres) {
        this.livres = livres;
    }
}
