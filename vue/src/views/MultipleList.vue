<template>
    <div v-if="multiples.length !== 0"style="padding: 15px">
        <p>二、多选题（每题一分，共{{multiples.length}}分)</p>
        <el-form :model="multipleForm"  ref="singleForm"  style="padding-top: 15px">
            <template v-for="(multiple,index) in multiples" >
                <p style="font-family: 微软雅黑;font-size: 15px">{{index+1}}.{{multiple.question}}</p>
                <el-form-item>
                    <el-checkbox-group v-model="multipleForm.answer[index]" style="margin: 15px;">
                        <el-checkbox label="A">A.&nbsp{{multiple.optionA}}</el-checkbox><br>
                        <el-checkbox label="B">B.&nbsp{{multiple.optionB}}</el-checkbox><br>
                        <el-checkbox label="C">C.&nbsp{{multiple.optionC}}</el-checkbox><br>
                        <el-checkbox label="D">D.&nbsp{{multiple.optionD}}</el-checkbox><br>
                    </el-checkbox-group>
                </el-form-item>


            </template>
        </el-form>
    </div>
</template>

<script>
    export default {
        name: "MultipleList",
        props:['multiples'],
        data (){
            return {
                multipleForm:{
                    answer:[

                    ]
                }
            }
        },
        mounted() {
            this.init();
        },
        methods:{
            init (){
                let num = JSON.parse(sessionStorage.getItem('testRequest')).multiple;
                for (let i = 0; i < num; i++) {
                    this.multipleForm.answer[i] = [];
                }
            }
        }
    }
</script>

<style scoped>

</style>