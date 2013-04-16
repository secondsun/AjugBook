/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.saga.ajugbook.controller;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import net.saga.ajugbook.controller.responses.IndexResponse;
import net.saga.ajugbook.vo.Post;
import org.jboss.aerogear.security.model.AeroGearUser;
import org.jboss.aerogear.security.picketlink.util.Converter;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.model.User;

/**
 *
 * @author Summers
 */
@Stateless
public class IndexController {

    @PersistenceUnit(unitName = "ajugBook")
    private EntityManagerFactory emf;
    @Inject
    private IdentityManager manager;

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)  
    public IndexResponse index() {

        IndexResponse response = new IndexResponse();
        final EntityManager em = emf.createEntityManager();
        try {
            
            List<Post> posts = em.createQuery("from Post order by posted asc").setMaxResults(10).getResultList();

            for (Post post : posts) {
                String authorUsername = post.getAuthorUsername();
                User user = manager.getUser(authorUsername);
                if (user == null) {
                    throw new RuntimeException("User do not exist");
                }
                AeroGearUser author = Converter.convertToAerogearUser(manager.getUser(authorUsername));
                IndexResponse.PostView pv = new IndexResponse.PostView();
                pv.authorUser = author;
                pv.post = post;
                response.getPosts().add(pv);
            }

            return response;
        } finally {
            em.close();
        }
    }
}
