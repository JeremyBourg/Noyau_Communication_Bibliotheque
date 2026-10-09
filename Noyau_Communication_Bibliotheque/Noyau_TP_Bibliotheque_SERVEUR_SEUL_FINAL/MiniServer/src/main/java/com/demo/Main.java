/*
 * ================================================================
 * CODE FOURNI - INF111
 * Noyau client-serveur
 *
 * Auteur original : Professeur Abdelmoumene  Toudeft
 *
 *
 * Ce fichier fait partie du noyau client-serveur fourni aux etudiants.
 * Le code de communication est deja fonctionnel et ne doit pas etre
 * modifie, sauf indication contraire dans l'enonce du travail pratique.
 * ================================================================
 */

package com.demo;

import com.atoudeft.serveur.Config;
import com.atoudeft.serveur.Serveur;
import gestionnaireBibliotheque.Emprunt;
import gestionnaireBibliotheque.Livre;

import java.util.Scanner;

/**
 * Programme simple de d�monstration d'un serveur. Le programme d�marre un serveur qui se met � �couter
 * l'arriv�e de connexions.
 *
 * @author Abdelmoum�ne Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class Main {
    /**
     * M�thode principale du programme.
     *
     * @param args Arguments du programme
     */
    public static void main(String[] args) {
        Livre testLivre = new Livre("Magnus", "Bolivian", "Fiction");
        Emprunt test = new Emprunt(testLivre, 20, 20);
        //Emprunt test2 = new Emprunt(testLivre, 2,2);
        System.out.println(testLivre);
        System.out.println(test);
//        System.out.println("Est en retard: " + test.estEnRetard(52));
//        System.out.println("Jours de retard: " + test.calculerJoursRetard(61));
//        System.out.println("ID emprunt : " + test.prochainID());
//        System.out.println(test.calculerJoursRetard(2));
//        test.retourner(82);
//        System.out.println(test);

        Scanner clavier = new Scanner(System.in);
        String saisie;

        Serveur serveur = new Serveur(Config.PORT_SERVEUR);
        if (serveur.demarrer()) {

            System.out.println("Saisissez EXIT pour arreter le serveur.");
            saisie = clavier.nextLine();
            while (!"EXIT".equals(saisie)) {
                System.out.println("??? Saisissez EXIT pour arreter le serveur.");
                saisie = clavier.nextLine();
            }System.out.println("Serveur demaré et à l'ecoute " );
        }

        serveur.arreter();
    }
}