package fr.delpharm.esacp.service.mapper;

import fr.delpharm.esacp.domain.*;
import fr.delpharm.esacp.service.dto.ActionsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Actions} and its DTO {@link ActionsDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = {
        RapportMapper.class,
        CorrectPreventMapper.class,
        PrioriteMapper.class,
        EtapeValidationMapper.class,
        TypeActionMapper.class,
        CriticiteMapper.class,
        PieceJointesMapper.class,
    }
)
public interface ActionsMapper extends EntityMapper<ActionsDTO, Actions> {
    @Mapping(target = "rapport", source = "rapport", qualifiedByName = "id")
    @Mapping(target = "correctPrevent", source = "correctPrevent", qualifiedByName = "correctPrevent")
    @Mapping(target = "priorite", source = "priorite", qualifiedByName = "priorite")
    @Mapping(target = "etapeValidation", source = "etapeValidation", qualifiedByName = "etapeValidation")
    @Mapping(target = "typeAction", source = "typeAction", qualifiedByName = "typeAction")
    @Mapping(target = "criticite", source = "criticite", qualifiedByName = "criticite")
    @Mapping(target = "pj", source = "pj", qualifiedByName = "pj")
    ActionsDTO toDto(Actions s);
}
