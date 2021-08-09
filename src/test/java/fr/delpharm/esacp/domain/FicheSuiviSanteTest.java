package fr.delpharm.esacp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.delpharm.esacp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FicheSuiviSanteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FicheSuiviSante.class);
        FicheSuiviSante ficheSuiviSante1 = new FicheSuiviSante();
        ficheSuiviSante1.setId(1L);
        FicheSuiviSante ficheSuiviSante2 = new FicheSuiviSante();
        ficheSuiviSante2.setId(ficheSuiviSante1.getId());
        assertThat(ficheSuiviSante1).isEqualTo(ficheSuiviSante2);
        ficheSuiviSante2.setId(2L);
        assertThat(ficheSuiviSante1).isNotEqualTo(ficheSuiviSante2);
        ficheSuiviSante1.setId(null);
        assertThat(ficheSuiviSante1).isNotEqualTo(ficheSuiviSante2);
    }
}
