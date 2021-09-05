/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.LendAndReturnEntity;
import entity.MemberEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.MemberNotFoundException;

/**
 *
 * @author jiajun
 */
@Stateless
public class MemberEntitySessionBean implements MemberEntitySessionBeanRemote, MemberEntitySessionBeanLocal {

    @PersistenceContext(unitName = "LMSJpa-ejbPU")
    private EntityManager em;
    
    @Override
    public List<MemberEntity> retrieveAllRecords() 
    {
        Query query = em.createQuery("SELECT r FROM BookEntity r");
        List<MemberEntity> members =  query.getResultList();
        
        for (MemberEntity member : members)
        {
            member.getLendAndReturn().size();
            member.getLendAndReturn();
        }
        return members;    
    }
    

    @Override
    public Long createNewRecord(MemberEntity newMemberEntity) 
    {
        em.persist(newMemberEntity);
        em.flush();
        
        return newMemberEntity.getMemberId();
    }
    
    
    

    @Override
    public Long createNewLendAndReturn(LendAndReturnEntity newLendAndReturnEntity)
    {
        
        em.persist(newLendAndReturnEntity);
        em.flush();
        
        return newLendAndReturnEntity.getLendId();
    }
    
    @Override
    public MemberEntity retrieveMemberByIdentity(String identityNo) throws MemberNotFoundException
    {
        Query query = em.createQuery("SELECT s FROM MemberEntity s WHERE s.identityNo = :inIdentityNo");
        query.setParameter("inIdentityNo", identityNo);
        
        try
        {
            return (MemberEntity)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new MemberNotFoundException("Member Identity No " + identityNo + " does not exist!");
        } 
    }
    

    
}
