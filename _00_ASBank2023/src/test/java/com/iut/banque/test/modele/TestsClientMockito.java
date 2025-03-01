package com.iut.banque.test.modele;

import com.iut.banque.exceptions.IllegalOperationException;
import com.iut.banque.modele.Client;
import com.iut.banque.modele.CompteAvecDecouvert;
import com.iut.banque.modele.CompteSansDecouvert;
import com.iut.banque.exceptions.IllegalFormatException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestsClientMockito {

    @Mock
    private Client client;

    @Mock
    private CompteAvecDecouvert compteAvecDecouvert;

    @Mock
    private CompteSansDecouvert compteSansDecouvert;

    @Test
    public void testMethodeCheckFormatUserIdClientCorrect() {
        String strClient = "a.utilisateur928";
        boolean isValid = Client.checkFormatUserIdClient(strClient);
        Assert.assertTrue("String " + strClient + " devrait être accepté", isValid);
    }

    @Test
    public void testMethodeCheckFormatUserIdClientCommencantParUnChiffre() {
        String strClient = "32a.abc1";
        boolean isValid = Client.checkFormatUserIdClient(strClient);
        Assert.assertFalse("String " + strClient + " devrait être refusé", isValid);
    }

    @Test
    public void testMethodePossedeComptesADecouvertPourClientAvecQueDesComptesSansDecouvert() throws IllegalFormatException {
        Mockito.when(compteSansDecouvert.getSolde()).thenReturn(42.0);
        Mockito.when(client.possedeComptesADecouvert()).thenReturn(false);

        client.addAccount(compteSansDecouvert);

        Assert.assertFalse("La méthode aurait dû renvoyer faux", client.possedeComptesADecouvert());
    }

    @Test
    public void testMethodePossedeComptesADecouvertPourClientAvecUnCompteADecouvertParmiPlusieursTypesDeComptes() throws IllegalFormatException {
        try {
            Client client = new Client("John", "Doe", "20 rue Bouvier", true, "j.doe1", "password", "1234567890");

            Mockito.when(compteSansDecouvert.getSolde()).thenReturn(42.0);
            Mockito.when(compteAvecDecouvert.getSolde()).thenReturn(-42.0);
            Mockito.when(compteAvecDecouvert.getDecouvertAutorise()).thenReturn(100.0);

            client.addAccount(compteSansDecouvert);
            client.addAccount(compteAvecDecouvert);

            Assert.assertTrue("La méthode aurait dû renvoyer vrai", client.possedeComptesADecouvert());
        } catch (Exception e) {
            Assert.fail("Exception récupérée -> " + e.getMessage());
        }
    }


    @Test
    public void testMethodeGetCompteAvecSoldeNonNulPourClientSansCompte() throws IllegalFormatException {
        Client c = new Client("John", "Doe", "20 rue Bouvier", true, "j.doe1", "password", "1234567890");

        Assert.assertTrue("La méthode aurait dû renvoyer une liste vide", c.getComptesAvecSoldeNonNul().isEmpty());
    }

    @Test
    public void testMethodeGetCompteAvecSoldeNonNulPourClientAvecCompteAvecSoldePositif() throws IllegalFormatException {

        Client c = new Client("John", "Doe", "20 rue Bouvier", true, "j.doe1", "password", "1234567890");

        c.addAccount(new CompteSansDecouvert("FR1234567890", 42, c));

        Assert.assertEquals("La méthode aurait dû renvoyer une liste contenant 1 compte", 1, c.getComptesAvecSoldeNonNul().size());
    }

    @Test
    public void testMethodeGetCompteAvecSoldeNonNulPourClientAvecCompteAvecSoldeNegatif() throws IllegalFormatException, IllegalOperationException {
        Client c = new Client("John", "Doe", "20 rue Bouvier", true, "j.doe1", "password", "1234567890");

        c.addAccount(new CompteAvecDecouvert("FR1234567892", -42, 100, c));

        Assert.assertEquals("La méthode aurait dû renvoyer une liste contenant 1 compte", 1, c.getComptesAvecSoldeNonNul().size());
    }

    @Test
    public void testMethodeGetCompteAvecSoldeNonNulPourClientAvecPlusieursComptes() throws IllegalFormatException, IllegalOperationException {
        Client c = new Client("John", "Doe", "20 rue Bouvier", true, "j.doe1", "password", "1234567890");

        c.addAccount(new CompteAvecDecouvert("FR1234567892", -42, 100, c));
        c.addAccount(new CompteSansDecouvert("FR1234567890", 42, c));
        c.addAccount(new CompteSansDecouvert("FR1234567891", 0, c));

        Assert.assertEquals("La méthode aurait dû renvoyer une liste contenant 2 comptes", 2, c.getComptesAvecSoldeNonNul().size());
    }
}
