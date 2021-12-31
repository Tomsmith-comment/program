<template>
    <div v-if="singles.length !== 0" style="padding: 15px">
        <p>一、单选题（每题一分，共{{singles.length}}分)</p>
        <el-form :model="singleForm"  ref="singleForm" style="padding-top: 15px" >
            <template v-for="(single,index) in singles" >
                <p style="font-family: 微软雅黑;font-size: 15px">{{index+1}}.{{single.question}}</p>
                    <el-form-item>
                        <el-radio-group v-model="singleForm.answer[index]" style="margin: 15px;">
                            <el-radio label="A">A.&nbsp{{single.optionA}}</el-radio><br>
                            <el-radio label="B">B.&nbsp{{single.optionB}}</el-radio><br>
                            <el-radio label="C">C.&nbsp{{single.optionC}}</el-radio><br>
                            <el-radio label="D">D.&nbsp{{single.optionD}}</el-radio><br>
                        </el-radio-group>
                    </el-form-item>


            </template>
        </el-form>
    </div>
</template>

<script>
    export default {
        name: "SingleList",
        components:{

        },
        props:[
            'singles'
        ],
        mounted (){
            this.init();
        },
        data (){
            return {
                singleForm:{
                    answer:[

                    ]
                },
            }
        },
        methods:{
            init (){
                let num = JSON.parse(sessionStorage.getItem('testRequest')).single;
                for (let i = 0; i < num; i++) {
                    this.singleForm.answer[i] = '';
                }

            },

        }
    }
</script>

<style scoped>

</style>