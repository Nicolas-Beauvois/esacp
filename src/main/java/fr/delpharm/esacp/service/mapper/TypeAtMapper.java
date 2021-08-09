package fr.delpharm.esacp.service.mapper;

import fr.delpharm.esacp.domain.*;
import fr.delpharm.esacp.service.dto.TypeAtDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TypeAt} and its DTO {@link TypeAtDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypeAtMapper extends EntityMapper<TypeAtDTO, TypeAt> {
    @Named("typeAt")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "typeAt", source = "typeAt")
    TypeAtDTO toDtoTypeAt(TypeAt typeAt);
}
