/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.BookEntity;
import entity.LendAndReturnEntity;
import java.util.List;
import javax.ejb.Remote;
import util.exception.BookNotFoundException;

/**
 *
 * @author jiajun
 */
@Remote
public interface BookEntitySessionBeanRemote {
    
    public List<BookEntity> retrieveAllRecords();
    
    public Long createNewRecord(BookEntity newBookEntity);
    
    public Long createNewLendAndReturn(LendAndReturnEntity newLendAndReturnEntity);
    
    public BookEntity retrieveBookByBookId(Long bookId) throws BookNotFoundException;
}
