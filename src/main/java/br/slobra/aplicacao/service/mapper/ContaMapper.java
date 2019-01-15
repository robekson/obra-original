package br.slobra.aplicacao.service.mapper;

import br.slobra.aplicacao.domain.*;
import br.slobra.aplicacao.service.dto.ContaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Conta and its DTO ContaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ContaMapper extends EntityMapper<ContaDTO, Conta> {



    default Conta fromId(Long id) {
        if (id == null) {
            return null;
        }
        Conta conta = new Conta();
        conta.setId(id);
        return conta;
    }
}
