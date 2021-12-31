<template>
    <div id="topper">
        <div id="head-left" style="width: 180px;">在线考试系统</div>
        <div style="flex:1"></div>
        <div style="width: 100px;">
            <el-dropdown @command = "handle">
              <span class="el-dropdown-link">
                {{ user.name }}
                  <i class="el-icon-arrow-down el-icon--right"></i>
              </span>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="a">个人信息</el-dropdown-item>
                    <el-dropdown-item command="b">退出系统</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
    </div>
</template>

<script>
    export default {
        name: "topper",
        props:['user'],
        methods:{
            handle (command){
                if(command === 'a'){
                    this.$router.push('/person')
                }
                if(command === 'b'){
                    this.$router.push("/login")
                }
            }
        }
    }
</script>

<style scoped>
    #topper{
        height: 50px;
        line-height: 50px;
        border-bottom: 1px solid #ccc;
        display: flex;
    }
    #head-left{
        width: 200px;
        padding-left: 30px;
        font-weight: bold;
        color: dodgerblue;
    }
</style>