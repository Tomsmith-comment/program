<template>
    <el-row :gutter="50">
        <el-col :span="12">
            <el-form>
                <el-form-item>
                    <el-button type="primary" plain v-click-down @click="stopF5Refresh">防刷新已开启</el-button>
                </el-form-item>
            </el-form>
        </el-col>
        <el-col :span="12"></el-col>
    </el-row>
</template>

<script>
    export default {
        name: "StopF5",
        data() {
            return {

            }
        },
        directives:{
            clickDown:{
                inserted(el){
                    el.click()
                }
            }
        },
        methods:{
            stopF5Refresh(){
                document.onkeydown = function (e){
                    let evt = window.event || e;
                    let code = evt.keyCode || evt.which;
                    if(code == 116){
                        if(evt.preventDefault){
                            evt.preventDefault();
                        }else{
                            evt.keyCode = 0;
                            evt.returnValue = false;
                        }
                    }
                }


            }
        }
    }
</script>

<style scoped>

</style>