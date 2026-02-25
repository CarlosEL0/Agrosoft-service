package com.agrosoft.api.shared.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public String subirImagen(MultipartFile multipartFile, String carpeta) throws IOException {
        // 1. Convertir MultipartFile a File
        File file = convertir(multipartFile);

        // 2. Subir a Cloudinary (Ej: carpeta "agrosoft/cultivos")
        Map resultado = cloudinary.uploader().upload(file, ObjectUtils.asMap(
                "folder", "agrosoft/" + carpeta,
                "public_id", UUID.randomUUID().toString()
        ));

        // 3. Limpiar archivo temporal
        if (!file.delete()) {
            System.out.println("Advertencia: No se pudo borrar el archivo temporal " + file.getName());
        }

        // 4. Retornar URL HTTPS
        return (String) resultado.get("secure_url");
    }

    public void borrarImagen(String publicId) throws IOException {
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }

    private File convertir(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        }
        return file;
    }
}