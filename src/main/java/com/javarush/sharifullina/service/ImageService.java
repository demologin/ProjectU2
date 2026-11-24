package com.javarush.sharifullina.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

public class ImageService {
    private final Path PATH = getImagesPath();
    private final String DEFAULT_IMAGE = "default_image.png";
    private final List<String> extList = List.of(".png", ".jpg", ".jpeg", ".gif", ".bmp", ".webp", ".tiff", ".svg");

    public String getImage(String imageName) {
        Path foundDiskPath = extList.stream()
                .map(ext -> PATH.resolve(imageName + ext))
                .filter(Files::exists)
                .findAny()
                .orElse(null);

        if (foundDiskPath != null) {
            String actualFileName = foundDiskPath.getFileName().toString();
            return "/images/" + actualFileName;
        }

        return "/images/" + DEFAULT_IMAGE;
    }

    @SneakyThrows
    private static Path getImagesPath() {
        return Paths.get(ImageService.class.getResource("/").toURI()) // WEB-INF/classes
                .getParent()                                          // WEB-INF
                .getParent()                                          // webapp
                .resolve("images");                                  // Папка images
    }

    public String uploadImage(HttpServletRequest req, String imageName){
        Part data;
        try {
            data = req.getPart("image");
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }

        if (Objects.nonNull(data) && data.getSize() > 0) {
            String originalFilename = data.getSubmittedFileName();

            int dotIndex = originalFilename.lastIndexOf(".");
            String ext = (dotIndex != -1) ? originalFilename.substring(dotIndex).toLowerCase() : ".png";

            deleteOldFiles(imageName);

            String newFilename = imageName + ext;

            Path targetPath = PATH.resolve(newFilename);
            try (InputStream input = data.getInputStream()) {
                Files.copy(input, targetPath, StandardCopyOption.REPLACE_EXISTING);
            }catch (IOException e){
                throw new RuntimeException(e);
            }

            return newFilename;
        }
        return null;
    }

    private void deleteOldFiles(String filename) {
        extList.forEach(ext -> {
            Path p = PATH.resolve(filename + ext);
            try {
                Files.deleteIfExists(p);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
