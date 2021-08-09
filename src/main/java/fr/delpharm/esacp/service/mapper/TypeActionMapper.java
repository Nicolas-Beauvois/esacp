package fr.delpharm.esacp.service.mapper;

import fr.delpharm.esacp.domain.*;
import fr.delpharm.esacp.service.dto.TypeActionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TypeAction} and its DTO {@link TypeActionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypeActionMapper extends EntityMapper<TypeActionDTO, TypeAction> {
    @Named("typeAction")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "typeAction", source = "typeAction")
    TypeActionDTO toDtoTypeAction(TypeAction typeAction);
}
