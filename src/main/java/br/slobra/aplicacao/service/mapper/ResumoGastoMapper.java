package br.slobra.aplicacao.service.mapper;

import br.slobra.aplicacao.domain.*;
import br.slobra.aplicacao.service.dto.ResumoGastoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ResumoGasto and its DTO ResumoGastoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ResumoGastoMapper extends EntityMapper<ResumoGastoDTO, ResumoGasto> {



    default ResumoGasto fromId(Long id) {
        if (id == null) {
            return null;
        }
        ResumoGasto resumoGasto = new ResumoGasto();
        resumoGasto.setId(id);
        return resumoGasto;
    }
}
