package fr.delpharm.esacp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RepartitionMapperTest {

    private RepartitionMapper repartitionMapper;

    @BeforeEach
    public void setUp() {
        repartitionMapper = new RepartitionMapperImpl();
    }
}
