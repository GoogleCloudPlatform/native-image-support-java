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

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.util.UUID;

public class StorageSampleApplication {

  /**
   * Runs the storage sample application.
   */
  public static void main(String[] args) {
    Storage storageClient = StorageOptions.getDefaultInstance().getService();

    String bucketName = "graalvm-sample-bucket-" + UUID.randomUUID();
    String fileName = "graalvm-sample-file-" + UUID.randomUUID();

    try {
      System.out.println("Creating bucket " + bucketName);
      BucketInfo bucketInfo =
          BucketInfo.newBuilder(bucketName)
              .setLocation("us-east1")
              .build();
      storageClient.create(bucketInfo);

      System.out.println("Write file to bucket.");
      BlobInfo blobInfo =
          BlobInfo.newBuilder(bucketName, fileName)
              .setContentType("text/plain")
              .build();
      storageClient.create(blobInfo, "Hello World!".getBytes());

      System.out.println("Reading the file that was written...");
      Blob blob = storageClient.get(bucketName, fileName);
      String content = new String(blob.getContent());
      System.out.println("Successfully wrote to file: " + content);
    } finally {
      System.out.println("Cleaning up resources...");

      System.out.println("Deleted file " + fileName);
      storageClient.delete(bucketName, fileName);

      System.out.println("Deleted bucket " + bucketName);
      storageClient.delete(bucketName);
    }
  }
}
