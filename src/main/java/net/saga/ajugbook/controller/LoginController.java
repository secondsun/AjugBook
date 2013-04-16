/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.saga.ajugbook.controller;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import net.saga.ajugbook.util.ResponseHeaders;
import net.saga.ajugbook.vo.Account;
import org.jboss.aerogear.security.auth.AuthenticationManager;
import org.jboss.aerogear.security.auth.Token;
import org.jboss.aerogear.security.authz.IdentityManagement;
import org.jboss.aerogear.security.model.AeroGearUser;

/**
 *
 * @author Summers
 */
@Stateless
public class LoginController {

    private static final String AUTH_TOKEN = "Auth-Token";
    @Inject
    private AuthenticationManager authenticationManager;

    @PersistenceUnit(unitName = "ajugBook")
    private EntityManagerFactory emf;
    
    @Inject
    private IdentityManagement configuration;

    @Inject
    Event<ResponseHeaders> headers;

    @Inject
    @Token
    private Instance<String> token;

    
    
    public void index() {
    }

    /**
     * {@link org.jboss.aerogear.security.model.AeroGearUser} registration
     *
     * @param user represents a simple implementation that holds user's
     * credentials.
     * @return HTTP response and the session ID
     */
    public AeroGearUser login(final AeroGearUser user) {
        performLogin(user);
        fireResponseHeaderEvent();
        return user;
    }

    public void enrollForm() {
    }

    public void logout() {
        authenticationManager.logout();
    }

    private void performLogin(AeroGearUser aeroGearUser) {
        authenticationManager.login(aeroGearUser);
    }

    private void fireResponseHeaderEvent() {
        headers.fire(new ResponseHeaders(AUTH_TOKEN, token.get().toString()));
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public AeroGearUser register(AeroGearUser user) {
        configuration.create(user);
        configuration.grant("logged_in").to(user);
        authenticationManager.login(user);
        
        EntityManager em = emf.createEntityManager();
        Account account = new Account();
        account.setAccountUserName(user.getUsername());
        em.persist(account);
        return user;
    }
}
