<template>
    <div id='home'style="width: 100%; overflow: hidden;background-color: #409EFF;">
        <div style="background-color: white;width: 900px;height: auto;margin: auto">
            <!--倒计时-->
            <div id="time">
                <Time :time="time"
                      :single="this.testMessage.single"
                      :multiple = "this.testMessage.multiple"
                      :judge = 'this.testMessage.judge'></Time>
            </div>

            <h1 style="text-align: center">{{testMessage.project}}</h1>
            <!--单选-->
            <SingleList :singles="singles" ref="singleScore"/>
            <!--多选-->
            <MultipleList :multiples="multiples" ref="multipleScore"></MultipleList>
            <!--判断-->
            <JudgeList :judges="judges" ref="judgeScore"></JudgeList>
            <!--提交-->
            <div style="text-align: center;padding: 40px">
                <el-popconfirm title="确定交卷吗？" @confirm="getScore">
                    <el-button slot="reference" size="mini" type="primary" plain >提交</el-button>
                </el-popconfirm>
            </div>



        </div>

    </div>
</template>

<script>
    import request from '../utils/request'
    import Time from './Time'
    import SingleList from './SingleList'
    import MultipleList from './MultipleList'
    import JudgeList from './JudgeList'

    export default {
        name: "TestList",
        components: {
            MultipleList,
            JudgeList,
            Time,
            SingleList,
        },
        data (){
            return {
                testMessage:{},
                time:0,
                singles:[],
                multiples:[],
                judges:[],
                score:0
            }
        },
        created() {
            this.load();
        },
        computed: {
            isNotice() {
                return this.$store.state.examFinish;
            }
        },
        watch: {
            isNotice(){
                this.getScore();
            }
        },
        methods:{
            load () {
                let testmessage = sessionStorage.getItem('testRequest');
                this.testMessage = JSON.parse(testmessage);
                this.time += this.testMessage.time;
                let singleNum,multipleNum,judgeNum;
                singleNum = this.testMessage.single;
                multipleNum = this.testMessage.multiple;
                judgeNum = this.testMessage.judge;

                // 随机从数据库中抽取需要的单选个数
                request.get('/single/'+singleNum).then( res=> {
                    this.singles = res.data;
                    console.log(this.singles);
                });

                request.get('/multiple/'+multipleNum).then( res => {
                    this.multiples = res.data;
                    console.log(this.multiples);
                });

                request.get('/judge/'+judgeNum).then( res => {
                    this.judges = res.data;
                    console.log(this.judges);
                })

            },
            getScore (){
                let single = this.$refs.singleScore;
                let singleForm = single.singleForm.answer;
                // 返回单选子组件的表单选项
                for(let i=0;i<singleForm.length;i++){
                    if(singleForm[i] === this.singles[i].answer){
                        this.score++;
                    }
                }

                //返回多选子组件的表单选项
                let multiple = this.$refs.multipleScore;
                let multipleForm = multiple.multipleForm.answer;
                let answerStr  = '';
                for(let i=0;i<multipleForm.length;i++){
                    for(let j=0;j<multipleForm[i].length;j++){
                        answerStr = answerStr + multipleForm[i][j];
                    }
                    if(answerStr === this.multiples[i].answer){
                        this.score++;
                    }
                    answerStr = '';
                }

                //返回判断子组件的表单选项
                let judge = this.$refs.judgeScore;
                let judgeForm = judge.judgeForm.answer;
               // console.log(judgeForm);
                for(let i=0;i<judgeForm.length;i++){
                    if(judgeForm[i] === this.judges[i].answer){
                        this.score++;
                    }
                }
                //console.log(this.score);
                this.storeTest();
                this.$router.push('/myscore')
            },
            storeTest (){
                let datetime = this.getTime();
                console.log(datetime);
                const userJson = sessionStorage.getItem("user");
                let tester = JSON.parse(userJson);
                request.post('/testmessage',{
                    username:tester.username,
                    testname:tester.name,
                    testId:this.testMessage.id,
                    test:this.testMessage.project,
                    score: this.score,
                    date:datetime
                }).then( res => {
                    if(res.code === '0') {
                        this.$message({
                            type: "success",
                            message: "提交成功"
                        });
                    }else{
                        this.$message({
                            type: "error",
                            message: res.msg
                        });
                    }
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

            },

        }
    }
</script>

<style scoped>
#home{
    font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
}
    #time{
        position: fixed;
        left: 0%;
        top: 30%;
    }
</style>