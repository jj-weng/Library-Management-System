/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.StaffEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.InvalidLoginException;
import util.exception.StaffNotFoundException;

/**
 *
 * @author jiajun
 */
@Stateless
public class StaffEntitySessionBean implements StaffEntitySessionBeanRemote, StaffEntitySessionBeanLocal {

    @PersistenceContext(unitName = "LMSJpa-ejbPU")
    private EntityManager em;

    @Override
    public List<StaffEntity> retrieveAllRecords() 
    {
        Query query = em.createQuery("SELECT r FROM StaffEntity r");
        return query.getResultList();
    }
    
    
    @Override
    public Long createNewRecord(StaffEntity newStaffEntity) 
    {
        em.persist(newStaffEntity);
        em.flush();
        
        return newStaffEntity.getStaffId();
    }
    
    @Override
    public StaffEntity retrieveStaffByUsername(String username) throws StaffNotFoundException
    {
        Query query = em.createQuery("SELECT s FROM StaffEntity s WHERE s.userName = :inUsername");
        query.setParameter("inUsername", username);
        
        try
        {
            return (StaffEntity)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new StaffNotFoundException("Staff Username " + username + " does not exist!");
        }      
    }
    
    
    @Override
    public StaffEntity staffLogin(String username, String password) throws InvalidLoginException
    {
        try
        {
            StaffEntity staffEntity = retrieveStaffByUsername(username);
            
            if(staffEntity.getPassword().equals(password))
            {               
                return staffEntity;
            }
            else
            {
                throw new InvalidLoginException("Username does not exist or invalid password!");
            }
        }
        catch(StaffNotFoundException ex)
        {
            throw new InvalidLoginException("Username does not exist or invalid password!");
        }     
    }
    
    

    
    
}
