
# ExternalTaskDTO

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** |  |  [optional]
**handler** | **String** |  |  [optional]
**groupId** | **String** |  |  [optional]
**state** | [**StateEnum**](#StateEnum) |  |  [optional]
**createdOn** | [**DateTime**](DateTime.md) |  |  [optional]
**subjectId** | **String** |  |  [optional]
**context** | **List&lt;byte[]&gt;** |  |  [optional]
**assigner** | **String** |  |  [optional]
**assignedOn** | [**DateTime**](DateTime.md) |  |  [optional]
**completedBy** | **String** |  |  [optional]
**completedOn** | [**DateTime**](DateTime.md) |  |  [optional]
**outcome** | **String** |  |  [optional]
**assignees** | **List&lt;String&gt;** |  |  [optional]


<a name="StateEnum"></a>
## Enum: StateEnum
Name | Value
---- | -----
ASSIGNED | &quot;ASSIGNED&quot;
COMPLETED | &quot;COMPLETED&quot;
DELETED | &quot;DELETED&quot;



