package com.javapub.liawan.generator.mapper;

import com.javapub.liawan.generator.domain.GenTableColumn;

import java.util.List;

/**
 * 业务字段 数据层
 *
 * @Author: JavaPub
 * @License: https://github.com/Rodert/liawan-vue https://gitee.com/rodert/
 * @Contact: https://javapub.blog.csdn.net/ http://liawan.javapub.net.cn/
 * @Date: 2022
 * @Version:
 * @Description:
 */
public interface GenTableColumnMapper {
    /**
     * 根据表名称查询列信息
     *
     * @param tableName 表名称
     * @return 列信息
     */
    public List<GenTableColumn> selectDbTableColumnsByName(String tableName);

    /**
     * 查询业务字段列表
     *
     * @param tableId 业务字段编号
     * @return 业务字段集合
     */
    public List<GenTableColumn> selectGenTableColumnListByTableId(Long tableId);

    /**
     * 新增业务字段
     *
     * @param genTableColumn 业务字段信息
     * @return 结果
     */
    public int insertGenTableColumn(GenTableColumn genTableColumn);

    /**
     * 修改业务字段
     *
     * @param genTableColumn 业务字段信息
     * @return 结果
     */
    public int updateGenTableColumn(GenTableColumn genTableColumn);

    /**
     * 删除业务字段
     *
     * @param genTableColumns 列数据
     * @return 结果
     */
    public int deleteGenTableColumns(List<GenTableColumn> genTableColumns);

    /**
     * 批量删除业务字段
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGenTableColumnByIds(Long[] ids);
}
