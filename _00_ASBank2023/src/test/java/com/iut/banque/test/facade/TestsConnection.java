package com.iut.banque.test.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import com.iut.banque.exceptions.TechnicalException;

import com.iut.banque.controller.Connect;
import com.iut.banque.facade.BanqueFacade;
import com.iut.banque.modele.Banque;
import com.iut.banque.modele.Utilisateur;
import com.iut.banque.facade.BanqueManager;
import com.iut.banque.facade.LoginManager;

public class TestsConnection {

    private Connect connect;
    private Banque banque;
    private LoginManager loginManager;
    private BanqueManager banqueManager;

    @Before
    public void setUp() {
        banque = Mockito.mock(Banque.class);
        loginManager = Mockito.mock(LoginManager.class);
        banqueManager = Mockito.mock(BanqueManager.class);

        connect = new Connect(banque, loginManager, banqueManager);
    }

    /**
     * Test de la méthode getRefreshedUser() quand loadUser fonctionne sans erreur
     */
    @Test
    public void testGetRefreshedUser_Success() {
        Mockito.doNothing().when(banque).loadUser();

        String result = connect.getRefreshedUser();


        assertEquals("SUCCESS", result);
    }

    /**
     * Test de la méthode getRefreshedUser() quand loadUser lance une exception
     */
    @Test
    public void testGetRefreshedUser_Error() {
        Mockito.doThrow(new TechnicalException("Erreur lors du rechargement des données")).when(banque).loadUser();
        String result = connect.getRefreshedUser();

        assertEquals("ERROR", result);
    }

    /**
     * Test de la méthode getRefreshedUser() quand loadUser lance une exception inattendue
     */
    @Test
    public void testGetRefreshedUser_UnexpectedException() {
        Mockito.doThrow(new RuntimeException("Erreur inattendue")).when(banque).loadUser();

        String result = connect.getRefreshedUser();

        assertEquals("ERROR", result);
    }

    /**
     * Test de la méthode loadUser() quand l'utilisateur est connecté et valide
     */
    @Test
    public void testLoadUser_ValidUser() {
        Utilisateur mockUser = Mockito.mock(Utilisateur.class);
        Mockito.when(loginManager.getConnectedUser()).thenReturn(mockUser);
        Mockito.when(banqueManager.getUserById(Mockito.anyInt())).thenReturn(mockUser);

        banque.loadUser();

        Mockito.verify(loginManager).setCurrentUser(mockUser);
    }

    /**
     * Test de la méthode loadUser() quand l'utilisateur n'est pas valide
     */
    @Test
    public void testLoadUser_InvalidUser() {
        Mockito.when(loginManager.getConnectedUser()).thenReturn(null);

        banque.loadUser();

        Mockito.verify(loginManager, Mockito.never()).setCurrentUser(Mockito.any());
    }
}
