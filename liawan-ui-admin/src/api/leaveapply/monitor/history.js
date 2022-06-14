import request from '@/utils/request'

// 查询历史实例列表
export function listHistory(query) {
    return request({
        url: '/flow/monitor/listHistoryProcess',
        method: 'post',
        params: query
    })
}
// 查询实例记录
export function listRunHistory(query) {
  return request({
      url: '/flow/monitor/history/' + query.processInstanceId,
      method: 'post',
      params: query
  })
}
// 查询变量记录
export function listVariables(query) {
  return request({
      url: '/flow/monitor/variables/' + query.processInstanceId,
      method: 'post',
      params: query
  })
}
