<template>
    <div>
        <!--头部-->
        <Topper :user="user"/>
        <!--主体-->
        <div style="display: flex">
            <!--侧边栏-->
            <Aside/>
            <!--内容区域-->
            <router-view style="flex: 1;" @userInfo="refreshUser"/>
        </div>
    </div>
</template>

<script>
    import Aside from "../components/Aside";
    import Topper from "../components/Topper";
    import request from "../utils/request";

    export default {
        name: "Layout.vue",
        data (){
           return {
                user:{}
           }
        },
        components:{
            Topper,
            Aside
        },
        created() {
            this.refreshUser()
        },
        methods: {
            refreshUser() {
                let userJson = sessionStorage.getItem("user");
                if (!userJson) {
                    return
                }
                let userId = JSON.parse(userJson).id;
                // 从后台取出更新后的最新用户信息
                request.get("/user/" + userId).then(res => {
                    this.user = res.data
                })
            }
        }
    }
</script>

<style scoped>

</style>