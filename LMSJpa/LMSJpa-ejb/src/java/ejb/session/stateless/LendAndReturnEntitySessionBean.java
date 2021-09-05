/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.LendAndReturnEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.LendingNotFoundException;

/**
 *
 * @author jiajun
 */
@Stateless
public class LendAndReturnEntitySessionBean implements LendAndReturnEntitySessionBeanRemote, LendAndReturnEntitySessionBeanLocal {

    @PersistenceContext(unitName = "LMSJpa-ejbPU")
    private EntityManager em;


    @Override
    public List<LendAndReturnEntity> retrieveAllRecords() 
    {
        Query query = em.createQuery("SELECT r FROM LendAndReturnEntity r");
        List<LendAndReturnEntity> records =  query.getResultList();
       
        return records;
    }
    
    @Override
    public Long createNewRecord(LendAndReturnEntity newLendAndReturnEntity) 
    {
        em.persist(newLendAndReturnEntity);
        em.flush();
        
        return newLendAndReturnEntity.getLendId();
    }

    @Override
    public LendAndReturnEntity retrieveLendAndReturnEntityByBookId(Long bookId) throws LendingNotFoundException
    {
        Query query = em.createQuery("SELECT s FROM LendAndReturnEntity s WHERE s.bookId = :inBookId");
        query.setParameter("inBookId", bookId);
        
        try
        {
            return (LendAndReturnEntity)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new LendingNotFoundException("Lend and return for " + bookId + " does not exist!");
        }  
    }
    
    
    @Override
    public LendAndReturnEntity retrieveLendAndReturnEntityByMemberId(Long memberId) throws LendingNotFoundException
    {
        Query query = em.createQuery("SELECT s FROM LendAndReturnEntity s WHERE s.memberId = :inMemberId");
        query.setParameter("inMemberId", memberId);
        
        try
        {
            return (LendAndReturnEntity)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new LendingNotFoundException("Lend and return for " + memberId + " does not exist!");
        }  
    }
    
    @Override
    public LendAndReturnEntity retrieveLendAndReturnEntityByLendId(Long lendId) throws LendingNotFoundException
    {
        try {
            Query query = em.createQuery("SELECT s FROM LendAndReturnEntity s WHERE s.lendId = :inLendId");
            query.setParameter("inLendId", lendId);

            LendAndReturnEntity lendAndReturnEntity = (LendAndReturnEntity)query.getSingleResult();
        
            if (lendAndReturnEntity != null && lendAndReturnEntity.getReturnDate() == null) {
                return lendAndReturnEntity;
            }
            else {   
                throw new LendingNotFoundException("Book is not lent out to anyone!");
            }
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            System.out.println("Lend and return for " + lendId + " does not exist!");
        }
        return null;
    }
    
    @Override
    public void update(LendAndReturnEntity lendAndReturnEntity)
    {
        em.merge(lendAndReturnEntity);
    }
    
    
    @Override
    public Boolean isReturnDateNull(Long bookId)
    {
        try 
        {
            Query query = em.createQuery("SELECT s FROM LendAndReturnEntity s WHERE s.bookId = :inBookId");
            query.setParameter("inBookId", bookId); 
        
            List<LendAndReturnEntity> records = query.getResultList();
            int recordSize = records.size();
        
            if (recordSize > 0)
            {
                LendAndReturnEntity lendAndReturnEntity = records.get(recordSize - 1);
                if (lendAndReturnEntity.getReturnDate() != null)
                {
                    return true;
                }
                else 
                {
                    return false;
                }
            }
            else 
            {
                return true;
            }
        }
        catch (NoResultException ex)
        {
            System.out.println("Invalid return date!");
        }
        return null;
    }     
}
