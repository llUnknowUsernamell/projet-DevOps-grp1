package com.iut.banque.test.modele;

import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.exceptions.InsufficientFundsException;
import com.iut.banque.modele.Client;
import com.iut.banque.modele.CompteSansDecouvert;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestsCompteSansDecouvertMockito {

    @Mock
    private Client clientMock;

    private CompteSansDecouvert compte;

    @Test
    public void testGetClassNameSansDecouvert() throws IllegalFormatException {
        compte = new CompteSansDecouvert("FR0123456789", 100, clientMock);

        Assert.assertEquals("CompteSansDecouvert", compte.getClass().getSimpleName());
    }

    @Test
    public void testCrediterCompteMontantNegatif() throws IllegalFormatException {
        compte = new CompteSansDecouvert("FR0123456789", 100, clientMock);

        try {
            compte.debiter(-100);
            Assert.fail("La méthode n'a pas renvoyé d'exception!");
        } catch (IllegalFormatException ife) {
        } catch (Exception e) {
            Assert.fail("Exception de type " + e.getClass().getSimpleName()
                    + " récupérée alors qu'un IllegalFormatException était attendu");
        }
    }

    @Test
    public void testDebiterCompteAvecDecouvertValeurPossible() throws IllegalFormatException {
        compte = new CompteSansDecouvert("FR0123456789", 100, clientMock);

        try {
            compte.debiter(50);
            Assert.assertEquals(50.0, compte.getSolde(), 0.001);
        } catch (InsufficientFundsException e) {
            Assert.fail("Il ne devrait pas y avoir d'exception ici.");
        }
    }

    @Test
    public void testDebiterCompteAvecDecouvertValeurImpossible() throws IllegalFormatException {
        compte = new CompteSansDecouvert("FR0123456789", 100, clientMock);

        try {
            compte.debiter(200);
            Assert.fail("Il devrait y avoir une InsufficientFundsException ici.");
        } catch (InsufficientFundsException e) {
        }
    }
}
