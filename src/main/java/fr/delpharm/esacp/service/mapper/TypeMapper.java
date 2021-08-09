package fr.delpharm.esacp.service.mapper;

import fr.delpharm.esacp.domain.*;
import fr.delpharm.esacp.service.dto.TypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Type} and its DTO {@link TypeDTO}.
 */
@Mapper(componentModel = "spring", uses = { RepartitionMapper.class })
public interface TypeMapper extends EntityMapper<TypeDTO, Type> {
    @Mapping(target = "repartition", source = "repartition", qualifiedByName = "repartition")
    TypeDTO toDto(Type s);

    @Named("origine")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "origine", source = "origine")
    TypeDTO toDtoOrigine(Type type);
}
