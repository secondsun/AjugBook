/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.saga.ajugbook.controller;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import net.saga.ajugbook.controller.responses.AccountBean;
import net.saga.ajugbook.vo.Account;
import org.jboss.aerogear.security.auth.AuthenticationManager;
import org.jboss.aerogear.security.authz.IdentityManagement;
import org.jboss.aerogear.security.model.AeroGearUser;
import org.picketlink.Identity;

/**
 *
 * @author Summers
 */
@Stateless
public class AccountController {

    @Inject
    private Identity identity;
    @PersistenceUnit(unitName = "ajugBook")
    private EntityManagerFactory emf;

    @Inject
    private AuthenticationManager authenticationManager;
    
    @Inject
    private IdentityManagement configuration;

    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public AccountBean index() {
        EntityManager em = emf.createEntityManager();

        Account account = (Account) em.createQuery("from Account where accountUserName = :name").setParameter("name", identity.getUser().getLoginName()).getSingleResult();

        AccountBean bean = new AccountBean();
        bean.setFirstName(identity.getUser().getFirstName());
        bean.setLastName(identity.getUser().getLastName());
        bean.setEmailAddress(identity.getUser().getEmail());
        bean.setImage(account.getImage());
        return bean;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public AccountBean updatePhoto(Account account) {
        EntityManager em = emf.createEntityManager();

        Account readAccount = (Account) em.createQuery("from Account where accountUserName = :name").setParameter("name", identity.getUser().getLoginName()).getSingleResult();
        account.getImage().setOwnerName(readAccount.getAccountUserName());
        readAccount.setImage(account.getImage());
        em.merge(readAccount);
        
        AccountBean bean = new AccountBean();
        bean.setFirstName(identity.getUser().getFirstName());
        bean.setLastName(identity.getUser().getLastName());
        bean.setEmailAddress(identity.getUser().getEmail());
        bean.setImage(readAccount.getImage());
        return bean;
    }
    
}
