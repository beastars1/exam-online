<!--// 我的考试页面-->
<template>
  <div id="myExam">
    <div class="title">我的练习</div>
    <div class="wrapper">
      <ul class="top">
        <li class="order">
          <el-badge :value="totalExamCount" class="item" type="primary">
            <span>全部</span>
          </el-badge>
        </li>
<!--        <li class="order">
          <el-badge :value="1" class="item" type="primary">
            <span>未开始</span>
          </el-badge>
        </li>
        <li class="order">
          <el-badge :value="2" class="item" type="primary">
            <span>已开始</span>
          </el-badge>
        </li>
        <li class="order">
          <el-badge :value="1" class="item">
            <span>已过期</span>
          </el-badge>
        </li>-->
        <li class="search-li">
          <div class="icon"><input type="text" placeholder="试卷名称" class="search" v-model="key"><i
            class="el-icon-search"></i></div>
        </li>
        <li>
          <el-button type="primary" @click="search()">搜索试卷</el-button>
        </li>
      </ul>
      <ul class="paper" v-loading="loading">
        <li class="item" v-for="(item,index) in pagination.records" :key="index">
          <h4 @click="toExamMsg(item.examId)">{{ item.course }}</h4>
          <p class="name">{{ item.course }}-{{ item.description }}</p>
          <div class="info">
            <i class="el-icon-loading"></i><span>{{ item.examDate.substr(0, 10) }}</span>
            <i class="iconfont icon-icon-time"></i><span v-if="item.totalTime != null">限时{{ item.totalTime }}分钟</span>
            <i class="iconfont icon-fenshu"></i><span>满分{{ item.fullScore }}分</span>
          </div>
        </li>
      </ul>
      <div class="pagination">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pagination.current"
          :page-sizes="[4, 8, 12, 16]"
          :page-size="pagination.size"
          layout="total, sizes, prev, pager, next, jumper"
          :total="pagination.total">
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  // name: 'myExam'
  data() {
    return {
      loading: false,
      key: null, //搜索关键字
      allExam: null, //所有考试信息
      pagination: { //分页后的考试信息
        current: 0, //当前页
        total: null, //记录条数
        size: 4 //每页条数
      },
      totalExamCount: 0
    }
  },
  created() {
    this.getExamInfo();
    this.loading = true;
    this.getTotalExam();
    // console.log(this.totalExamCount)
  },
  // watch: {

  // },
  methods: {
    //获取当前所有考试信息
    getExamInfo() {
      let page = 0;
      if (this.pagination.current > 0)
        page = this.pagination.current - 1;
      this.$axios(`/api/exam/page?page=${page}&size=${this.pagination.size}`)
        .then(res => {
          this.pagination = res.data;
          this.loading = false;
        }).catch(error => {
        console.log(error);
      })
    },
    //改变当前记录条数
    handleSizeChange(val) {
      this.pagination.size = val;
      this.getExamInfo();
    },
    //改变当前页码，重新发送请求
    handleCurrentChange(val) {
      this.pagination.current = val;
      this.getExamInfo();
    },
    //搜索试卷
    search() {
      this.$axios('/api/exam').then(res => {
        if (res.status === 200) {
          let allExam = res.data
          this.pagination.records = allExam.filter(item => {
            return item.course.includes(this.key);
          })
        }
      })
    },
    getTotalExam() {
      this.$axios('/api/exam').then(res => {
        if (res.status === 200) {
          // console.log(res.data)
          this.totalExamCount = res.data.length;
        }
      })
    },
    //跳转到试卷详情页
    toExamMsg(examId) {
      this.$router.push({path: '/examMsg', query: {examId: examId}})
    }
  }
}
</script>


<style lang="scss" scoped>
.pagination {
  padding: 20px 0px 30px 0px;

  .el-pagination {
    display: flex;
    justify-content: center;
  }
}

.paper {
  h4 {
    cursor: pointer;
  }
}

