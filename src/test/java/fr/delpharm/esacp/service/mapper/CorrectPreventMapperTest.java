package fr.delpharm.esacp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CorrectPreventMapperTest {

    private CorrectPreventMapper correctPreventMapper;

    @BeforeEach
    public void setUp() {
        correctPreventMapper = new CorrectPreventMapperImpl();
    }
}
