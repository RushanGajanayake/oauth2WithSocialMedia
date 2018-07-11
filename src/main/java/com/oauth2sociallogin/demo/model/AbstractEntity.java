package com.oauth2sociallogin.demo.model;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by rusha on 7/10/2018.
 */

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CreatedDate
    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @LastModifiedDate
    @Column(name = "modified_date")
    private Date modifiedDate;

    @CreatedBy
    @Column(name = "created_by", nullable = true)
    private Integer createdBy;

    @LastModifiedBy
    @Column(name = "modified_by", nullable = true)
    private Integer modifiedBy;

    public Integer getId(){
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }

        if (this.id == null || obj == null || !(this.getClass().equals(obj.getClass()))){
            return false;
        }

        AbstractEntity that = (AbstractEntity) obj;

        return this.id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
