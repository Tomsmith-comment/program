<template>
    <div style="width: 100%; height: 100vh;overflow: hidden;background-color: dodgerblue">
        <div style="margin: 120px auto; width: 400px;">
            <div style="color: white;font-size: 30px;text-align: center;padding: 30px 0">欢迎登录</div>
            <el-form ref="form" :model="form" :rules="rules" size="normal" >
                <el-form-item prop="username">
                    <el-input prefix-icon="el-icon-s-custom" v-model="form.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input prefix-icon="el-icon-lock" v-model="form.password" show-password></el-input>
                </el-form-item>
                <el-form-item style="width: 100%" prop="identity">
                    <el-select v-model="form.identity" placeholder="请选择身份" style="width: 100%">
                        <el-option label="学生" value="学生"></el-option>
                        <el-option label="教师" value="教师"></el-option>
                    </el-select>
                </el-form-item>
                <el-button style="width: 100%" type="primary" @click="login">登录</el-button>
            </el-form>
                <el-button style="width: 100%;margin-top: 10px" type="primary" plain @click="register">去注册</el-button>
        </div>
    </div>
</template>

<script>
    import request from "../utils/request"
    export default {
        name: "Login.vue",
        data (){
            return {
                form:{},
                 rules: {
                     username: [
                         {required: true, message: '请输入用户名', trigger: 'blur'}
                     ],
                     password: [
                         {required: true, message: '请输入密码', trigger: 'blur'}
                     ],
                     identity: [
                         {required: true, message: '请选择身份', trigger: 'blur'}
                     ]
                 }
            }
        },
        methods: {
            login (){
                this.$refs['form'].validate((valid) => {
                    if (valid) {
                        request.post("/user/login",this.form).then(res => {
                            if(res.code === '0') {
                                this.$message({
                                    type: "success",
                                    message: "登录成功"
                                });
                                sessionStorage.setItem("user",JSON.stringify(res.data));
                                this.$router.push("/") //登陆成功后进行页面跳转，跳转到主页
                            }
                            else {
                                this.$message({
                                    type: "error",
                                    message: res.msg
                                })
                            }
                        })
                    }
                })

            },
            register (){
                this.$router.push('/register');
            }
        }
    }
</script>

<style scoped>

</style>