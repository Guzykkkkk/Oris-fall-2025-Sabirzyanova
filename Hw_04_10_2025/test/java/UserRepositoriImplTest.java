import org.example.FourthOctober.model.UserEntity;
import org.example.FourthOctober.repository.Impl.UserRepositoryImpl;
import org.example.FourthOctober.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserRepositoriImplTest {
    private UserRepositoryImpl userRepository;

    @BeforeEach
    void setUp() throws Exception {
        String databaseUrl = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
        Connection connection = DriverManager.getConnection(databaseUrl, "grim", "");
        Statement statement = connection.createStatement();
        statement.execute("""
            CREATE TABLE user_entity (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                email VARCHAR(255) NOT NULL UNIQUE,
                password VARCHAR(255) NOT NULL,
                name VARCHAR(255) NOT NULL
            )
        """);
        statement.execute("""
            INSERT INTO user_entity (email, password, name) 
            VALUES ('test@example.com', 'testpass', 'Test User')
        """);

        statement.close();
        org.springframework.jdbc.core.JdbcTemplate jdbcTemplate =
                new org.springframework.jdbc.core.JdbcTemplate();
        jdbcTemplate.setDataSource(new SimpleTestDataSource(databaseUrl));
        userRepository = new UserRepositoryImpl(jdbcTemplate);
    }

    @Test
    void testCanFindUserByEmail() {
        Optional<UserEntity> user = userRepository.findByEmail("test@example.com");

        assertTrue(user.isPresent(),
                "must find user by email");
        assertEquals("Test User", user.get().getNickname(),
                "must correct read an user");
    }

    @Test
    void testReturnsEmptyForNonExistentUser() {
        Optional<UserEntity> user = userRepository.findByEmail("nonexistent@test.com");

        assertTrue(user.isEmpty(),
                "returns empty list for not existing user");
    }
    private static class SimpleTestDataSource implements javax.sql.DataSource {
        private final String url;

        public SimpleTestDataSource(String url) {
            this.url = url;
        }

        @Override
        public Connection getConnection() {
            try {
                return DriverManager.getConnection(url, "sa", "");
            } catch (Exception e) {
                throw new RuntimeException("couldn't connect to db", e);
            }
        }
        @Override public Connection getConnection(String username, String password) { return null; }
        @Override public <T> T unwrap(Class<T> iface) { return null; }
        @Override public boolean isWrapperFor(Class<?> iface) { return false; }
        @Override public java.io.PrintWriter getLogWriter() { return null; }
        @Override public void setLogWriter(java.io.PrintWriter out) {}
        @Override public void setLoginTimeout(int seconds) {}
        @Override public int getLoginTimeout() { return 0; }
        @Override public java.util.logging.Logger getParentLogger() { return null; }
    }
    @Test
    void testInterfaceMethods() {
        UserRepository repository = new UserRepository() {
            public Optional<UserEntity> findByEmail(String email) { return Optional.empty(); }
            public void save(UserEntity entity) { }
        };
        assertTrue(repository.findByEmail("test").isEmpty());
        repository.save(UserEntity.builder().build());
    }
}
