/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RecordEntity;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jiajun
 */
@Local
public interface RecordEntitySessionBeanLocal {

    public List<RecordEntity> retrieveAllRecords();

    public Long createNewRecord(RecordEntity newRecordEntity);
    
}
