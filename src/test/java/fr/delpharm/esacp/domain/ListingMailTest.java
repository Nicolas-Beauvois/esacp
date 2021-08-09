package fr.delpharm.esacp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.delpharm.esacp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ListingMailTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListingMail.class);
        ListingMail listingMail1 = new ListingMail();
        listingMail1.setId(1L);
        ListingMail listingMail2 = new ListingMail();
        listingMail2.setId(listingMail1.getId());
        assertThat(listingMail1).isEqualTo(listingMail2);
        listingMail2.setId(2L);
        assertThat(listingMail1).isNotEqualTo(listingMail2);
        listingMail1.setId(null);
        assertThat(listingMail1).isNotEqualTo(listingMail2);
    }
}
