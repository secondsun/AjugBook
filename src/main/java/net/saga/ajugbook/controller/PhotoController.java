/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.saga.ajugbook.controller;

import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import net.saga.ajugbook.vo.Image;
import org.picketlink.Identity;

/**
 *
 * @author Summers
 */
@Stateless
public class PhotoController {
    
    @PersistenceUnit(unitName = "ajugBook")
    EntityManagerFactory emf;
    
    @Inject
    private Identity identity;
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ArrayList<Image> index() {
        return new ArrayList<>(emf.createEntityManager().createQuery("from Image where ownerName = :name").setParameter("name", identity.getUser().getLoginName()).getResultList());
    }
}
