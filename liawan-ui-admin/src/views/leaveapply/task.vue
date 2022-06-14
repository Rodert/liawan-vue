<template>
    <div class="app-container">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
                        <el-form-item label="流程名称" prop="processName">
                            <el-input
                                    v-model="queryParams.processName"
                                    placeholder="请输入流程名称"
                                    clearable
                                    @keyup.enter.native="handleQuery"
                            />
                        </el-form-item>
                        <el-form-item label="任务名称" prop="taskName">
                            <el-input
                                    v-model="queryParams.taskName"
                                    placeholder="请输入任务名称"
                                    clearable
                                    @keyup.enter.native="handleQuery"
                            />
                        </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>


        <el-table v-loading="loading" :data="taskList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
                    <el-table-column label="任务名称" align="center" prop="taskName" />
                    <el-table-column label="流程实例编号" align="center" prop="processInstanceId" />
                    <el-table-column label="执行实例编号" align="center" prop="executionId" />
                    <el-table-column label="业务号" align="center" prop="businessKey" />
                    <el-table-column label="流程名称" align="center" prop="processName" />
                    <el-table-column label="发起人" align="center" prop="starter" />
                    <el-table-column label="办理人" align="center" prop="assignee" />
                    <el-table-column label="任务创建时间" align="center" prop="createTime" width="180">
                        <template slot-scope="scope">
                            <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="流程启动时间" align="center" prop="startTime" width="180">
                        <template slot-scope="scope">
                            <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
                        </template>
                    </el-table-column>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                    <el-button
                            size="mini"
                            type="text"
                            icon="el-icon-edit"
                            @click="handleUpdateByTask(scope.row)"
                            v-hasPermi="['system:apply:edit']"
                    >办理</el-button>
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

        <!-- 部门领导审批对话框对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
            <el-form ref="form" :model="form" :rules="rules" label-width="80px">
                                <el-form-item label="任务id：" prop="taskId">
                                    <el-input v-model="form.taskId" placeholder="任务id" :disabled="true"/>
                                </el-form-item>
                                <el-form-item label="请假人：" prop="userId">
                                    <el-input v-model="form.userId" placeholder="请假人" :disabled="true"/>
                                </el-form-item>
                                <el-form-item label="类型：" prop="leaveType">
                                    <el-input v-model="form.leaveType" placeholder="类型" :disabled="true"/>
                                </el-form-item>
                                <el-form-item label="起始时间：" prop="startTime">
                                    <el-date-picker clearable
                                                    v-model="form.startTime"
                                                    type="date"
                                                    value-format="yyyy-MM-dd"
                                                    placeholder="请选择起始时间"
                                                    :disabled="true">
                                    </el-date-picker>
                                </el-form-item>
                                <el-form-item label="结束时间：" prop="endTime">
                                    <el-date-picker clearable
                                                    v-model="form.endTime"
                                                    type="date"
                                                    value-format="yyyy-MM-dd"
                                                    placeholder="请选择结束时间"
                                                    :disabled="true">
                                    </el-date-picker>
                                </el-form-item>
                                <el-form-item label="原因：" prop="reason">
                                    <el-input v-model="form.reason" placeholder="请输入原因" :disabled="true"/>
                                </el-form-item>
                                <el-form-item label="人事：" prop="hr">
                                    <el-select v-model="form.hr" placeholder="人事">
                                        <el-option v-for="item in userList" :key="item.userName" :label="item.userName" :value="item.userName"/>
                                    </el-select>
                                </el-form-item>
                                <el-form-item label="审批结果：">
                                    <el-radio-group v-model="form.deptleaderapprove">
                                        <el-radio :label="true">同意</el-radio>
                                        <el-radio :label="false">拒绝</el-radio>
                                    </el-radio-group>
                                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitForm2">确 定</el-button>
                <!-- <el-button @click="cancel">取 消</el-button> -->
            </div>
        </el-dialog>

        <!-- hr审批对话框 -->
        <el-dialog :title="title" :visible.sync="openHr" width="500px" append-to-body>
            <el-form ref="form" :model="form" :rules="rules" label-width="80px">
                                <el-form-item label="任务id：" prop="taskId">
                                    <el-input v-model="form.taskId" placeholder="任务id" :disabled="true"/>
                                </el-form-item>
                                <el-form-item label="请假人：" prop="userId">
                                    <el-input v-model="form.userId" placeholder="请假人" :disabled="true"/>
                                </el-form-item>
                                <el-form-item label="类型：" prop="leaveType">
                                    <el-input v-model="form.leaveType" placeholder="类型" :disabled="true"/>
                                </el-form-item>
                                <el-form-item label="起始时间：" prop="startTime">
                                    <el-date-picker clearable
                                                    v-model="form.startTime"
                                                    type="date"
                                                    value-format="yyyy-MM-dd"
                                                    placeholder="请选择起始时间"
                                                    :disabled="true">
                                    </el-date-picker>
                                </el-form-item>
                                <el-form-item label="结束时间：" prop="endTime">
                                    <el-date-picker clearable
                                                    v-model="form.endTime"
                                                    type="date"
                                                    value-format="yyyy-MM-dd"
                                                    placeholder="请选择结束时间"
                                                    :disabled="true">
                                    </el-date-picker>
                                </el-form-item>
                                <el-form-item label="原因：" prop="reason">
                                    <el-input v-model="form.reason" placeholder="请输入原因" :disabled="true"/>
                                </el-form-item>
                                <el-form-item label="审批结果：">
                                    <el-radio-group v-model="form.hrapprove">
                                        <el-radio :label="true">同意</el-radio>
                                        <el-radio :label="false">拒绝</el-radio>
                                    </el-radio-group>
                                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitForm2">确 定</el-button>
                <!-- <el-button @click="cancel">取 消</el-button> -->
            </div>
        </el-dialog>

        <!-- 销假对话框 -->
        <el-dialog v-loading="loading" :title="title" :visible.sync="openEnd" width="500px" append-to-body>
            <el-form ref="form" :model="form" :rules="rules" label-width="80px">
                                <el-form-item label="id：" prop="id">
                                    <el-input v-model="form.id" placeholder="id" :disabled="true"/>
                                </el-form-item>
                                <el-form-item label="任务id：" prop="taskId">
                                    <el-input v-model="form.taskId" placeholder="任务id" :disabled="true"/>
                                </el-form-item>
                                <el-form-item label="请假人：" prop="userId">
                                    <el-input v-model="form.userId" placeholder="请假人" :disabled="true"/>
                                </el-form-item>
                                <el-form-item label="类型：" prop="leaveType">
                                    <el-input v-model="form.leaveType" placeholder="类型" :disabled="true"/>
                                </el-form-item>
                                <el-form-item label="起始时间：" prop="startTime">
                                    <el-date-picker clearable
                                                    v-model="form.startTime"
                                                    type="date"
                                                    value-format="yyyy-MM-dd"
                                                    placeholder="请选择起始时间"
                                                    :disabled="true">
                                    </el-date-picker>
                                </el-form-item>
                                <el-form-item label="结束时间：" prop="endTime">
                                    <el-date-picker clearable
                                                    v-model="form.endTime"
                                                    type="date"
                                                    value-format="yyyy-MM-dd"
                                                    placeholder="请选择结束时间"
                                                    :disabled="true">
                                    </el-date-picker>
                                </el-form-item>
                                <el-form-item label="原因：" prop="reason">
                                    <el-input v-model="form.reason" placeholder="请输入原因" :disabled="true"/>
                                </el-form-item>
                                <el-form-item label="实际开始时间：" prop="realityStartTime">
                                    <el-date-picker clearable
                                                    v-model="form.realityStartTime"
                                                    type="date"
                                                    value-format="yyyy-MM-dd"
                                                    placeholder="请选择实际开始时间">
                                    </el-date-picker>
                                </el-form-item>
                                <el-form-item label="实际结束时间：" prop="realityEndTime">
                                    <el-date-picker clearable
                                                    v-model="form.realityEndTime"
                                                    type="date"
                                                    value-format="yyyy-MM-dd"
                                                    placeholder="请选择实际结束时间">
                                    </el-date-picker>
                                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitApply">提 交</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import { listUser } from "@/api/system/user";
    import { listApply, alllist, delApply, addApply, updateApply, getApplyByTaskId, examineTask } from "@/api/leaveapply/task";

    export default {
        name: "LeaveApplyTask",
        data() {
            return {
                // 遮罩层
                loading: true,
                // 选中数组
                ids: [],
                businessKeys: [],
                //任务id
                taskId: '',
                taskIds: [],
                leaveType: '',
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
                // 任务数据
                taskList: [],
                // 弹出层标题
                title: "",
                // 是否显示弹出层
                open: false,
                openHr: false,
                openEnd: false,
                // 查询参数
                queryParams: {
                    pageNum: 1,
                    pageSize: 10,
                    isAsc: null,
                    processName: null,
                    taskName: null,
                },
                // 表单参数
                form: {},
                // 表单校验
                rules: {
                },
                userName: '',
                //全部用户数据
                userList: null,
            };
        },
        created() {
            this.getList();
        },
        methods: {
            /** 查询任务列表 */
            getList() {
                this.loading = true;
                alllist(this.queryParams).then(response => {
                    this.taskList = response.rows;
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
                        taskId: null,
                        startTime: null,
                        endTime: null,
                        leaveType: null,
                        reason: null,
                        userId: null,
                        hr: null,
                        deptleaderapprove: null,
                        hrapprove: null,
                        id: null,
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
            },
            /** 修改按钮操作 */
            // handleUpdate(row) {
            //     this.reset();
            //     const id = row.id || this.ids
            //     getApply(id).then(response => {
            //         this.form = response.data;
            //         this.open = true;
            //         this.title = "审批请假";
            //     });
            // },
            handleUpdateByTask(row) {
                this.loading = true;
                this.reset();
                this.form = row;
                const taskId = row.taskId || this.taskIds;
                getApplyByTaskId(taskId).then(response => {
                    console.log(response)
                    this.form = response;
                    this.form.leaveType = response.leaveApply.leaveType;
                    this.form.reason = response.leaveApply.reason;
                    this.form.userId = response.leaveApply.userId;
                    this.form.id = response.leaveApply.id;
                    this.title = "审批请假";
                });
                listUser().then(response => {
                    this.userList = response.rows;
                });
                if(row.taskName == "部门领导审批") {
                    this.open = true;
                } else if(row.taskName == "人事审批") {
                    this.openHr = true;
                } else if(row.taskName == "销假") {
                    this.openEnd = true;
                }
                console.log(this.form)
                this.loading = false;
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
            submitForm2() {
                examineTask(this.form).then(response => {
                    this.$modal.msgSuccess("审批成功");
                    this.open = false;
                    this.openHr = false;
                    this.getList();
                });
            },
            submitApply() {
                updateApply(this.form).then(response => {
                    this.$modal.msgSuccess("修改成功");
                });
                examineTask(this.form).then(response => {
                    this.$modal.msgSuccess("审批成功");
                    this.getList();
                });
                this.openEnd = false;
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
                }, 
                `apply_${new Date().getTime()}.xlsx`)
            }
        }
    };
</script>