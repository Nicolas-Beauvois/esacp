package fr.delpharm.esacp.service.mapper;

import fr.delpharm.esacp.domain.*;
import fr.delpharm.esacp.service.dto.CorrectPreventDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CorrectPrevent} and its DTO {@link CorrectPreventDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorrectPreventMapper extends EntityMapper<CorrectPreventDTO, CorrectPrevent> {
    @Named("correctPrevent")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "correctPrevent", source = "correctPrevent")
    CorrectPreventDTO toDtoCorrectPrevent(CorrectPrevent correctPrevent);
}
