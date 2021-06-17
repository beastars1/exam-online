<!-- 添加学生 -->
<template>
  <section class="add">
    <el-form ref="form" :model="form" label-width="100px">
      <el-form-item label="学号">
        <el-input v-model="form.id"></el-input>
      </el-form-item>
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
          <img v-if="form.avatar" :src="form.avatar" class="avatar" height="100" width="100" align="middle" />
          <el-button v-else size="small" type="primary">点击上传</el-button>
        </el-upload>
      </el-form-item>
      <el-form-item label="学院">
        <el-input v-model="form.department"></el-input>
      </el-form-item>
      <el-form-item label="所属专业">
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
      <el-form-item label="密码">
        <el-input v-model="form.pwd"></el-input>
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
        id: null,
        name: null,
        avatar: null,
        grade: null,
        major: null,
        clazz: null,
        department: null,
        email: null,
        pwd: null,
        role: 2
      }
    };
  },
  methods: {
    // 当上传成功之后，将表数据改变，但只有当确认更新的时候才会真正提交
    uploadOnSuccess(response) {
      // console.log(response)
      this.form.avatar = response;
    },
    onSubmit() { //数据提交
      this.$axios({
        url: '/api/student',
        method: 'post',
        data: {
          ...this.form
        }
      }).then(res => {
        if(res.status === 201) {
          this.$message({
            message: '数据添加成功',
            type: 'success'
          })
          this.$router.push({path: '/studentManage'})
        }
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

