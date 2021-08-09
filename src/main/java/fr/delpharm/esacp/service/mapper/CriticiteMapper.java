package fr.delpharm.esacp.service.mapper;

import fr.delpharm.esacp.domain.*;
import fr.delpharm.esacp.service.dto.CriticiteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Criticite} and its DTO {@link CriticiteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CriticiteMapper extends EntityMapper<CriticiteDTO, Criticite> {
    @Named("criticite")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "criticite", source = "criticite")
    CriticiteDTO toDtoCriticite(Criticite criticite);
}
