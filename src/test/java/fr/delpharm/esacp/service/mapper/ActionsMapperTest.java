package fr.delpharm.esacp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ActionsMapperTest {

    private ActionsMapper actionsMapper;

    @BeforeEach
    public void setUp() {
        actionsMapper = new ActionsMapperImpl();
    }
}
