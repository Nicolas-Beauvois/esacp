package fr.delpharm.esacp.service.mapper;

import fr.delpharm.esacp.domain.*;
import fr.delpharm.esacp.service.dto.RepartitionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Repartition} and its DTO {@link RepartitionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RepartitionMapper extends EntityMapper<RepartitionDTO, Repartition> {
    @Named("repartition")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "repartition", source = "repartition")
    RepartitionDTO toDtoRepartition(Repartition repartition);
}
