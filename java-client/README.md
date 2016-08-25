# swagger-java-client

## Requirements

Building the API client library requires [Maven](https://maven.apache.org/) to be installed.

## Installation

To install the API client library to your local Maven repository, simply execute:

```shell
mvn install
```

To deploy it to a remote Maven repository instead, configure the settings of the repository and execute:

```shell
mvn deploy
```

Refer to the [official documentation](https://maven.apache.org/plugins/maven-deploy-plugin/usage.html) for more information.

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
    <groupId>io.swagger</groupId>
    <artifactId>swagger-java-client</artifactId>
    <version>1.0.0</version>
    <scope>compile</scope>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
compile "io.swagger:swagger-java-client:1.0.0"
```

### Others

At first generate the JAR by executing:

    mvn package

Then manually install the following JARs:

* target/swagger-java-client-1.0.0.jar
* target/lib/*.jar

## Getting Started

Please follow the [installation](#installation) instruction and execute the following Java code:

```java

import io.swagger.client.*;
import io.swagger.client.auth.*;
import io.swagger.client.model.*;
import io.swagger.client.api.DefaultApi;

import java.io.File;
import java.util.*;

public class DefaultApiExample {

    public static void main(String[] args) {
        
        DefaultApi apiInstance = new DefaultApi();
        //access token which is received from the tradeshift authentication server
        apiInstance.getApiClient().setAccessToken("AAAA%2FAAA%3DAAAAAAAA");
        apiInstance.getApiClient().setBasePath("https://api-sandbox.tradeshift.com/tradeshift/rest");
        
        String taskId = "taskId_example"; // String | 
        String outcome = "outcome_example"; // String | 
        String completedBy = "completedBy_example"; // String | 
        try {
            apiInstance.completeTask(taskId, outcome, completedBy);
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#completeTask");
            e.printStackTrace();
        }
    }
}

```

## Documentation for API Endpoints

All URIs are relative to *http://api-sandbox.tradeshift.com/tradeshift/external/rest*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*DefaultApi* | [**completeTask**](docs/DefaultApi.md#completeTask) | **PUT** /external/tasks/{taskId}/outcome/{outcome} | Marks a task as completed, setting the provided outcome
*DefaultApi* | [**createTask**](docs/DefaultApi.md#createTask) | **POST** /external/tasks | Create a Task
*DefaultApi* | [**deleteTask**](docs/DefaultApi.md#deleteTask) | **DELETE** /external/tasks/{taskId} | Delete an Existing Task
*DefaultApi* | [**getTask**](docs/DefaultApi.md#getTask) | **GET** /external/tasks/{taskId} | Fetches the task with the provided ID
*DefaultApi* | [**getTaskCounts**](docs/DefaultApi.md#getTaskCounts) | **GET** /external/tasks/count | Get a list of task counts for an account
*DefaultApi* | [**getTasks**](docs/DefaultApi.md#getTasks) | **GET** /external/tasks | Fetches all tasks the user has access to and lets page through them
*DefaultApi* | [**updateTask**](docs/DefaultApi.md#updateTask) | **PUT** /external/tasks/{taskId} | Update Task


## Documentation for Models

 - [ExternalTaskDTO](docs/ExternalTaskDTO.md)
 - [IDSecurityGroup](docs/IDSecurityGroup.md)
 - [IDTask](docs/IDTask.md)
 - [IDUser](docs/IDUser.md)
 - [TaskDTO](docs/TaskDTO.md)


## Documentation for Authorization

All endpoints do not require authorization.
Authentication schemes defined for the API:

## Recommendation

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issue.

## Author

apps@tradeshift.com
