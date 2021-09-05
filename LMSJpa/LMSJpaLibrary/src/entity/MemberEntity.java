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
public class MemberEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    private String firstName;
    private String lastName;
    private Character gender;
    private Integer age;
    private String identityNo;
    private String phone;
    private String address;
    
    @OneToMany(mappedBy = "member")
    private List<LendAndReturnEntity> lendAndReturn;
    
    public MemberEntity() {
        lendAndReturn = new ArrayList<>();
    }

    public MemberEntity(String firstName, String lastName, Character gender, Integer age, String identityNo, String phone, String address) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.identityNo = identityNo;
        this.phone = phone;
        this.address = address;
    }
    
    

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (memberId != null ? memberId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the memberId fields are not set
        if (!(object instanceof MemberEntity)) {
            return false;
        }
        MemberEntity other = (MemberEntity) object;
        if ((this.memberId == null && other.memberId != null) || (this.memberId != null && !this.memberId.equals(other.memberId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Member[ id=" + memberId + " ]";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<LendAndReturnEntity> getLendAndReturn() {
        return lendAndReturn;
    }

    public void setLendAndReturn(List<LendAndReturnEntity> lendAndReturn) {
        this.lendAndReturn = lendAndReturn;
    }

  
    
}
