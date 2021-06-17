<!--考生答题界面-->
<template>
  <div id="answer" @mouseleave="mouseMonitor">
    <!--顶部信息栏-->
    <div class="top">
      <ul class="item">
        <li><i class="iconfont icon-menufold icon20" ref="toggle" @click="slider_flag = !slider_flag"></i></li>
        <li>{{ examData.term }}-{{ examData.course }}</li>
        <li @click="flag = !flag">
          <i class="iconfont icon-user icon20"></i>
          <div class="msg" v-if="flag" @click="flag = !flag">
            <p>姓名：{{ userInfo.name }}</p>
            <p>学号: {{ userInfo.id }}</p>
          </div>
        </li>
        <li><i class="iconfont icon-arrLeft icon20"></i></li>
      </ul>
    </div>
    <div class="flexarea">
      <!--左边题目编号区-->
      <transition name="slider-fade">
        <div class="left" v-if="slider_flag">
          <div class="camera_outer" v-if="!isPractice">
            <!--          <div class="camera_outer" @click="faceSearch">-->
            <video id="videoCamera" :width="videoWidth" :height="videoHeight" autoplay></video>
            <canvas
              style="display:none;"
              id="canvasCamera"
              :width="videoWidth"
              :height="videoHeight"
            ></canvas>
          </div>
          <div class="l-bottom">
            <div class="item">
              <p>选择题部分</p>
              <ul>
                <li v-for="(list, index1) in topic[1]" :key="index1">
                  <a href="javascript:"
                     @click="change(index1)"
                     :class="{
                       'border': index === index1 && currentType === 1,
                       'bg': bg_flag && topic[1][index1].isClick === true
                     }">
                    <span :class="{'mark': topic[1][index1].isMark === true}"></span>
                    {{ index1 + 1 }}
                  </a>
                </li>
              </ul>
            </div>
            <div class="item">
              <p>填空题部分</p>
              <ul>
                <li v-for="(list, index2) in topic[2]" :key="index2">
                  <a href="javascript:" @click="fill(index2)"
                     :class="{
                       'border': index === index2 && currentType === 2,
                       'bg': fillAnswer[index2][3] === true
                     }">
                    <span :class="{'mark': topic[2][index2].isMark === true}"></span>
                    {{ topicCount[0] + index2 + 1 }}
                  </a>
                </li>
              </ul>
            </div>
            <div class="item">
              <p>判断题部分</p>
              <ul>
                <li v-for="(list, index3) in topic[3]" :key="index3">
                  <a href="javascript:" @click="judge(index3)"
                     :class="{
                    'border': index === index3 && currentType === 3,
                    'bg': bg_flag && topic[3][index3].isClick === true
                  }">
                    <span :class="{'mark': topic[3][index3].isMark === true}"></span>
                    {{ topicCount[0] + topicCount[1] + index3 + 1 }}
                  </a>
                </li>
              </ul>
            </div>
            <div class="final" @click="computeScore" v-if="!isPractice">结束考试</div>
          </div>
          <ul class="l-top">
            <li>
              <a href="javascript:"></a>
              <span>当前</span>
            </li>
            <li>
              <a href="javascript:"></a>
              <span>未答</span>
            </li>
            <li>
              <a href="javascript:"></a>
              <span>已答</span>
            </li>
            <li>
              <a href="javascript:"></a>
              <span>标记</span>
            </li>
          </ul>
        </div>
      </transition>
      <!--右边选择答题区-->
      <transition name="slider-fade">
        <div class="right">
          <div class="title">
            <p>{{ title }}</p>
            <i class="iconfont icon-right auto-right"></i>
            <span>全卷共{{ topicCount[0] + topicCount[1] + topicCount[2] }}题
              <i class="iconfont icon-time"></i>倒计时：<b id="remainTime" v-model="this.time">{{ this.time }}</b>分钟</span>
          </div>
          <div class="content">
            <p class="topic">
              <span class="number">{{ number }}. ({{ perScore }})分</span>
              {{ showQuestion }}
            </p>
            <div v-if="currentType === 1">
              <el-radio-group v-model="radio[index]" @change="getChangeLabel">
                <el-radio :label="1">{{ showAnswer.optionA }}</el-radio>
                <el-radio :label="2">{{ showAnswer.optionB }}</el-radio>
                <el-radio :label="3">{{ showAnswer.optionC }}</el-radio>
                <el-radio :label="4">{{ showAnswer.optionD }}</el-radio>
              </el-radio-group>
              <div class="analysis" v-if="isPractice">
                <ul>
                  <li>
                    <el-tag type="success">正确答案：</el-tag>
                    <span class="right">{{ reduceAnswer.answer }}</span></li>
                  <li>
                    <el-tag>题目解析：</el-tag>
                  </li>
                  <li>{{ reduceAnswer.analysis == null ? '暂无解析' : reduceAnswer.analysis }}</li>
                </ul>
              </div>
            </div>
            <div class="fill" v-if="currentType === 2">
              <div v-for="(item,currentIndex) in part" :key="currentIndex">
                <label>{{ currentIndex + 1 }}.</label>
                <el-input placeholder="请填在此处"
                          v-model="fillAnswer[index][currentIndex]"
                          clearable
                          @blur="fillBG"
                          style="width: 300px;"
                >
                </el-input>
              </div>
              <div class="analysis" v-if="isPractice">
                <ul>
                  <li>
                    <el-tag type="success">正确答案：</el-tag>
                    <span class="right">{{ topic[2][index].answer }}</span></li>
                  <li>
                    <el-tag>题目解析：</el-tag>
                  </li>
                  <li>{{ topic[2][index].analysis == null ? '暂无解析' : topic[2][index].analysis }}</li>
                </ul>
              </div>
            </div>
            <div class="judge" v-if="currentType === 3">
              <el-radio-group v-model="judgeAnswer[index]"
                              @change="getJudgeLabel" v-if="currentType === 3">
                <el-radio :label="1">正确</el-radio>
                <el-radio :label="2">错误</el-radio>
              </el-radio-group>
              <div class="analysis" v-if="isPractice">
                <ul>
                  <li>
                    <el-tag type="success">正确答案：</el-tag>
                    <span class="right">{{ topic[3][index].answer }}</span></li>
                  <li>
                    <el-tag>题目解析：</el-tag>
                  </li>
                  <li>{{ topic[3][index].analysis == null ? '暂无解析' : topic[3][index].analysis }}</li>
                </ul>
              </div>
            </div>
          </div>
          <div class="operation">
            <ul class="end">
              <li @click="previous()"><i class="iconfont icon-previous"></i><span>上一题</span></li>
              <li @click="mark()"><i class="iconfont icon-mark-o"></i><span>标记</span></li>
              <li @click="next()"><span>下一题</span><i class="iconfont icon-next"></i></li>
            </ul>
          </div>
        </div>
      </transition>
    </div>
  </div>
