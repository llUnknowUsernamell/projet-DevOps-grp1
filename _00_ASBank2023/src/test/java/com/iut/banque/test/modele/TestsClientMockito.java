package com.iut.banque.test.modele;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import com.iut.banque.exceptions.IllegalFormatException;
import org.junit.Before;
import org.junit.Test;

import com.iut.banque.modele.Client;
import com.iut.banque.modele.CompteAvecDecouvert;
import com.iut.banque.modele.CompteSansDecouvert;

public class TestsClientMockito {

    private Client client;
    private CompteAvecDecouvert compteAvecDecouvert;
    private CompteSansDecouvert compteSansDecouvert;

    @Before
    public void setUp() throws IllegalFormatException {
        client = new Client("John", "Doe", "20 rue Bouvier", true, "j.doe1", "password", "1234567890");
        compteAvecDecouvert = mock(CompteAvecDecouvert.class);
        compteSansDecouvert = mock(CompteSansDecouvert.class);
    }

    @Test
    public void testMethodeCheckFormatUserIdClientCorrect() {
        String strClient = "a.utilisateur928";
        if (!Client.checkFormatUserIdClient(strClient)) {
            fail("String " + strClient + " refusé dans le test");
        }
    }

    @Test
    public void testMethodeCheckFormatUserIdClientCommencantParUnChiffre() {
        String strClient = "32a.abc1";
        if (Client.checkFormatUserIdClient(strClient)) {
            fail("String " + strClient + " validé dans le test");
        }
    }

    @Test
    public void testMethodePossedeComptesADecouvertPourClientAvecQueDesComptesSansDecouvert() {
        try {
            when(compteSansDecouvert.getSolde()).thenReturn(42.0);
            client.addAccount(compteSansDecouvert);
            when(compteSansDecouvert.getSolde()).thenReturn(0.0);
            client.addAccount(compteSansDecouvert);

            if (client.possedeComptesADecouvert()) {
                fail("La méthode aurait dû renvoyer faux");
            }
        } catch (Exception e) {
            fail("Exception récupérée -> " + e.getMessage());
        }
    }

    @Test
    public void testMethodePossedeComptesADecouvertPourClientAvecUnCompteADecouvertParmiPlusieursTypesDeComptes() {
        try {
            when(compteSansDecouvert.getSolde()).thenReturn(42.0);
            client.addAccount(compteSansDecouvert);

            when(compteAvecDecouvert.getSolde()).thenReturn(-42.0);
            when(compteAvecDecouvert.getDecouvertAutorise()).thenReturn(100.0);
            client.addAccount(compteAvecDecouvert);

            if (!client.possedeComptesADecouvert()) {
                fail("La méthode aurait dû renvoyer vrai");
            }
        } catch (Exception e) {
            fail("Exception récupérée -> " + e.getMessage());
        }
    }

    @Test
    public void testMethodePossedeComptesADecouvertPourClientAvecPlusieursComptesADecouvertParmiPlusieursTypesDeComptes() {
        try {
            when(compteSansDecouvert.getSolde()).thenReturn(42.0);
            client.addAccount(compteSansDecouvert);

            when(compteAvecDecouvert.getSolde()).thenReturn(-42.0);
            when(compteAvecDecouvert.getDecouvertAutorise()).thenReturn(100.0);
            client.addAccount(compteAvecDecouvert);

            when(compteAvecDecouvert.getSolde()).thenReturn(-4242.0);
            when(compteAvecDecouvert.getDecouvertAutorise()).thenReturn(5000.0);
            client.addAccount(compteAvecDecouvert);

            if (!client.possedeComptesADecouvert()) {
                fail("La méthode aurait dû renvoyer vrai");
            }
        } catch (Exception e) {
            fail("Exception récupérée -> " + e.getMessage());
        }
    }

    @Test
    public void testMethodeGetCompteAvecSoldeNonNulPourClientSansCompte() {
        try {
            Client c = new Client("John", "Doe", "20 rue Bouvier", true, "j.doe1", "password", "1234567890");
            if (c.getComptesAvecSoldeNonNul().size() != 0) {
                fail("La méthode aurait du renvoyer une liste vide");
            }
        } catch (Exception e) {
            fail("Exception récupérée -> " + e.getStackTrace().toString());
        }
    }

    @Test
    public void testMethodeGetCompteAvecSoldeNonNulPourClientAvecCompteAvecSoldePositif() {
        try {
            Client c = new Client("John", "Doe", "20 rue Bouvier", true, "j.doe1", "password", "1234567890");
            c.addAccount(new CompteSansDecouvert("FR1234567890", 42, c));
            if (c.getComptesAvecSoldeNonNul().size() != 1) {
                fail("La méthode aurait du renvoyer une liste contenant 1 compte");
            }
        } catch (Exception e) {
            fail("Exception récupérée -> " + e.getStackTrace().toString());
        }
    }

    @Test
    public void testMethodeGetCompteAvecSoldeNonNulPourClientAvecCompteAvecSoldeNegatif() {
        try {
            Client c = new Client("John", "Doe", "20 rue Bouvier", true, "j.doe1", "password", "1234567890");
            c.addAccount(new CompteAvecDecouvert("FR1234567892", -42, 100, c));
            if (c.getComptesAvecSoldeNonNul().size() != 1) {
                fail("La méthode aurait du renvoyer une liste contenant 1 compte");
            }
        } catch (Exception e) {
            fail("Exception récupérée -> " + e.getStackTrace().toString());
        }
    }

    @Test
    public void testMethodeGetCompteAvecSoldeNonNulPourClientAvecPlusieursComptes() {
        try {
            Client c = new Client("John", "Doe", "20 rue Bouvier", true, "j.doe1", "password", "1234567890");
            c.addAccount(new CompteAvecDecouvert("FR1234567892", -42, 100, c));
            c.addAccount(new CompteSansDecouvert("FR1234567890", 42, c));
            c.addAccount(new CompteSansDecouvert("FR1234567891", 0, c));
            if (c.getComptesAvecSoldeNonNul().size() != 2) {
                fail("La méthode aurait du renvoyer une liste contenant 2 comptes");
            }
        } catch (Exception e) {
            fail("Exception récupérée -> " + e.getStackTrace().toString());
        }
    }

    @Test
    public void testMethodeGetCompteAvecSoldeNonNulPourClientAvecSoldeZero() {
        try {
            Client c = new Client("John", "Doe", "20 rue Bouvier", true, "j.doe1", "password", "1234567890");
            c.addAccount(new CompteSansDecouvert("FR1234567890", 0, c));
            if (c.getComptesAvecSoldeNonNul().size() != 0) {
                fail("La méthode aurait du renvoyer une liste vide");
            }
        } catch (Exception e) {
            fail("Exception récupérée -> " + e.getStackTrace().toString());
        }
    }
}

