package fr.delpharm.esacp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SiegeLesionsMapperTest {

    private SiegeLesionsMapper siegeLesionsMapper;

    @BeforeEach
    public void setUp() {
        siegeLesionsMapper = new SiegeLesionsMapperImpl();
    }
}
