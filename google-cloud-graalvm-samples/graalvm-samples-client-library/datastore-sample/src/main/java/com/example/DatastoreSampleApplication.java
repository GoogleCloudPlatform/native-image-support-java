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

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery;
import com.google.cloud.datastore.Transaction;
import java.util.UUID;

/**
 * Sample Datastore Application.
 */
public class DatastoreSampleApplication {

  /* Datastore namespace where entities will be created. */
  private static final String TEST_NAMESPACE = "graalvm-test-namespace";

  /* Datastore kind used. */
  private static final String TEST_KIND = "test-kind";

  /**
   * Entrypoint to the Datastore sample application.
   */
  public static void main(String[] args) throws Exception {
    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

    String testId = UUID.randomUUID().toString();

    addEntity(datastore, testId);
    getEntity(datastore, testId);
    deleteEntity(datastore, testId);

    runTransaction(datastore);
    runTransactionCallable(datastore);
  }

  private static void addEntity(Datastore datastore, String id) {
    Key key = createKey(datastore, id);
    Entity entity = Entity.newBuilder(key)
        .set("description", "hello world")
        .build();
    datastore.add(entity);
    System.out.println("Successfully added entity.");
  }

  private static void getEntity(Datastore datastore, String id) {
    Key key = createKey(datastore, id);
    Entity entity = datastore.get(key);
    System.out.println("Reading entity: " + entity.getKey().getName());
  }

  private static void deleteEntity(Datastore datastore, String id) {
    Key key = createKey(datastore, id);
    datastore.delete(key);

    Entity entity = datastore.get(key);
    if (entity == null) {
      System.out.println("Successfully deleted entity: " + id);
    } else {
      throw new RuntimeException("Failed to delete entity: " + id);
    }
  }

  private static void runTransactionCallable(Datastore datastore) {
    datastore.runInTransaction(client -> {
      String id = UUID.randomUUID().toString();
      Key key = createKey(datastore, id);
      Entity entity = Entity.newBuilder(key)
          .set("description", "hello world")
          .build();
      datastore.add(entity);

      StructuredQuery query =
          Query.newEntityQueryBuilder()
              .setNamespace(TEST_NAMESPACE)
              .setKind(TEST_KIND)
              .build();
      QueryResults<Entity> results = datastore.run(query);
      System.out.println("Found entity: " + results.next());

      datastore.delete(key);
      return null;
    });

    System.out.println("Ran transaction callable.");
  }

  private static void runTransaction(Datastore datastore) {
    Transaction transaction = datastore.newTransaction();
    transaction.commit();
    transaction = datastore.newTransaction();
    transaction.rollback();
    System.out.println("Run dummy transaction code.");
  }

  private static Key createKey(Datastore datastore, String id) {
    return datastore.newKeyFactory()
        .setNamespace(TEST_NAMESPACE)
        .setKind(TEST_KIND)
        .newKey(id);
  }
}
