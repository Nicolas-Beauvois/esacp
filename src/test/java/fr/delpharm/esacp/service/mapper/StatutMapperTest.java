package fr.delpharm.esacp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StatutMapperTest {

    private StatutMapper statutMapper;

    @BeforeEach
    public void setUp() {
        statutMapper = new StatutMapperImpl();
    }
}
