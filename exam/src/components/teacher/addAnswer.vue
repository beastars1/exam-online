<!--//获取试卷并跳转到添加题库-->
<template>
  <div class="exam">
    <el-table :data="pagination.records" border>
      <el-table-column fixed="left" prop="description" label="试卷名称" width="100"></el-table-column>
      <el-table-column prop="course" label="课程" width="150"></el-table-column>
      <el-table-column prop="term" label="试卷类型" width="120"></el-table-column>
      <el-table-column prop="department" label="所属学院" width="120"></el-table-column>
      <el-table-column prop="major" label="所属专业" width="100"></el-table-column>
      <el-table-column prop="grade" label="年级" width="100"></el-table-column>
      <el-table-column prop="examDate" label="考试日期" width="120"></el-table-column>
      <el-table-column prop="totalTime" label="持续时间" width="120"></el-table-column>
      <el-table-column prop="fullScore" label="总分" width="120"></el-table-column>
      <el-table-column prop="notes" label="考生提示" width="400"></el-table-column>
      <el-table-column fixed="right" label="操作" width="120">
        <template slot-scope="scope">
          <el-button @click="add(scope.row.examId, scope.row.paperId, scope.row.course)" type="primary" size="small">增加题库</el-button>
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
    }
  },
  created() {
    this.getExamInfo()
  },
  methods: {
    getExamInfo() { //分页查询所有试卷信息
      this.$axios(`/api/exam/page?page=${this.pagination.current}&size=${this.pagination.size}`).then(res => {
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
    add(examId, paperId, course) { //增加题库
      this.$router.push({path: '/addAnswerChildren', query: {examId: examId, paperId: paperId, course: course}})
    }
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
