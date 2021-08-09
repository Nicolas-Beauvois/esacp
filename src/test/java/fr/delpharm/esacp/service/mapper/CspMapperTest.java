package fr.delpharm.esacp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CspMapperTest {

    private CspMapper cspMapper;

    @BeforeEach
    public void setUp() {
        cspMapper = new CspMapperImpl();
    }
}
