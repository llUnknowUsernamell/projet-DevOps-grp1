package com.iut.banque.test.modele;

import com.iut.banque.interfaces.IDao;
import com.iut.banque.modele.Gestionnaire;
import com.iut.banque.modele.Utilisateur;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.regex.Matcher;

@RunWith(MockitoJUnitRunner.class)
public class TestsUtilisateur {

    @Test
    public void testUserConnectionWithDao() {
        // Créer des mocks pour les objets
        IDao dao = Mockito.mock(IDao.class);
        Gestionnaire gestionnaireUser = Mockito.mock(Gestionnaire.class);


        // Stubbing : définir le comportement des méthodes mockées
        Mockito.when(gestionnaireUser.getUserId()).thenReturn("id_jean");
        Mockito.when(gestionnaireUser.getUserPwd()).thenReturn("mdp_jean");

        // Lorsque getUserById est appelé avec n'importe quelle chaîne, il renverra l'utilisateur mocké
        Mockito.when(dao.getUserById(Matchers.anyString())).thenReturn(gestionnaireUser);

        // Exécuter la méthode testée
        Utilisateur result = dao.getUserById("id_jean");

        // Vérification des résultats
        Assert.assertNotNull(result);  // Vérifier que l'utilisateur retourné n'est pas nul
        Assert.assertEquals(result.getUserId(), "id_jean");  // Vérifier que l'ID de l'utilisateur est correct
        Assert.assertEquals(result.getUserPwd(), "mdp_jean");  // Vérifier que le mot de passe est correct

        // Vérification que la méthode getUserById a bien été appelée avec l'ID "id"
        Mockito.verify(dao).getUserById("id_jean");
    }


}