package br.slobra.aplicacao.service.mapper;

import br.slobra.aplicacao.domain.*;
import br.slobra.aplicacao.service.dto.LancamentoGastosDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity LancamentoGastos and its DTO LancamentoGastosDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LancamentoGastosMapper extends EntityMapper<LancamentoGastosDTO, LancamentoGastos> {



    default LancamentoGastos fromId(Long id) {
        if (id == null) {
            return null;
        }
        LancamentoGastos lancamentoGastos = new LancamentoGastos();
        lancamentoGastos.setId(id);
        return lancamentoGastos;
    }
}
