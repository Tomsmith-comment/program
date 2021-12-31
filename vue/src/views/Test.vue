<template>
    <div class="home" style="padding: 10px" >
        <!--功能栏-->
        <div>
            <el-button type="primary" @click="addQuestion">添加试题</el-button>
            <el-button type="primary" @click="addTest">添加考试</el-button>
        </div>

        <!--查询栏-->
        <div style="margin: 10px 0;">
            <el-input v-model="search" placeholder="请输入试卷名" style="width: 20%;" clearable></el-input>
            <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
        </div>

        <el-table
                :data="tableData"
                border
                stripe
                style="width: 100%">
            <el-table-column
                    prop="id"
                    label="考试编号">
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
                    label="考试状态"
                    width="200px">
                <el-switch
                        slot-scope="scope"
                        @change="changeState(scope.$index)"
                        v-model="open[scope.$index]"
                        active-color="#13ce66"
                        inactive-color="#ff4949"
                        active-text="开始"
                        inactive-text="结束">
                </el-switch>
            </el-table-column>
            <el-table-column
                    fixed="right"
                    label="操作"
                    width="200px">
                <template slot-scope="scope">
                    <div style="">
                        <el-button size="mini" @click="handleEdit(scope.row,scope.$index)"  >编辑</el-button>
                        <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.row.id)">
                            <el-button slot="reference" size="mini" type="danger" style="margin-left: 10px">删除</el-button>
                        </el-popconfirm>
                    </div>
                </template>

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


        <!--添加试题-->
        <el-dialog title="添加试题" :visible.sync="dialogVisible1" width="40%">
            <el-form :model="test" label-width="80px" >
                <!--选项菜单-->
                <div style="text-align: center">
                    <el-radio-group  v-model="typeChoose" size="normal" @change="choose">
                        <el-radio-button label="单选"></el-radio-button>
                        <el-radio-button label="多选"></el-radio-button>
                        <el-radio-button label="判断"></el-radio-button>
                    </el-radio-group>
                </div>

                <el-form-item label="题目" style="padding-top: 10px">
                    <el-input v-model="test.question" class="addForm"></el-input>
                </el-form-item>
                <div v-if="!isJudge">
                    <el-form-item label="A">
                        <el-input v-model="test.optionA" class="addForm"></el-input>
                    </el-form-item>
                    <el-form-item label="B">
                        <el-input v-model="test.optionB" class="addForm"></el-input>
                    </el-form-item>
                    <el-form-item label="C">
                        <el-input v-model="test.optionC" class="addForm"></el-input>
                    </el-form-item>
                    <el-form-item label="D">
                        <el-input v-model="test.optionD" class="addForm"></el-input>
                    </el-form-item>
                    <el-form-item label="答案">
                        <el-input v-model="test.answer" class="addForm"></el-input>
                    </el-form-item>
                </div>
                <div v-if="isJudge">
                    <el-form-item label="答案">
                        <el-radio-group v-model="test.answer">
                            <el-radio label="对"></el-radio>
                            <el-radio label="错"></el-radio>
                        </el-radio-group>
                    </el-form-item>
                </div>


            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible1 = false; test = {}">取 消</el-button>
                <el-button type="primary" @click="saveQuestion">确 定</el-button>
            </span>
        </el-dialog>

        <!--添加考试-->
        <el-dialog title="添加考试" :visible.sync="dialogVisible2" width="20%">
            <el-form :model="msg" label-width="80px" >
                <el-form-item label="出卷人">
                    <el-input v-model="msg.name" class="addForm" disabled></el-input>
                </el-form-item>
                <el-form-item label="考试标题">
                    <el-input v-model="msg.project" class="addForm"></el-input>
                </el-form-item>
                <el-form-item label="单选个数">
                    <el-input v-model="msg.single" class="addForm"></el-input>
                </el-form-item>
                <el-form-item label="多选个数">
                    <el-input v-model="msg.multiple" class="addForm"></el-input>
                </el-form-item>
                <el-form-item label="判断个数">
                    <el-input v-model="msg.judge" class="addForm"></el-input>
                </el-form-item>
                <el-form-item label="考试时长">
                    <el-input v-model="msg.time" class="addForm"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible2 = false; msg = {}">取 消</el-button>
                <el-button type="primary" @click="saveTest">确 定</el-button>
            </span>
        </el-dialog>

        <!--考试信息修改和更新-->
        <el-dialog title="考试信息编辑" :visible.sync="dialogVisible" width="25%">
            <el-form :model="form" label-width="100px" >
                <el-form-item label="教师编号">
                    <el-input v-model="form.username" class="addForm" disabled></el-input>
                </el-form-item>
                <el-form-item label="出卷人">
                    <el-input v-model="form.name" class="addForm" disabled></el-input>
                </el-form-item>
                <el-form-item label="考试标题">
                    <el-input v-model="form.project" class="addForm"></el-input>
                </el-form-item>
                <el-form-item label="单选">
                    <el-input v-model="form.single" class="addForm"></el-input>
                </el-form-item>
                <el-form-item label="多选">
                    <el-input v-model="form.multiple" class="addForm"></el-input>
                </el-form-item>
                <el-form-item label="判断">
                    <el-input v-model="form.judge" class="addForm"></el-input>
                </el-form-item>
                <el-form-item label="考试时长/小时">
                    <el-input v-model="form.time" class="addForm"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateTestMessage">确 定</el-button>
      </span>
        </el-dialog>

    </div>
