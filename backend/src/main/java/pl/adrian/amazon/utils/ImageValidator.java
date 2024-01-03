package pl.adrian.amazon.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class ImageValidator {

    public static final List<String> IMAGE_TYPES = List.of(".png", ".jpg");

    public boolean isImageTypeAllowed(MultipartFile image) {
        String imageName = image.getOriginalFilename();
        int imageTypePosition = imageName.lastIndexOf(".");

        if (imageTypePosition == -1) return false;

        String imageType = imageName.substring(imageTypePosition);

        return IMAGE_TYPES.contains(imageType);
    }
}
