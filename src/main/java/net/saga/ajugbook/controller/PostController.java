/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.saga.ajugbook.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;
import net.saga.ajugbook.vo.Post;
import org.picketlink.Identity;

/**
 *
 * @author Summers
 */
@Stateless
public class PostController {

    @Inject
    private Identity identity;
    
    @Resource           
    private   UserTransaction tx;    

    @PersistenceUnit(unitName = "ajugBook")
    private EntityManagerFactory emf;
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)  
    public Post post(Post post) {
        post.setAuthorUsername(identity.getUser().getLoginName());
        if (post.getImage() != null) {
            post.getImage().setOwnerName(identity.getUser().getLoginName());
        }
        
        final EntityManager em = emf.createEntityManager();
        try {
            em.persist(post);
        } catch (final Exception e) {
               Logger.getLogger(PostController.class.getName()).log(Level.SEVERE, null, e); 
        } finally {
            em.close();
        }
        
        return post;
        
    }

    public void index() {
    }
    
}
