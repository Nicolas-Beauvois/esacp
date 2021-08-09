package fr.delpharm.esacp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NatureAccidentMapperTest {

    private NatureAccidentMapper natureAccidentMapper;

    @BeforeEach
    public void setUp() {
        natureAccidentMapper = new NatureAccidentMapperImpl();
    }
}
