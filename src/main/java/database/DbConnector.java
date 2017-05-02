package database; 

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DbConnector { 
    private static DbConnector dbConnector;
    private EntityManagerFactory emf;
    
    
    private DbConnector(){
        emf = Persistence.createEntityManagerFactory("ExamPrep_JPA1PU");
    }
    
    public static DbConnector Instance(){
        if(dbConnector == null)
            dbConnector = new DbConnector();
        return dbConnector;
    }
    
    public EntityManager getEm(){
        return emf.createEntityManager();
    }
}