package com.agrosoft.api.features.images.services.impl;

import com.agrosoft.api.features.images.dto.ImagenResponseDTO;
import com.agrosoft.api.features.images.entities.Imagen;
import com.agrosoft.api.features.images.mappers.ImagenMapper;
import com.agrosoft.api.features.images.repositories.ImagenRepository;
import com.agrosoft.api.features.images.services.ImagenService;
import com.agrosoft.api.shared.services.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        Imagen imagenGuardada = imagenRepository.save(imagen);
        return imagenMapper.toDTO(imagenGuardada);
    }
    @Override
    @Transactional(readOnly = true)
    public List<ImagenResponseDTO> obtenerTodasLasImagenes() {
        return imagenRepository.findAll()
                .stream()
                .map(imagenMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ImagenResponseDTO obtenerImagenPorId(UUID idImagen) {
        Imagen imagen = imagenRepository.findById(idImagen)
                .orElseThrow(() -> new RuntimeException("Error: Imagen no encontrada con ID: " + idImagen));

        return imagenMapper.toDTO(imagen);
    }
}