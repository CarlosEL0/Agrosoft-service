package com.agrosoft.api.features.notifications.services.impl;

import com.agrosoft.api.features.crops.entities.CultivoEntity;
import com.agrosoft.api.features.crops.entities.EtapaCrecimientoEntity;
import com.agrosoft.api.features.crops.entities.FaseAgricolaEntity;
import com.agrosoft.api.features.crops.repositories.CultivoRepository;
import com.agrosoft.api.features.crops.repositories.EtapaCrecimientoRepository;
import com.agrosoft.api.features.crops.repositories.FaseAgricolaRepository;
import com.agrosoft.api.features.notifications.dto.NotificacionResponseDTO;
import com.agrosoft.api.features.notifications.entities.DescripcionNotificacion;
import com.agrosoft.api.features.notifications.entities.Notificacion;
import com.agrosoft.api.features.notifications.mappers.NotificacionMapper;
import com.agrosoft.api.features.notifications.repositories.DescripcionNotificacionRepository;
import com.agrosoft.api.features.notifications.repositories.NotificacionRepository;
import com.agrosoft.api.features.notifications.service.NotificacionService;
import com.agrosoft.api.shared.exceptions.IntegrationException;
import com.agrosoft.api.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificacionServiceImpl implements NotificacionService {

    private final EtapaCrecimientoRepository etapaRepository;
    private final FaseAgricolaRepository faseRepository;
    private final CultivoRepository cultivoRepository;

    private final NotificacionRepository notificacionRepository;
    private final DescripcionNotificacionRepository descripcionRepository;
    private final NotificacionMapper notificacionMapper;


    @Override
    @Transactional
    public void generarNotificacionesFinEtapa() {
        LocalDate hoy = LocalDate.now();

        // 1. Buscar etapas que vencen hoy
        List<EtapaCrecimientoEntity> etapasVencidas = etapaRepository.findByFechaFin(hoy);

        if (etapasVencidas.isEmpty()) return;

        // 2. Obtener plantilla
        DescripcionNotificacion plantilla = descripcionRepository
                .findByTipoNotificacion("FIN_ETAPA")
                .orElseThrow(() -> new ResourceNotFoundException("Error: Plantilla 'FIN_ETAPA' no encontrada en DB"));

        for (EtapaCrecimientoEntity etapa : etapasVencidas) {

            // 3. Evitar duplicados
            if (notificacionRepository.existsByIdRecursoAndTipoRecurso(etapa.getIdEtapa(), "ETAPA")) {
                continue;
            }

            // 4. Buscar Usuario (Etapa -> Fase -> Cultivo -> Usuario)
            try {
                UUID idUsuario = obtenerIdUsuario(etapa.getIdCiclo());

                Notificacion notificacion = Notificacion.builder()
                        .idUsuario(idUsuario)
                        .descripcion(plantilla)
                        .idRecurso(etapa.getIdEtapa())
                        .tipoRecurso("ETAPA")
                        .fechaEnvio(LocalDateTime.now())
                        .leido(false)
                        .build();

                notificacionRepository.save(notificacion);
            } catch (IntegrationException e) {
                System.err.println("Error generando notificacion para etapa: " + etapa.getIdEtapa());
            }
        }
    }

    @Override
    public List<NotificacionResponseDTO> obtenerNotificacionesUsuario(UUID idUsuario) {
        return notificacionRepository.findByIdUsuarioOrderByFechaEnvioDesc(idUsuario)
                .stream()
                .map(notificacionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void marcarComoLeida(UUID idNotificacion) {
        Notificacion notificacion = notificacionRepository.findById(idNotificacion)
                .orElseThrow(() -> new ResourceNotFoundException("Notificación no encontrada"));
        notificacion.setLeido(true);
        notificacionRepository.save(notificacion);
    }

    private UUID obtenerIdUsuario(UUID idCiclo) {
        FaseAgricolaEntity fase = faseRepository.findById(idCiclo)
                .orElseThrow(() -> new ResourceNotFoundException("Fase no encontrada"));
        CultivoEntity cultivo = cultivoRepository.findById(fase.getIdCultivo())
                .orElseThrow(() -> new ResourceNotFoundException("Cultivo no encontrado"));
        return cultivo.getIdUsuario();
    }
}