package fr.delpharm.esacp.service.mapper;

import fr.delpharm.esacp.domain.*;
import fr.delpharm.esacp.service.dto.NatureAccidentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NatureAccident} and its DTO {@link NatureAccidentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NatureAccidentMapper extends EntityMapper<NatureAccidentDTO, NatureAccident> {
    @Named("typeNatureAccident")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "typeNatureAccident", source = "typeNatureAccident")
    NatureAccidentDTO toDtoTypeNatureAccident(NatureAccident natureAccident);
}
