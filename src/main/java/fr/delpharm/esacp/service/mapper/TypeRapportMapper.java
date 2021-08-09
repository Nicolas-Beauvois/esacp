package fr.delpharm.esacp.service.mapper;

import fr.delpharm.esacp.domain.*;
import fr.delpharm.esacp.service.dto.TypeRapportDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TypeRapport} and its DTO {@link TypeRapportDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypeRapportMapper extends EntityMapper<TypeRapportDTO, TypeRapport> {
    @Named("typeRapport")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "typeRapport", source = "typeRapport")
    TypeRapportDTO toDtoTypeRapport(TypeRapport typeRapport);
}
