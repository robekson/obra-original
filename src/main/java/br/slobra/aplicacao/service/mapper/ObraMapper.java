package br.slobra.aplicacao.service.mapper;

import br.slobra.aplicacao.domain.*;
import br.slobra.aplicacao.service.dto.ObraDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Obra and its DTO ObraDTO.
 */
@Mapper(componentModel = "spring", uses = {ResumoGastoMapper.class})
public interface ObraMapper extends EntityMapper<ObraDTO, Obra> {

    @Mapping(source = "resumoGasto.id", target = "resumoGastoId")
    ObraDTO toDto(Obra obra);

    @Mapping(source = "resumoGastoId", target = "resumoGasto")
    @Mapping(target = "gastos", ignore = true)
    Obra toEntity(ObraDTO obraDTO);

    default Obra fromId(Long id) {
        if (id == null) {
            return null;
        }
        Obra obra = new Obra();
        obra.setId(id);
        return obra;
    }
}
