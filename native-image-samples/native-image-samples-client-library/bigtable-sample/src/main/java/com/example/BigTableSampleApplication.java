/*
 * Copyright 2020-2021 Google LLC
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

import com.google.api.gax.rpc.ServerStream;
import com.google.cloud.ServiceOptions;
import com.google.cloud.bigtable.admin.v2.BigtableTableAdminClient;
import com.google.cloud.bigtable.admin.v2.BigtableTableAdminSettings;
import com.google.cloud.bigtable.admin.v2.models.CreateTableRequest;
import com.google.cloud.bigtable.data.v2.BigtableDataClient;
import com.google.cloud.bigtable.data.v2.BigtableDataSettings;
import com.google.cloud.bigtable.data.v2.models.Query;
import com.google.cloud.bigtable.data.v2.models.Row;
import com.google.cloud.bigtable.data.v2.models.RowCell;
import com.google.cloud.bigtable.data.v2.models.RowMutation;
import com.google.protobuf.ByteString;
import java.io.IOException;
import java.util.UUID;

/**
 * Sample Cloud BigTable application.
 */
public class BigTableSampleApplication {

  private static final String INSTANCE_NAME =
      System.getProperty("bigtable.instance", "nativeimage-test-instance");
  private static final String TABLE_NAME = "nativeimage-test-";

  private static final String COLUMN_FAMILY_NAME = "stats_summary";

  /**
   * Entrypoint to the BigTable sample application.
   */
  public static void main(String[] args) throws IOException {
    String projectId = ServiceOptions.getDefaultProjectId();

    BigtableTableAdminSettings adminClientSettings =
        BigtableTableAdminSettings.newBuilder()
            .setInstanceId(INSTANCE_NAME)
            .setProjectId(projectId)
            .build();

    BigtableDataSettings clientSettings =
        BigtableDataSettings.newBuilder()
            .setInstanceId(INSTANCE_NAME)
            .setProjectId(projectId)
            .build();

    BigtableTableAdminClient adminClient = BigtableTableAdminClient.create(adminClientSettings);
    BigtableDataClient standardClient = BigtableDataClient.create(clientSettings);

    String tableName = TABLE_NAME + UUID.randomUUID().toString();

    createTable(adminClient, tableName);
    insertData(standardClient, tableName);
    readData(standardClient, tableName);

    // Clean up
    deleteTable(adminClient, tableName);
  }

  private static void readData(BigtableDataClient client, String tableId) {
    Query query = Query.create(tableId).prefix("");
    ServerStream<Row> rows = client.readRows(query);

    System.out.println("Reading phone data in table: ");
    for (Row row : rows) {
      System.out.println("Key: " + row.getKey().toStringUtf8());
      for (RowCell cell : row.getCells()) {
        System.out.printf(
            "\t%s: %s @%s%n",
            cell.getQualifier().toStringUtf8(),
            cell.getValue().toStringUtf8(),
            cell.getTimestamp());
      }
      System.out.println();
    }
  }

  private static void insertData(BigtableDataClient client, String tableId) {
    long timestamp = System.currentTimeMillis() * 1000;
    String rowKey = String.format("phone#%d", timestamp);

    RowMutation rowMutation =
        RowMutation.create(tableId, rowKey)
            .setCell(
                COLUMN_FAMILY_NAME,
                ByteString.copyFrom("connected_cell".getBytes()),
                timestamp,
                1)
            .setCell(
                COLUMN_FAMILY_NAME,
                ByteString.copyFrom("connected_wifi".getBytes()),
                timestamp,
                1)
            .setCell(COLUMN_FAMILY_NAME, "os_build", timestamp, "PQ2A.190405.003");

    client.mutateRow(rowMutation);
    System.out.println("Successfully wrote row: " + rowKey);
  }

  private static void createTable(BigtableTableAdminClient adminClient, String table) {
    adminClient.createTable(CreateTableRequest.of(table).addFamily(COLUMN_FAMILY_NAME));
    System.out.println("Created table: " + table);
  }

  private static void deleteTable(BigtableTableAdminClient adminClient, String table) {
    adminClient.deleteTable(table);
    System.out.println("Deleted table: " + table);
  }
}
