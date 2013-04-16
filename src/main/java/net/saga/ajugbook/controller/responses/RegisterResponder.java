/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.saga.ajugbook.controller.responses;

import org.jboss.aerogear.controller.router.MediaType;
import org.jboss.aerogear.controller.router.Responder;
import org.jboss.aerogear.controller.router.RouteContext;
import org.jboss.aerogear.controller.view.JspViewResolver;
import org.jboss.aerogear.controller.view.View;

    
/**
 *
 * @author Summers
 */
public class RegisterResponder implements Responder{

    private final JspViewResolver jspViewResolver = new JspViewResolver();

    @Override
    public MediaType getMediaType() {
        return MediaType.JSP;
    }
    
   
    @Override
    public boolean accepts(final String mediaType) {
        return getMediaType().getType().equals(mediaType);
    }
    
    @Override
    public void respond(Object entity, RouteContext routeContext) throws Exception {
        final String viewPath = jspViewResolver.resolveViewPathFor(routeContext.getRoute());
        final View view = new View(viewPath, entity);
        if (view.hasModelData()) {
            routeContext.getRequest().setAttribute(view.getModelName(), view.getModel());
        }
        routeContext.getRequest().getRequestDispatcher(view.getViewPath())
                .forward(routeContext.getRequest(), routeContext.getResponse());

    }
    
}