</template>

<script>
    import request from '../utils/request'
    export default {
        name: "test",
        data (){
            return {
                test:{},
                msg:{},
                form:{},
                isJudge:false,
                typeChoose:'单选',
                dialogVisible1:false,
                dialogVisible2:false,
                dialogVisible:false,
                search:'',
                currentPage:1,
                pageSize:10,
                total:0,
                tableData:[],
                open:[]
            }
        },
        created (){
            this.load();
            let str = sessionStorage.getItem("user") || "{}";
            console.log(str);
            this.msg.name = JSON.parse(str).name;
            this.msg.username = JSON.parse(str).username;
        },
        methods:{
            load (){
                const username = JSON.parse(sessionStorage.getItem("user") || "{}").username;
                request.get("/test/teacher/"+username,{
                    params: {
                        pageNum: this.currentPage,
                        pageSize: this.pageSize,
                        search: this.search
                    }
                }).then( res => {
                    this.tableData = res.data.records;
                    this.pageSize = res.data.size;
                    this.total = res.data.total;
                    console.log(this.tableData);
                    for(let i=0;i<this.tableData.length;i++){
                        if(this.tableData[i].flag === 0){
                            this.open[i] = false;
                        }else{
                            this.open[i] = true;
                        }

                    }
                })
            },
            saveQuestion (){
                if(this.typeChoose === '单选'){
                    request.post("/single",this.test).then(res => {
                        window.console.log(res);
                        if (res.code === '0') {
                            this.test={};
                            this.$message({
                                type: "success",
                                message: "添加成功"
                            })
                        } else {
                            this.$message({
                                type: "error",
                                message: "添加失败"
                            })
                        }
                    });
                }
                else if(this.typeChoose === '判断'){
                    request.post("/judge",this.test).then(res => {
                        window.console.log(res);
                        if (res.code === '0') {
                            this.test={};
                            this.$message({
                                type: "success",
                                message: "添加成功"
                            })
                        } else {
                            this.$message({
                                type: "error",
                                message: "添加失败"
                            })
                        }
                    });
                }
                else{      //添加多选
                    request.post("/multiple",this.test).then(res => {
                        window.console.log(res);
                        if (res.code === '0') {
                            this.test={};
                            this.$message({
                                type: "success",
                                message: "添加成功"
                            })
                        } else {
                            this.$message({
                                type: "error",
                                message: "添加失败"
                            })
                        }
                    });
                }
                this.test = {};
                this.dialogVisible1 = false;
            },
            saveTest(){
                request.post('/test',this.msg).then( res => {
                    console.log(res);
                    if (res.code === '0') {
                        this.$message({
                            type: "success",
                            message: "添加成功"
                        });
                        this.load();
                    } else {
                        this.$message({
                            type: "error",
                            message: "添加失败"
                        })
                    }
                });
                this.dialogVisible2 = false;

            },
            choose (){
                if(this.typeChoose === '判断'){
                    this.isJudge = true;
                }else{
                    this.isJudge = false;
                }
                this.test = {};

            },
            addQuestion(){
                this.dialogVisible1 = true;
            },
            addTest (){
                this.dialogVisible2 = true;
            },
            handleSizeChange (pageSize){
                this.pageSize = pageSize;
                this.load();
            },
            handleCurrentChange (pageNum){
                this.currentPage = pageNum;
                this.load();
            },
            handleEdit (row,index){
                // 深拷贝，与表单数据对象无关
                this.form = JSON.parse(JSON.stringify(row));
                if(this.tableData[index].flag === 1){
                    const datetime = this.getTime();
                    this.form.start = datetime;
                }
                this.dialogVisible = true;
            },
            changeState(index){
                //console.log(this.open[index]);
                const result = this.open[index] === true ? 1:0;
                //console.log(result);
                this.form = this.tableData[index];
                this.form.flag = result;
                if(this.form.flag === 1){
                    const datetime = this.getTime();
                    this.form.start = datetime;
                }
                this.updateTestMessage();
            },
            updateTestMessage (){
                request.put('/test',this.form).then( res => {
                    if(res.code === '0'){
                        this.$message({
                            type:"success",
                            message:"更新成功"
                        })
                    }else{       //添加
                        this.$message({
                            type:"error",
                            message:"更新失败"
                        })
                    }
                    this.form = {};
                    this.dialogVisible = false;
                    this.load();
                })
            },
            handleDelete (id){
                request.delete("/test/"+id).then( res => {
                    if(res.code === '0'){
                        this.$message({
                            type:"success",
                            message:"删除成功"
                        })
                    }else{
                        this.$message({
                            type:"error",
                            message: res.msg
                        })
                    }
                    this.load();
                })
            },
            getTime(){
                let date=new Date();
                let year=date.getFullYear();
                /* 在日期格式中，月份是从0开始的，因此要加0
                 * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
                 * */
                let month= date.getMonth()+1<10 ? "0"+(date.getMonth()+1) : date.getMonth()+1;
                let day=date.getDate()<10 ? "0"+date.getDate() : date.getDate();
                let hours=date.getHours()<10 ? "0"+date.getHours() : date.getHours();
                let minutes=date.getMinutes()<10 ? "0"+date.getMinutes() : date.getMinutes();
                let seconds=date.getSeconds()<10 ? "0"+date.getSeconds() : date.getSeconds();
                // 拼接
                return year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;

            }

        }
    }
</script>

<style scoped>
    .addForm{
        width: 100%;

    }
</style>