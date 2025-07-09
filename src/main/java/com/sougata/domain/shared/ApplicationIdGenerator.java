package com.sougata.domain.shared;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ApplicationIdGenerator implements IdentifierGenerator {
    @Override
    public Object generate(SharedSessionContractImplementor session, Object o) {
        String prefix = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Connection connection = null;
        try {
            connection = session.getJdbcConnectionAccess().obtainConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select nextvalu('application_id_seq')");

            if (resultSet.next()) {
                int sequence = resultSet.getInt(1);
                String formatted = String.format("%02d", sequence);
                return prefix + formatted;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    session.getJdbcConnectionAccess().releaseConnection(connection);
                } catch (Exception ignored) {
                }
            }
        }
        throw new RuntimeException("No sequence generator retrieved");
    }
}
