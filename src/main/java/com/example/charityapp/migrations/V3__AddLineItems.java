package com.example.charityapp.migrations;

import com.example.charityapp.model.LineItemStatus;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class V3__AddLineItems extends BaseJavaMigration {
  private final MigrationConnectionFactory connectionFactory;

  @Autowired
  public V3__AddLineItems(MigrationConnectionFactory connectionFactory) {
    this.connectionFactory = connectionFactory;
  }

  @Override
  public void migrate(Context context) {
    var template = connectionFactory.newTemplate(context);

    insert(template, "Brownie", 48);
    insert(template, "Muffin", 36);
    insert(template, "Cake Pop", 24);
    insert(template, "Apple tart", 60);
    insert(template, "Water", 30);
  }

  private void insert(JdbcTemplate template, String name, int quantity) {
    var productKey =
        template.queryForObject("select id from product where name = ?", Integer.class, name);

    var statement = "insert into product_line_item values (?, ?, ?)";
    for (int i = 0; i < quantity; i++) {
      template.update(statement, getNextId(template), LineItemStatus.OPEN.name(), productKey);
    }
  }

  private int getNextId(JdbcTemplate template) {
    var id = template.queryForObject("select nextval('hibernate_sequence');", Integer.class);
    return id == null ? 0 : id;
  }

  @Override
  public String getDescription() {
    return V3__AddLineItems.class.getSimpleName();
  }
}
