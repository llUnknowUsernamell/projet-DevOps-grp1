package com.iut.banque.test.modele;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.modele.Client;
import com.iut.banque.modele.Compte;
import com.iut.banque.modele.CompteSansDecouvert;

/**
 * Tests en rapport avec la méthode "créditer" de la classe Banque
 */
public class TestsCompteMockito {

    private Compte compte;
    private Client clientMock;

    @Before
    public void setUp() throws IllegalFormatException {
        clientMock = mock(Client.class);
        compte = new CompteSansDecouvert("WU1234567890", 0, clientMock);
    }

    /**
     * Test de la méthode crediter
     */
    @Test
    public void testCrediterCompte() throws IllegalFormatException {
        compte.crediter(100);
        assertEquals(100.0, compte.getSolde(), 0.001);
    }

    /**
     * Test de la méthode crediter avec un montant négatif
     */
    @Test
    public void testCrediterCompteMontantNegatif() {
        try {
            compte.crediter(-100);
            fail("La méthode n'a pas renvoyé d'exception!");
        } catch (IllegalFormatException ife) {
        } catch (Exception e) {
            fail("Exception de type " + e.getClass().getSimpleName()
                    + " récupérée alors qu'un IllegalFormatException était attendu");
        }
    }

    /**
     * Test du constructeur avec un format de compte volontairement faux pour
     * tester si une exception est renvoyée.
     */
    @Test
    public void testConstruireCompteAvecFormatNumeroCompteIncorrect() {
        try {
            compte = new CompteSansDecouvert("&éþ_ëüú¤", 0, clientMock);
            fail("Exception non renvoyée par le constructeur avec un format de numéro de compte incorrect");
        } catch (IllegalFormatException ife) {
        } catch (Exception e) {
            fail("Exception de type " + e.getClass().getSimpleName()
                    + " récupérée à la place d'une de type IllegalFormatException");
        }
    }

    @Test
    public void testMethodeCheckFormatNumeroCompteCorrect() {
        String strNumCompte = "FR0123456789";
        if (!Compte.checkFormatNumeroCompte(strNumCompte)) {
            fail("String " + strNumCompte + " refusée dans le test");
        }
    }

    @Test
    public void testMethodeCheckFormatNumeroCompteAvecUneSeuleLettreAuDebut() {
        String strNumCompte = "F0123456789";
        if (Compte.checkFormatNumeroCompte(strNumCompte)) {
            fail("String " + strNumCompte + " validée dans le test");
        }
    }

    @Test
    public void testMethodeCheckFormatNumeroCompteAucuneLettreAuDebut() {
        String strNumCompte = "0123456789";
        if (Compte.checkFormatNumeroCompte(strNumCompte)) {
            fail("String " + strNumCompte + " validée dans le test");
        }
    }

    @Test
    public void testMethodeCheckFormatNumeroCompteAvecTroisLettresAuDebut() {
        String strNumCompte = "FRA0123456789";
        if (Compte.checkFormatNumeroCompte(strNumCompte)) {
            fail("String " + strNumCompte + " validée dans le test");
        }
    }

    @Test
    public void testMethodeCheckFormatNumeroCompteAvecLettresAuMillieu() {
        String strNumCompte = "FR0123A456789";
        if (Compte.checkFormatNumeroCompte(strNumCompte)) {
            fail("String " + strNumCompte + " validée dans le test");
        }
    }

    @Test
    public void testMethodeCheckFormatNumeroCompteAvecPlusDeChiffresQueAttendu() {
        String strNumCompte = "FR00123456789";
        if (Compte.checkFormatNumeroCompte(strNumCompte)) {
            fail("String " + strNumCompte + " validée dans le test");
        }
    }

    @Test
    public void testMethodeCheckFormatNumeroCompteAvecMoinsDeChiffresQueAttendu() {
        String strNumCompte = "FR123456789";
        if (Compte.checkFormatNumeroCompte(strNumCompte)) {
            fail("String " + strNumCompte + " validée dans le test");
        }
    }

    @Test
    public void testMethodeCheckFormatNumeroCompteAvecLettresALaFin() {
        String strNumCompte = "FR0123456789A";
        if (Compte.checkFormatNumeroCompte(strNumCompte)) {
            fail("String " + strNumCompte + " validée dans le test");
        }
    }
}
