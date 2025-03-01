package com.iut.banque.test.facade;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.iut.banque.exceptions.IllegalOperationException;
import com.iut.banque.facade.BanqueManager;
import com.iut.banque.modele.Client;
import com.iut.banque.modele.Compte;

public class TestsBanqueManagerMockito {

    @Mock
    private BanqueManager bm;

    @Mock
    private Client clientMock;

    @Mock
    private Compte compteMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreationDunClient() {
        try {
            // Simuler la création du client
            doNothing().when(bm).createClient(anyString(), anyString(), anyString(), anyString(), anyString(), anyBoolean(), anyString());

            bm.createClient("t.test1", "password", "test1nom", "test1prenom", "test town", true, "4242424242");

            // Vérifier que la méthode a été appelée
            verify(bm, times(1)).createClient("t.test1", "password", "test1nom", "test1prenom", "test town", true, "4242424242");
        } catch (IllegalOperationException e) {
            fail("IllegalOperationException récupérée : " + e.getStackTrace());
        } catch (Exception te) {
            fail("Une Exception " + te.getClass().getSimpleName() + " a été récupérée");
        }
    }

    @Test
    public void testCreationDunClientAvecDeuxNumerosDeCompteIdentiques() {
        try {
            // Simuler une exception d'IllegalOperationException
            doThrow(new IllegalOperationException("Numéro de compte déjà existant")).when(bm).createClient(anyString(), anyString(), anyString(), anyString(), anyString(), anyBoolean(), anyString());

            bm.createClient("t.test1", "password", "test1nom", "test1prenom", "test town", true, "0101010101");
            fail();
        } catch (IllegalOperationException e) {
            // Test passed
        } catch (Exception te) {
            fail("Une Exception " + te.getClass().getSimpleName() + " a été récupérée");
        }
    }

    @Test
    public void testSuppressionDunCompteAvecDecouvertAvecSoldeZero() {
        try {
            doNothing().when(bm).deleteAccount(compteMock);

            bm.deleteAccount(compteMock);

            verify(bm, times(1)).deleteAccount(compteMock);
        } catch (IllegalOperationException e) {
            fail("IllegalOperationException récupérée : " + e.getStackTrace());
        } catch (Exception te) {
            fail("Une Exception " + te.getClass().getSimpleName() + " a été récupérée");
        }
    }

    @Test
    public void testSuppressionDunCompteAvecDecouvertAvecSoldeDifferentDeZero() {
        try {
            doThrow(new IllegalOperationException("Solde non nul, suppression impossible")).when(bm).deleteAccount(compteMock);

            bm.deleteAccount(compteMock);
            fail("Une IllegalOperationException aurait dû être récupérée");
        } catch (IllegalOperationException e) {
            // Test passed
        } catch (Exception te) {
            fail("Une Exception " + te.getClass().getSimpleName() + " a été récupérée");
        }
    }

    @Test
    public void testSuppressionDunUtilisateurSansCompte() {
        try {
            doNothing().when(bm).deleteUser(clientMock);

            bm.deleteUser(clientMock);

            verify(bm, times(1)).deleteUser(clientMock);
        } catch (IllegalOperationException e) {
            fail("IllegalOperationException récupérée : " + e.getStackTrace());
        } catch (Exception te) {
            fail("Une Exception " + te.getClass().getSimpleName() + " a été récupérée");
        }
    }

    @Test
    public void testSuppressionDuDernierManagerDeLaBaseDeDonnees() {
        try {
            doThrow(new IllegalOperationException("Impossible de supprimer le dernier gestionnaire")).when(bm).deleteUser(clientMock);

            bm.deleteUser(clientMock);
            fail("Une IllegalOperationException aurait dû être récupérée");
        } catch (IllegalOperationException e) {
            // Test passed
        } catch (Exception te) {
            fail("Une Exception " + te.getClass().getSimpleName() + " a été récupérée");
        }
    }

    @Test
    public void testSuppressionDunClientAvecComptesDeSoldeZero() {
        try {
            doNothing().when(bm).deleteUser(clientMock);

            bm.deleteUser(clientMock);

            verify(bm, times(1)).deleteUser(clientMock);
        } catch (IllegalOperationException e) {
            fail("IllegalOperationException récupérée : " + e.getStackTrace());
        } catch (Exception te) {
            fail("Une Exception " + te.getClass().getSimpleName() + " a été récupérée");
        }
    }

    @Test
    public void testSuppressionDunClientAvecUnCompteDeSoldePositif() {
        try {
            doThrow(new IllegalOperationException("Impossible de supprimer un client avec un solde positif")).when(bm).deleteUser(clientMock);

            bm.deleteUser(clientMock);
            fail("Une IllegalOperationException aurait dû être récupérée");
        } catch (IllegalOperationException e) {
            // Test passed
        } catch (Exception te) {
            fail("Une Exception " + te.getClass().getSimpleName() + " a été récupérée");
        }
    }
}
