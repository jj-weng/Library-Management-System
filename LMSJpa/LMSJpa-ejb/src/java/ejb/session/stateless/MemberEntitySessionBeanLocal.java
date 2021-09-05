/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.LendAndReturnEntity;
import entity.MemberEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.MemberNotFoundException;

/**
 *
 * @author jiajun
 */
@Local
public interface MemberEntitySessionBeanLocal {

    public List<MemberEntity> retrieveAllRecords();

    public Long createNewRecord(MemberEntity newMemberEntity);

    public Long createNewLendAndReturn(LendAndReturnEntity newLendAndReturnEntity);

    public MemberEntity retrieveMemberByIdentity(String identityNo) throws MemberNotFoundException;

}
