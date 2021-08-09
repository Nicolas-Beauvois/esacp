package fr.delpharm.esacp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.delpharm.esacp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FicheSuiviSanteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FicheSuiviSanteDTO.class);
        FicheSuiviSanteDTO ficheSuiviSanteDTO1 = new FicheSuiviSanteDTO();
        ficheSuiviSanteDTO1.setId(1L);
        FicheSuiviSanteDTO ficheSuiviSanteDTO2 = new FicheSuiviSanteDTO();
        assertThat(ficheSuiviSanteDTO1).isNotEqualTo(ficheSuiviSanteDTO2);
        ficheSuiviSanteDTO2.setId(ficheSuiviSanteDTO1.getId());
        assertThat(ficheSuiviSanteDTO1).isEqualTo(ficheSuiviSanteDTO2);
        ficheSuiviSanteDTO2.setId(2L);
        assertThat(ficheSuiviSanteDTO1).isNotEqualTo(ficheSuiviSanteDTO2);
        ficheSuiviSanteDTO1.setId(null);
        assertThat(ficheSuiviSanteDTO1).isNotEqualTo(ficheSuiviSanteDTO2);
    }
}
