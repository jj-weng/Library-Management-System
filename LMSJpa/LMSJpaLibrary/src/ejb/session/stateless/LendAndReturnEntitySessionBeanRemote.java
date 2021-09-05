/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.LendAndReturnEntity;
import java.util.List;
import javax.ejb.Remote;
import util.exception.LendingNotFoundException;

/**
 *
 * @author jiajun
 */
@Remote
public interface LendAndReturnEntitySessionBeanRemote {
    
    public List<LendAndReturnEntity> retrieveAllRecords();
    
    public Long createNewRecord(LendAndReturnEntity newLendAndReturnEntity);
    
    public LendAndReturnEntity retrieveLendAndReturnEntityByBookId(Long bookId) throws LendingNotFoundException;
    
    public LendAndReturnEntity retrieveLendAndReturnEntityByMemberId(Long memberId) throws LendingNotFoundException;
    
    public LendAndReturnEntity retrieveLendAndReturnEntityByLendId(Long lendId) throws LendingNotFoundException;
    
    public Boolean isReturnDateNull(Long bookId);
    
    public void update(LendAndReturnEntity lendAndReturnEntity);
    
}
