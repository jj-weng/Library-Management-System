/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author jiajun
 */
@Entity
public class BookEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    private String title;
    private String isbn;
    private String author;
    
    @OneToMany(mappedBy = "book")
    private List<LendAndReturnEntity> lendAndReturns;
    
    public BookEntity() {
        lendAndReturns = new ArrayList<>();
    }

    public BookEntity(String title, String isbn, String author) {
        this();
        this.title = title;
        this.isbn = isbn;
        this.author = author;
    }
    

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getBookId() != null ? getBookId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the bookId fields are not set
        if (!(object instanceof BookEntity)) {
            return false;
        }
        BookEntity other = (BookEntity) object;
        if ((this.getBookId() == null && other.getBookId() != null) || (this.getBookId() != null && !this.bookId.equals(other.bookId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Book[ id=" + getBookId() + " ]";
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<LendAndReturnEntity> getLendAndReturns() {
        return lendAndReturns;
    }

    public void setLendAndReturns(List<LendAndReturnEntity> lendAndReturns) {
        this.lendAndReturns = lendAndReturns;
    }

   
    
}
