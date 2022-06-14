<template>
    <div class="app-container">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
                        <el-form-item label="流程名称：" prop="name">
                            <el-input
                                    v-model="queryParams.name"
                                    placeholder="请输入流程名称"
                                    clearable
                                    @keyup.enter.native="handleQuery"
                            />
                        </el-form-item>
                        <el-form-item label="业务号：" prop="bussinesskey">
                            <el-input
                                    v-model="queryParams.bussinesskey"
                                    placeholder="请输入业务号"
                                    clearable
                                    @keyup.enter.native="handleQuery"
                            />
                        </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>

        <el-table v-loading="loading" :data="processList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
                    <el-table-column label="流程实例编号" align="center" prop="processInstanceId" />
                    <el-table-column label="业务号" align="center" prop="businessKey" />
                    <el-table-column label="流程名称" align="center" prop="name" />
                    <el-table-column label="当前节点" align="center" prop="currentTask" />
                    <el-table-column label="当前处理人" align="center" prop="assignee" />
                    <el-table-column label="是否挂起" align="center" prop="suspended" />
                    <el-table-column label="起始时间" align="center" prop="startTime" width="180">
                        <template slot-scope="scope">
                            <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="发起人" align="center" prop="startUserId" />
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                    <el-button
                            size="mini"
                            type="text"
                            icon="el-icon-edit"
                            @click="handleLink(scope.row)"
                    >流程进度</el-button>
                    <el-button
                            size="mini"
                            type="text"
                            icon="el-icon-delete"
                            @click="hangup(scope.row)"
                            v-if="scope.row.suspended"
                    >挂起</el-button>
                    <el-button
                            size="mini"
                            type="text"
                            icon="el-icon-delete"
                            @click="rouse(scope.row)"
                            v-if="!scope.row.suspended"
                    >唤醒</el-button>
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

    </div>
</template>

<script>
    import { getUserProfile, listUser } from "@/api/system/user";
    // import { listApply, getApply, delApply, addApply, updateApply } from "@/api/leaveapply/apply";
    import { listApply, getApply, delApply, addApply, updateApply, listProcess, redirection, hangupProcess, rouseProcess } from "@/api/leaveapply/monitor/instance";


    export default {
        name: "LeaveApplyInstance",
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
                // 流程实例表格数据
                processList: [],
                // 弹出层标题
                title: "",
                // 是否显示弹出层
                open: false,
                // 查询参数
                queryParams: {
                    pageNum: 1,
                    pageSize: 10,
                    name: null,
                    bussinesskey: null,
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
            // this.getList();
            this.getProcessList();
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
            // 查询流程实例列表
            getProcessList() {
                this.loading = true;
                listProcess(this.queryParams).then(response => {
                    this.processList = response.rows;
                    this.total = response.total;
                    this.loading = false;
                });
            },
            // 跳转
            handleLink(row) {
                console.log("/flow/monitor/traceProcess/" + row.processInstanceId)
                this.$router.replace("/flow/monitor/traceProcess/" + row.processInstanceId)
            },
            hangup(row) {
                hangupProcess(row.processInstanceId).then(response => {
                    this.getProcessList();
                    this.$modal.msgSuccess("挂起成功");
                });
            },
            rouse(row) {
                rouseProcess(row.processInstanceId).then(response => {
                    this.getProcessList();
                    this.$modal.msgSuccess("唤醒成功");
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