package fr.delpharm.esacp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TypeAtMapperTest {

    private TypeAtMapper typeAtMapper;

    @BeforeEach
    public void setUp() {
        typeAtMapper = new TypeAtMapperImpl();
    }
}
