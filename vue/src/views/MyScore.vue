<template>
    <div>
        <div class="home" style="padding: 10px" v-if="isStu">

        <!--查询栏-->
        <div style="margin: 10px 0;">
            <el-input v-model="search" placeholder="请输入考试号" style="width: 20%;" clearable></el-input>
            <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
        </div>

        <el-table
                :data="tableData"
                border
                stripe
                style="width: 100%">
            <el-table-column
                    prop="username"
                    label="学号">
            </el-table-column>
            <el-table-column
                    prop="testname"
                    label="姓名">
            </el-table-column>
            <el-table-column
                    prop="testId"
                    label="考试号"
                    sortable>
            </el-table-column>
            <el-table-column
                    prop="test"
                    label="考试名">
            </el-table-column>
            <el-table-column
                    prop="score"
                    label="考试成绩">
            </el-table-column>
            <el-table-column
                    prop="date"
                    label="交卷时间">
            </el-table-column>
        </el-table>

        <div style="margin: 10px 0;">
            <!--size-change	pageSize 改变时会触发	每页条数-->
            <!--current-change	currentPage 改变时会触发	当前页-->
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-sizes="[5, 10, 20]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
        </div>
    </div>
    </div>
</template>

<script>
    import request from '../utils/request'
    export default {
        name: "MyScore",
        data(){
            return {
                form:{},
                search:'',
                currentPage:1,
                pageSize:10,
                total:0,
                isStu:true,
                dialogVisible:false,
                tableData:[

                ]
            }
        },
        created (){
            this.load();

        },
        methods: {
            load() {
                const userJson = sessionStorage.getItem("user");
                let user = JSON.parse(userJson).username;
                request.get("/testmessage/"+user, {
                    params: {
                        pageNum: this.currentPage,
                        pageSize: this.pageSize,
                        search: this.search
                    }
                }).then(res => {
                    window.console.log(res);
                    this.tableData = res.data.records;
                    this.pageSize = res.data.size;
                    this.total = res.data.total;

                })
            },
            handleSizeChange (pageSize){
                this.pageSize = pageSize;
                this.load();
            },
            handleCurrentChange (pageNum){
                this.currentPage = pageNum;
                this.load();
            }

        }
    }
</script>

<style scoped>

</style>