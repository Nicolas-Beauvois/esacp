package fr.delpharm.esacp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrigineLesionsMapperTest {

    private OrigineLesionsMapper origineLesionsMapper;

    @BeforeEach
    public void setUp() {
        origineLesionsMapper = new OrigineLesionsMapperImpl();
    }
}
