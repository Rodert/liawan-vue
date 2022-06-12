[toc]

## 开源审批流对比

## 业务流程

1. 请假申请
2. 待办处理
3. 流程实例监控
4. 审核运行历史记录
5. 流程部署-bpmn
6. 统计

# flowable

因为使用的是框架，所以涉及到很多关键字的使用，掌握这些关键字尤为重要。比如java中的String、static等。

## 数据表

**Flowable数据库表命名规则**

ACT_RE_*      :      ’RE’表示repository（存储）。RepositoryService接口操作的表。带此前缀的表包含的是静态信息，如，流程定义，流程的资源（图片，规则等）。

ACT_RU_*      :      ’RU’表示runtime。RuntimeService、TaskService等接口操作的表。这是运行时的表存储着流程变量，用户任务，变量，职责（job）等运行时的数据。flowable只存储实例执行期间的运行时数据，当流程实例结束时，将删除这些记录。这就保证了这些运行时的表小且快。

ACT_ID_*      :      ’ID’表示identity(组织机构)。IdentityService接口操作的表。这些表包含标识的信息，如用户，用户组，等等。

ACT_HI_*      :      ’HI’表示history。HistoryService接口操作的表。就是这些表包含着历史的相关数据，如结束的流程实例，变量，任务，等等。

ACT_GE_*      :      普通数据，各种情况都使用的数据。


## Flowable流程实例processInstance基本操作

- 流程开启
- 流程中止
- 流程激活
- 流程删除
- 流程查询

## API介绍

 引擎API是与Flowable交互的最常用手段。总入口点是ProcessEngine。

1、RepositoryService很可能是使用Flowable引擎要用的第一个服务。这个服务提供了管理与控制部署(deployments)与流程定义(process definitions)的操作。管理静态信息，

2、RuntimeService用于启动流程定义的新流程实例。

3、IdentityService很简单。它用于管理（创建，更新，删除，查询……）组与用户。

4、FormService是可选服务。也就是说Flowable没有它也能很好地运行，而不必牺牲任何功能。

5、HistoryService暴露Flowable引擎收集的所有历史数据。要提供查询历史数据的能力。

6、ManagementService通常在用Flowable编写用户应用时不需要使用。它可以读取数据库表与表原始数据的信息，也提供了对作业(job)的查询与管理操作。

7、DynamicBpmnService可用于修改流程定义中的部分内容，而不需要重新部署它。例如可以修改流程定义中一个用户任务的办理人设置，或者修改一个服务任务中的类名。


