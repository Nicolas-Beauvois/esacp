package fr.delpharm.esacp.service.mapper;

import fr.delpharm.esacp.domain.*;
import fr.delpharm.esacp.service.dto.StatutDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Statut} and its DTO {@link StatutDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StatutMapper extends EntityMapper<StatutDTO, Statut> {
    @Named("statut")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "statut", source = "statut")
    StatutDTO toDtoStatut(Statut statut);
}
