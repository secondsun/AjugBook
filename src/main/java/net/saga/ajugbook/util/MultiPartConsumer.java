/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.saga.ajugbook.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import net.saga.ajugbook.vo.Image;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.jboss.aerogear.controller.router.AeroGearException;
import org.jboss.aerogear.controller.router.Consumer;

/**
 *
 * @author Summers
 */
@Stateless
public class MultiPartConsumer implements Consumer {

    @Override
    public String mediaType() {
        return "multipart/form-data";
    }

    @Override
    public <T> T unmarshall(HttpServletRequest request, Class<T> type) {
        try {

            T object = type.newInstance();

            // Create a factory for disk-based file items
            DiskFileItemFactory factory = new DiskFileItemFactory();

            File repository = (File) request.getServletContext().getAttribute("javax.servlet.context.tempdir");
            factory.setRepository(repository);

            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);
            
            // Parse the request
            List<FileItem> items = upload.parseRequest(request);
            for (FileItem item : items) {
                String fieldName = item.getFieldName();
                Boolean field = PropertyUtils.isWriteable(object, fieldName);
                
                if (field) {
                    Class fieldClass = PropertyUtils.getPropertyType(object, fieldName);
                    if (item.isFormField()) {
                        Object itemValue = ConvertUtils.convert(item.getString(), fieldClass);
                        BeanUtils.setProperty(object, fieldName, itemValue);
                    } else if (item.getSize() > 0) {
                        if (FileItem.class.isAssignableFrom(fieldClass)) {
                            BeanUtils.setProperty(object, fieldName, item);
                        } else if (byte[].class.isAssignableFrom(fieldClass)) {
                            BeanUtils.setProperty(object, fieldName, item.get());
                        } else if (Image.class.isAssignableFrom(fieldClass)) {
                            Image image = new Image();
                            image.setType(item.getContentType());
                            
                            BufferedImage itemImage = ImageIO.read(item.getInputStream());
                            ByteArrayOutputStream out = new ByteArrayOutputStream();
                            ImageIO.write(itemImage, image.getType().split("/")[1] /* for instance */, out);
                            byte []imageBytes = out.toByteArray();
                            image.setImageData(imageBytes);
                            BeanUtils.setProperty(object, fieldName, image);
                        } else if (InputStream.class.isAssignableFrom(fieldClass)) {
                            BeanUtils.setProperty(object, fieldName, item.getInputStream());
                        }
                    }
                }
            }
            return object;
        } catch (NoSuchMethodException | IOException | InvocationTargetException | InstantiationException | IllegalAccessException | FileUploadException e) {
            throw new AeroGearException(e);
        }
    }

}
