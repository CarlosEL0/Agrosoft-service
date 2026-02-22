package com.agrosoft.api.features.ai_analysis.mappers;

import com.agrosoft.api.features.ai_analysis.dto.AnalisisIaResponseDTO;
import com.agrosoft.api.features.ai_analysis.dto.RecomendacionDTO;
import com.agrosoft.api.features.ai_analysis.entities.AnalisisIa;
import com.agrosoft.api.features.ai_analysis.entities.Recomendacion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AiAnalysisMapper {

    @Mapping(source = "id", target = "idRecomendacion")
    RecomendacionDTO toRecomendacionDTO(Recomendacion entity);

    List<RecomendacionDTO> toRecomendacionDTOList(List<Recomendacion> entities);

    @Mapping(target = "idAnalisis", source = "analisis.id")
    @Mapping(target = "idCultivo", source = "analisis.idCultivo")
    @Mapping(target = "tipoAnalisis", source = "analisis.tipoAnalisis")
    @Mapping(target = "resultadoAnalisis", source = "analisis.resultadoAnalisis")
    @Mapping(target = "recomendaciones", source = "recomendaciones")
    AnalisisIaResponseDTO toResponseDTO(AnalisisIa analisis, List<RecomendacionDTO> recomendaciones);
}