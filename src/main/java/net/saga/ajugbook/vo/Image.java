/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.saga.ajugbook.vo;

import com.sun.javadoc.SerialFieldTag;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.picketlink.common.util.Base64;

/**
 *
 * @author Summers
 */
@Entity
public class Image implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String ownerName;
    @Lob
    @Column(length = 0xFFFFFF)
    private byte[] imageData;
    @Temporal(TemporalType.TIMESTAMP)
    private Date posted;
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String owernName) {
        this.ownerName = owernName;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public Date getPosted() {
        return posted;
    }

    public void setPosted(Date posted) {
        this.posted = posted;
    }

    public String getImageBase64() {
        if (imageData != null) {
            return Base64.encodeBytes(imageData);
        } else {
            return null;
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @PrePersist
    void updateDates() {
        if (posted == null) {
            posted = new Date();
        }
    }
}