.paper .item a {
  color: #000;
}

.wrapper .top .order {
  cursor: pointer;
}

.wrapper .top .order:hover {
  color: #0195ff;
  border-bottom: 2px solid #0195ff;
}

.wrapper .top .order:visited {
  color: #0195ff;
  border-bottom: 2px solid #0195ff;
}

.item .info i {
  margin-right: 5px;
  color: #0195ff;
}

.item .info span {
  margin-right: 14px;
}

.paper .item {
  width: 380px;
  border-radius: 4px;
  padding: 20px 30px;
  border: 1px solid #eee;
  box-shadow: 0 0 4px 2px rgba(217, 222, 234, 0.3);
  transition: all 0.6s ease;
}

.paper .item:hover {
  box-shadow: 0 0 4px 2px rgba(140, 193, 248, 0.45);
  transform: scale(1.03);
}

.paper .item .info {
  font-size: 14px;
  color: #88949b;
}

.paper .item .name {
  font-size: 14px;
  color: #88949b;
}

.paper * {
  margin: 20px 0;
}

.wrapper .paper {
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
}

.top .el-icon-search {
  position: absolute;
  right: 10px;
  top: 10px;
}

.top .icon {
  position: relative;
}

.wrapper .top {
  border-bottom: 1px solid #eee;
  margin-bottom: 20px;
}

#myExam .search-li {
  margin-left: auto;
}

.top .search-li {
  margin-left: auto;
}

.top li {
  display: flex;
  align-items: center;
}

.top .search {
  margin-left: auto;
  padding: 10px;
  border-radius: 4px;
  border: 1px solid #eee;
  box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
  transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
}

.top .search:hover {
  color: #0195ff;
  border-color: #0195ff;
}

.wrapper .top {
  display: flex;
}

.wrapper .top li {
  margin: 20px;
}

#myExam {
  width: 980px;
  margin: 0 auto;
}

#myExam .title {
  margin: 20px;
}

#myExam .wrapper {
  background-color: #fff;
}

.wrapper .top .order {
  cursor: pointer;
}

.wrapper .top .order:hover {
  color: #0195ff;
  border-bottom: 2px solid #0195ff;
}

.wrapper .top .order:visited {
  color: #0195ff;
  border-bottom: 2px solid #0195ff;
}

.item .info i {
  margin-right: 5px;
  color: #0195ff;
}

.item .info span {
  margin-right: 14px;
}

.paper .item {
  border-radius: 4px;
  padding: 20px 30px;
  border: 1px solid #eee;
  box-shadow: 0 0 4px 2px rgba(217, 222, 234, 0.3);
  transition: all 0.6s ease;
}

.paper .item:hover {
  box-shadow: 0 0 4px 2px rgba(140, 193, 248, 0.45)
}

.paper .item .info {
  font-size: 14px;
  color: #88949b;
}

.paper .item .name {
  font-size: 14px;
  color: #88949b;
}

.paper * {
  margin: 20px 0;
}

.wrapper .paper {
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
}

.top .el-icon-search {
  position: absolute;
  right: 10px;
  top: 10px;
}

.top .icon {
  position: relative;
}

.wrapper .top {
  border-bottom: 1px solid #eee;
}

#myExam .search-li {
  margin-left: auto;
}

.top .search-li {
  margin-left: auto;
}

.top li {
  display: flex;
  align-items: center;
}

.top .search {
  margin-left: auto;
  padding: 10px;
  border-radius: 4px;
  border: 1px solid #eee;
  box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
  transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
}

.top .search:hover {
  color: #0195ff;
  border-color: #0195ff;
}

.wrapper .top {
  display: flex;
}

.wrapper .top li {
  margin: 20px;
}

#myExam {
  width: 980px;
  margin: 0 auto;
}

#myExam .title {
  margin: 20px;
}

#myExam .wrapper {
  background-color: #fff;
}
</style>
