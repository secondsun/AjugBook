/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.saga.ajugbook;

import java.util.Map;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import net.saga.ajugbook.util.ResponseHeaders;
import org.jboss.aerogear.controller.router.MediaType;
import org.jboss.aerogear.controller.router.RouteContext;
import org.jboss.aerogear.controller.router.rest.JsonResponder;


/**
 *
 * @author Summers
 */
@ApplicationScoped
public class CustomMediaTypeResponder extends JsonResponder {

    
    private ResponseHeaders responseHeaders;
    
    @Override
    public MediaType getMediaType() {
        MediaType CUSTOM_MEDIA_TYPE = new MediaType(MediaType.JSON.getType(), CustomMediaTypeResponder.class);
        return CUSTOM_MEDIA_TYPE;
    }

    public void loggedInHeaders(@Observes ResponseHeaders headers) { 
        responseHeaders = headers;
    }
    
    @Override
    public void writeResponse(Object entity, RouteContext routeContext) throws Exception {
        if (responseHeaders != null && responseHeaders.getHeaders() != null) {
            Set<Map.Entry<String, String>> entrySet = responseHeaders.getHeaders().entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                routeContext.getResponse().setHeader(entry.getKey(), entry.getValue());
            }
        }
        super.writeResponse(entity, routeContext);
    }

}
