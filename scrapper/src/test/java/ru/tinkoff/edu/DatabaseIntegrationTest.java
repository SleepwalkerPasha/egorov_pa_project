package ru.tinkoff.edu;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;
import liquibase.resource.ResourceAccessor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DatabaseIntegrationTest extends IntegrationEnvironment {

    static Connection connection;

    @BeforeAll
    static void setUp() throws SQLException, LiquibaseException, FileNotFoundException {
        String url = DB_CONTAINER.getJdbcUrl();
        String user = DB_CONTAINER.getUsername();
        String pass = DB_CONTAINER.getPassword();

        connection = DriverManager.getConnection(url, user, pass);
        Database database =
            DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

        Path path = new File(".").toPath().toAbsolutePath()
            .getParent()
            .getParent();
        ResourceAccessor resourceAccessor = new DirectoryResourceAccessor(path);
        Liquibase liquibase = new Liquibase("migrations/master.yaml", resourceAccessor, database);
        liquibase.update(new Contexts(), new LabelExpression());
    }

    @Test
    void databaseTest() throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        String[] types = {"TABLE"};
        ResultSet rs = metaData.getTables(null, null, "%", types);
        List<String> tableNames = new ArrayList<>();
        while (rs.next()) {
            tableNames.add(rs.getString("TABLE_NAME"));
        }
        assertTrue(tableNames.contains("chat") && tableNames.contains("link"));
    }

    @AfterAll
    static void tearDown() throws SQLException {
        connection.close();
    }
}
