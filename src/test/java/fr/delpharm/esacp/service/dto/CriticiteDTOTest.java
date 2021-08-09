package fr.delpharm.esacp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.delpharm.esacp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CriticiteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CriticiteDTO.class);
        CriticiteDTO criticiteDTO1 = new CriticiteDTO();
        criticiteDTO1.setId(1L);
        CriticiteDTO criticiteDTO2 = new CriticiteDTO();
        assertThat(criticiteDTO1).isNotEqualTo(criticiteDTO2);
        criticiteDTO2.setId(criticiteDTO1.getId());
        assertThat(criticiteDTO1).isEqualTo(criticiteDTO2);
        criticiteDTO2.setId(2L);
        assertThat(criticiteDTO1).isNotEqualTo(criticiteDTO2);
        criticiteDTO1.setId(null);
        assertThat(criticiteDTO1).isNotEqualTo(criticiteDTO2);
    }
}
