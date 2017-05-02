package database; 

import entity.Project;
import entity.ProjectUser;
import entity.Task;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

public class DbFacade { 
    private DbConnector dbConnector;

    public DbFacade(DbConnector dbConnector) {
        this.dbConnector = dbConnector;
    }
    
    public ProjectUser createUser(String userName, String email){
        EntityManager em = dbConnector.getEm();
        
        ProjectUser projectUser = new ProjectUser();
        
        projectUser.setUserName(userName);
        projectUser.setEmail(email);
        projectUser.setCreated(Date.from(Instant.now()));
        
        try{
            em.getTransaction().begin();
            
            em.persist(projectUser);
            
            em.getTransaction().commit();
        }finally{
            em.close();
        }
        
        return projectUser;
    }
    
    public ProjectUser findUser(long id){
        EntityManager em = dbConnector.getEm();
        
        ProjectUser user;
        
        try{
            em.getTransaction().begin();
            
            user = em.find(ProjectUser.class, id);
            
            em.getTransaction().commit();
        }finally{
            em.close();
        }

        return user;
    }
    
    public List<ProjectUser> getAllUsers(){
        EntityManager em = dbConnector.getEm();
        
        List<ProjectUser> users;
        
        try{
            em.getTransaction().begin();
            
            users = em.createNamedQuery("ProjectUser.findAll").getResultList();
            
            em.getTransaction().commit();
        }finally{
            em.close();
        }
        
        return users;
    }
    
    public Project createProject(String name, String description){
        EntityManager em = dbConnector.getEm();
        
        Project project = new Project();
        project.setName(name);
        project.setDescription(description);
        project.setCreated(Date.from(Instant.now()));
        project.setLastModified(Date.from(Instant.now()));
        
        try{
            em.getTransaction().begin();
            
            em.persist(project);
            
            em.getTransaction().commit();
        }finally{
            em.close();
        }
        
        return project;
    }
    
    public Project assignUserToProject(long userId, long projectId){
        EntityManager em = dbConnector.getEm();
        
        Project project = em.find(Project.class, projectId);
        ProjectUser user = em.find(ProjectUser.class, userId);
        
        try{
            em.getTransaction().begin();
            
            project.addContributor(user);
            project.setLastModified(Date.from(Instant.now()));
            
            em.getTransaction().commit();
        }finally{
            em.close();
        }
        
        return project;
    }
    
    public Project findProject(long id){
         EntityManager em = dbConnector.getEm();
        
         Project project;
         
        try{
            em.getTransaction().begin();
            
            project = em.find(Project.class, id);
            
            em.getTransaction().commit();
        }finally{
            em.close();
        }
        
        return project;
    }
    
    public Task createTaskAndAssignToProject(String name, String description, long projectId){
        EntityManager em = dbConnector.getEm();
        
        Task task = new Task();
        task.setName(name);
        task.setDescription(description);

        Project project = em.find(Project.class, projectId);
        
        try{
            em.getTransaction().begin();
            
            em.persist(task);
            project.addTask(task);
            project.setLastModified(Date.from(Instant.now()));
            
            em.getTransaction().commit();
        }finally{
            em.close();
        }
        
        return task;
    }

}