package com.ensas.garageservice.service;

import com.cloudinary.Cloudinary;
import com.ensas.garageservice.dto.CloudinaryResponse;
import com.ensas.garageservice.exception.FuncErrorException;
import com.ensas.garageservice.util.FileUploadUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;

    @Transactional
    public CloudinaryResponse uploadFile(final MultipartFile file, final String fileName) {
        try {
            String originalName = FilenameUtils.getBaseName(file.getOriginalFilename());
            String generatedName = FileUploadUtil.getFileName(originalName);
            String publicId = "atlas/garages/" + generatedName;

            System.out.println("Uploading to public_id: " + publicId); // Debug print

            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), Map.of(
                    "public_id", publicId,
                    "use_filename", false,
                    "unique_filename", false,
                    "overwrite", true,
                    "folder", "atlas/garages"
            ));

            final String url = (String) uploadResult.get("secure_url");
            final String resultPublicId = (String) uploadResult.get("public_id");

            return CloudinaryResponse.builder()
                    .publicId(resultPublicId)
                    .url(url)
                    .build();

        } catch (final Exception e) {
            throw new FuncErrorException("Failed to upload file");
        }
    }

    public void deleteFileByUrl(String imageUrl) {
        try {
            // Extraire le publicId de l'URL
            String publicId = extractPublicIdFromUrl(imageUrl);
            cloudinary.uploader().destroy(publicId, Map.of());
        } catch (Exception e) {
            throw new RuntimeException("Échec de suppression dans Cloudinary");
        }
    }

    private String extractPublicIdFromUrl(String url) {

        String folder = "atlas/garages/";
        int start = url.indexOf(folder);
        int end = url.lastIndexOf('.');

        if (start == -1 || end == -1 || start + folder.length() >= end) {
            throw new IllegalArgumentException("URL invalide ou format inattendu : " + url);
        }

        return url.substring(start, end); // ex : atlas/garages/mon-image
    }



}