</template>

<script>

import store from '@/vuex/store'
import {mapState} from 'vuex'

export default {
  store,
  data() {
    return {
      faceTimer:null,
      remainTimer:null,
      startTime: null, //考试开始时间
      endTime: null, //考试结束时间
      time: null, //考试持续时间
      reduceAnswer: [],  //vue官方不支持3层以上数据嵌套,如嵌套则会数据渲染出现问题,此变量直接接收3层嵌套时的数据。
      answerScore: 0, //答题总分数
      bg_flag: false, //已答标识符,已答改变背景色
      isFillClick: false, //选择题是否点击标识符
      slider_flag: true, //左侧显示隐藏标识符
      flag: false, //个人信息显示隐藏标识符
      currentType: 1, //当前题型类型  1--选择题  2--填空题  3--判断题
      radio: [], //保存考生所有选择题的选项
      title: "请选择正确的选项",
      index: 0, //全局index
      userInfo: { //用户信息
        name: null,
        id: null
      },
      topicCount: [],//每种类型题目的总数
      score: [],  //每种类型分数的总数
      examData: { //考试信息
        // source: null,
        // totalScore: null,
      },
      topic: {  //试卷信息

      },
      showQuestion: [], //当前显示题目信息
      showAnswer: {}, //当前题目对应的答案选项
      number: 1, //题号
      part: null, //填空题的空格数量
      fillAnswer: [[]], //二维数组保存所有填空题答案
      judgeAnswer: [], //保存所有判断题答案
      topicAnswer: [],  //学生选择题作答编号,
      answer: '',
      perScore: 0,
      cheatCount: null,
      notOnCamera: null,
      videoWidth: 260,
      videoHeight: 220,
      thisCanvas: null,
      thisContext: null,
      thisVideo: null,
      switchScreen: document.documentElement.onblur,
      isHavenExam: false
    }
  },
  created() {
    this.cheatCount = localStorage.getItem("cheatCount");
    this.notOnCamera = localStorage.getItem("notOnCamera");
    if (this.cheatCount === null)
      this.cheatCount = 5;
    if (this.notOnCamera === null)
      this.notOnCamera = 5;
    this.getCookies();
    this.getExamData();
  },
  beforeUpdate() {
    localStorage.setItem("cheatCount", this.cheatCount);
    localStorage.setItem("notOnCamera", this.notOnCamera);
  },
  mounted() {
    this.getCompetence()
    this.showTime();
    // this.timeForFaceSearch();
    this.faceTimer = setInterval(() => {
      if (this.isPractice) {
        clearInterval(this.faceTimer);
        this.faceTimer = null;
      } else {
        this.faceSearch();
      }
    }, 5000);
    console.log(this.isPractice)
  },
  beforeDestroy() {
    /*setTimeout(() => {  //此处必须要加延迟执行
      this.$confirm("考试期间退出页面，视为交卷！", "提示", {
        distinguishCancelAndClose: true,
        confirmButtonText: '离开',
        cancelButtonText: '继续答题',
        confirmButtonClass: "btn-class"
      }).then(() => {
        console.log("111")
        this.commit(this.computeScore());
      }).catch(action => {
        this.$router.push({path: "/answer", query: {examId: id}})
      })
    }, 200);*/
    this.stopNavigator();
    clearInterval(this.faceTimer);
    this.faceTimer = null;
    clearInterval(this.remainTimer);
    this.remainTimer = null;
  },
  methods: {
    getTime(date) { //日期格式化
      let year = date.getFullYear()
      let month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
      let day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      let hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
      let minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
      let seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
      // 拼接
      return year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
    },
    getCookies() {  //获取cookie
      this.userInfo.name = this.$cookies.get("cname");
      this.userInfo.id = this.$cookies.get("cid");
    },
    getExamData() { //获取当前试卷所有信息
      let date = new Date();
      this.startTime = this.getTime(date);
      let examId = this.$route.query.examId //获取路由传递过来的试卷编号
      this.$axios(`/api/exam/${examId}`)
        .then(res => {  //通过examCode请求试卷详细信息
          this.examData = {...res.data} //获取考试详情
          this.haveExam(this.examData.examId);
          this.index = 0;
          // this.time = this.examData.totalTime //获取分钟数
          this.time = this.examData.totalTime - Math.floor((new Date() - new Date(this.examData.examDate)) / (1000*60))
          let paperId = this.examData.paperId
          this.$axios(`/api/paper/${paperId}`)
            .then(res => {  //通过paperId获取试题题目信息
              this.topic = {...res.data}
              this.reduceAnswer = this.topic[1][this.index];
              let keys = Object.keys(this.topic) //对象转数组
              keys.forEach(e => {
                let data = this.topic[e];
                this.topicCount.push(data.length);
                let currentScore = 0;
                for (let i = 0; i < data.length; i++) { //循环每种题型,计算出总分
                  currentScore += data[i].score;
                }
                this.score.push(currentScore); //把每种题型总分存入score
              })
              let len = this.topicCount[1];
              let father = [];
              for (let i = 0; i < len; i++) { //根据填空题数量创建二维空数组存放每道题答案
                let children = [null, null, null, null];
                father.push(children);
              }
              this.fillAnswer = father;
              let dataInit = this.topic[1];
              this.number = 1;
              this.showQuestion = dataInit[0].question;
              this.showAnswer = dataInit[0];
            })
        })
    },
    change(index) { //选择题
      this.index = index
      let reduceAnswer = this.topic[1][this.index]
      this.reduceAnswer = reduceAnswer
      this.isFillClick = true
      this.currentType = 1
      let len = this.topic[1].length
      if (this.index < len) {
        if (this.index <= 0) {
          this.index = 0
        }
        // console.log(`总长度${len}`)
        // console.log(`当前index:${index}`)
        this.title = "请选择正确的选项"
        let Data = this.topic[1]
        this.showQuestion = Data[this.index].question //获取题目信息
        this.showAnswer = Data[this.index]
        this.number = this.index + 1;
        this.perScore = Data[this.index].score;
      } else if (this.index >= len) {
        this.index = 0
        this.fill(this.index)
      }
    },
    fillBG() { //填空题已答题目 如果已答该题目,设置第四个元素为true为标识符
      if (this.fillAnswer[this.index][0] != null) {
        this.fillAnswer[this.index][3] = true
      }
    },
    fill(index) { //填空题
      let len = this.topic[2].length
      this.currentType = 2;
      this.index = index;
      if (index < len) {
        if (index < 0) {
          index = this.topic[1].length - 1;
          this.change(index);
        } else {
          // console.log(`总长度${len}`)
          // console.log(`当前index:${index}`)
          this.title = "请在横线处填写答案";
          let Data = this.topic[2];
          this.showQuestion = Data[index].question; //获取题目信息
          //根据题目中括号的数量确定填空横线数量
          this.part = this.showQuestion.split("()").length - 1 + this.showQuestion.split("（）").length - 1;
          this.number = this.topicCount[0] + index + 1;
          this.perScore = Data[this.index].score;
        }
      } else if (index >= len) {
        this.index = 0;
        this.judge(this.index);
      }
    },
    judge(index) { //判断题
      let len = this.topic[3].length
      this.currentType = 3
      this.index = index
      if (this.index < len) {
        if (this.index < 0) {
          this.index = this.topic[2].length - 1
          this.fill(this.index)
        } else {
          this.title = "请作出正确判断"
          let Data = this.topic[3]
          this.showQuestion = Data[index].question //获取题目信息
          this.number = this.topicCount[0] + this.topicCount[1] + index + 1;
          this.perScore = Data[this.index].score;
        }
      } else if (this.index >= len) {
        this.index = 0
        this.change(this.index)
      }
    },
    getChangeLabel(val) { //获取选择题作答选项
      this.radio[this.index] = val; //当前选择的序号
      if (val) {
        let data = this.topic[1];
        this.bg_flag = true;
        data[this.index]["isClick"] = true;
      }
      /* 保存学生答题选项 */
      this.topicAnswer[this.index] = val;
    },
    getJudgeLabel(val) {  //获取判断题作答选项
      this.judgeAnswer[this.index] = val;
      if (val) {
        let data = this.topic[3];
        this.bg_flag = true
        data[this.index]["isClick"] = true
      }
    },
    previous() { //上一题
      this.index--
      switch (this.currentType) {
        case 1:
          this.change(this.index)
          break
        case 2:
          this.fill(this.index)
          break
        case 3:
          this.judge(this.index)
          break
      }
    },
    next() { //下一题
      this.index++
      switch (this.currentType) {
        case 1:
          this.change(this.index)
          break
        case 2:
          this.fill(this.index)
          break
        case 3:
          this.judge(this.index)
          break
      }
    },
    mark() { //标记功能
      switch (this.currentType) {
        case 1:
          this.topic[1][this.index]["isMark"] = true //选择题标记
          break
        case 2:
          this.topic[2][this.index]["isMark"] = true //填空题标记
          break
        case 3:
          this.topic[3][this.index]["isMark"] = true //判断题标记
      }
    },
    computeScore() {
      /* 计算选择题总分 */
      let topicAnswer = this.topicAnswer
      let finalScore = 0
      topicAnswer.forEach((element, index) => { //循环每道选择题根据选项计算分数
        let right = null
        if (element != null) {
          switch (element) { //选项1,2,3,4 转换为 "A","B","C","D"
            case 1:
              right = "A"
              break
            case 2:
              right = "B"
              break
            case 3:
              right = "C"
              break
            case 4:
              right = "D"
          }
          if (right === this.topic[1][index].answer) { // 当前选项与正确答案对比
            finalScore += this.topic[1][index].score // 计算总分数
          }
          // console.log(right, this.topic[1][index].answer)
        }
        // console.log(topicAnswer)
      })
      /**计算填空题总分 */
      let fillAnswer = this.fillAnswer;
      fillAnswer.forEach((element, index) => { //此处index和 this.index数据不一致，注意
        let answers = this.topic[2][index].answer.split("；");
        const len = answers.length;
        const childScore = Math.floor(this.topic[2][this.index].score / len);
        element.forEach((inner) => {
          if (answers.includes(inner)) { //判断填空答案是否与数据库一致
            finalScore += childScore;
          }
        })
      });
      /** 计算判断题总分 */
      let judgeAnswer = this.judgeAnswer;
      judgeAnswer.forEach((element, index) => {
        let right = null;
        switch (element) {
          case 1:
            right = "T";
            break;
          case 2:
            right = "F";
        }
        if (right === this.topic[3][index].answer) { // 当前选项与正确答案对比
          finalScore += this.topic[3][index].score; // 计算总分数
        }
      })
      // console.log(`目前总分${finalScore}`)
      if (this.time !== 0) {
        this.$confirm("考试结束时间未到,是否提前交卷", "提示", {
          confirmButtonText: '立即交卷',
          cancelButtonText: '再检查一下',
          type: 'warning'
        }).then(() => {
          this.commit(finalScore);
        });
      } else {
        this.commit(finalScore);
      }
    },
    commit(finalScore) { //答案提交计算分数
      this.destroyTimer();
      if (!this.isHavenExam) {
        console.log("交卷")
        let date = new Date()
        this.endTime = this.getTime(date)
        let answerDate = this.endTime
        //提交成绩信息
        this.$axios({
          url: '/api/score',
          method: 'post',
          data: {
            examId: this.examData.examId, //考试编号
            studentId: this.userInfo.id, //学号
            course: this.examData.course, //课程名称
            description: this.examData.description, //考试名称
            score: finalScore, //答题成绩
            answerDate: answerDate, //答题日期
          }
        }).then(res => {
          if (res.status === 201) {
            this.$router.push({
              path: '/studentScore', query: {
                score: finalScore,
                startTime: this.startTime,
                endTime: this.endTime,
                examId: this.examData.examId, //考试编号
                studentId: this.userInfo.id, //学号
                course: this.examData.course //课程名称
              }
            })
          }
        })
      }
    },
    haveExam(id) { // 判断是否已经考完试了
      let data = new FormData();
      data.append("examId", id);
      data.append("studentId", this.userInfo.id);
      this.$axios.post(`/api/score/d`, data)
        .then(res => {
          this.isHavenExam = res.data
        });
    },
    showTime() { //倒计时
      if (!this.isPractice) {
        this.remainTimer = setInterval(() => {
          console.log(this.faceTimer)
          console.log(this.remainTimer)
          this.time -= 1;
          if (this.time === 10) {
            this.$message({
              showClose: true,
              type: 'warning',
              message: '考生注意,考试时间还剩10分钟！！！'
            })
          }
          if (this.time === 0) {
            console.log("考试时间已到,强制交卷。")
            this.$message({
              showClose: true,
              type: 'warning',
              message: '考试时间已到,强制交卷。'
            })
            this.commit(this.computeScore());
          }
        }, 1000 * 60);
      }
    },
    mouseMonitor() {
      if (!this.isPractice) {
        if (this.cheatCount-- > 0) {
          localStorage.setItem("cheatCount", this.cheatCount);
          // 如果次数大于0，认为是意外，次数减一
          // console.log("切屏 " + cheat);
          this.warnMsgBox(this.cheatCount, "考试中禁止切屏", "切屏");
        } else {
          this.forceCommit();
        }
      }
    },
    warnMsgBox(count, msg, type) { // 警告作弊
      this.$alert(`${msg}`, '警告', {
        confirmButtonText: '确定',
        callback: action => {
          this.$message({
            type: 'warning',
            message: `${type}警告剩余次数：${count}`
          });
        }
      });
    },
    forceCommit() { // 强制交卷
      console.log("疑似作弊，强制交卷");
      // 如果次数为0，强制交卷，且分数为-1
      this.$alert('您已因作弊，被强制交卷！', '警告', {
        confirmButtonText: '确定',
        callback: action => {
          this.commit(-1);
        }
      });
    },
    getCompetence() { // 调用权限
      var _this = this
      this.thisCanvas = document.getElementById('canvasCamera')
      this.thisContext = this.thisCanvas.getContext('2d')
      this.thisVideo = document.getElementById('videoCamera')
      // 旧版本浏览器可能根本不支持mediaDevices，我们首先设置一个空对象
      if (navigator.mediaDevices === undefined) {
        navigator.mediaDevices = {}
      }
      // 一些浏览器实现了部分mediaDevices，我们不能只分配一个对象
      // 使用getUserMedia，因为它会覆盖现有的属性。
      // 这里，如果缺少getUserMedia属性，就添加它。
      if (navigator.mediaDevices.getUserMedia === undefined) {
        navigator.mediaDevices.getUserMedia = function (constraints) {
          // 首先获取现存的getUserMedia(如果存在)
          var getUserMedia = navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.getUserMedia
          // 有些浏览器不支持，会返回错误信息
          // 保持接口一致
          if (!getUserMedia) {
            return Promise.reject(new Error('getUserMedia is not implemented in this browser'))
          }
          // 否则，使用Promise将调用包装到旧的navigator.getUserMedia
          return new Promise(function (resolve, reject) {
            getUserMedia.call(navigator, constraints, resolve, reject)
          })
        }
      }
      var constraints = {
        audio: false,
        video: {width: this.videoWidth, height: this.videoHeight, transform: 'scaleX(-1)'}
      }
      navigator.mediaDevices.getUserMedia(constraints).then(function (stream) {
        // 旧的浏览器可能没有srcObject
        if ('srcObject' in _this.thisVideo) {
          _this.thisVideo.srcObject = stream
        } else {
          // 避免在新的浏览器中使用它，因为它正在被弃用。
          _this.thisVideo.src = window.URL.createObjectURL(stream)
        }
        _this.thisVideo.onloadedmetadata = function (e) {
          _this.thisVideo.play()
        }
      }).catch(err => {
        console.log(err)
      })
    },
    getImage() { // 捕获的图片并返回
      const _this = this;
      // 点击，canvas画图
      _this.thisContext.drawImage(_this.thisVideo, 0, 0, _this.videoWidth, _this.videoHeight)
      // 获取图片base64链接
      // console.log(image)
      return this.thisCanvas.toDataURL();
    },
    dataURLtoFile(dataurl, filename) { // base64转文件
      var arr = dataurl.split(',')
      var mime = arr[0].match(/:(.*?);/)[1]
      var bstr = atob(arr[1])
      var n = bstr.length
      var u8arr = new Uint8Array(n)
      while (n--) {
        u8arr[n] = bstr.charCodeAt(n)
      }
      return new File([u8arr], filename, {type: mime})
    },
    stopNavigator() { // 关闭摄像头
      this.thisVideo.srcObject.getTracks()[0].stop()
    },
    faceSearch() { // 进行人脸识别
      this.idToFaceSearch();
    },
    timeForFaceSearch() { // 定时进行人脸比对
      const timer = setInterval(() => {
        if (this.isPractice) {
          clearInterval(timer);
        }
        this.faceSearch();
      }, 5000);
      // 通过$once来监听定时器，在beforeDestroy钩子可以被清除。
      this.$once(['hook:beforeDestroy', ], () => {
        clearInterval(timer);
      })
    },
    destroyTimer(){
      clearInterval(this.faceTimer);
      this.faceTimer = null;
      clearInterval(this.remainTimer);
      this.remainTimer = null;
    },
    idToFaceSearch() { // 第一次时，根据学号查询数据库来比对特征值
      let image = this.getImage();
      const data = new FormData();
      data.append("file", image);
      data.append("studentId", this.userInfo.id);
      this.$axios({
        url: `/api/face/search`,
        method: "post",
        data: data
      }).then(res => {
        if (res.status === 200 && !res.data) {
          this.$alert("人脸检测到异常：人脸信息不匹配", '警告', {
            confirmButtonText: '确定',
            callback: action => {
              this.forceCommit();
            }
          });
        }
      }).catch(error => {
        let response = JSON.parse(error.request.response);
        // if (response.msg === '未检测到人脸')
        //   this.warningCamera("请确保可以在摄像头中看到自己或打开摄像头");
        // if (response.msg === '识别出多个人脸')
        //   this.warningCamera("请确保摄像头中没有其他人");
        if (response.msg === '识别出多个人脸' || response.msg === '未检测到人脸') {
          this.$alert(`人脸检测到异常：${response.msg}`, '警告', {
            confirmButtonText: '确定',
            callback: action => {
              this.forceCommit();
            }
          });
        }
      });
    },
    warningCamera(msg) {
      console.log(false);
      if (!this.isPractice) {
        if (this.notOnCamera-- > 0) {
          localStorage.setItem("notOnCamera", this.notOnCamera);
          this.cheatCount++;
          localStorage.setItem("cheatCount", this.cheatCount);
          // 如果次数大于0，认为是意外，次数减一
          // console.log("切屏 " + cheat);
          this.warnMsgBox(this.notOnCamera, msg, "人脸识别");
        } else {
          this.forceCommit();
        }
      }
    }
  },
  computed: mapState(["isPractice"])
}
</script>

