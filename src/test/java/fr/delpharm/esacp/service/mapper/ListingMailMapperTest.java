package fr.delpharm.esacp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ListingMailMapperTest {

    private ListingMailMapper listingMailMapper;

    @BeforeEach
    public void setUp() {
        listingMailMapper = new ListingMailMapperImpl();
    }
}
