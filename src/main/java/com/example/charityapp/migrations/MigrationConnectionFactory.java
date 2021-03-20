package com.example.charityapp.migrations;

import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Service;

@Service
public class MigrationConnectionFactory {

  public JdbcTemplate newTemplate(Context context) {
    var connection = new SingleConnectionDataSource(context.getConnection(), true);
    return new JdbcTemplate(connection);
  }
}