<style lang="scss">
.camera_outer {
  position: relative;
  overflow: hidden;

  video, canvas {
    -moz-transform: scaleX(-1);
    -webkit-transform: scaleX(-1);
    -o-transform: scaleX(-1);
    transform: scaleX(-1);
  }
}

.iconfont.icon-time {
  color: #2776df;
  margin: 0px 6px 0px 20px;
}

.analysis {
  margin-top: 20px;

  .right {
    color: #2776df;
    font-size: 18px;
    border: 1px solid #2776df;
    padding: 0px 6px;
    border-radius: 4px;
    margin-left: 20px;
  }

  ul li:nth-child(2) {
    margin: 20px 0px;
  }

  ul li:nth-child(3) {
    padding: 10px;
    background-color: #d3c6c9;
    border-radius: 4px;
  }
}

.analysis span:nth-child(1) {
  font-size: 18px;
}

.mark {
  position: absolute;
  width: 4px;
  height: 4px;
  content: "";
  background-color: red;
  border-radius: 50%;
  top: 0px;
  left: 22px;
}

.border {
  position: relative;
  border: 1px solid #FF90AA !important;
}

.bg {
  background-color: #5188b8 !important;
}

.fill .el-input {
  display: inline-flex;
  width: 150px;
  margin-left: 20px;

  .el-input__inner {
    border: 1px solid transparent;
    border-bottom: 1px solid #eee;
    padding-left: 20px;
  }
}

