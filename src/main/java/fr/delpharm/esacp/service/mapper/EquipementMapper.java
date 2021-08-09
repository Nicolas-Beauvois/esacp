package fr.delpharm.esacp.service.mapper;

import fr.delpharm.esacp.domain.*;
import fr.delpharm.esacp.service.dto.EquipementDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Equipement} and its DTO {@link EquipementDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EquipementMapper extends EntityMapper<EquipementDTO, Equipement> {
    @Named("equipement")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "equipement", source = "equipement")
    EquipementDTO toDtoEquipement(Equipement equipement);
}
