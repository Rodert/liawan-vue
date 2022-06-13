<template>
    <div class="app-container">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
                        <el-form-item label="请假人" prop="userId">
                            <el-input
                                    v-model="queryParams.userId"
                                    placeholder="请输入请假人"
                                    clearable
                                    @keyup.enter.native="handleQuery"
                            />
                        </el-form-item>
                        <el-form-item label="起始时间" prop="startTime" >
                            <el-date-picker clearable
                                            v-model="queryParams.startTime"
                                            type="date"
                                            value-format="yyyy-MM-dd"
                                            placeholder="请选择起始时间">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item label="结束时间" prop="endTime">
                            <el-date-picker clearable
                                            v-model="queryParams.endTime"
                                            type="date"
                                            value-format="yyyy-MM-dd"
                                            placeholder="请选择结束时间">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item label="原因" prop="reason">
                            <el-input
                                    v-model="queryParams.reason"
                                    placeholder="请输入原因"
                                    clearable
                                    @keyup.enter.native="handleQuery"
                            />
                        </el-form-item>
                        <el-form-item label="申请时间" prop="applyTime">
                            <el-date-picker clearable
                                            v-model="queryParams.applyTime"
                                            type="date"
                                            value-format="yyyy-MM-dd"
                                            placeholder="请选择申请时间">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item label="实际起始时间" prop="realityStartTime">
                            <el-date-picker clearable
                                            v-model="queryParams.realityStartTime"
                                            type="date"
                                            value-format="yyyy-MM-dd"
                                            placeholder="请选择实际起始时间">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item label="实际结束时间" prop="realityEndTime">
                            <el-date-picker clearable
                                            v-model="queryParams.realityEndTime"
                                            type="date"
                                            value-format="yyyy-MM-dd"
                                            placeholder="请选择实际结束时间">
                            </el-date-picker>
                        </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
                <el-button
                        type="primary"
                        plain
                        icon="el-icon-plus"
                        size="mini"
                        @click="handleAdd"
                        v-hasPermi="['system:apply:add']"
                >新增</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button
                        type="success"
                        plain
                        icon="el-icon-edit"
                        size="mini"
                        :disabled="single"
                        @click="handleUpdate"
                        v-hasPermi="['system:apply:edit']"
                >修改</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button
                        type="danger"
                        plain
                        icon="el-icon-delete"
                        size="mini"
                        :disabled="multiple"
                        @click="handleDelete"
                        v-hasPermi="['system:apply:remove']"
                >删除</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button
                        type="warning"
                        plain
                        icon="el-icon-download"
                        size="mini"
                        @click="handleExport"
                        v-hasPermi="['system:apply:export']"
                >导出</el-button>
            </el-col>
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="applyList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
                    <el-table-column label="主键" align="center" prop="id" />
                    <el-table-column label="请假人" align="center" prop="userId" />
                    <el-table-column label="起始时间" align="center" prop="startTime" width="180">
                        <template slot-scope="scope">
                            <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="结束时间" align="center" prop="endTime" width="180">
                        <template slot-scope="scope">
                            <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="类型" align="center" prop="leaveType" />
                    <el-table-column label="原因" align="center" prop="reason" />
                    <el-table-column label="申请时间" align="center" prop="applyTime" width="180">
                        <template slot-scope="scope">
                            <span>{{ parseTime(scope.row.applyTime, '{y}-{m}-{d}') }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="实际起始时间" align="center" prop="realityStartTime" width="180">
                        <template slot-scope="scope">
                            <span>{{ parseTime(scope.row.realityStartTime, '{y}-{m}-{d}') }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="实际结束时间" align="center" prop="realityEndTime" width="180">
                        <template slot-scope="scope">
                            <span>{{ parseTime(scope.row.realityEndTime, '{y}-{m}-{d}') }}</span>
                        </template>
                    </el-table-column>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                    <el-button
                            size="mini"
                            type="text"
                            icon="el-icon-edit"
                            @click="handleUpdate(scope.row)"
                            v-hasPermi="['system:apply:edit']"
                    >修改</el-button>
                    <el-button
                            size="mini"
                            type="text"
                            icon="el-icon-delete"
                            @click="handleDelete(scope.row)"
                            v-hasPermi="['system:apply:remove']"
                    >删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <pagination
                v-show="total>0"
                :total="total"
                :page.sync="queryParams.pageNum"
                :limit.sync="queryParams.pageSize"
                @pagination="getList"
        />

        <!-- 添加或修改请假对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
            <el-form ref="form" :model="form" :rules="rules" label-width="80px">
                                <el-form-item label="请假人" prop="userId">
                                    <el-input v-model="form.userId" placeholder="请输入请假人" :disabled="true" />
                                </el-form-item>
                                <el-form-item label="请假类型" prop="leaveType">
                                    <el-select v-model="form.leaveType" placeholder="请输入类型">
                                        <el-option v-for="item in leaveTypeList" :key="item" :label="item" :value="item"></el-option>
                                    </el-select>
                                </el-form-item>
                                <el-form-item label="起始时间" prop="startTime" type="daterange">
                                    <el-date-picker clearable
                                                    v-model="form.startTime"
                                                    type="date"
                                                    value-format="yyyy-MM-dd HH:mm:ss"
                                                    placeholder="请选择起始时间">
                                    </el-date-picker>
                                </el-form-item>
                                <el-form-item label="结束时间" prop="endTime">
                                    <el-date-picker clearable
                                                    v-model="form.endTime"
                                                    type="date"
                                                    value-format="yyyy-MM-dd HH:mm:ss"
                                                    placeholder="请选择结束时间">
                                    </el-date-picker>
                                </el-form-item>
                                <el-form-item label="原因" prop="reason">
                                    <el-input v-model="form.reason" placeholder="请输入原因" />
                                </el-form-item>
                                <el-form-item label="部门领导：" prop="deptleader">
                                    <el-select v-model="form.deptleader" placeholder="请输入部门领导">
                                        <el-option v-for="item in userList" :key="item.userName" :label="item.userName" :value="item.userName"/>
                                    </el-select>
                                </el-form-item>
                                <!-- <el-form-item label="申请时间" prop="applyTime">
                                    <el-date-picker clearable
                                                    v-model="form.applyTime"
                                                    type="date"
                                                    value-format="yyyy-MM-dd"
                                                    placeholder="请选择申请时间">
                                    </el-date-picker>
                                </el-form-item>
                                <el-form-item label="实际起始时间" prop="realityStartTime">
                                    <el-date-picker clearable
                                                    v-model="form.realityStartTime"
                                                    type="date"
                                                    value-format="yyyy-MM-dd"
                                                    placeholder="请选择实际起始时间">
                                    </el-date-picker>
                                </el-form-item>
                                <el-form-item label="实际结束时间" prop="realityEndTime">
                                    <el-date-picker clearable
                                                    v-model="form.realityEndTime"
                                                    type="date"
                                                    value-format="yyyy-MM-dd"
                                                    placeholder="请选择实际结束时间">
                                    </el-date-picker>
                                </el-form-item> -->
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitForm">确 定</el-button>
                <el-button @click="cancel">取 消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import { getUserProfile, listUser } from "@/api/system/user";
    import { listApply, getApply, delApply, addApply, updateApply } from "@/api/leaveapply/apply";


    export default {
        name: "Apply",
        data() {
            return {
                // 遮罩层
                loading: true,
                // 选中数组
                ids: [],
                // 非单个禁用
                single: true,
                // 非多个禁用
                multiple: true,
                // 显示搜索条件
                showSearch: true,
                // 总条数
                total: 0,
                // 请假表格数据
                applyList: [],
                // 弹出层标题
                title: "",
                // 是否显示弹出层
                open: false,
                // 查询参数
                queryParams: {
                    pageNum: 1,
                    pageSize: 10,
                    userId: null,
                    startTime: null,
                    endTime: null,
                    leaveType: null,
                    reason: null,
                    applyTime: null,
                    realityStartTime: null,
                    realityEndTime: null,
                    //部门领导
                    deptleader: null,
                },
                // 表单参数
                form: {},
                // 表单校验
                rules: {
                },
                leaveTypeList: [
                  "事假",
                  "病假",
                  "年假",
                  "婚假",
                  "产假",
                  "调休",
                ],
                user: {},
                roleGroup: {},
                postGroup: {},
                userId: '',
                //全部用户数据
                userList: null,
            };
        },
        created() {
            this.getList();
        },
        methods: {
            /** 查询请假列表 */
            getList() {
                this.loading = true;
                listApply(this.queryParams).then(response => {
                    this.applyList = response.rows;
                    this.total = response.total;
                    this.loading = false;
                });
            },
            // 取消按钮
            cancel() {
                this.open = false;
                this.reset();
            },
            // 表单重置
            reset() {
                this.form = {
                        id: null,
                        userId: null,
                        startTime: null,
                        endTime: null,
                        leaveType: null,
                        reason: null,
                        applyTime: null,
                        realityStartTime: null,
                        realityEndTime: null,
                        createBy: null,
                        createTime: null,
                        updateBy: null,
                        updateTime: null,
                        //部门领导
                        deptleader: null,
                };
                this.resetForm("form");
            },
            /** 搜索按钮操作 */
            handleQuery() {
                this.queryParams.pageNum = 1;
                this.getList();
            },
            /** 重置按钮操作 */
            resetQuery() {
                this.resetForm("queryForm");
                this.handleQuery();
            },
            // 多选框选中数据
            handleSelectionChange(selection) {
                this.ids = selection.map(item => item.id)
                this.single = selection.length!==1
                this.multiple = !selection.length
            },
            /** 新增按钮操作 */
            handleAdd() {
                this.reset();
                this.open = true;
                this.title = "添加请假";
                this.loading = true;
                //获取用户信息
                getUserProfile().then(response => {
                    this.form.userId = response.data.userName;
                });
                listUser().then(response => {
                    this.userList = response.rows;
                });
                this.loading = false;
            },
            /** 修改按钮操作 */
            handleUpdate(row) {
                this.reset();
                const id = row.id || this.ids
                getApply(id).then(response => {
                    this.form = response.data;
                    this.open = true;
                    this.title = "修改请假";
                });
            },
            /** 提交按钮 */
           submitForm() {
                this.$refs["form"].validate(valid => {
                    if (valid) {
                        if (this.form.id != null) {
                            updateApply(this.form).then(response => {
                                this.$modal.msgSuccess("修改成功");
                                this.open = false;
                                this.getList();
                            });
                        } else {
                            addApply(this.form).then(response => {
                                this.$modal.msgSuccess("新增成功");
                                this.open = false;
                                this.getList();
                            });
                        }
                    }
                });
            },
            /** 删除按钮操作 */
            handleDelete(row) {
                const ids = row.id || this.ids;
                this.$modal.confirm('是否确认删除请假编号为"' + ids + '"的数据项？').then(function() {
                    return delApply(ids);
                }).then(() => {
                    this.getList();
                    this.$modal.msgSuccess("删除成功");
                }).catch(() => {});
            },
            /** 导出按钮操作 */
            handleExport() {
                this.download('system/apply/export', {
                    ...this.queryParams
                }, `apply_${new Date().getTime()}.xlsx`)
            }
        }
    };
</script>