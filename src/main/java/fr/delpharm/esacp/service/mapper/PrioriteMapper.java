package fr.delpharm.esacp.service.mapper;

import fr.delpharm.esacp.domain.*;
import fr.delpharm.esacp.service.dto.PrioriteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Priorite} and its DTO {@link PrioriteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PrioriteMapper extends EntityMapper<PrioriteDTO, Priorite> {
    @Named("priorite")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "priorite", source = "priorite")
    PrioriteDTO toDtoPriorite(Priorite priorite);
}
