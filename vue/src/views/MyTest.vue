<template>
    <div>
        <div class="home" style="padding: 10px">
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
                        prop="id"
                        label="考试编号"
                        sortable>
                </el-table-column>
                <el-table-column
                        prop="username"
                        label="教师编号">
                </el-table-column>
                <el-table-column
                        prop="name"
                        label="出卷老师">
                </el-table-column>
                <el-table-column
                        prop="project"
                        label="考试名">
                </el-table-column>
                <el-table-column
                        prop="single"
                        label="单选">
                </el-table-column>
                <el-table-column
                        prop="multiple"
                        label="多选">
                </el-table-column>
                <el-table-column
                        prop="judge"
                        label="判断">
                </el-table-column>
                <el-table-column
                        prop="time"
                        label="考试时长/小时">
                </el-table-column>
                <el-table-column
                        prop="start"
                        label="发布时间">
                </el-table-column>
                <el-table-column
                        fixed="right"
                        label="操作"
                        width="200">
                    <template slot-scope="scope">
                        <!--开始考试-->
                        <el-button size="mini" type="primary" plain @click="start(scope.$index)" >开始考试</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>

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
</template>

<script>
    import request from '../utils/request'
    export default {
        name: "MyTest",
        created() {
            this.load()
        },
        data (){
            return {
                tableData:[],
                canDo:[],
                tested:[],
                search:'',
                currentPage:1,
                pageSize:10,
                total:0
            }
        },
        methods:{
            load (){
                const userJson = sessionStorage.getItem("user");
                let user = JSON.parse(userJson).username;
                request.get("/test/stu/"+user,{
                    params: {
                        pageNum: this.currentPage,
                        pageSize: this.pageSize,
                        search: this.search
                    }
                }).then( res => {
                    //console.log(res);
                    this.tableData = res.data.records;
                    this.pageSize = res.data.size;
                    this.total = res.data.total;

                    //console.log(this.tableData);
                });

            },
            start (index){
                sessionStorage.setItem('testRequest',JSON.stringify(this.tableData[index]));
                this.$router.push("/testList");
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