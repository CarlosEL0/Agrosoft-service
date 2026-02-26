package com.agrosoft.api.features.images.services.impl;

import com.agrosoft.api.features.images.dto.ImagenResponseDTO;
import com.agrosoft.api.features.images.entities.Imagen;
import com.agrosoft.api.features.images.mappers.ImagenMapper;
import com.agrosoft.api.features.images.repositories.ImagenRepository;
import com.agrosoft.api.features.images.service.ImagenService;
import com.agrosoft.api.shared.services.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImagenServiceImpl implements ImagenService {

    private final ImagenRepository imagenRepository;
    private final CloudinaryService cloudinaryService;
    private final ImagenMapper imagenMapper;

    @Override
    public ImagenResponseDTO subirEvidencia(
            MultipartFile archivo,
            String descripcion,
            UUID idReferencia,
            String tipoReferencia
    ) throws IOException {

        // 1. Definir carpeta en Cloudinary
        String carpeta = "evidencias/" + tipoReferencia.toLowerCase();

        // 2. Subir imagen a la nube
        String urlImagen = cloudinaryService.subirImagen(archivo, carpeta);

        // 3. Crear la entidad base
        Imagen imagen = Imagen.builder()
                .urlArchivo(urlImagen)
                .descripcion(descripcion)
                // La fecha se pone sola gracias al @PrePersist de la entidad
                .build();

        // 4. Asignar el ID a la columna correcta según el tipo
        switch (tipoReferencia.toUpperCase()) {
            case "CRECIMIENTO":
                imagen.setIdRegistroCrecimiento(idReferencia);
                break;
            case "RIEGO":
                imagen.setIdRiego(idReferencia);
                break;
            case "FERTILIZACION":
                imagen.setIdFertilizacion(idReferencia);
                break;
            case "FUMIGACION":
                imagen.setIdFumigacion(idReferencia);
                break;
            case "PODA":
                imagen.setIdPoda(idReferencia);
                break;
            case "IRREGULARIDAD":
                imagen.setIdIrregularidad(idReferencia);
                break;
            default:
                throw new IllegalArgumentException("Tipo de referencia no válido: " + tipoReferencia);
        }

        // 5. Guardar en Base de Datos
        Imagen imagenGuardada = imagenRepository.save(imagen);

        // 6. Retornar DTO
        return imagenMapper.toDTO(imagenGuardada);
    }
}