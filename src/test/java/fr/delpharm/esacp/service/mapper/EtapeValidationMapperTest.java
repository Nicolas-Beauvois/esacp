package fr.delpharm.esacp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EtapeValidationMapperTest {

    private EtapeValidationMapper etapeValidationMapper;

    @BeforeEach
    public void setUp() {
        etapeValidationMapper = new EtapeValidationMapperImpl();
    }
}
