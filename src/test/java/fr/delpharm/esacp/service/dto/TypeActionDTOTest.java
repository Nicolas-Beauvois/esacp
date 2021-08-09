package fr.delpharm.esacp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.delpharm.esacp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypeActionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeActionDTO.class);
        TypeActionDTO typeActionDTO1 = new TypeActionDTO();
        typeActionDTO1.setId(1L);
        TypeActionDTO typeActionDTO2 = new TypeActionDTO();
        assertThat(typeActionDTO1).isNotEqualTo(typeActionDTO2);
        typeActionDTO2.setId(typeActionDTO1.getId());
        assertThat(typeActionDTO1).isEqualTo(typeActionDTO2);
        typeActionDTO2.setId(2L);
        assertThat(typeActionDTO1).isNotEqualTo(typeActionDTO2);
        typeActionDTO1.setId(null);
        assertThat(typeActionDTO1).isNotEqualTo(typeActionDTO2);
    }
}
