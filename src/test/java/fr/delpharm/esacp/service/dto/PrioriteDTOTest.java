package fr.delpharm.esacp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.delpharm.esacp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PrioriteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrioriteDTO.class);
        PrioriteDTO prioriteDTO1 = new PrioriteDTO();
        prioriteDTO1.setId(1L);
        PrioriteDTO prioriteDTO2 = new PrioriteDTO();
        assertThat(prioriteDTO1).isNotEqualTo(prioriteDTO2);
        prioriteDTO2.setId(prioriteDTO1.getId());
        assertThat(prioriteDTO1).isEqualTo(prioriteDTO2);
        prioriteDTO2.setId(2L);
        assertThat(prioriteDTO1).isNotEqualTo(prioriteDTO2);
        prioriteDTO1.setId(null);
        assertThat(prioriteDTO1).isNotEqualTo(prioriteDTO2);
    }
}
