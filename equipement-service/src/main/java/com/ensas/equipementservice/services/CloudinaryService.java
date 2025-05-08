package com.ensas.equipementservice.services;

import com.cloudinary.Cloudinary;
import com.ensas.equipementservice.dtos.CloudinaryResponse;
import com.ensas.equipementservice.exception.FuncErrorException;
import com.ensas.equipementservice.util.FileUploadUtil;
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
            String publicId = "atlas/equipements/" + generatedName;

            System.out.println("Uploading to public_id: " + publicId); // Debug print

            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), Map.of(
                    "public_id", publicId,
                    "use_filename", false,
                    "unique_filename", false,
                    "overwrite", true,
                    "folder", "atlas/equipements"
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

}