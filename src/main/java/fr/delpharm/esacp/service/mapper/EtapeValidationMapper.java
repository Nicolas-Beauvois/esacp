package fr.delpharm.esacp.service.mapper;

import fr.delpharm.esacp.domain.*;
import fr.delpharm.esacp.service.dto.EtapeValidationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EtapeValidation} and its DTO {@link EtapeValidationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EtapeValidationMapper extends EntityMapper<EtapeValidationDTO, EtapeValidation> {
    @Named("etapeValidation")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "etapeValidation", source = "etapeValidation")
    EtapeValidationDTO toDtoEtapeValidation(EtapeValidation etapeValidation);
}
