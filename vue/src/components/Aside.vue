<template>
    <div>
        <el-menu
                style="width: 200px;min-height: calc(100vh - 50px);"
                :default-active="isStu ? 'stuUser':'mytest'"
                router
                class="el-menu-vertical-demo">
            <el-submenu index="1">
                <template slot="title">
                    <i class="el-icon-menu"></i>
                    <span>功能列表</span>
                </template>
                <el-menu-item-group v-if="isStu">
                    <el-menu-item index="stuUser" >学生管理</el-menu-item>
                    <el-menu-item index="test" >考试管理</el-menu-item>
                    <el-menu-item index="stutest" >学生成绩</el-menu-item>
                </el-menu-item-group>
                <el-menu-item-group v-if="!isStu">
                    <el-menu-item index="mytest" >我的考试</el-menu-item>
                    <el-menu-item index="myscore" >考试成绩</el-menu-item>
                </el-menu-item-group>
            </el-submenu>
        </el-menu>
    </div>
</template>

<script>
    export default {
        name: "Aside",
        created() {
            this.confirmIdentity();
        },
        data (){
            return{
                isStu:true
            }
        },
        methods:{
            confirmIdentity (){
                const userJson = sessionStorage.getItem("user");
                if (userJson != null) {
                    let userIdentity = JSON.parse(userJson).identity;
                    if(userIdentity === '学生'){
                        this.isStu = false;
                    }else{
                        this.isStu = true;
                    }
                }

            }
        }
    }
</script>

<style scoped>

</style>