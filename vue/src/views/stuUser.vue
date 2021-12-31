<template>

  <div class="home" style="padding: 10px" v-if="isStu">
    <!--添加栏-->
    <div style="margin: 10px 0;">
      <el-button type="primary" @click="add">添加</el-button>
    </div>

    <!--查询栏-->
    <div style="margin: 10px 0;">
      <el-input v-model="searchName" placeholder="请输入关键字" style="width: 20%;" clearable></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div>

    <el-table
            :data="tableData"
            border
            stripe
            style="width: 100%">
      <el-table-column
              prop="id"
              label="ID"
              sortable>
      </el-table-column>
      <el-table-column
              prop="username"
              label="学号">
      </el-table-column>
      <el-table-column
              prop="name"
              label="姓名">
      </el-table-column>
      <el-table-column
              prop="age"
              label="年龄">
      </el-table-column>
      <el-table-column
              prop="sex"
              label="性别">
      </el-table-column>
      <el-table-column
              fixed="right"
              label="操作"
              width="200">
        <template slot-scope="scope">
          <!--编辑-->
          <el-button size="mini" @click="handleEdit(scope.row)"  >编辑</el-button>
          <!--删除-->
          <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.row.id)">
            <el-button slot="reference" size="mini" type="danger" style="margin-left: 10px">删除</el-button>
          </el-popconfirm>
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

    <el-dialog title="学生信息编辑" :visible.sync="dialogVisible" width="25%">
      <el-form :model="form" label-width="100px" >
        <el-form-item label="学号">
          <el-input v-model="form.username" class="addForm"></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="form.name" class="addForm"></el-input>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="form.age" class="addForm"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio v-model="form.sex" label="男" class="addForm">男</el-radio>
          <el-radio v-model="form.sex" label="女" style="margin-left: -70px">女</el-radio>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
  import request from "../utils/request.js";

  export default {
    name: "stuUser",
    components: {

    },
    data(){
      return {
        form:{},
        searchName:'',
        currentPage:1,
        pageSize:10,
        total:10,
        isStu:true,
        dialogVisible:false,
        tableData:[

        ]
      }
    },
    created (){
      this.load();
      this.confirmIdentity();
    },
    methods: {
      load (){
        request.get("/user",{
          params:{
            pageNum:this.currentPage,
            pageSize:this.pageSize,
            searchName:this.searchName
          }
        }).then(res => {
          //window.console.log(res);
          this.tableData = res.data.records;
          this.pageSize = res.data.size;
          this.total = res.data.total;

        })
      },
      confirmIdentity (){
        const userJson = sessionStorage.getItem("user");
        if(userJson != null){
          let userIdentity = JSON.parse(userJson).identity;
          if(userIdentity === '学生'){
            this.isStu = false;
          }else{
            this.isStu = true;
          }
        }

      },
      add (){
        this.dialogVisible = true;
        this.form = {}
      },
      save (){
        if(this.form.id){  //更新
          request.put("/user",this.form).then(res =>{
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
            this.load();
            this.dialogVisible = false;
          });
        }
        else{
          request.post("/user",this.form).then(res =>{
            if(res.code === '0'){
              this.$message({
                type:"success",
                message:"添加成功"
              })
            }else{
              this.$message({
                type:"error",
                message:"添加失败"
              })
            }
            this.load();
            this.dialogVisible = false;
          });
        }
      },
      handleEdit (row){
        // 深拷贝，与表单数据对象无关
        this.form = JSON.parse(JSON.stringify(row));
        this.dialogVisible = true;
      },
      handleSizeChange (pageSize){
        this.pageSize = pageSize;
        this.load();
      },
      handleCurrentChange (pageNum){
        this.currentPage = pageNum;
        this.load();
      },
      handleDelete (id){
        request.delete("/user/"+id).then( res => {
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
      }
    }
  };
</script>

<style>
  .addForm{
    width: 70%;

  }
</style>
