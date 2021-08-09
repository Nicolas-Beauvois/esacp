package fr.delpharm.esacp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PrioriteMapperTest {

    private PrioriteMapper prioriteMapper;

    @BeforeEach
    public void setUp() {
        prioriteMapper = new PrioriteMapperImpl();
    }
}
