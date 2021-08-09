package fr.delpharm.esacp.service.mapper;

import fr.delpharm.esacp.domain.*;
import fr.delpharm.esacp.service.dto.CategorieDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Categorie} and its DTO {@link CategorieDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategorieMapper extends EntityMapper<CategorieDTO, Categorie> {
    @Named("categorie")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "categorie", source = "categorie")
    CategorieDTO toDtoCategorie(Categorie categorie);
}
