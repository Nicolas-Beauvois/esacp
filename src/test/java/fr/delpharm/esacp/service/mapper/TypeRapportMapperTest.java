package fr.delpharm.esacp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TypeRapportMapperTest {

    private TypeRapportMapper typeRapportMapper;

    @BeforeEach
    public void setUp() {
        typeRapportMapper = new TypeRapportMapperImpl();
    }
}
