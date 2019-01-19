package br.slobra.aplicacao.service.mapper;

import br.slobra.aplicacao.domain.*;
import br.slobra.aplicacao.service.dto.GastoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Gasto and its DTO GastoDTO.
 */
@Mapper(componentModel = "spring", uses = {ObraMapper.class})
public interface GastoMapper extends EntityMapper<GastoDTO, Gasto> {

    @Mapping(source = "obra.id", target = "obraId")
    GastoDTO toDto(Gasto gasto);

    @Mapping(source = "obraId", target = "obra")
    Gasto toEntity(GastoDTO gastoDTO);

    default Gasto fromId(Long id) {
        if (id == null) {
            return null;
        }
        Gasto gasto = new Gasto();
        gasto.setId(id);
        return gasto;
    }
}
