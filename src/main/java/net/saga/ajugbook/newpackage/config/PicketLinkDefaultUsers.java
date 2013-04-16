/**
 * JBoss, Home of Professional Open Source Copyright Red Hat, Inc., and
 * individual contributors by the
 *
 * @authors tag. See the copyright.txt in the distribution for a full listing of
 * individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */
package net.saga.ajugbook.newpackage.config;

import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.credential.Password;
import org.picketlink.idm.model.Role;
import org.picketlink.idm.model.SimpleRole;
import org.picketlink.idm.model.SimpleUser;
import org.picketlink.idm.model.User;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import net.saga.ajugbook.vo.Account;

@Singleton
@Startup
public class PicketLinkDefaultUsers {

    @PersistenceContext(unitName = "ajugBook")
    private EntityManager em;
    @Inject
    private IdentityManager identityManager;

    /**
     * <p>Loads some users during the first construction.</p>
     */
    //TODO this entire initialization code will be removed
    @PostConstruct
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void create() {

        User user = new SimpleUser("john");

        user.setEmail("john@doe.com");
        user.setFirstName("John");
        user.setLastName("Doe");

        Account account = new Account();
        account.setAccountUserName("john");

        
        /*
         * Note: Password will be encoded in SHA-512 with SecureRandom-1024 salt
         * See http://lists.jboss.org/pipermail/security-dev/2013-January/000650.html for more information
         */
        this.identityManager.add(user);
        this.identityManager.updateCredential(user, new Password("123"));
        em.persist(account);

        Role roleDeveloper = new SimpleRole("simple");
        Role roleAdmin = new SimpleRole("admin");

        this.identityManager.add(roleDeveloper);
        this.identityManager.add(roleAdmin);

        identityManager.grantRole(user, roleDeveloper);
        identityManager.grantRole(user, roleAdmin);

    }
}