/* slider过渡效果 */
.slider-fade-enter-active {
  transition: all .3s ease;
}

.slider-fade-leave-active {
  transition: all .3s cubic-bezier(1.0, 0.5, 0.8, 1.0);
}

.slider-fade-enter, .slider-fade-leave-to {
  transform: translateX(-100px);
  opacity: 0;
}

.operation .end li:nth-child(2) {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: rgb(39, 118, 223);
  border-radius: 50%;
  width: 50px;
  height: 50px;
  color: #fff;
}

.operation .end li {
  cursor: pointer;
  margin: 0 100px;
}

.operation {
  background-color: #fff;
  border-radius: 4px;
  padding: 10px 0px;
  margin-right: 10px;
}

.operation .end {
  display: flex;
  justify-content: center;
  align-items: center;
  color: rgb(39, 118, 223);
}

.content .number {
  display: inline-flex;
  justify-content: center;
  align-items: center;
  width: 80px;
  height: 23px;
  background-color: rgb(39, 118, 223);
  border-radius: 4px;
  margin-right: 4px;
}

.content {
  padding: 0px 20px;
}

.content .topic {
  padding: 20px 0px;
  padding-top: 30px;
}

.right .content {
  background-color: #fff;
  margin: 10px;
  margin-left: 0px;
  height: 470px;
}

