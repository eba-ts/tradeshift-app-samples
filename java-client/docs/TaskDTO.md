
# TaskDTO

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | [**IDTask**](IDTask.md) |  |  [optional]
**handler** | **String** |  |  [optional]
**groupId** | [**IDSecurityGroup**](IDSecurityGroup.md) |  |  [optional]
**state** | [**StateEnum**](#StateEnum) |  |  [optional]
**createdOn** | [**DateTime**](DateTime.md) |  |  [optional]
**assigner** | [**IDUser**](IDUser.md) |  |  [optional]
**assignedOn** | [**DateTime**](DateTime.md) |  |  [optional]
**assignees** | [**List&lt;IDUser&gt;**](IDUser.md) |  |  [optional]
**subjectId** | **String** |  |  [optional]
**context** | **List&lt;byte[]&gt;** |  |  [optional]
**completedBy** | [**IDUser**](IDUser.md) |  |  [optional]
**completedOn** | [**DateTime**](DateTime.md) |  |  [optional]
**outcome** | **String** |  |  [optional]


<a name="StateEnum"></a>
## Enum: StateEnum
Name | Value
---- | -----
ASSIGNED | &quot;ASSIGNED&quot;
COMPLETED | &quot;COMPLETED&quot;
DELETED | &quot;DELETED&quot;



