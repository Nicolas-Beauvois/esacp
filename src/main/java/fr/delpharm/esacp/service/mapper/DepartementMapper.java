package fr.delpharm.esacp.service.mapper;

import fr.delpharm.esacp.domain.*;
import fr.delpharm.esacp.service.dto.DepartementDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Departement} and its DTO {@link DepartementDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DepartementMapper extends EntityMapper<DepartementDTO, Departement> {
    @Named("departement")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "departement", source = "departement")
    DepartementDTO toDtoDepartement(Departement departement);
}
