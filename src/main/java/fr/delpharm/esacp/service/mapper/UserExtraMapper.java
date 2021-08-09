package fr.delpharm.esacp.service.mapper;

import fr.delpharm.esacp.domain.*;
import fr.delpharm.esacp.service.dto.UserExtraDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserExtra} and its DTO {@link UserExtraDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = {
        UserMapper.class,
        StatutMapper.class,
        SexeMapper.class,
        DepartementMapper.class,
        ContratMapper.class,
        SiteMapper.class,
        CspMapper.class,
    }
)
public interface UserExtraMapper extends EntityMapper<UserExtraDTO, UserExtra> {
    @Mapping(target = "user", source = "user", qualifiedByName = "id")
    @Mapping(target = "statut", source = "statut", qualifiedByName = "statut")
    @Mapping(target = "sexe", source = "sexe", qualifiedByName = "sexe")
    @Mapping(target = "departement", source = "departement", qualifiedByName = "departement")
    @Mapping(target = "contrat", source = "contrat", qualifiedByName = "contrat")
    @Mapping(target = "site", source = "site", qualifiedByName = "site")
    @Mapping(target = "csp", source = "csp", qualifiedByName = "csp")
    UserExtraDTO toDto(UserExtra s);
}
