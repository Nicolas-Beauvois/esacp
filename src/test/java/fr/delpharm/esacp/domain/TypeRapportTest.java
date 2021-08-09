package fr.delpharm.esacp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.delpharm.esacp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypeRapportTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeRapport.class);
        TypeRapport typeRapport1 = new TypeRapport();
        typeRapport1.setId(1L);
        TypeRapport typeRapport2 = new TypeRapport();
        typeRapport2.setId(typeRapport1.getId());
        assertThat(typeRapport1).isEqualTo(typeRapport2);
        typeRapport2.setId(2L);
        assertThat(typeRapport1).isNotEqualTo(typeRapport2);
        typeRapport1.setId(null);
        assertThat(typeRapport1).isNotEqualTo(typeRapport2);
    }
}
