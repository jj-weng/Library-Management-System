/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.BookEntitySessionBeanLocal;
import ejb.session.stateless.MemberEntitySessionBeanLocal;
import ejb.session.stateless.RecordEntitySessionBeanLocal;
import ejb.session.stateless.StaffEntitySessionBeanLocal;
import entity.BookEntity;
import entity.LendAndReturnEntity;
import entity.MemberEntity;
import entity.RecordEntity;
import entity.StaffEntity;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jiajun
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB(name = "MemberEntitySessionBeanLocal")
    private MemberEntitySessionBeanLocal memberEntitySessionBeanLocal;

    @EJB(name = "BookEntitySessionBeanLocal")
    private BookEntitySessionBeanLocal bookEntitySessionBeanLocal;

    @EJB(name = "StaffEntitySessionBeanLocal")
    private StaffEntitySessionBeanLocal staffEntitySessionBeanLocal;

    //@EJB(name = "RecordEntitySessionBeanLocal")
    //private RecordEntitySessionBeanLocal recordEntitySessionBeanLocal;
    
    @PersistenceContext(unitName = "LMSJpa-ejbPU")
    private EntityManager em;
    
    
    
    
    @PostConstruct
    public void postConstruct() 
    {
        if (em.find(StaffEntity.class, 1L) == null)
        {
        staffEntitySessionBeanLocal.createNewRecord(new StaffEntity("Eric", "Some", "eric", "password"));
        staffEntitySessionBeanLocal.createNewRecord(new StaffEntity("Sarah", "Brightman", "sarah", "password"));
        }
        
        
        if (em.find(BookEntity.class, 1L) == null)
        {
        bookEntitySessionBeanLocal.createNewRecord(new BookEntity("Anna Karenina", "0451528611", "Leo Tolstoy"));
        
        bookEntitySessionBeanLocal.createNewRecord(new BookEntity("Madame Bovary", "979-8649042031", "Gustave Flaubert"));
        
        bookEntitySessionBeanLocal.createNewRecord(new BookEntity("Hamlet", "1980625026", "William Shakespeare"));
        
        bookEntitySessionBeanLocal.createNewRecord(new BookEntity("The Hobbit", "9780007458424", "J R R Tolkien"));
        
        bookEntitySessionBeanLocal.createNewRecord(new BookEntity("Great Expectations", "1521853592", "Charles Dickens"));
        
        bookEntitySessionBeanLocal.createNewRecord(new BookEntity("Pride and Prejudice", "979-8653642272", "Jane Austen"));
        
        bookEntitySessionBeanLocal.createNewRecord(new BookEntity("Wuthering Heights", "3961300224", "Emily Brontë"));
        }
        
        if (em.find(MemberEntity.class, 1L) == null) {
        memberEntitySessionBeanLocal.createNewRecord(new MemberEntity("Tony", "Shade", 'M', 31, "S8900678A", "83722773", "13 Jurong East, Ave 3"));
        
        memberEntitySessionBeanLocal.createNewRecord(new MemberEntity("Dewi", "Tan", 'F', 35, "S8581028X", "94602711", "15 Cumputing Dr"));
        }

        /*
        recordEntitySessionBeanLocal.createNewRecord(new RecordEntity("Record A"));
        recordEntitySessionBeanLocal.createNewRecord(new RecordEntity("Record B"));
        recordEntitySessionBeanLocal.createNewRecord(new RecordEntity("Record C"));
        recordEntitySessionBeanLocal.createNewRecord(new RecordEntity("Record D"));
        recordEntitySessionBeanLocal.createNewRecord(new RecordEntity("Record E"));
        */
    }    

}
