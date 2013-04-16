/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.saga.ajugbook.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import net.saga.ajugbook.vo.Post;
import org.picketlink.Identity;
import org.picketlink.idm.IdentityManager;

/**
 *
 * @author Summers
 */
@Stateless
public class PostController {

    @Inject
    private IdentityManager manager;

    
    @Inject
    private Identity identity;
    
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

    public List<Post> getAll() {
        final EntityManager em = emf.createEntityManager();
        try {
            List<Post> posts = em.createQuery("from Post order by posted asc").setMaxResults(10).getResultList();
            return posts;
        } finally {
            em.close();
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Long> deleteById(String idString) {
        if (identity == null || identity.getUser() == null) {
            throw new RuntimeException("not logged in");
        }
        final EntityManager em = emf.createEntityManager();
        Long id = Long.parseLong(idString);
        try {
            Post post = em.find(Post.class, id);
            if (!post.getAuthorUsername().equals(identity.getUser().getLoginName())) {
                throw new RuntimeException("no permissions");
            }
            em.remove(post);
            ArrayList<Long> toReturn = new ArrayList<Long>();
            toReturn.add(id);
            return toReturn;
        } finally {
            em.close();
        }
    }

    
}
