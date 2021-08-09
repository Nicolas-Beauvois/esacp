package fr.delpharm.esacp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CriticiteMapperTest {

    private CriticiteMapper criticiteMapper;

    @BeforeEach
    public void setUp() {
        criticiteMapper = new CriticiteMapperImpl();
    }
}
