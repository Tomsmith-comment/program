<template>
    <div style="width: 200px;height: 150px;background-color: white;">
        <div >
            <p style="padding-top: 20px;">单选题：共{{single}}道</p>
            <p>多选题：共{{multiple}}道</p>
            <p>判断题：共{{judge}}道</p>
            <p style="padding-left: 35px;color: red;">倒计时：{{hour}}:{{minute}}:{{second}}</p>
        </div>
        <div style="padding-left: 40px;padding-top: 10px">
            <stop-f5></stop-f5>
        </div>

    </div>



</template>

<script>
    import StopF5 from '../components/StopF5'
    export default {
        name: "Time",
        components: {StopF5},
        props:[
          'time',
            'single',
            'multiple',
            'judge'
        ],
        data() {
            return {
                hours: 0,
                minutes: 0,
                seconds: 5
            }
        },
        mounted() {
            this.add();
        },
        methods: {
            // 防止数值小于10时，出现一位数
            num(n) {
                return n < 10 ? '0' + n : '' + n
            },
            // 倒计时函数
            add() {
                let time = window.setInterval( ()=> {
                    if (this.hours !== 0 && this.minutes === 0 && this.seconds === 0) {
                        this.hours -= 1;
                        this.minutes = 59;
                        this.seconds = 59;
                    } else if (this.hours === 0 && this.minutes !== 0 && this.seconds ===
                        0) {
                        this.minutes -= 1;
                        this.seconds = 59;
                    } else if (this.hours === 0 && this.minutes === 0 && this.seconds ===
                        0) {
                        this.seconds = 0;
                        window.clearInterval(time)
                    } else if (this.hours !== 0 && this.minutes !== 0 && this.seconds ===
                        0) {
                        this.minutes -= 1;
                        this.seconds = 59;
                    } else {
                        this.seconds -= 1;
                    }
                }, 1000)
            }
        },
        watch: {
            //监听数值变化
            second: {
                handler(newVal) {
                    this.num(newVal);
                    console.log(this.seconds);
                    if(this.hours === 0 && this.minutes === 0 && this.seconds === 0 )
                    {

                        this.$store.commit('finish');
                    }
                }
            },
            minute: {
                handler(newVal) {
                    this.num(newVal)
                }
            },
            hour: {
                handler(newVal) {
                    this.num(newVal)
                }
            }
        },
        computed: {
            // 初始化数据
            second() {
                return this.num(this.seconds)
            },
            minute() {
                return this.num(this.minutes)
            },
            hour() {
                return this.num(this.hours)
            },
        }
    }
</script>

<style scoped>

    p{
        padding-left: 40px;
    }

</style>