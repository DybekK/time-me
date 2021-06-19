package com.dybek.timeme.IT;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.jooq.DSLContext;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ContextConfiguration(initializers = AbstractIT.DockerPostgreDataSourceInitializer.class)
@Testcontainers
public abstract class AbstractIT {
    protected static Environment env;
    protected static PostgreSQLContainer<?> postgreDBContainer = new PostgreSQLContainer<>("postgres:latest");
    protected final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected DSLContext dsl;

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

    protected Connection getConnection() throws SQLException {
        DataSource ds = getDataSource(postgreDBContainer);
        return ds.getConnection();
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
