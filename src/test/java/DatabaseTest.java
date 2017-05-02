/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import database.DbConnector;
import database.DbFacade;
import entity.ProjectUser;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author alexander
 */
public class DatabaseTest {

    @Test
    public void testGetUser() {
        DbConnector con = mock(DbConnector.class);
        EntityManager em = mock(EntityManager.class);
        EntityTransaction et = mock(EntityTransaction.class);
        
        DbFacade facade = new DbFacade(con);

        long id = 1;
        ProjectUser returnUser = new ProjectUser();
        returnUser.setId(id);
        returnUser.setEmail("test@test.dk");
        returnUser.setUserName("test");

        when(con.getEm()).thenReturn(em);

        when(em.find(ProjectUser.class, id)).thenReturn(returnUser);
        when(em.getTransaction()).thenReturn(et);
        
        ProjectUser user = facade.findUser(id);

        assertEquals(returnUser, user);

        verify(con).getEm();
        verify(em).find(ProjectUser.class, id);
    }
}
