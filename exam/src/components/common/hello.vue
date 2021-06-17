<template>
  <section class="index">
    <div class="hello">
      <span style="align-content: center">欢迎您，{{ user.name }}老师。</span>
    </div>
    <div class="block">
      <el-image src="https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg">
        <div slot="placeholder" class="image-slot">
          加载中<span class="dot">...</span>
        </div>
      </el-image>
    </div>
  </section>
</template>

<script>
export default {
  data() {
    return {
      user: { //用户信息
        name: null,
        id: null
      }
    }
  },
  created() {
    this.getUserInfo()
  },
  methods: {
    getUserInfo() { //获取用户信息
      let userName = this.$cookies.get("cname")
      let userId = this.$cookies.get("cid")
      this.user.name = userName
      this.user.id = userId
    },
  }
}
</script>


<style lang="scss" scoped>
.index {
  margin-left: 20px;
  height: 50%;

  .hello {
    font-size: 40px;
    color: #726f70;

    .icon-xihuan {
      font-size: 40px;
      color: #dd6572;
    }
  }

  .msg {
    .title {
      font-size: 16px;
      color: #000;
      margin-top: 20px;
      margin-left: 10px;
    }

    ul {
      display: flex;
      flex-direction: column;
      width: 200px;
      overflow: hidden;
    }

    li {
      margin-top: 10px;
      font-size: 14px;
      color: lightcoral;
      cursor: pointer;
      display: inline-block;
    }
  }
}
</style>

