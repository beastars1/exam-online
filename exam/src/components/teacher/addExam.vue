<!-- 添加考试 -->
<template>
  <section class="add">
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="试卷名称">
        <el-input v-model="form.description"></el-input>
      </el-form-item>
      <el-form-item label="课程">
        <el-input v-model="form.course"></el-input>
      </el-form-item>
      <el-form-item label="所属学院">
        <el-input v-model="form.department"></el-input>
      </el-form-item>
      <el-form-item label="所属专业">
        <el-input v-model="form.major"></el-input>
      </el-form-item>
      <el-form-item label="年级">
        <el-input v-model="form.grade"></el-input>
      </el-form-item>
      <el-form-item label="考试日期">
        <el-col :span="11">
          <el-date-picker
            type="datetime"
            placeholder="选择日期时间"
            v-model="form.examDate"
          >
          </el-date-picker>
        </el-col>
      </el-form-item>
      <el-form-item label="持续时间">
        <el-input v-model="form.totalTime"></el-input>
      </el-form-item>
<!--      <el-form-item label="总分">-->
<!--        <el-input v-model="form.fullScore"></el-input>-->
<!--      </el-form-item>-->
      <el-form-item label="考试类型">
        <el-input v-model="form.term"></el-input>
      </el-form-item>
      <el-form-item label="考生提示">
        <el-input type="textarea" v-model="form.notes"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit()">立即创建</el-button>
        <el-button type="text" @click="cancel()">取消</el-button>
      </el-form-item>
    </el-form>
  </section>
</template>

<script>
export default {
  data() {
    return {
      form: { //表单数据初始化
        description: null,
        course: null,
        department: null,
        major: null,
        grade: null,
        examDate: null,
        totalTime: null,
        fullScore: null,
        term: null,
        notes: null,
        paperId: null,
      }
    };
  },
  methods: {
    formatTime(date) { //日期格式化
      let year = date.getFullYear();
      let month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
      let day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      let hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
      let minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
      let seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
      // 拼接
      return year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
    },
    onSubmit() {
      this.form.examDate = this.formatTime(new Date(this.form.examDate));
      this.$axios(`/api/exam/last`).then(res => {
        this.form.paperId = res.data.paperId + 1; //实现paperId自增1
        this.$axios({
          url: '/api/exam',
          method: 'post',
          data: {
            ...this.form
          }
        }).then(res => {
          if (res.status === 201) {
            this.$message({
              message: '数据添加成功',
              type: 'success'
            })
            this.$router.push({path: '/selectExam'})
          }
        })
      })
    },
    cancel() { //取消按钮
      this.form = {}
    },

  }
};
</script>
<style lang="scss" scoped>
.add {
  padding: 0px 40px;
  width: 400px;
}
</style>

