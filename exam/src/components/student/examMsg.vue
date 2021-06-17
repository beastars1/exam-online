<!--// 点击试卷后的缩略信息-->
<template>
  <div id="msg">
    <div class="title">
      <span>试卷列表</span>
      <span>/  {{ examData.description }}</span>
    </div>
    <div class="wrapper">
      <ul class="top">
        <li class="example">{{ examData.description }}</li>
        <li><i class="iconfont icon-pen-"></i></li>
        <li><i class="iconfont icon-share"></i></li>
        <li class="right">
          <div>
            <span class="count">总分</span>
            <span class="score">{{ score[0] + score[1] + score[2] }}</span>
          </div>
        </li>
      </ul>
      <ul class="bottom">
        <li>更新于{{ examData.examDate }}</li>
        <li>来自 {{ examData.department }}</li>
        <li class="btn">{{ examData.term }}</li>
        <li class="right">
          <el-button @click="toAnswer(examData.examId)">开始答题</el-button>
        </li>
      </ul>
      <ul class="info">
        <li @click="dialogVisible = true">
          <a href="javascript:"><i class="iconfont icon-info"></i>考生须知</a>
        </li>
      </ul>
    </div>
    <div class="content">
      <el-collapse v-model="activeName">
        <el-collapse-item class="header" name="0">
          <template slot="title" class="stitle">
            <div class="title">
              <span>{{ examData.source }}</span><i class="header-icon el-icon-info"></i>
              <span class="time">{{ examData.fullScore }}分 / {{ examData.totalTime }}分钟</span>
              <el-button type="primary" size="small">点击查看试题详情</el-button>
            </div>
          </template>
          <el-collapse class="inner">
            <el-collapse-item>
              <template slot="title" name="1">
                <div class="titlei">选择题 (共{{ topicCount[0] }}题 共计{{ score[0] }}分)</div>
              </template>
              <div class="contenti">
                <ul class="question" v-for="(list, index) in topic[1]" :key="index">
                  <li>{{ index + 1 }}. {{ list.question }} {{ list.score }}分</li>
                </ul>
              </div>
            </el-collapse-item>
            <el-collapse-item>
              <template slot="title" name="2">
                <div class="titlei">填空题 (共{{ topicCount[1] }}题 共计{{ score[1] }}分)</div>
              </template>
              <div class="contenti">
                <ul class="question" v-for="(list, index) in topic[2]" :key="index">
                  <li>{{ topicCount[0] + index + 1 }}.{{ list.question }} {{ list.score }}分</li>
                </ul>
              </div>
            </el-collapse-item>
            <el-collapse-item>
              <template slot="title" name="3">
                <div class="titlei">判断题 (共{{ topicCount[2] }}题 共计{{ score[2] }}分)</div>
              </template>
              <div class="contenti">
                <ul class="question" v-for="(list, index) in topic[3]" :key="index">
                  <li>{{ topicCount[0] + topicCount[1] + index + 1 }}. {{ list.question }} {{ list.score }}分</li>
                </ul>
              </div>
            </el-collapse-item>
          </el-collapse>
        </el-collapse-item>

      </el-collapse>
    </div>
    <!--考生须知对话框-->
    <el-dialog
      title="考生须知"
      :visible.sync="dialogVisible"
      width="30%">
      <span>{{ examData.notes }}</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">知道了</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {mapState} from "vuex";

