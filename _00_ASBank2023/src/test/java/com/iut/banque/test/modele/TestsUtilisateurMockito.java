package com.iut.banque.test.modele;

import com.iut.banque.dao.DaoHibernate;
import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.exceptions.InsufficientFundsException;
import com.iut.banque.facade.BanqueManager;
import com.iut.banque.interfaces.IDao;
import com.iut.banque.modele.Client;
import com.iut.banque.modele.Compte;
import com.iut.banque.modele.CompteAvecDecouvert;
import com.iut.banque.modele.CompteSansDecouvert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
@RunWith(MockitoJUnitRunner.class)
public class TestsUtilisateurMockito {

    @Mock
    private DaoHibernate daoMock;

    @InjectMocks
    private BanqueManager bm;  // Mockito va injecter daoMock ici

    private CompteSansDecouvert compte;

    @Before
    public void setUp() throws IllegalFormatException {
        // Instancier un compte factice
        compte = new CompteSansDecouvert("FR0123456789", 100, new Client("John", "Doe", "20 rue Bouvier", true, "j.doe1", "password", "1234567890"));

        // S'assurer que le mock est utilisé
        when(daoMock.getAccountById("AEZ484Z")).thenReturn(compte);
    }

    @Test
    public void testUserConnectionWithDao() throws IllegalFormatException {
        // Appel de la méthode testée
        Compte cmpt = bm.getAccountById("AEZ484Z");

        // Vérifier que le compte récupéré est bien celui mocké
        assertNotNull(cmpt);
        assertEquals(100, cmpt.getSolde(), 0.01);

        // Tenter un débit et vérifier le solde mis à jour
        try {
            cmpt.debiter(50);
        } catch (InsufficientFundsException e) {
            fail("Il ne devrait pas avoir d'exception ici.");
        }

        // Vérifier que `daoMock.getAccountById` a bien été appelé une seule fois
        verify(daoMock, times(1)).getAccountById("AEZ484Z");
    }
}