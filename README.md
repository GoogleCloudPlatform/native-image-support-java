# Google Cloud GraalVM Support

[![experimental](http://badges.github.io/stability-badges/dist/experimental.svg)](http://github.com/badges/stability-badges)

This repository provides support for applications using the [Google Java Client Libraries](https://github.com/googleapis/google-cloud-java) to be built as [GraalVM Native Images](https://www.graalvm.org/reference-manual/native-image/).

## Setup

Add the `google-cloud-graalvm-support` artifact to your project to take advantage of native image support.

For example, in Maven:

```
<dependency>
    <groupId>com.google.cloud</groupId>
    <artifactId>google-cloud-graalvm-support</artifactId>
    <version>0.3.0</version>
</dependency>
```

This dependency contains the GraalVM configurations to provide out-of-the-box support for native-image compilation of applications using Google Java Client Libraries.

### Client Library Versions

To compile with GraalVM (native-image), ensure the client library version in your project is supported by `google-cloud-graalvm-support`.

| GraalVM Support version | *`libraries-bom` version | `grpc-netty-shaded` version |
|-------------------------|:-------------------------|-----------------------------|
| `0.3.0`                 | `11.0.0` or later        | `1.32.1` or later           |

Typically, you can just depend on the latest versions of the client libraries to get something working if you are not sure about what versions of (transitive) dependencies are being used by your project.

**NOTE:** Most users typically manage their client library versions using the [Cloud Libraries Bill of Materials](https://github.com/GoogleCloudPlatform/cloud-opensource-java/wiki/The-Google-Cloud-Platform-Libraries-BOM) (`libraries-bom`).
This is an easy way to ensure that the versions of all the client libraries in your project are compatible with each other and up-to-date.
The `libraries-bom` also manages the version of `grpc-netty-shaded` as well and ensures that it is at the latest compatible version.

## Supported Libraries

Most of the Java Google Client Libraries are supported for GraalVM compilation using this dependency.
If you find an unsupported library, please make a feature request via our [Github Issue Tracker](https://github.com/GoogleCloudPlatform/google-cloud-graalvm-support/issues).

GraalVM-compatible sample code using various Google Cloud libraries can be found below:

| Google Cloud Service Library  | Sample Link              | 
|-------------------------|--------------------------|
| [Cloud BigQuery](https://github.com/googleapis/java-bigquery) | [bigquery-sample](./google-cloud-graalvm-samples/graalvm-samples-client-library/bigquery-sample) |
| [Cloud BigTable](https://github.com/googleapis/java-bigtable) | [bigtable-sample](./google-cloud-graalvm-samples/graalvm-samples-client-library/bigtable-sample) |
| [Cloud Datastore](https://github.com/googleapis/java-datastore) | [datastore-sample](./google-cloud-graalvm-samples/graalvm-samples-client-library/datastore-sample) |
| [Cloud Firestore](https://github.com/googleapis/java-firestore) | [firestore-sample](./google-cloud-graalvm-samples/graalvm-samples-client-library/firestore-sample) |
| [Cloud Logging](https://github.com/googleapis/java-logging) | [logging-sample](./google-cloud-graalvm-samples/graalvm-samples-client-library/logging-sample) |
| [Cloud Pub/Sub](https://github.com/googleapis/java-pubsub) | [pubsub-sample](./google-cloud-graalvm-samples/graalvm-samples-client-library/pubsub-sample) |
| [Cloud Secret Manager](https://github.com/googleapis/java-secretmanager) | [secretmanager-sample](./google-cloud-graalvm-samples/graalvm-samples-client-library/secretmanager-sample) |
| [Cloud Spanner](https://github.com/googleapis/java-spanner) | [spanner-sample](./google-cloud-graalvm-samples/graalvm-samples-client-library/spanner-sample) |
| [Cloud Storage](https://github.com/googleapis/java-storage) | [storage-sample](./google-cloud-graalvm-samples/graalvm-samples-client-library/storage-sample) |

Additional API compatibility is in active development.

Please also consult the project [samples applications directory](./google-cloud-graalvm-samples) for the full range of code samples.

### Additional Frameworks

Our project `google-cloud-graalvm-support` targets compatibility for native image frameworks as well, such as for Quarkus, Micronaut, and Spring.
We are in the early stages of research for these frameworks and maintain some [code samples](./google-cloud-graalvm-samples).

We are also interested in collaborating with other open source projects to improve framework-level compatibility.

Related projects:

*  [Quarkus Extension for Google Cloud Services](https://github.com/quarkiverse/quarkiverse-google-cloud-services) - Enables usage of Google Cloud libraries in Quarkus applications.

Please let us know if you are interested in collaborating by contacting us via our [Issue Tracker](https://github.com/GoogleCloudPlatform/google-cloud-graalvm-support/issues).

## Questions

Please report any issues and questions to our [Github Issue Tracker](https://github.com/GoogleCloudPlatform/google-cloud-graalvm-support/issues).
