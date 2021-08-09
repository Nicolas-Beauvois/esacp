package fr.delpharm.esacp.service.mapper;

import fr.delpharm.esacp.domain.*;
import fr.delpharm.esacp.service.dto.FicheSuiviSanteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FicheSuiviSante} and its DTO {@link FicheSuiviSanteDTO}.
 */
@Mapper(componentModel = "spring", uses = { TypeAtMapper.class })
public interface FicheSuiviSanteMapper extends EntityMapper<FicheSuiviSanteDTO, FicheSuiviSante> {
    @Mapping(target = "typeAt", source = "typeAt", qualifiedByName = "typeAt")
    FicheSuiviSanteDTO toDto(FicheSuiviSante s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FicheSuiviSanteDTO toDtoId(FicheSuiviSante ficheSuiviSante);
}
