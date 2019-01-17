package br.slobra.aplicacao.service.mapper;

import br.slobra.aplicacao.domain.*;
import br.slobra.aplicacao.service.dto.ObrasDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Obras and its DTO ObrasDTO.
 */
@Mapper(componentModel = "spring", uses = {LancamentoGastosMapper.class})
public interface ObrasMapper extends EntityMapper<ObrasDTO, Obras> {

    @Mapping(source = "lancamentoGastos.id", target = "lancamentoGastosId")
    ObrasDTO toDto(Obras obras);

    @Mapping(target = "obras", ignore = true)
    @Mapping(source = "lancamentoGastosId", target = "lancamentoGastos")
    Obras toEntity(ObrasDTO obrasDTO);

    default Obras fromId(Long id) {
        if (id == null) {
            return null;
        }
        Obras obras = new Obras();
        obras.setId(id);
        return obras;
    }
}
