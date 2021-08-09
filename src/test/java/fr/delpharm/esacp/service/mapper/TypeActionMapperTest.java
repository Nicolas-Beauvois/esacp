package fr.delpharm.esacp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TypeActionMapperTest {

    private TypeActionMapper typeActionMapper;

    @BeforeEach
    public void setUp() {
        typeActionMapper = new TypeActionMapperImpl();
    }
}
