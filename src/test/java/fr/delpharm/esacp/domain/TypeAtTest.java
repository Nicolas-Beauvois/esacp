package fr.delpharm.esacp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.delpharm.esacp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypeAtTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeAt.class);
        TypeAt typeAt1 = new TypeAt();
        typeAt1.setId(1L);
        TypeAt typeAt2 = new TypeAt();
        typeAt2.setId(typeAt1.getId());
        assertThat(typeAt1).isEqualTo(typeAt2);
        typeAt2.setId(2L);
        assertThat(typeAt1).isNotEqualTo(typeAt2);
        typeAt1.setId(null);
        assertThat(typeAt1).isNotEqualTo(typeAt2);
    }
}
