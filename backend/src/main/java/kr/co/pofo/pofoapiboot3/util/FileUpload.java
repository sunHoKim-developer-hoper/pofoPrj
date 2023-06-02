package kr.co.pofo.pofoapiboot3.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class FileUpload {

    @Autowired
    private HttpServletRequest request;

    public void upload(MultipartFile img, String modifiedName, int type) {
        String urlPath = "";
        if(type == 0)
            urlPath = "portfolio/thumbnails" + File.separator + modifiedName;
        if(type == 1) 
            urlPath = "portfolio/contents" + File.separator + modifiedName;
        if(type == 2) 
            urlPath = "communityImage" + File.separator + modifiedName;
        String realPath = request.getServletContext().getRealPath(urlPath);
        
        try {
            img.transferTo(new File(realPath));
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
    }

    public String modifyImgName(String originalName) {
        int index = originalName.lastIndexOf(".");
        String extension = originalName.substring(index);
        String modifiedName = String.valueOf(UUID.randomUUID()) + extension;
        return modifiedName;
    }
}
