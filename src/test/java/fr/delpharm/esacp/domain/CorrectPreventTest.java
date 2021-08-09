package fr.delpharm.esacp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.delpharm.esacp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CorrectPreventTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorrectPrevent.class);
        CorrectPrevent correctPrevent1 = new CorrectPrevent();
        correctPrevent1.setId(1L);
        CorrectPrevent correctPrevent2 = new CorrectPrevent();
        correctPrevent2.setId(correctPrevent1.getId());
        assertThat(correctPrevent1).isEqualTo(correctPrevent2);
        correctPrevent2.setId(2L);
        assertThat(correctPrevent1).isNotEqualTo(correctPrevent2);
        correctPrevent1.setId(null);
        assertThat(correctPrevent1).isNotEqualTo(correctPrevent2);
    }
}
