package fr.delpharm.esacp.service.mapper;

import fr.delpharm.esacp.domain.*;
import fr.delpharm.esacp.service.dto.OrigineLesionsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrigineLesions} and its DTO {@link OrigineLesionsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrigineLesionsMapper extends EntityMapper<OrigineLesionsDTO, OrigineLesions> {
    @Named("origineLesions")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "origineLesions", source = "origineLesions")
    OrigineLesionsDTO toDtoOrigineLesions(OrigineLesions origineLesions);
}
