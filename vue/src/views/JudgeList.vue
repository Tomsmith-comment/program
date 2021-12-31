<template>
    <div v-if="judges.length !== 0" style="padding: 15px">
        <p>三、判断题（每题一分，共{{judges.length}}分)</p>
        <el-form :model="judgeForm"  ref="singleForm" style="padding-top: 15px" >
            <template v-for="(judge,index) in judges" >
                <p style="font-family: 微软雅黑;font-size: 15px">{{index+1}}.{{judge.question}}</p>
                <el-form-item>
                    <el-radio-group v-model="judgeForm.answer[index]" style="margin: 15px;">
                        <el-radio label="对">A.&nbsp{{judge.optionA}}</el-radio><br>
                        <el-radio label="错">B.&nbsp{{judge.optionB}}</el-radio><br>
                    </el-radio-group>
                </el-form-item>
            </template>
        </el-form>
    </div>
</template>

<script>
    export default {
        name: "JudgeList",
        props:['judges'],
        mounted() {
            this.init();
        },
        data (){
            return{
                judgeForm:{
                    answer:[]
                }
            }
        },
        methods:{
            init (){
                let num = JSON.parse(sessionStorage.getItem('testRequest')).judge;
                for (let i = 0; i < num; i++) {
                    this.judgeForm.answer[i] = '';
                }
            }
        }
    }
</script>

<style scoped>

</style>