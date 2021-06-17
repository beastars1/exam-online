<!-- 学生管理页面 -->
<template>
  <div class="all">
    <el-table :data="pagination.records" border>
      <el-table-column fixed="left" prop="name" label="姓名" width="120"></el-table-column>
      <!--      <el-table-column prop="avatar" label="照片" width="200"></el-table-column>-->
      <el-table-column prop="department" label="学院" width="180"></el-table-column>
      <el-table-column prop="major" label="专业" width="100"></el-table-column>
      <el-table-column prop="grade" label="年级" width="100"></el-table-column>
      <el-table-column prop="clazz" label="班级" width="100"></el-table-column>
      <el-table-column prop="email" label="联系方式" width="150"></el-table-column>
      <el-table-column fixed="right" label="操作" width="150">
        <template slot-scope="scope">
          <el-button @click="checkGrade(scope.row.id)" type="primary" size="small">编辑</el-button>
          <el-button @click="deleteById(scope.row.id)" type="danger" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pagination.current"
      :page-sizes="[5, 10]"
      :page-size="pagination.size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pagination.total"
      class="page">
    </el-pagination>
    <!-- 编辑对话框-->
    <el-dialog
      title="编辑学生信息"
      :visible.sync="dialogVisible"
      width="40%"
      :before-close="handleClose">
      <section class="update">
        <el-form ref="form" :model="form" label-width="50px">
          <el-form-item label="姓名">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item v-model="form.avatar" label="头像">
            <el-upload
              ref="upload"
              class="avatar-uploader"
              action="/api/upload"
              accept="image/png,image/jpg,image/jpeg"
              :limit="1"
              :auto-upload="true"
              :data="form"
              :on-success="uploadOnSuccess"
            >
              <img v-if="form.avatar" :src="form.avatar" class="avatar" height="100" width="100" align="middle"/>
              <el-button v-else size="small" type="primary">点击上传</el-button>
            </el-upload>
          </el-form-item>
          <el-form-item label="学院">
            <el-input v-model="form.department"></el-input>
          </el-form-item>
          <el-form-item label="专业">
            <el-input v-model="form.major"></el-input>
          </el-form-item>
          <el-form-item label="年级">
            <el-input v-model="form.grade"></el-input>
          </el-form-item>
          <el-form-item label="班级">
            <el-input v-model="form.clazz"></el-input>
          </el-form-item>
          <el-form-item label="邮箱">
            <el-input v-model="form.email"></el-input>
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
      pagination: {
        //分页后的考试信息
        current: 0, //当前页
        total: null, //记录条数
        size: 5, //每页条数
      },
      dialogVisible: false, //对话框
      form: {}, //保存点击以后当前试卷的信息
    };
  },
  created() {
    this.getStudentInfo();
  },
  methods: {
    // 当上传成功之后，将表数据改变，但只有当确认更新的时候才会真正提交
    uploadOnSuccess(response) {
      // console.log(response)
      this.form.avatar = response;
    },
    getStudentInfo() {
      let page = 0;
      if (this.pagination.current > 0)
        page = this.pagination.current - 1;
      //分页查询所有试卷信息
      this.$axios.get(`/api/student/page?page=${page}&size=${this.pagination.size}`)
        .then(res => {
          // console.log(res)
          // console.log(res.data)
          this.pagination = res.data;
        }).catch(error => {
      });
    },
    //改变当前记录条数
    handleSizeChange(val) {
      this.pagination.size = val;
      this.getStudentInfo();
    },
    //改变当前页码，重新发送请求
    handleCurrentChange(val) {
      this.pagination.current = val;
      this.getStudentInfo();
    },
    checkGrade(studentId) { //修改学生信息
      this.dialogVisible = true
      this.$axios(`/api/student/${studentId}`).then(res => {
        this.form = res.data
      })
    },
    deleteById(studentId) { //删除当前学生
      this.$confirm("确定删除当前学生吗？删除后无法恢复", "Warning", {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'danger'
      }).then(() => { //确认删除
        this.$axios({
          url: `/api/student/${studentId}`,
          method: 'delete',
        }).then(res => {
          if (res.status === 204) {
            this.$message({
              message: '删除成功',
              type: 'success'
            });
            this.getStudentInfo();
          } else {
            this.$message({
              message: '删除失败',
              type: 'error'
            });
          }
        })
      }).catch(() => {

      })
    },
    submit() { //提交更改
      this.dialogVisible = false
      this.$axios({
        url: '/api/student',
        method: 'put',
        data: {
          ...this.form
        }
      }).then(res => {
        if (res.status === 204) {
          this.$message({
            message: '更新成功',
            type: 'success'
          })
        }
        this.getStudentInfo()
      })
    },
    handleClose(done) { //关闭提醒
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        }).catch(_ => {
      });
    },
    findStudentById(studentId) {
      this.$axios.get(`/api/student/${studentId}`)
        .then(res => {
          // console.log(res.data)
          return res.data;
        }).catch(error => {
      });
    }
  }
};
</script>
<style lang="scss" scoped>
.all {
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

  .el-table tr {
    background-color: #dd5862 !important;
  }
}

.el-table .warning-row {
  background: #000 !important;
}

.el-table .success-row {
  background: #dd5862;
}
</style>