.content .el-radio-group label {
  color: #000;
  margin: 5px 0px;
}

.content .el-radio-group {
  display: flex;
  flex-direction: column;
}

.right .title p {
  margin-left: 20px;
}

.flexarea {
  display: flex;
}

.flexarea .right {
  flex: 1;
}

.auto-right {
  margin-left: auto;
  color: #2776df;
  margin-right: 10px;
}

.right .title {
  margin-right: 10px;
  padding-right: 30px;
  display: flex;
  margin-top: 10px;
  background-color: #fff;
  height: 50px;
  line-height: 50px;
}

.clearfix {
  clear: both;
}

.l-bottom .final {
  cursor: pointer;
  display: inline-block;
  text-align: center;
  background-color: rgb(39, 118, 223);
  width: 240px;
  margin: 20px 0px 20px 10px;
  border-radius: 4px;
  height: 30px;
  line-height: 30px;
  color: #fff;
  margin-top: 22px;
}

#answer .left .item {
  padding: 0px;
  font-size: 16px;
}

.l-bottom {
  border-radius: 4px;
  background-color: #fff;
}

.l-bottom .item p {
  margin-bottom: 15px;
  margin-top: 10px;
  color: #000;
  margin-left: 10px;
  letter-spacing: 2px;
}

.l-bottom .item li {
  width: 15%;
  margin-left: 5px;
  margin-bottom: 10px;
}

