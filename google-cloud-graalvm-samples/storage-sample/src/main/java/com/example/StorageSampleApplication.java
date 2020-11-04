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

import com.google.cloud.BatchResult.Callback;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageBatch;
import com.google.cloud.storage.StorageException;
import com.google.cloud.storage.StorageOptions;
import java.util.UUID;

public class StorageSampleApplication {

  private static final String TEST_USER =
      "cloud-graalvm-support-ci@cloud-graalvm-support-ci.iam.gserviceaccount.com";

  /**
   * Runs the storage sample application.
   */
  public static void main(String[] args) throws InterruptedException {
    Storage storageClient = StorageOptions.getDefaultInstance().getService();

    String bucketName = "graalvm-sample-bucket-" + UUID.randomUUID();
    String fileName = "graalvm-sample-file-" + UUID.randomUUID();

    try {
      createBucket(storageClient, bucketName);
      createFile(storageClient, bucketName, fileName);
      runBatchOperations(storageClient, bucketName, fileName);
    } finally {
      System.out.println("Deleting resources.");
      storageClient.delete(bucketName, fileName);
      storageClient.delete(fileName);
    }
  }

  private static void runBatchOperations(
      Storage storageClient, String bucketName, String fileName) {
    BlobId blobId = BlobId.of(bucketName, fileName);

    StorageBatch batch = storageClient.batch();
    batch.update(BlobInfo.newBuilder(blobId).build())
        .notify(new Callback<Blob, StorageException>() {
          @Override
          public void success(Blob blob) {
            System.out.println("Batch update succeeded on " + fileName);
          }

          @Override
          public void error(StorageException e) {
            System.out.println("Batch update failed with cause: " + e);
          }
        });

    batch.submit();
  }

  private static void createBucket(Storage storageClient, String bucketName) {
    BucketInfo bucketInfo =
        BucketInfo.newBuilder(bucketName)
            .setLocation("us-east1")
            .build();
    storageClient.create(bucketInfo);
    storageClient.createAcl(bucketName, Acl.of(new User(TEST_USER), Role.READER));
    storageClient.listAcls(bucketName);
    System.out.println("Created bucket " + bucketName);
  }

  private static void createFile(Storage storageClient, String bucketName, String fileName) {
    BlobInfo blobInfo =
        BlobInfo.newBuilder(bucketName, fileName)
            .setContentType("text/plain")
            .build();
    storageClient.create(blobInfo, "Hello World!".getBytes());
    storageClient.createAcl(blobInfo.getBlobId(), Acl.of(new User(TEST_USER), Role.READER));
    storageClient.listAcls(blobInfo.getBlobId());
    System.out.println("Created file " + blobInfo.getName());

    Blob blob = storageClient.get(bucketName, fileName);
    String content = new String(blob.getContent());
    System.out.println("Successfully wrote to file: " + content);
  }
}
