<!--//查询所有考试-->
<template>
  <div class="exam">
    <el-table :data="pagination.records" border>
      <el-table-column fixed="left" prop="description" label="试卷名称" width="120"></el-table-column>
      <el-table-column prop="course" label="课程" width="150"></el-table-column>
      <el-table-column prop="term" label="考试类型" width="120"></el-table-column>
      <el-table-column prop="examDate" label="考试日期" width="180"></el-table-column>
      <el-table-column prop="department" label="所属学院" width="120"></el-table-column>
      <el-table-column prop="major" label="所属专业" width="100"></el-table-column>
      <el-table-column prop="grade" label="年级" width="100"></el-table-column>
      <el-table-column prop="totalTime" label="持续时间(分钟)" width="120"></el-table-column>
      <el-table-column prop="fullScore" label="总分" width="100"></el-table-column>
      <el-table-column prop="notes" label="考生提示" width="200"></el-table-column>
      <el-table-column fixed="right" label="操作" width="150">
        <template slot-scope="scope">
          <el-button @click="edit(scope.row.examId)" type="primary" size="small">编辑</el-button>
          <el-button @click="deleteRecord(scope.row.examId)" type="danger" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pagination.current"
      :page-sizes="[5, 10, 15, 20]"
      :page-size="pagination.size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pagination.total" class="page">
    </el-pagination>
    <!-- 编辑对话框-->
    <el-dialog
      title="编辑试卷信息"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <section class="update">
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
          <el-form-item label="总分">
            <el-input v-model="form.fullScore"></el-input>
          </el-form-item>
          <el-form-item label="试卷类型">
            <el-input v-model="form.term"></el-input>
          </el-form-item>
          <el-form-item label="考生提示">
            <el-input type="textarea" v-model="form.notes"></el-input>
          </el-form-item>
        </el-form>
      </section>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {}, //保存点击以后当前试卷的信息
      pagination: { //分页后的考试信息
        current: 0, //当前页
        total: null, //记录条数
        size: 5 //每页条数
      },
      dialogVisible: false
    }
  },
  created() {
    this.getExamInfo()
  },
  methods: {
    edit(examCode) { //编辑试卷
      this.dialogVisible = true
      this.$axios(`/api/exam/${examCode}`).then(res => { //根据试卷id请求后台
        if (res.status === 200) {
          this.form = res.data;
        }
      })
    },
    handleClose(done) { //关闭提醒
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        }).catch(_ => {
      });
    },
    formatTime(date) { //日期格式化
      // console.log(date);
      let year = date.getFullYear();
      let month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
      let day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      let hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
      // hours = hours === "00" ? "09" : hours;
      let minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
      let seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
      // 拼接
      return year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
    },
    submit() { //提交修改后的试卷信息
      this.dialogVisible = false;
      let examDate = this.formatTime(new Date(this.form.examDate));
      console.log(examDate);
      this.form.examDate = examDate;
      this.$axios({
        url: '/api/exam',
        method: 'put',
        data: {
          ...this.form
        }
      }).then(res => {
        if (res.status === 204) {
          this.$message({ //成功修改提示
            message: '更新成功',
            type: 'success'
          })
        }
        this.getExamInfo();
      })
    },
    deleteRecord(examCode) {
      this.$confirm("确定删除该记录吗,该操作不可逆！！！", "删除提示", {
        confirmButtonText: '确定删除',
        cancelButtonText: '取消',
        type: 'danger'
      }).then(() => { //确认删除
        this.$axios({
          url: `/api/exam/${examCode}`,
          method: 'delete',
        }).then(res => {
          if (res.status === 204) {
            this.$message({ //成功修改提示
              message: '删除成功',
              type: 'success'
            })
          }
          this.getExamInfo()
        })
      }).catch((e) => {
        console.error(e)
      })
    },
    getExamInfo() { //分页查询所有试卷信息
      let page = 0;
      if (this.pagination.current > 0)
        page = this.pagination.current - 1;
      this.$axios(`/api/exam/page?page=${page}&size=${this.pagination.size}`).then(res => {
        this.pagination = res.data
      }).catch(error => {
      })
    },
    //改变当前记录条数
    handleSizeChange(val) {
      this.pagination.size = val;
      this.getExamInfo()
    },
    //改变当前页码，重新发送请求
    handleCurrentChange(val) {
      this.pagination.current = val;
      this.getExamInfo()
    },
  },
};
</script>
<style lang="scss" scoped>
.exam {
  padding: 0px 40px;

  .page {
    margin-top: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .edit {
    margin-left: 20px;
  }
}
</style>
