package com.example.charityapp.migrations;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

    var id = getNextId(template);

    var statement = "insert into product_line_item values (?, ?)";
    for (int i = 0; i < quantity; i++) {
      template.update(statement, id++, productKey);
    }
  }

  private int getNextId(JdbcTemplate template) {
    int id;
    try {
      var dbId =
          template.queryForObject(
              "select id from product_line_item order by id desc limit 1", int.class);
      id = dbId == null ? 0 : dbId;
    } catch (EmptyResultDataAccessException e) {
      id = 0;
    }
    return id + 1;
  }

  @Override
  public String getDescription() {
    return V3__AddLineItems.class.getSimpleName();
  }
}
