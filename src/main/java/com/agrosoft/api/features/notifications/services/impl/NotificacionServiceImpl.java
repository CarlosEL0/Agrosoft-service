package com.agrosoft.api.features.notifications.services.impl;

import com.agrosoft.api.features.crops.entities.Cultivo;
import com.agrosoft.api.features.crops.entities.EtapaCrecimiento;
import com.agrosoft.api.features.crops.entities.FaseAgricola;
import com.agrosoft.api.features.crops.repositories.CultivoRepository;
import com.agrosoft.api.features.crops.repositories.EtapaCrecimientoRepository;
import com.agrosoft.api.features.crops.repositories.FaseAgricolaRepository;
import com.agrosoft.api.features.notifications.dto.NotificacionResponseDTO;
import com.agrosoft.api.features.notifications.entities.DescripcionNotificacion;
import com.agrosoft.api.features.notifications.entities.Notificacion;
import com.agrosoft.api.features.notifications.mappers.NotificacionMapper;
import com.agrosoft.api.features.notifications.repositories.DescripcionNotificacionRepository;
import com.agrosoft.api.features.notifications.repositories.NotificacionRepository;
import com.agrosoft.api.features.notifications.services.NotificacionService;
import com.agrosoft.api.shared.exceptions.IntegrationException;
import com.agrosoft.api.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
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

    @Scheduled(cron = "0 0 0 * * *") // Se ejecuta todos los días a medianoche
    public void tareaProgramadaRevisionEtapas() {
        generarNotificacionesFinEtapa();
    }

    @Override
    @Transactional
    public void generarNotificacionesFinEtapa() {
        LocalDate hoy = LocalDate.now();
        LocalDate manana = hoy.plusDays(1);

        // 1. Buscar etapas que vencen hoy o mañana
        List<EtapaCrecimiento> etapasCercanas = etapaRepository.findByFechaFin(hoy);
        etapasCercanas.addAll(etapaRepository.findByFechaFin(manana));

        if (etapasCercanas.isEmpty()) return;

        // 2. Obtener plantilla (o crearla si no existe para evitar errores)
        DescripcionNotificacion plantilla = descripcionRepository
                .findByTipoNotificacion("FIN_ETAPA")
                .orElseGet(() -> {
                    DescripcionNotificacion nuevaPlantilla = DescripcionNotificacion.builder()
                            .titulo("Finalización de Etapa")
                            .mensajeBase("Tu cultivo está por finalizar una etapa.")
                            .tipoNotificacion("FIN_ETAPA")
                            .build();
                    return descripcionRepository.save(nuevaPlantilla);
                });

        for (EtapaCrecimiento etapa : etapasCercanas) {
            // 3. Evitar duplicados
            if (notificacionRepository.existsByIdRecursoAndTipoRecurso(etapa.getIdEtapa(), "ETAPA")) {
                continue;
            }

            try {
                // 4. Obtener datos del cultivo
                FaseAgricola fase = faseRepository.findById(etapa.getIdCiclo())
                        .orElseThrow(() -> new ResourceNotFoundException("Fase no encontrada"));
                Cultivo cultivo = cultivoRepository.findById(fase.getIdCultivo())
                        .orElseThrow(() -> new ResourceNotFoundException("Cultivo no encontrado"));

                String tiempoMsg = etapa.getFechaFin().isEqual(hoy) ? "hoy mismo" : "mañana";
                String mensajeCuerpo = String.format("Tu cultivo de %s está por finalizar la etapa de %s %s.", 
                        cultivo.getNombreCultivo(), etapa.getNombreEtapa(), tiempoMsg);

                Notificacion notificacion = Notificacion.builder()
                        .idUsuario(cultivo.getIdUsuario())
                        .descripcion(plantilla)
                        .idRecurso(etapa.getIdEtapa())
                        .tipoRecurso("ETAPA")
                        .fechaEnvio(LocalDateTime.now())
                        .leido(false)
                        .build();

                // Guardamos el mensaje dinámico en un campo si existiera, 
                // o por ahora aseguramos que se cree la notificación.
                notificacionRepository.save(notificacion);
                System.out.println(">>> Notificación generada: " + mensajeCuerpo);
            } catch (Exception e) {
                System.err.println("Error generando notificacion para etapa: " + etapa.getIdEtapa() + " - " + e.getMessage());
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
        FaseAgricola fase = faseRepository.findById(idCiclo)
                .orElseThrow(() -> new ResourceNotFoundException("Fase no encontrada"));
        Cultivo cultivo = cultivoRepository.findById(fase.getIdCultivo())
                .orElseThrow(() -> new ResourceNotFoundException("Cultivo no encontrado"));
        return cultivo.getIdUsuario();
    }
}