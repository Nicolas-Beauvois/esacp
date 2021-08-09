package fr.delpharm.esacp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.delpharm.esacp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ListingMailDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListingMailDTO.class);
        ListingMailDTO listingMailDTO1 = new ListingMailDTO();
        listingMailDTO1.setId(1L);
        ListingMailDTO listingMailDTO2 = new ListingMailDTO();
        assertThat(listingMailDTO1).isNotEqualTo(listingMailDTO2);
        listingMailDTO2.setId(listingMailDTO1.getId());
        assertThat(listingMailDTO1).isEqualTo(listingMailDTO2);
        listingMailDTO2.setId(2L);
        assertThat(listingMailDTO1).isNotEqualTo(listingMailDTO2);
        listingMailDTO1.setId(null);
        assertThat(listingMailDTO1).isNotEqualTo(listingMailDTO2);
    }
}
