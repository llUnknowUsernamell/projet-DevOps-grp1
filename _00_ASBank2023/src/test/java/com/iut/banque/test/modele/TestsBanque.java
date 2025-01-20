package com.iut.banque.test.modele;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.iut.banque.exceptions.InsufficientFundsException;
import com.iut.banque.exceptions.IllegalOperationException;
import com.iut.banque.exceptions.TechnicalException;


import com.iut.banque.modele.Client;
import com.iut.banque.modele.CompteAvecDecouvert;
import org.junit.Before;
import org.junit.Test;


import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.modele.Banque;
import com.iut.banque.modele.CompteSansDecouvert;


public class TestsBanque {

    private Banque banque;
    private CompteSansDecouvert compteSansDecouvert;
    private CompteAvecDecouvert compteAvecDecouvert;
    private Client client;

    @Before
    public void setUp() throws IllegalFormatException {
        // Initialisation de la banque
        banque = new Banque();

        client = new Client("michelle", "jaquemin", "123, grande rue, Metz", false, "m.jaquin1", "clientpass3", "1234567890");

        // Initialisation d'un compte sans découvert avec un numéro conforme
        compteSansDecouvert = new CompteSansDecouvert("FR1234567890", 50, client);
    try {
        compteAvecDecouvert = new CompteAvecDecouvert("FR1234567899", 50, 40, client);
    } catch (Exception e) {
        fail("L'initialisation du compte avec découvert a échoué : " + e.getMessage());
    }
    }

    @Test
    public void testDebiter() throws IllegalFormatException {
        try {
            banque.debiter(compteSansDecouvert, 50);
            assertEquals(0, compteSansDecouvert.getSolde(), 0.001);
        } catch (InsufficientFundsException | IllegalFormatException e) {
            fail("Exception ne devrait pas être levée");
        }
    }

    @Test
    public void testCrediter() throws IllegalFormatException {
        try {
            banque.crediter(compteSansDecouvert, 50);
            assertEquals(100, compteSansDecouvert.getSolde(), 0.001);
        } catch (IllegalFormatException e) {
            fail("Exception ne devrait pas être levée");
        }
    }

    @Test
    public void testDebiterAvecDecouvertAutorise() {
        try {
            // Solde initial : 50, Découvert autorisé : 40
            banque.debiter(compteAvecDecouvert, 85); // Résultat attendu : Solde = -35
            assertEquals(-150, compteAvecDecouvert.getSolde(), 0.001);
        } catch (Exception e) {
            fail("Aucune exception ne devrait être levée pour ce test.");
        }
    }



}
