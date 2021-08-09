package fr.delpharm.esacp.service.mapper;

import fr.delpharm.esacp.domain.*;
import fr.delpharm.esacp.service.dto.SexeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sexe} and its DTO {@link SexeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SexeMapper extends EntityMapper<SexeDTO, Sexe> {
    @Named("sexe")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "sexe", source = "sexe")
    SexeDTO toDtoSexe(Sexe sexe);
}
