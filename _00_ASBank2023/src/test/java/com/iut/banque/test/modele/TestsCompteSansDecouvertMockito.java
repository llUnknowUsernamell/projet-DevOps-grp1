package com.iut.banque.test.modele;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.exceptions.InsufficientFundsException;
import com.iut.banque.modele.Client;
import com.iut.banque.modele.CompteSansDecouvert;

public class TestsCompteSansDecouvertMockito {

    private CompteSansDecouvert compte;
    private Client clientMock;

    @Before
    public void setUp() throws IllegalFormatException {
        clientMock = mock(Client.class);
        compte = new CompteSansDecouvert("FR0123456789", 100, clientMock);
    }

    /**
     * Test de la classe getClassName() pour les CompteSansDecouvert
     */
    @Test
    public void testGetClassNameSansDecouvert() {
        assertEquals("CompteSansDecouvert", compte.getClassName());
    }

    /**
     * Test de la méthode débiter avec un montant négatif
     */
    @Test
    public void testCrediterCompteMontantNegatif() {
        try {
            compte.debiter(-100);
            fail("La méthode n'a pas renvoyé d'exception!");
        } catch (IllegalFormatException ife) {
            // Test réussi, exception attendue
        } catch (Exception e) {
            fail("Exception de type " + e.getClass().getSimpleName()
                    + " récupérée alors qu'un IllegalFormatException était attendu");
        }
    }

    /**
     * Tests en rapport avec la méthode "Debiter" de la classe CompteSansDecouvert
     *
     * @throws IllegalFormatException
     */
    @Test
    public void testDebiterCompteAvecDecouvertValeurPossible() throws IllegalFormatException {
        try {
            compte.debiter(50);
            assertEquals(50.0, compte.getSolde(), 0.001);
        } catch (InsufficientFundsException e) {
            fail("Il ne devrait pas avoir d'exception ici.");
        }
    }

    @Test
    public void testDebiterCompteAvecDecouvertValeurImpossible() throws IllegalFormatException {
        try {
            compte.debiter(200);
            fail("Il devrait avoir une InsufficientFundsException ici.");
        } catch (InsufficientFundsException e) {
        }
    }
}
