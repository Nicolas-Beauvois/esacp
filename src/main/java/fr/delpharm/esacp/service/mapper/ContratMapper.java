package fr.delpharm.esacp.service.mapper;

import fr.delpharm.esacp.domain.*;
import fr.delpharm.esacp.service.dto.ContratDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Contrat} and its DTO {@link ContratDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ContratMapper extends EntityMapper<ContratDTO, Contrat> {
    @Named("contrat")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "contrat", source = "contrat")
    ContratDTO toDtoContrat(Contrat contrat);
}
