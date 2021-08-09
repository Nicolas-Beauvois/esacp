package fr.delpharm.esacp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.delpharm.esacp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EtapeValidationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtapeValidationDTO.class);
        EtapeValidationDTO etapeValidationDTO1 = new EtapeValidationDTO();
        etapeValidationDTO1.setId(1L);
        EtapeValidationDTO etapeValidationDTO2 = new EtapeValidationDTO();
        assertThat(etapeValidationDTO1).isNotEqualTo(etapeValidationDTO2);
        etapeValidationDTO2.setId(etapeValidationDTO1.getId());
        assertThat(etapeValidationDTO1).isEqualTo(etapeValidationDTO2);
        etapeValidationDTO2.setId(2L);
        assertThat(etapeValidationDTO1).isNotEqualTo(etapeValidationDTO2);
        etapeValidationDTO1.setId(null);
        assertThat(etapeValidationDTO1).isNotEqualTo(etapeValidationDTO2);
    }
}
