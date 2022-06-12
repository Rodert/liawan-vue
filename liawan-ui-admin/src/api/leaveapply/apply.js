import request from '@/utils/request'

// 查询请假列表
export function listApply(query) {
    return request({
        url: '/system/apply/list',
        method: 'get',
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
