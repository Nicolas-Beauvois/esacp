package fr.delpharm.esacp.service.mapper;

import fr.delpharm.esacp.domain.*;
import fr.delpharm.esacp.service.dto.RapportDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Rapport} and its DTO {@link RapportDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = {
        UserMapper.class,
        SiegeLesionsMapper.class,
        FicheSuiviSanteMapper.class,
        TypeMapper.class,
        CategorieMapper.class,
        EquipementMapper.class,
        TypeRapportMapper.class,
        NatureAccidentMapper.class,
        OrigineLesionsMapper.class,
    }
)
public interface RapportMapper extends EntityMapper<RapportDTO, Rapport> {
    @Mapping(target = "user", source = "user", qualifiedByName = "login")
    @Mapping(target = "siegeLesions", source = "siegeLesions", qualifiedByName = "id")
    @Mapping(target = "ficheSuiviSante", source = "ficheSuiviSante", qualifiedByName = "id")
    @Mapping(target = "type", source = "type", qualifiedByName = "origine")
    @Mapping(target = "categorie", source = "categorie", qualifiedByName = "categorie")
    @Mapping(target = "equipement", source = "equipement", qualifiedByName = "equipement")
    @Mapping(target = "typeRapport", source = "typeRapport", qualifiedByName = "typeRapport")
    @Mapping(target = "natureAccident", source = "natureAccident", qualifiedByName = "typeNatureAccident")
    @Mapping(target = "origineLesions", source = "origineLesions", qualifiedByName = "origineLesions")
    RapportDTO toDto(Rapport s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RapportDTO toDtoId(Rapport rapport);
}
