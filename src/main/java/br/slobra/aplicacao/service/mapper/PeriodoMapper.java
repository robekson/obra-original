package br.slobra.aplicacao.service.mapper;

import br.slobra.aplicacao.domain.*;
import br.slobra.aplicacao.service.dto.PeriodoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Periodo and its DTO PeriodoDTO.
 */
@Mapper(componentModel = "spring", uses = {ObrasMapper.class, ContaMapper.class})
public interface PeriodoMapper extends EntityMapper<PeriodoDTO, Periodo> {

    @Mapping(source = "obras.id", target = "obrasId")
    @Mapping(source = "periodo.id", target = "periodoId")
    PeriodoDTO toDto(Periodo periodo);

    @Mapping(source = "obrasId", target = "obras")
    @Mapping(source = "periodoId", target = "periodo")
    Periodo toEntity(PeriodoDTO periodoDTO);

    default Periodo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Periodo periodo = new Periodo();
        periodo.setId(id);
        return periodo;
    }
}
