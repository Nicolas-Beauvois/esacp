package fr.delpharm.esacp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PieceJointesMapperTest {

    private PieceJointesMapper pieceJointesMapper;

    @BeforeEach
    public void setUp() {
        pieceJointesMapper = new PieceJointesMapperImpl();
    }
}