.l-bottom .item {
  display: flex;
  flex-direction: column;
}

.l-bottom .item ul {
  width: 100%;
  margin-bottom: -8px;
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
}

.l-bottom .item ul li a {
  position: relative;
  justify-content: center;
  display: inline-flex;
  align-items: center;
  width: 30px;
  height: 30px;
  border-radius: 50%;
  background-color: #fff;
  border: 1px solid #eee;
  text-align: center;
  color: #000;
  font-size: 16px;
}

.left .l-top {
  display: flex;
  justify-content: space-around;
  padding: 16px 0px;
  border: 1px solid #eee;
  border-radius: 4px;
  margin-bottom: 10px;
  background-color: #fff;
}

.left {
  width: 260px;
  height: 100%;
  margin: 10px 10px 0px 10px;
}

.left .l-top li:nth-child(2) a {
  border: 1px solid #eee;
}

.left .l-top li:nth-child(3) a {
  background-color: #5188b8;
  border: none;
}

.left .l-top li:nth-child(4) a {
  position: relative;
  border: 1px solid #eee;
}

.left .l-top li:nth-child(4) a::before {
  width: 4px;
  height: 4px;
  content: " ";
  position: absolute;
  background-color: red;
  border-radius: 50%;
  top: 0px;
  left: 16px;
}

.left .l-top li {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

.left .l-top li a {
  display: inline-block;
  padding: 10px;
  border-radius: 50%;
  background-color: #fff;
  border: 1px solid #FF90AA;
}

#answer .top {
  background-color: rgb(39, 118, 223);
}

#answer .item {
  color: #fff;
  display: flex;
  padding: 20px;
  font-size: 20px;
}

#answer .top .item li:nth-child(1) {
  margin-right: 10px;
}

#answer .top .item li:nth-child(3) {
  position: relative;
  margin-left: auto;
}

#answer {
  padding-bottom: 30px;
}

.icon20 {
  font-size: 20px;
  font-weight: bold;
}

.item .msg {
  padding: 10px 15px;
  border-radius: 4px;
  top: 47px;
  right: -30px;
  color: #6c757d;
  position: absolute;
  border: 1px solid rgba(0, 0, 0, .15);
  background-color: #fff;
}

.item .msg p {
  font-size: 16px;
  width: 200px;
  text-align: left;
}
</style>
