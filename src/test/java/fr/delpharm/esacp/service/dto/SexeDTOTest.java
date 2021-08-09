package fr.delpharm.esacp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.delpharm.esacp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SexeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SexeDTO.class);
        SexeDTO sexeDTO1 = new SexeDTO();
        sexeDTO1.setId(1L);
        SexeDTO sexeDTO2 = new SexeDTO();
        assertThat(sexeDTO1).isNotEqualTo(sexeDTO2);
        sexeDTO2.setId(sexeDTO1.getId());
        assertThat(sexeDTO1).isEqualTo(sexeDTO2);
        sexeDTO2.setId(2L);
        assertThat(sexeDTO1).isNotEqualTo(sexeDTO2);
        sexeDTO1.setId(null);
        assertThat(sexeDTO1).isNotEqualTo(sexeDTO2);
    }
}