export default {
  data() {
    return {
      dialogVisible: false, //对话框属性
      activeName: '0',  //默认打开序号
      topicCount: [],//每种类型题目的总数
      score: [],  //每种类型分数的总数
      examData: { //考试信息
        // source: null,
        // totalScore: null,
      },
      topic: {  //试卷信息

      },
      studentId: null,
      examId: null,
      isHavenExam: false
    }
  },
  mounted() {
    this.studentId = this.$cookies.get("cid");
    this.init()
    this.haveExam(this.examId)
  },
  methods: {
    //初始化页面数据
    init() {
      this.examId = this.$route.query.examId //获取路由传递过来的试卷编号
      this.$axios(`/api/exam/${this.examId}`).then(res => {  //通过this.examId请求试卷详细信息
        // res.data.examDate = res.data.examDate.substr(0, 10)
        this.examData = {...res.data}
        let paperId = this.examData.paperId
        this.$axios(`/api/paper/${paperId}`).then(res => {  //通过paperId获取试题题目信息
          this.topic = {...res.data}
          let keys = Object.keys(this.topic) //对象转数组
          keys.forEach(e => {
            let data = this.topic[e]
            this.topicCount.push(data.length)
            let currentScore = 0
            for (let i = 0; i < data.length; i++) { //循环每种题型,计算出总分
              currentScore += data[i].score
            }
            this.score.push(currentScore) //把每种题型总分存入score
          })
        })
      })
    },
    toAnswer(id) {
      let now = new Date();
      // let examTime = this.examData.examDate.replace("-", "/");
      // console.log(now)
      // console.log(this.examData.examDate)
      // console.log(new Date(this.examData.examDate))
      // console.log(this.examData)
      // 考试是否结束
      let isOff = now.getTime() > (new Date(this.examData.examDate).getTime() + 1000 * 60 * this.examData.totalTime);
      if (now.getTime() > new Date(this.examData.examDate).getTime()) {
        if (!this.isPractice) {
          if (!isOff) {
            if (!this.isHavenExam) {
              this.$confirm('请打开摄像头权限，现在开始答题？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
              }).then(() => {
                localStorage.setItem('cheatCount', '5');
                localStorage.setItem('notOnCamera', '5');
                this.$router.push({path: "/answer", query: {examId: id}})
              }).catch(() => {
                this.$message({
                  type: 'info',
                  message: '已取消'
                })
              });
            } else {
              this.$alert('你已经完成了这次考试，请不要再次作答', '提示', {
                confirmButtonText: '确定',
                type: "warning",
                callback: action => {
                }
              });
            }
          } else {
            this.$alert('考试已经结束', '提示', {
              confirmButtonText: '确定',
              type: "warning",
              callback: action => {
              }
            });
          }
        } else {
          // 判断考试是否结束
          if (isOff) {
            this.$router.push({path: "/answer", query: {examId: id}});
          } else {
            this.$alert('考试还未结束，请等到考试结束后进行测试', '提示', {
              confirmButtonText: '确定',
              type: "warning",
              callback: action => {
              }
            });
          }
        }
      } else {
        // 说明时间还没到
        this.$alert('考试时间未到', '提示', {
          confirmButtonText: '确定',
          type: "warning",
          callback: action => {
          }
        });
      }
    },
    haveExam(id) { // 判断是否已经考完试了
      let data = new FormData();
      data.append("examId", id);
      data.append("studentId", this.studentId);
      this.$axios.post(`/api/score/d`, data)
        .then(res => {
          this.isHavenExam = res.data
        });
    }
  },
  computed: mapState(["isPractice"])
}
</script>

<style lang="scss" scoped>
.bottom {
  .right {
    .el-button {
      color: #409EFF;
      border-color: #c6e2ff;
      background-color: #ecf5ff;
    }
  }
}

.right {
  margin-left: auto;
}

.inner .contenti .question {
  margin-left: 40px;
  color: #9a9a9a;
  font-size: 14px;
}

.content .inner .titlei {
  margin-left: 20px;
  font-size: 16px;
  color: #88949b;
  font-weight: bold;
}

.content .title .time {
  font-size: 16px;
  margin-left: 420px;
  color: #999;
}

.content .stitle {
  background-color: #0195ff;
}

.content .title span {
  margin-right: 10px;
}

#msg .content .title {
  font-size: 20px;
  margin: 0px;
  display: flex;
  align-items: center;
}

.content {
  margin-top: 20px;
  background-color: #fff;
}

.content .header {
  padding: 10px 30px;
}

.wrapper .info {
  margin: 20px 0px 0px 20px;
  border-top: 1px solid #eee;
  padding: 20px 0px 10px 0px;
}

.wrapper .info a {
  color: #88949b;
  font-size: 14px;
}

.wrapper .info a:hover {
  color: #0195ff;
}

.wrapper .bottom .btn {
  cursor: pointer;
  padding: 5px 10px;
  border: 1px solid #88949b;
  border-radius: 4px;
}

.wrapper .bottom {
  display: flex;
  margin-left: 20px;
  color: #999;
  font-size: 14px;
  align-items: center;
}

.wrapper .bottom li {
  margin-right: 14px;
}

#msg {
  background-color: #eee;
  width: 980px;
  margin: 0 auto;
}

#msg .title {
  margin: 20px;
}

#msg .wrapper {
  background-color: #fff;
  padding: 10px;
}

.wrapper .top {
  display: flex;
  margin: 20px;
  align-items: center;
}

.wrapper .top .right {
  margin-left: auto;
}

.wrapper .top .example {
  color: #333;
  font-size: 22px;
  font-weight: 700;
}

.wrapper .top li i {
  margin-left: 20px;
  color: #88949b;
}

.wrapper .right .count {
  margin-right: 60px;
  color: #fff;
  padding: 4px 10px;
  background-color: #88949b;
  border-top-left-radius: 4px;
  border-bottom-left-radius: 4px;
  border: 1px solid #88949b;
}

.wrapper .right .score {
  position: absolute;
  left: 53px;
  top: -5px;
  padding: 1px 12px;
  font-size: 20px;
  color: #88949b;
  border: 1px dashed #88949b;
  border-left: none;
  border-top-right-radius: 4px;
  border-bottom-right-radius: 4px;
  font-weight: bold;
}

.wrapper .right div {
  position: relative;
}
</style>
