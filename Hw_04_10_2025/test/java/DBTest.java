import org.example.FourthOctober.config.DataBaseConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DBTest {
    @Test
    void testConfigExists() {
        assertNotNull(DataBaseConfig.class);
    }

    @Test
    void testJdbcTemplateExists() {
        assertNotNull(DataBaseConfig.jdbcTemplate);
    }
}
