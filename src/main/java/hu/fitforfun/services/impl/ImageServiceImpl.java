package hu.fitforfun.services.impl;

import hu.fitforfun.model.Image;
import hu.fitforfun.repositories.ImageRepository;
import hu.fitforfun.services.ImageService;
import hu.fitforfun.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Override
    public void uploadImage(MultipartFile file) throws IOException {
        Image img = new Image(file.getOriginalFilename(), file.getContentType(),
                ImageUtils.compressBytes(file.getBytes()));
        imageRepository.save(img);
    }

    @Override
    public Image getImage(String imageName) throws IOException {
        final Optional<Image> retrievedImage = imageRepository.findByName(imageName);
        Image img = new Image(retrievedImage.get().getName(), retrievedImage.get().getType(),
                ImageUtils.decompressBytes(retrievedImage.get().getPicByte()));
        return img;
    }

    @Override
    public Image getImageById(Long id) throws IOException {
        final Optional<Image> retrievedImage = imageRepository.findById(id);
        Image img = new Image(retrievedImage.get().getName(), retrievedImage.get().getType(),
                ImageUtils.decompressBytes(retrievedImage.get().getPicByte()));
        return img;
    }
}
