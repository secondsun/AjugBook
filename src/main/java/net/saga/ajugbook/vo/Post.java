/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.saga.ajugbook.vo;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String authorUsername;
    private String text;
    @Temporal(TemporalType.TIMESTAMP)
    private Date posted;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Image image;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
    
    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String author) {
        this.authorUsername = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getPosted() {
        return posted;
    }

    public void setPosted(Date posted) {
        this.posted = posted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @PrePersist
    void updateDates() {
        if (posted == null) {
            posted = new Date();
        }
    }
}
