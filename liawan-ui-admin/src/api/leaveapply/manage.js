import request from '@/utils/request'

// 查询请假列表
export function listApply(query) {
    return request({
        url: '/system/apply/list',
        method: 'get',
        params: query
    })
}
export function listProcess(query) {
  return request({
      url: '/flow/manage/getprocesslists',
      method: 'post',
      params: query
  })
}

// 查询请假详细
export function getApply(id) {
    return request({
        url: '/system/apply/' + id,
        method: 'get'
    })
}
//查询任务详细信息
export function getApplyByTaskId(taskid) {
  return request({
      url: '/system/apply/getLeavepplyInfo/' + taskid,
      method: 'get'
  })
}
//办理任务
export function examineTask(data) {
  return request({
      url: '/task/manage/completeTask/' + data.taskId,
      method: 'post',
      data: data,
  })
}

// 新增请假
export function addApply(data) {
    return request({
        url: '/system/apply',
        method: 'post',
        data: data
    })
}

// 修改请假
export function updateApply(data) {
    return request({
        url: '/system/apply',
        method: 'put',
        data: data
    })
}

// 删除请假
export function delApply(id) {
    return request({
        url: '/system/apply/' + id,
        method: 'delete'
    })
}
