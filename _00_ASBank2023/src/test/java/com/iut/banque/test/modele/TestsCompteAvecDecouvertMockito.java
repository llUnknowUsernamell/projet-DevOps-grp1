package com.iut.banque.test.modele;

import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.exceptions.IllegalOperationException;
import com.iut.banque.exceptions.InsufficientFundsException;
import com.iut.banque.modele.Client;
import com.iut.banque.modele.CompteAvecDecouvert;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestsCompteAvecDecouvertMockito {

    @Mock
    private Client clientMock;

    private CompteAvecDecouvert compte;

    @Test
    public void testGetClassNameAvecDecouvert() throws IllegalFormatException, IllegalOperationException {
        compte = new CompteAvecDecouvert("FR0123456789", 100, 100, clientMock);
        Assert.assertEquals("CompteAvecDecouvert", compte.getClass().getSimpleName());
    }

    @Test
    public void testCrediterCompteMontantNegatif() throws IllegalFormatException, IllegalOperationException {
        compte = new CompteAvecDecouvert("FR0123456789", 100, 100, clientMock);

        try {
            compte.debiter(-100);
            Assert.fail("La méthode n'a pas renvoyé d'exception!");
        } catch (IllegalFormatException ife) {
        } catch (Exception e) {
            Assert.fail("Exception de type " + e.getClass().getSimpleName()
                    + " récupérée alors qu'une IllegalFormatException était attendue");
        }
    }

    @Test
    public void testDebiterCompteAvecDecouvertValeurPossible() throws IllegalFormatException, IllegalOperationException {
        compte = new CompteAvecDecouvert("FR0123456789", 100, 100, clientMock);

        try {
            compte.debiter(150);
            Assert.assertEquals(-50.0, compte.getSolde(), 0.0001);
        } catch (InsufficientFundsException e) {
            Assert.fail("Il ne devrait pas y avoir d'exception ici.");
        }
    }

    @Test
    public void testDebiterCompteAvecDecouvertValeurImpossible() throws IllegalFormatException, IllegalOperationException {
        compte = new CompteAvecDecouvert("FR0123456789", 100, 100, clientMock);

        try {
            compte.debiter(250);
            Assert.fail("Il devrait y avoir une InsufficientFundsException ici.");
        } catch (InsufficientFundsException e) {
        }
    }
}
