package com.example.charityapp.migrations;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class V2__ProductMigration extends BaseJavaMigration {
  private final MigrationConnectionFactory connectionFactory;

  public V2__ProductMigration(MigrationConnectionFactory connectionFactory) {
    this.connectionFactory = connectionFactory;
  }

  @Override
  public void migrate(Context context) {
    var template = connectionFactory.newTemplate(context);

    var items =
        List.of(
            new ProductItem("Brownie", 0.65),
            new ProductItem("Muffin", 1.00),
            new ProductItem("Cake Pop", 1.35),
            new ProductItem("Apple tart", 1.50),
            new ProductItem("Water", 1.50),
            new ProductItem("Shirt", 2.00),
            new ProductItem("Pants", 3.00),
            new ProductItem("Jacket", 4.00),
            new ProductItem("Toy", 1.00));

    for (ProductItem item : items) {
      insert(template, getNextId(template), item);
    }
  }

  private int getNextId(JdbcTemplate template) {
    var id = template.queryForObject("select nextval('hibernate_sequence');", Integer.class);
    return id == null ? 0 : id;
  }

  private void insert(JdbcTemplate template, int id, ProductItem item) {
    var statement = "insert into product values (?, ?, ?)";
    template.update(statement, id, item.name, BigDecimal.valueOf(item.price));
  }

  @Override
  public String getDescription() {
    return V2__ProductMigration.class.getSimpleName();
  }

  private static class ProductItem {
    private final String name;
    private final double price;

    private ProductItem(String name, double price) {
      this.name = name;
      this.price = price;
    }
  }
}
