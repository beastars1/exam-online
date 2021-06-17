<template>
  <div class="part">
    <div v-if="isNull">
      <span>该门考试还没人参考哦,请提醒学生参加考试。</span>
    </div>
    <div class="box" ref="box" v-else></div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isNull: false, //是否有成绩标志位
      description: null,
      category: { //保存分数段
        '90分及以上': 0,
        '80-89分': 0,
        '70-79分': 0,
        '60-69分': 0,
        '60分以下': 0,
        '作弊': 0,
      }
    }
  },
  created() {
    this.getScoreInfo();
  },
  methods: {
    getScoreInfo() {
      let examId = this.$route.query.examId
      this.description = this.$route.query.description
      this.$axios(`/api/score/m/${examId}`).then(res => {
        let data = res.data;
        if (data.length > 0) {
          // console.log(data)
          let box = this.$refs['box']
          // console.log(box)
          let charts = this.$echarts.init(box)
          // console.log(charts)
          data.forEach(element => {
            const p = Math.floor(element.score / 10);
            switch (p) {
              case 10:
              case 9:
                this.category['90分及以上']++
                break
              case 8:
                this.category['80-89分']++
                break
              case 7:
                this.category["70-79分"]++
                break
              case 6:
                this.category['60-69分']++
                break
              case -1:
                this.category['作弊']++
                break
              default:
                // console.log(p)
                this.category['60分以下']++
            }
          });
          let option = {
            title: {
              text: `${this.description}分数段图`,
              subtext: '分数段饼图',
              x: 'center'
            },
            tooltip: {
              trigger: 'item',
              formatter: "{b} <br/> {c}人 ({d}%)"
            },
            legend: {
              orient: 'vertical',
              left: 'left',
              data: ['90分及以上', '80-89分', '70-79分', '60-69分', '60分以下', '作弊']
            },
            series: [
              {
                description: '分数段',
                type: 'pie',
                radius: '35%',
                center: ['50%', '35%'],
                data: [
                  {value: this.category['90分及以上'], name: '90分及以上'},
                  {value: this.category['80-89分'], name: '80-89分'},
                  {value: this.category['70-79分'], name: '70-79分'},
                  {value: this.category['60-69分'], name: '60-69分'},
                  {value: this.category['60分以下'], name: '60分以下'},
                  {value: this.category['作弊'], name: '作弊'}
                ],
                itemStyle: {
                  emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                  }
                }
              }
            ]
          };
          charts.setOption(option)
        } else {
          this.isNull = true;
        }
      })
    }
  },

}
</script>

<style lang="scss" scoped>
.part {
  .box {
    width: 800px;
    height: 800px;
    margin-left: 40px;
  }
}
</style>


