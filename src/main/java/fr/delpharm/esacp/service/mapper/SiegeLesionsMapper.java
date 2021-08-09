package fr.delpharm.esacp.service.mapper;

import fr.delpharm.esacp.domain.*;
import fr.delpharm.esacp.service.dto.SiegeLesionsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SiegeLesions} and its DTO {@link SiegeLesionsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SiegeLesionsMapper extends EntityMapper<SiegeLesionsDTO, SiegeLesions> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SiegeLesionsDTO toDtoId(SiegeLesions siegeLesions);
}
