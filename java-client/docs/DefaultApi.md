# DefaultApi

All URIs are relative to *http://api-sandbox.tradeshift.com/tradeshift/external/rest*

Method | HTTP request | Description
------------- | ------------- | -------------
[**completeTask**](DefaultApi.md#completeTask) | **PUT** /external/tasks/{taskId}/outcome/{outcome} | Marks a task as completed, setting the provided outcome
[**createTask**](DefaultApi.md#createTask) | **POST** /external/tasks | Create a Task
[**deleteTask**](DefaultApi.md#deleteTask) | **DELETE** /external/tasks/{taskId} | Delete an Existing Task
[**getTask**](DefaultApi.md#getTask) | **GET** /external/tasks/{taskId} | Fetches the task with the provided ID
[**getTaskCounts**](DefaultApi.md#getTaskCounts) | **GET** /external/tasks/count | Get a list of task counts for an account
[**getTasks**](DefaultApi.md#getTasks) | **GET** /external/tasks | Fetches all tasks the user has access to and lets page through them
[**updateTask**](DefaultApi.md#updateTask) | **PUT** /external/tasks/{taskId} | Update Task


<a name="completeTask"></a>
# **completeTask**
> completeTask(taskId, outcome, completedBy)

Marks a task as completed, setting the provided outcome



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.DefaultApi;


DefaultApi apiInstance = new DefaultApi();
String taskId = "taskId_example"; // String | 
String outcome = "outcome_example"; // String | 
String completedBy = "completedBy_example"; // String | 
try {
    apiInstance.completeTask(taskId, outcome, completedBy);
} catch (ApiException e) {
    System.err.println("Exception when calling DefaultApi#completeTask");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **taskId** | **String**|  |
 **outcome** | **String**|  |
 **completedBy** | **String**|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/xml, application/json

<a name="createTask"></a>
# **createTask**
> ExternalTaskDTO createTask(externalTaskDTO)

Create a Task

Create a task. The handler and assignees params are mandatory, all other parameters are optional. if the assigner field is not provided, the currently logged-in user will be set as the task&#39;s assigner

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.DefaultApi;


DefaultApi apiInstance = new DefaultApi();
ExternalTaskDTO externalTaskDTO = new ExternalTaskDTO(); // ExternalTaskDTO | \"Takes an ExternalTaskDTO that only updates the following parameters:\" +             \"handler - A string value that specifies the UI component that would consume the task\",             \"subjectId - An identifier to the document or object associated with this task\",             \"context - App-specific information needed by the handler in order to handle this task. This can be in any format \" +                     \"that is convenient for the handler, and is entirely for its consumption\",             \"assignees - A list of ids of the users to whom this task will be assigned\",             \"assigner - The user who assigned this task\"
try {
    ExternalTaskDTO result = apiInstance.createTask(externalTaskDTO);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DefaultApi#createTask");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **externalTaskDTO** | [**ExternalTaskDTO**](ExternalTaskDTO.md)| \&quot;Takes an ExternalTaskDTO that only updates the following parameters:\&quot; +             \&quot;handler - A string value that specifies the UI component that would consume the task\&quot;,             \&quot;subjectId - An identifier to the document or object associated with this task\&quot;,             \&quot;context - App-specific information needed by the handler in order to handle this task. This can be in any format \&quot; +                     \&quot;that is convenient for the handler, and is entirely for its consumption\&quot;,             \&quot;assignees - A list of ids of the users to whom this task will be assigned\&quot;,             \&quot;assigner - The user who assigned this task\&quot; |

### Return type

[**ExternalTaskDTO**](ExternalTaskDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/xml, application/json

<a name="deleteTask"></a>
# **deleteTask**
> ExternalTaskDTO deleteTask(taskId)

Delete an Existing Task



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.DefaultApi;


DefaultApi apiInstance = new DefaultApi();
String taskId = "taskId_example"; // String | 
try {
    ExternalTaskDTO result = apiInstance.deleteTask(taskId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DefaultApi#deleteTask");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **taskId** | **String**|  |

### Return type

[**ExternalTaskDTO**](ExternalTaskDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/xml, application/json

<a name="getTask"></a>
# **getTask**
> getTask(taskId)

Fetches the task with the provided ID



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.DefaultApi;


DefaultApi apiInstance = new DefaultApi();
String taskId = "taskId_example"; // String | 
try {
    apiInstance.getTask(taskId);
} catch (ApiException e) {
    System.err.println("Exception when calling DefaultApi#getTask");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **taskId** | **String**|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/xml, application/json

<a name="getTaskCounts"></a>
# **getTaskCounts**
> Map&lt;String, Object&gt; getTaskCounts(handler, state)

Get a list of task counts for an account

Returns a map of task groups and counts in each group

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.DefaultApi;


DefaultApi apiInstance = new DefaultApi();
String handler = "handler_example"; // String | 
String state = "state_example"; // String | 
try {
    Map<String, Object> result = apiInstance.getTaskCounts(handler, state);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DefaultApi#getTaskCounts");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **handler** | **String**|  | [optional]
 **state** | **String**|  | [optional] [enum: ASSIGNED, COMPLETED, DELETED]

### Return type

[**Map&lt;String, Object&gt;**](Map.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/xml

<a name="getTasks"></a>
# **getTasks**
> List&lt;ExternalTaskDTO&gt; getTasks(handler, subjectId, state, assignee, limit, page)

Fetches all tasks the user has access to and lets page through them



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.DefaultApi;


DefaultApi apiInstance = new DefaultApi();
String handler = "handler_example"; // String | 
String subjectId = "subjectId_example"; // String | 
String state = "state_example"; // String | 
String assignee = "assignee_example"; // String | 
Integer limit = 10; // Integer | 
Integer page = 0; // Integer | 
try {
    List<ExternalTaskDTO> result = apiInstance.getTasks(handler, subjectId, state, assignee, limit, page);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DefaultApi#getTasks");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **handler** | **String**|  | [optional]
 **subjectId** | **String**|  | [optional]
 **state** | **String**|  | [optional] [enum: ASSIGNED, COMPLETED, DELETED]
 **assignee** | **String**|  | [optional]
 **limit** | **Integer**|  | [optional] [default to 10]
 **page** | **Integer**|  | [optional] [default to 0]

### Return type

[**List&lt;ExternalTaskDTO&gt;**](ExternalTaskDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/xml, application/json

<a name="updateTask"></a>
# **updateTask**
> TaskDTO updateTask(taskId)

Update Task



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.DefaultApi;


DefaultApi apiInstance = new DefaultApi();
String taskId = "taskId_example"; // String | 
try {
    TaskDTO result = apiInstance.updateTask(taskId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DefaultApi#updateTask");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **taskId** | **String**|  |

### Return type

[**TaskDTO**](TaskDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/xml, application/json

