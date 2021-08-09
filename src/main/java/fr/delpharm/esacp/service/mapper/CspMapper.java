package fr.delpharm.esacp.service.mapper;

import fr.delpharm.esacp.domain.*;
import fr.delpharm.esacp.service.dto.CspDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Csp} and its DTO {@link CspDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CspMapper extends EntityMapper<CspDTO, Csp> {
    @Named("csp")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "csp", source = "csp")
    CspDTO toDtoCsp(Csp csp);
}
