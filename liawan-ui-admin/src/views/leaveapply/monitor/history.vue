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

        <el-table v-loading="loading" :data="historyList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
                    <el-table-column label="流程实例编号" align="center" prop="processInstanceId" />
                    <el-table-column label="业务号" align="center" prop="businessKey" />
                    <el-table-column label="流程名称" align="center" prop="name" />
                    <el-table-column label="当前节点" align="center" prop="currentTask" />
                    <el-table-column label="当前处理人" align="center" prop="assignee" />
                    <el-table-column label="是否结束" align="center" prop="ended" />
                    <el-table-column label="开始时间" align="center" prop="startTime" width="180">
                        <template slot-scope="scope">
                            <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="结束时间" align="center" prop="endTime" width="180">
                        <template slot-scope="scope">
                            <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="发起人" align="center" prop="startUserId" />
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                    <el-button
                            size="mini"
                            type="text"
                            icon="el-icon-edit"
                            @click="getRunHistory(scope.row)"
                    >查看历史</el-button>
                    <el-button
                            size="mini"
                            type="text"
                            icon="el-icon-delete"
                            @click="getVariables(scope.row)"
                    >查看变量</el-button>
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

        <!-- 查看历史对话框 -->
        <el-dialog v-loading="loading" :title="title" :visible.sync="openRunHistory" width="500px" append-to-body>
            <el-table v-loading="loading" :data="runHistoryList">
                <el-table-column type="selection" width="55" align="center" />
                        <el-table-column label="活动名称" align="center" prop="activityName" />
                        <el-table-column label="办理人" align="center" prop="assignee" />
                        <el-table-column label="开始时间" align="center" prop="startTime" width="180">
                            <template slot-scope="scope">
                                <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="结束时间" align="center" prop="endTime" width="180">
                            <template slot-scope="scope">
                                <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="发起人" align="center" prop="startUserId" />
            </el-table>
        </el-dialog>

        <!-- 查看变量对话框 -->
        <el-dialog v-loading="loading" :title="title" :visible.sync="openVariables" width="500px" append-to-body>
            <el-table v-loading="loading" :data="variablesList">
                <el-table-column type="selection" width="55" align="center" />
                        <el-table-column label="变量名" align="center" prop="name" />
                        <el-table-column label="变量类型" align="center" prop="typeTemp" />
                        <el-table-column label="变量值" align="center" prop="cachedValue" />
                        <el-table-column label="创建时间" align="center" prop="createTime" width="180">
                            <template slot-scope="scope">
                                <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="最后修改时间" align="center" prop="lastUpdatedTime" width="180">
                            <template slot-scope="scope">
                                <span>{{ parseTime(scope.row.lastUpdatedTime, '{y}-{m}-{d}') }}</span>
                            </template>
                        </el-table-column>
            </el-table>
        </el-dialog>

    </div>
</template>

<script>
    import { getUserProfile, listUser } from "@/api/system/user";
    // import { listApply, getApply, delApply, addApply, updateApply } from "@/api/leaveapply/apply";
    import { listApply, getApply, delApply, addApply, updateApply, listHistory, redirection, listRunHistory, listVariables } from "@/api/leaveapply/monitor/history";


    export default {
        name: "LeaveApplyHistory",
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
                historyList: [],
                runHistoryList: [],
                variablesList: [],
                // 流程实例表格数据
                processList: [],
                // 弹出层标题
                title: "",
                // 是否显示弹出层
                open: false,
                openRunHistory: false,
                openVariables: false,
                // 查询参数
                queryParams: {
                    pageNum: 1,
                    pageSize: 10,
                    name: null,
                    bussinesskey: null,
                    processInstanceId: null,
                },
                // 表单参数
                form: {},
                // 表单校验
                rules: {
                },
            };
        },
        created() {
            // this.getList();
            this.getHistory();
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
            // 查询历史列表
            getHistory() {
                this.loading = true;
                listHistory(this.queryParams).then(response => {
                    this.historyList = response.rows;
                    this.total = response.total;
                    this.loading = false;
                });
            },
            // 查询运行实例历史列表
            getRunHistory(row) {
                this.loading = true;
                this.queryParams.processInstanceId = row.processInstanceId;
                listRunHistory(this.queryParams).then(response => {
                    this.runHistoryList = response.rows;
                    console.log(this.runHistoryList)
                    this.total = response.total;
                    this.loading = false;
                    this.openRunHistory = true;
                });
            },
            // 查询变量列表
            getVariables(row) {
                this.loading = true;
                this.queryParams.processInstanceId = row.processInstanceId;
                listVariables(this.queryParams).then(response => {
                    this.variablesList = response.rows;
                    this.total = response.total;
                    this.loading = false;
                    this.openVariables = true;
                });
            },
            // 跳转
            handleLink(row) {
                redirection(row.processInstanceId);
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
        }
    };
</script>