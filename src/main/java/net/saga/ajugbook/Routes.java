/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.saga.ajugbook;

import net.saga.ajugbook.controller.AccountController;
import net.saga.ajugbook.controller.IndexController;
import net.saga.ajugbook.controller.LoginController;
import org.jboss.aerogear.controller.router.AbstractRoutingModule;
import static org.jboss.aerogear.controller.router.AbstractRoutingModule.JSON;
import static org.jboss.aerogear.controller.router.AbstractRoutingModule.JSP;
import org.jboss.aerogear.controller.router.MediaType;
import org.jboss.aerogear.controller.router.RequestMethod;
import org.jboss.aerogear.controller.router.parameter.MissingRequestParameterException;
import org.jboss.aerogear.security.exception.AeroGearSecurityException;
import org.jboss.aerogear.security.model.AeroGearUser;
import org.picketlink.authentication.UnexpectedCredentialException;
import net.saga.ajugbook.controller.Error;
import net.saga.ajugbook.controller.PhotoController;
import net.saga.ajugbook.controller.PostController;
import net.saga.ajugbook.vo.Account;
import net.saga.ajugbook.vo.Post;
import org.jboss.aerogear.controller.view.JspViewResponder;

/**
 *
 * @author Summers
 */
public class Routes extends AbstractRoutingModule {

    public static final MediaType CUSTOM_MEDIA_TYPE = new MediaType(MediaType.JSON.getType(), CustomMediaTypeResponder.class);
    
    @Override
    public void configuration() throws Exception {

        MediaType MULTIPART = new MediaType("multipart/form-data", JspViewResponder.class);

        route()
                .on(MissingRequestParameterException.class)
                .produces(JSON)
                .to(net.saga.ajugbook.controller.Error.class).handleMissingRequestParameter(param(MissingRequestParameterException.class));
        route()
                .on(AeroGearSecurityException.class)
                .produces(JSP, JSON)
                .to(Error.class).security(param(RuntimeException.class));
        
        route()
                .on(UnexpectedCredentialException.class)
                .produces(JSP)
                .to(Error.class).alreadyLoggedIn();

        route()
                .on(Exception.class)
                .produces(JSP, JSON)
                .to(Error.class).index(param(Exception.class));

        route().from("/")
                .on(RequestMethod.GET)
                .consumes(MediaType.ANY)
                .produces(JSP)
                .to(IndexController.class).index();
        
        route().from("/login").on(RequestMethod.GET).consumes(MediaType.ANY).produces(JSP).to(LoginController.class).index();
        route().from("/login")
                .on(RequestMethod.POST)
                .consumes(MediaType.ANY)
                .produces(JSP)
                .to(LoginController.class).login(param(AeroGearUser.class));
        route()
                .from("/logout")
                .on(RequestMethod.GET)
                .to(LoginController.class).logout();
        route()
                .from("/register")
                .on(RequestMethod.GET)
                .to(LoginController.class).enrollForm();
        route()
                .from("/register")
                .on(RequestMethod.POST)
                .produces(JSP)
                .consumes(JSP)
                .to(LoginController.class).register(param(AeroGearUser.class));

        route().from("/post")
               .on(RequestMethod.GET)
               .consumes(MediaType.HTML, MediaType.JSP)
               .produces(MediaType.JSP).to(PostController.class).index();
        
        route().from("/post")
               .roles("logged_in")
               .on(RequestMethod.POST)
               .consumes(MULTIPART)
               .produces(MediaType.JSP)
               .to(PostController.class).post(param(Post.class));
        
        route().from("/account")
               .on(RequestMethod.GET)
               .consumes(MediaType.ANY)
               .produces(MediaType.JSP, MediaType.JSON).to(AccountController.class).index();
        
        route().from("/updatePhoto").roles("logged_in").on(RequestMethod.POST).consumes(MULTIPART).produces(MediaType.JSP).to(AccountController.class).updatePhoto(param(Account.class));
        
        route().from("/photos").on(RequestMethod.GET).consumes(MediaType.ANY).produces(MediaType.JSP).to(PhotoController.class).index();
        
        //Client Route configs
        route().from("/post")
               .on(RequestMethod.GET)
               .consumes(MediaType.JSON)
               .produces(MediaType.JSON)
               .to(PostController.class).getAll();
        
         route()
                .from("/post")
                .roles("logged_in")
                .on(RequestMethod.POST)
                .consumes(JSON)
                .produces(MediaType.JSON)
                .to(PostController.class).post(param(Post.class));
        route()
                .from("/post/{id}")
                .roles("logged_in")
                .on(RequestMethod.DELETE)
                .consumes(JSON)
                .produces(MediaType.JSON)
                .to(PostController.class).deleteById(param("id"));
        
        route()
                .from("/auth/login")
                .on(RequestMethod.POST)
                .consumes(MediaType.JSON)
                .produces(CUSTOM_MEDIA_TYPE)
                .to(LoginController.class).login(param(AeroGearUser.class));
        route()
                .from("/auth/logout")
                .on(RequestMethod.POST)
                .consumes(JSON)
                .produces(JSON)
                .to(LoginController.class).logout();
        route()
                .from("/auth/enroll")
                .on(RequestMethod.POST)
                .consumes(JSON)
                .produces(CUSTOM_MEDIA_TYPE)
                .to(LoginController.class).register(param(AeroGearUser.class));

    }
}
