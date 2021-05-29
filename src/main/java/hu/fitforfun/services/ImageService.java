package hu.fitforfun.services;

import hu.fitforfun.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    void uploadImage(MultipartFile file) throws IOException;

    Image getImage(String imageName) throws IOException;

    Image getImageById(Long id) throws IOException;
}
