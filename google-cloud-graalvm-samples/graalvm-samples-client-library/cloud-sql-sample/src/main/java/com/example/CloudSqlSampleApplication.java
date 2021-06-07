/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Sample application demonstrating usage of Cloud SQL connector with GraalVM.
 */
public class CloudSqlSampleApplication {

  /**
   * Entrypoint to the Cloud SQL (with MySQL) sample application.
   */
  public static void main(String[] args) throws Exception {

    String user = System.getProperty("user", "root");
    String password = System.getProperty("password", "");
    String instanceName = System.getProperty("instance");
    String databaseName = System.getProperty("database", "test_db");

    HikariConfig config = new HikariConfig();

    config.setJdbcUrl(String.format("jdbc:mysql:///%s", databaseName));
    config.addDataSourceProperty("user", user);
    config.addDataSourceProperty("password", password);
    config.addDataSourceProperty("socketFactory", "com.google.cloud.sql.mysql.SocketFactory");
    config.addDataSourceProperty("cloudSqlInstance", instanceName);

    HikariDataSource connectionPool = new HikariDataSource(config);

    // Create a table.
    try (Connection conn = connectionPool.getConnection()) {
      String dropTable = "DROP TABLE IF EXISTS Books";
      try (PreparedStatement createTableStatement = conn.prepareStatement(dropTable)) {
        createTableStatement.execute();
      }

      String stmt =
          "CREATE TABLE Books ("
          + "  ID VARCHAR(36) NOT NULL,"
          + "  TITLE TEXT NOT NULL"
          + ");";
      try (PreparedStatement createTableStatement = conn.prepareStatement(stmt)) {
        createTableStatement.execute();
      }
    }

    // Insert a new record
    try (Connection conn = connectionPool.getConnection()) {
      String stmt = "INSERT INTO Books (ID, TITLE) VALUES (?, ?)";
      try (PreparedStatement insertStmt = conn.prepareStatement(stmt)) {
        insertStmt.setQueryTimeout(10);
        insertStmt.setString(1, UUID.randomUUID().toString());
        insertStmt.setString(2, "The Book");
        insertStmt.execute();
      }
    }

    // Read records
    ArrayList<String> books = new ArrayList<>();
    try (Connection conn = connectionPool.getConnection()) {
      String stmt = "SELECT * FROM Books";
      try (PreparedStatement selectStmt = conn.prepareStatement(stmt)) {
        selectStmt.setQueryTimeout(10); // 10s
        ResultSet rs = selectStmt.executeQuery();

        while (rs.next()) {
          books.add(resultSetToString(rs));
        }
      }
    }

    System.out.println("Books in database:");
    for (String book : books) {
      System.out.println(book);
    }

    if (books.size() == 0) {
      throw new RuntimeException("Expected records in database, found none: " + books);
    }
  }

  private static String resultSetToString(ResultSet resultSet) throws SQLException {
    ArrayList<String> row = new ArrayList<>();
    for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
      row.add(resultSet.getString(i));
    }

    return row.stream().collect(Collectors.joining(", "));
  }
}
