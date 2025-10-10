import org.example.FourthOctober.dto.FieldErrorDto;
import org.example.FourthOctober.service.Impl.RegexAuthDataValidationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegexAuthValidationTest {
    private RegexAuthDataValidationServiceImpl validator;

    @BeforeEach
    void setUp() {
        validator = new RegexAuthDataValidationServiceImpl();
    }

    @Test
    void testGoodEmailPasses() {
        List<FieldErrorDto> errors = validator.validateEmail("good@example.com");
        assertTrue(errors.isEmpty());
    }

    @Test
    void testBadEmailFails() {
        List<FieldErrorDto> errors = validator.validateEmail("bad-email");
        assertFalse(errors.isEmpty());
    }

    @Test
    void testNullEmailGivesError() {
        List<FieldErrorDto> errors = validator.validateEmail(null);
        assertFalse(errors.isEmpty());
    }

    @Test
    void testShortEmailFails() {
        List<FieldErrorDto> errors = validator.validateEmail("a@b.c");
        assertFalse(errors.isEmpty());
    }

    @Test
    void testGoodPasswordPasses() {
        List<FieldErrorDto> errors = validator.validatePassword("GoodPass123");
        assertTrue(errors.isEmpty());
    }
}