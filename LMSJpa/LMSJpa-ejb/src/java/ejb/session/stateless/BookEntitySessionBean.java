/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.BookEntity;
import entity.LendAndReturnEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.BookNotFoundException;

/**
 *
 * @author jiajun
 */
@Stateless
public class BookEntitySessionBean implements BookEntitySessionBeanRemote, BookEntitySessionBeanLocal {

    @PersistenceContext(unitName = "LMSJpa-ejbPU")
    private EntityManager em;

    @Override
    public List<BookEntity> retrieveAllRecords() 
    {
        Query query = em.createQuery("SELECT r FROM BookEntity r");
        List<BookEntity> books =  query.getResultList();
        
        for (BookEntity book : books)
        {
            book.getLendAndReturns().size();
            book.getLendAndReturns();
        }
        return books;
    }
    
    
    @Override
    public Long createNewRecord(BookEntity newBookEntity) 
    {
        em.persist(newBookEntity);
        em.flush();
        
        return newBookEntity.getBookId();
    }
    
    @Override
    public Long createNewLendAndReturn(LendAndReturnEntity newLendAndReturnEntity)
    {
        em.persist(newLendAndReturnEntity);
        em.flush();
        
        return newLendAndReturnEntity.getLendId();
    }   
    
    @Override
     public BookEntity retrieveBookByBookId(Long bookId) throws BookNotFoundException
    {
        Query query = em.createQuery("SELECT s FROM BookEntity s WHERE s.bookId = :inBookId");
        query.setParameter("inBookId", bookId);
        
        try
        {
            return (BookEntity)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new BookNotFoundException("Book ID " + bookId + " does not exist!");
        } 
    }
    
}
