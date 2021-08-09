package fr.delpharm.esacp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.delpharm.esacp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypeAtDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeAtDTO.class);
        TypeAtDTO typeAtDTO1 = new TypeAtDTO();
        typeAtDTO1.setId(1L);
        TypeAtDTO typeAtDTO2 = new TypeAtDTO();
        assertThat(typeAtDTO1).isNotEqualTo(typeAtDTO2);
        typeAtDTO2.setId(typeAtDTO1.getId());
        assertThat(typeAtDTO1).isEqualTo(typeAtDTO2);
        typeAtDTO2.setId(2L);
        assertThat(typeAtDTO1).isNotEqualTo(typeAtDTO2);
        typeAtDTO1.setId(null);
        assertThat(typeAtDTO1).isNotEqualTo(typeAtDTO2);
    }
}
