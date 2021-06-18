package com.dybek.timeme.IT;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ContextConfiguration(initializers = AbstractIT.DockerPostgreDataSourceInitializer.class)
@Testcontainers
public abstract class AbstractIT {
    public static Environment env;
    public static PostgreSQLContainer<?> postgreDBContainer = new PostgreSQLContainer<>("postgres:9.4");

    @Container
    public static KeycloakContainer keycloakContainer = new KeycloakContainer()
            .withRealmImportFile("realm-export.json")
            .withAdminPassword("passwd")
            .withAdminUsername("admin");

    static {
        postgreDBContainer.start();
        keycloakContainer.start();
    }

    protected void clearKeycloakUsers() {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakContainer.getAuthServerUrl())
                .username(keycloakContainer.getAdminUsername())
                .password(keycloakContainer.getAdminPassword())
                .realm("master")
                .clientId("admin-cli")
                .clientSecret(env.getProperty("keycloak.credentials.secret"))
                .build();

        RealmResource realm = keycloak.realm(env.getProperty("keycloak.realm"));
        realm.users().list().forEach(user -> realm.users().delete(user.getId()));
    }

    protected ResultSet performQuery(JdbcDatabaseContainer<?> container, String sql) throws SQLException {
        DataSource ds = getDataSource(container);
        Statement statement = ds.getConnection().createStatement();
        statement.execute(sql);
        ResultSet resultSet = statement.getResultSet();

        resultSet.next();
        return resultSet;
    }

    protected DataSource getDataSource(JdbcDatabaseContainer<?> container) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(container.getJdbcUrl());
        hikariConfig.setUsername(container.getUsername());
        hikariConfig.setPassword(container.getPassword());
        hikariConfig.setDriverClassName(container.getDriverClassName());
        return new HikariDataSource(hikariConfig);
    }

    public static class DockerPostgreDataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            env = applicationContext.getEnvironment();
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.datasource.url=" + postgreDBContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreDBContainer.getUsername(),
                    "spring.datasource.password=" + postgreDBContainer.getPassword(),
                    "keycloak.auth-server-url=" + keycloakContainer.getAuthServerUrl()
            );
        }
    }
}
