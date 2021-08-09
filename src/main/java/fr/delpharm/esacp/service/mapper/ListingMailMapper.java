package fr.delpharm.esacp.service.mapper;

import fr.delpharm.esacp.domain.*;
import fr.delpharm.esacp.service.dto.ListingMailDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ListingMail} and its DTO {@link ListingMailDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ListingMailMapper extends EntityMapper<ListingMailDTO, ListingMail> {}
