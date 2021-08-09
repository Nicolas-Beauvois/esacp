package fr.delpharm.esacp.service.mapper;

import fr.delpharm.esacp.domain.*;
import fr.delpharm.esacp.service.dto.PieceJointesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PieceJointes} and its DTO {@link PieceJointesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PieceJointesMapper extends EntityMapper<PieceJointesDTO, PieceJointes> {
    @Named("pj")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "pj", source = "pj")
    PieceJointesDTO toDtoPj(PieceJointes pieceJointes);
}
