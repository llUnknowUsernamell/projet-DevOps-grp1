package com.iut.banque.test.modele;

import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.modele.Client;
import com.iut.banque.modele.Compte;
import com.iut.banque.modele.CompteSansDecouvert;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestsCompteMockito {

    @Mock
    private Client clientMock;

    private Compte compte;

    @Test
    public void testCrediterCompte() throws IllegalFormatException {
        compte = new CompteSansDecouvert("WU1234567890", 0, clientMock);

        compte.crediter(100);
        Assert.assertEquals("Le solde après crédit devrait être 100", 100.0, compte.getSolde(), 0.001);
    }

    @Test
    public void testCrediterCompteMontantNegatif() throws IllegalFormatException {
        compte = new CompteSansDecouvert("WU1234567890", 0, clientMock);

        try {
            compte.crediter(-100);
            Assert.fail("La méthode n'a pas renvoyé d'exception!");
        } catch (IllegalFormatException ife) {
        } catch (Exception e) {
            Assert.fail("Exception de type " + e.getClass().getSimpleName()
                    + " récupérée alors qu'une IllegalFormatException était attendue");
        }
    }

    @Test
    public void testConstruireCompteAvecFormatNumeroCompteIncorrect() {
        try {
            compte = new CompteSansDecouvert("&éþ_ëüú¤", 0, clientMock);
            Assert.fail("Exception non renvoyée par le constructeur avec un format de numéro de compte incorrect");
        } catch (IllegalFormatException ife) {
        } catch (Exception e) {
            Assert.fail("Exception de type " + e.getClass().getSimpleName()
                    + " récupérée à la place d'une de type IllegalFormatException");
        }
    }

    @Test
    public void testMethodeCheckFormatNumeroCompteCorrect() {
        String strNumCompte = "FR0123456789";
        Assert.assertTrue("Le numéro de compte " + strNumCompte + " devrait être valide", Compte.checkFormatNumeroCompte(strNumCompte));
    }

    @Test
    public void testMethodeCheckFormatNumeroCompteAvecUneSeuleLettreAuDebut() {
        String strNumCompte = "F0123456789";
        Assert.assertFalse("Le numéro de compte " + strNumCompte + " ne devrait pas être valide", Compte.checkFormatNumeroCompte(strNumCompte));
    }

    @Test
    public void testMethodeCheckFormatNumeroCompteAucuneLettreAuDebut() {
        String strNumCompte = "0123456789";
        Assert.assertFalse("Le numéro de compte " + strNumCompte + " ne devrait pas être valide", Compte.checkFormatNumeroCompte(strNumCompte));
    }

    @Test
    public void testMethodeCheckFormatNumeroCompteAvecTroisLettresAuDebut() {
        String strNumCompte = "FRA0123456789";
        Assert.assertFalse("Le numéro de compte " + strNumCompte + " ne devrait pas être valide", Compte.checkFormatNumeroCompte(strNumCompte));
    }

    @Test
    public void testMethodeCheckFormatNumeroCompteAvecLettresAuMillieu() {
        String strNumCompte = "FR0123A456789";
        Assert.assertFalse("Le numéro de compte " + strNumCompte + " ne devrait pas être valide", Compte.checkFormatNumeroCompte(strNumCompte));
    }

    @Test
    public void testMethodeCheckFormatNumeroCompteAvecPlusDeChiffresQueAttendu() {
        String strNumCompte = "FR00123456789";
        Assert.assertFalse("Le numéro de compte " + strNumCompte + " ne devrait pas être valide", Compte.checkFormatNumeroCompte(strNumCompte));
    }

    @Test
    public void testMethodeCheckFormatNumeroCompteAvecMoinsDeChiffresQueAttendu() {
        String strNumCompte = "FR123456789";
        Assert.assertFalse("Le numéro de compte " + strNumCompte + " ne devrait pas être valide", Compte.checkFormatNumeroCompte(strNumCompte));
    }

    @Test
    public void testMethodeCheckFormatNumeroCompteAvecLettresALaFin() {
        String strNumCompte = "FR0123456789A";
        Assert.assertFalse("Le numéro de compte " + strNumCompte + " ne devrait pas être valide", Compte.checkFormatNumeroCompte(strNumCompte));
    }
}
