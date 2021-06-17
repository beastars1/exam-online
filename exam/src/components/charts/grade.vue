<!--// 成绩统计页面-->
<template>
  <div id="grade">
    <div ref="box" class="box"></div>
    <div class="notFound" v-if="isNull">
      <i class="iconfont icon-LC_icon_tips_fill"></i>
      <span>该考生未参加考试</span>
    </div>
  </div>
</template>

<script>
export default {
  name: "grade",
  data() {
    return {
      isNull: false, // 原始数据
      tableDataX: [], // x轴数据 保存次数
      tableDataY: [], // y轴数据 保存分数
      titleName: [],
    }
  },
  mounted() {
    // 初始化时加载
    this.getScore();
  },
  methods: {
    groupBy(array, f) { // 分组
      let groups = {};
      array.forEach(function (o) {
        let group = JSON.stringify(f(o));
        groups[group] = groups[group] || [];
        groups[group].push(o);
      });
      return Object.keys(groups).map(function (group) {
        return groups[group];
      });
    },
    getScore() {
      let studentId = this.$route.query.studentId;
      this.$axios(`/api/score/${studentId}`).then(res => { // 根据学生Id查询成绩
        let rootData = res.data;

        if (rootData.length !== 0) { // 如果有成绩数据
          rootData.forEach((score, index) => {
            // this.tableDataX.push(`第${index + 1}次`)
            // this.tableDataX.push(`${score.answerDate.substr(0, 10)}`)
            this.tableDataX.push(`${score.description}`);
            this.tableDataY.push(score.score);
          });

            this.setCharts();
        } else {
          this.isNull = true;
        }
      })
    },
    setCharts() { // 设置图表
      let boxDom = this.$refs["box"];
      let scoreCharts = this.$echarts.init(boxDom, 'light');

      let option = {
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: this.tableDataX
        },
        yAxis: {
          type: "value"
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            label: {
              backgroundColor: '#6a7985'
            }
          }
        },
        toolbox: {
          show: true,
          feature: {
            dataZoom: {
              yAxisIndex: 'none'
            },
            dataView: {readOnly: false},
            magicType: {type: ['line', 'bar']},
            restore: {},
            saveAsImage: {}
          }
        },
        dataZoom: [
          {   // 这个dataZoom组件，默认控制x轴。
            type: 'slider', // 这个 dataZoom 组件是 slider 型 dataZoom 组件
            start: 0,      // 左边在 10% 的位置。
            end: 50         // 右边在 60% 的位置。
          }
        ],
        series: [
          {
            data: this.tableDataY,
            type: "line",
            itemStyle: {normal: {label: {show: true}}},
            markPoint: {
              data: [
                {type: 'max', name: '最大值'},
                {type: 'min', name: '最小值'}
              ]
            },
          }
        ]
      };

      scoreCharts.setOption(option);
      scoreCharts.on("mouseover", params => {
        console.log(params)
      });
    },
    getScoreOld() {
      let studentId = this.$route.query.studentId;
      this.$axios(`/api/score/${studentId}`).then(res => { // 根据学生Id查询成绩
        let rootData = res.data;

        if (rootData.length !== 0) { // 如果有成绩数据
          let sorted = this.groupBy(rootData, function (item) {
            return [item.examId];
          });

          console.log(sorted)

          let examIds = []; // 考试的编号列表
          sorted.forEach((scores, index) => {
            examIds.push(scores[0].examId.toString());
            this.tableDataY.push([]);
            // console.log(scores)
            scores.forEach((element, index2) => {
              // this.tableDataX.push(`第${index + 1}次`)
              if (this.tableDataX.length < index2)
                this.tableDataX.push(`${element.answerDate.substr(0, 10)}`)
              this.tableDataY[index].push(element.score)
            });
          });
          console.log(this.tableDataY)

          // 根据考试id查询考试详情
          this.$axios({
            url: `/api/exam/exams`,
            method: "post",
            data: examIds
          }).then(res => {
            // console.log(res)
            for (let exam of res.data) {
              this.titleName.push(exam.description);
            }

            this.setCharts();
          });
        } else {
          this.isNull = true;
        }
      })
    },
    setChartsOld() { // 设置图表
      let boxDom = this.$refs["box"];
      let scoreCharts = this.$echarts.init(boxDom, 'light');

      let tableSeries = []
      for (let i = 0; i < this.tableDataY.length; i++) {
        tableSeries.push({
          name: this.titleName[i],
          data: this.tableDataY[i],
          type: "line",
          itemStyle: {normal: {label: {show: true}}}
        })
      }

      let option = {
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: this.tableDataX
        },
        yAxis: {
          type: "value"
        },
        legend: {
          data: this.titleName
        },
        tooltip: {
          trigger: 'axis'
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        dataZoom: [
          {   // 这个dataZoom组件，默认控制x轴。
            type: 'slider', // 这个 dataZoom 组件是 slider 型 dataZoom 组件
            start: 0,      // 左边在 10% 的位置。
            end: 50         // 右边在 60% 的位置。
          }
        ],
        series: tableSeries
      };

      scoreCharts.setOption(option);
      scoreCharts.on("mouseover", params => {
        console.log(params)
      });
    },
  }
};
</script>

<style lang="scss" scoped>
#grade {
  position: relative;

  .box {
    width: 600px;
    height: 400px;
  }

  .notFound {
    position: absolute;
    top: 0px;
    left: 0px;
  }
}
</style>
