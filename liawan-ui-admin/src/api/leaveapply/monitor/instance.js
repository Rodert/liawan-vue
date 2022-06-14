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
        url: '/flow/monitor/listProcess',
        method: 'post',
        params: query
    })
}
//跳转
export function redirection(processInstanceId) {
  this.$router.push("/flow/monitor/traceProcess/" + processInstanceId)
}
//挂起
export function hangupProcess(processInstanceId) {
    return request({
        url: '/flow/monitor/suspend/' + processInstanceId,
        method: 'get',
    })
}
//唤醒
export function rouseProcess(processInstanceId) {
  return request({
      url: '/flow/monitor/run/' + processInstanceId,
      method: 'get',
  })
}

// 查询请假详细
export function getApply(id) {
    return request({
        url: '/system/apply/' + id,
        method: 'get'
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
