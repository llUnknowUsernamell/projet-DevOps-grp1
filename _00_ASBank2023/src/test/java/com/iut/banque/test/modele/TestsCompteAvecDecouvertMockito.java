package com.iut.banque.test.modele;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.exceptions.IllegalOperationException;
import com.iut.banque.exceptions.InsufficientFundsException;
import com.iut.banque.modele.Client;
import com.iut.banque.modele.CompteAvecDecouvert;

public class TestsCompteAvecDecouvertMockito {

    private CompteAvecDecouvert compte;
    private Client clientMock;

    @Before
    public void setUp() throws IllegalFormatException, IllegalOperationException {
        clientMock = mock(Client.class);
        compte = new CompteAvecDecouvert("FR0123456789", 100, 100, clientMock);
    }

    /**
     * Test de la classe getClassName() pour les CompteAvecDecouvert
     */
    @Test
    public void testGetClassNameAvecDecouvert() {
        assertEquals("CompteAvecDecouvert", compte.getClass().getSimpleName());
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
            // Exception attendue
        } catch (Exception e) {
            fail("Exception de type " + e.getClass().getSimpleName()
                    + " récupérée alors qu'un IllegalFormatException était attendu");
        }
    }

    /**
     * Test du débit d'un montant valide pour un compte avec découvert
     */
    @Test
    public void testDebiterCompteAvecDecouvertValeurPossible() throws IllegalFormatException {
        try {
            compte.debiter(150);
            assertEquals(-50.0, compte.getSolde(), 0.0001);
        } catch (InsufficientFundsException e) {
            fail("Il ne devrait pas avoir d'exception ici.");
        }
    }

    /**
     * Test du débit d'un montant trop élevé pour un compte avec découvert
     */
    @Test
    public void testDebiterCompteAvecDecouvertValeurImpossible() throws IllegalFormatException {
        try {
            compte.debiter(250);
            fail("Il devrait avoir une InsufficientFundsException ici.");
        } catch (InsufficientFundsException e) {
            // Exception attendue
        }
    }


}
