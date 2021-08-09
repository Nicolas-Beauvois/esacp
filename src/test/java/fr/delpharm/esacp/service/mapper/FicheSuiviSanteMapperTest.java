package fr.delpharm.esacp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FicheSuiviSanteMapperTest {

    private FicheSuiviSanteMapper ficheSuiviSanteMapper;

    @BeforeEach
    public void setUp() {
        ficheSuiviSanteMapper = new FicheSuiviSanteMapperImpl();
    }
}
