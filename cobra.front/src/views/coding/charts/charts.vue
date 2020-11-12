<style lang="less" scoped>

</style>

<template>
    <Row class="max-size">
        <Row class="breadcrumb center-vertical">
            <Breadcrumb class="f12">
                <BreadcrumbItem>
                    <Icon type="md-analytics" /> 图表展示
                </BreadcrumbItem>
            </Breadcrumb>
        </Row>
        <Row class="p-content bg-gray">
            <Row class-name="m-b-10">
                <Row ref="map" :style="{width: '100%', height: '800px'}"></Row>
            </Row>

            <Row class-name="m-b-10">
                <Row ref="pie" :style="{width: '100%', height: '500px'}"></Row>
            </Row>

            <Row>
                <Card style="width:450px" class="float-l m-r-10 m-b-10">
                    <p slot="title">
                        <Icon type="md-stats" /> 柱状图
                    </p>
                    <Row ref="bar" :style="{width: '400px', height: '300px'}"></Row>
                </Card>

                <Card style="width:450px" class="float-l m-r-10 m-b-10">
                    <p slot="title">
                        <i class="iconfont iaiai-chart-line" /> 折线图1
                    </p>
                    <Row ref="line1" :style="{width: '400px', height: '300px'}"></Row>
                </Card>

                <Card style="width:450px" class="float-l m-r-10 m-b-10">
                    <p slot="title">
                        <i class="iconfont iaiai-chart-line" /> 折线图2
                    </p>
                    <Row ref="line2" :style="{width: '400px', height: '300px'}"></Row>
                </Card>
            </Row>
        </Row>
    </Row>
</template>

<script>
    import '../../../../node_modules/echarts/map/js/china.js' // 引入中国地图数据

    export default {
        data() {
            return {

            }
        },
        mounted() {
            this.onLoad()
        },
        methods: {
            onLoad() {
                // 绘制图表
                this.$echarts.init(this.$refs.bar.$el).setOption({
                    grid: {
                        top: '10px',
                        left: '0px',
                        right: '0px',
                        bottom: '0px',
                        containLabel: true,
                    },
                    xAxis: {
                        data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
                    },
                    yAxis: {},
                    series: [{
                        type: 'bar',
                        data: [5, 20, 36, 10, 10, 20]
                    }],
                })

                this.$echarts.init(this.$refs.line1.$el).setOption({
                    grid: {
                        top: '10px',
                        left: '0px',
                        right: '0px',
                        bottom: '0px',
                        containLabel: true,
                    },
                    xAxis: {
                        data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
                    },
                    yAxis: {},
                    series: [{
                        type: 'line',
                        data: [5, 20, 36, 10, 10, 20]
                    }],
                })

                this.$echarts.init(this.$refs.line2.$el).setOption({
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        data: ['近七日收益']
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },

                    toolbox: {
                        feature: {
                            saveAsImage: {}
                        }
                    },
                    xAxis: {
                        type: 'category',
                        boundaryGap: false,
                        data: ["1","2","3","4","5"]

                    },
                    yAxis: {
                        type: 'value'
                    },

                    series: [{
                        name: '近七日收益',
                        type: 'line',
                        stack: '总量',
                        data: ["3", "2", "4", "4", "5"]
                    }]
                })

                this.$echarts.init(this.$refs.pie.$el).setOption({
                    //需要的话下面内容copy到主体代码块即可
                    backgroundColor: '#45515a',
                    //标题
                    title: {
                        text: '流量来源',
                        subtext:'饼图示例',
                        left: 'center',
                        top: 20,
                        textStyle: {
                            color: '#ccc',
                            fontStyle:'italic'//标题字体
                        }
                    },
                    //弹窗，响应鼠标指向，显示具体细节
                    tooltip : {
                        trigger: 'item',//以具体项目触发弹窗
                        /*
                        内容格式器，一共有abcd四个代号，例如在饼图中，{a}指系列，即流量来源，{b}指数据项目，如搜索引擎，{c}指数值，如
                        value，{d}百分比。{x}本身代表了相应字符，可以和其他字符拼凑，在弹窗中显示
                        */
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    //图例，选择要显示的项目
                    legend:{
                        orient:'vertical',
                        left:'left',
                        textStyle:{
                            color:'#c8c8d0'
                        },
                        data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']  //注意要和数据的name相对应
                    },
                    //工具箱
                    toolbox:{
                        show:true,//显示工具箱
                        feature:{
                            dataView:{show:true}, //以文字形式显示数据
                            restore:{show:true},   //还原
                            //dataZoom:{show:true}, //区域缩放
                            saveAsImage:{show:true},  //保存图片
                            //magicType:{type:['line','bar']}//动态数据切换，数据显示可以在该规定内容中切换显示方式，
                        }
                    },
                    //视觉映射组件，将数据映射到视觉元素上
                    visualMap: {
                        show: false,
                        min: 10,
                        max: 650,
                        dimension: 0, //选取数据的维度，如人数据：[身高，体重]，则1代表将体重进行映射，默认值为数组的最后一位
                        // seriesIndex: 4, //选取数据集合中的哪个数组，如{一班}，{二班}，默认选取data中的所有数据集
                        inRange: {
                            //选定了要映射的对象，用inRange详细写要渲染的具体细节，[x，y]中x指最小值对应的量（亮度，饱和度等），y指最大值对应的量，其余的按各自value线性渲染
                            color:['red'],
                            colorLightness: [0,1],
                            colorSaturation:[0,1]
                        }
                    },
                    //数据
                    series : [
                        {
                            name:'访问来源',
                            type:'pie',
                            radius : '55%',
                            center: ['50%', '50%'],
                            data:[
                                {value:335, name:'直接访问'},
                                {value:310, name:'邮件营销'},
                                {value:274, name:'联盟广告'},
                                {value:235, name:'视频广告'},
                                {value:400, name:'搜索引擎'}
                            ].sort(function (a, b) { return a.value - b.value; }),
                            roseType: 'radius',//角度和半径展现百分比，'area'只用半径展现
                            label: { //饼图图形的文本标签
                                normal: {  //下同，normal指在普通情况下样式，而非高亮时样式
                                    textStyle: {
                                        color: 'rgba(255, 255, 255, 0.3)'
                                    }
                                }
                            },
                            labelLine: {  //引导线样式
                                normal: {
                                    lineStyle: {
                                        color: 'rgba(255, 255, 255, 0.3)'
                                    },
                                    smooth: 0.5, //0-1，越大越平滑弯曲
                                    length: 10,  //从块到文字的第一段长
                                    length2: 20  //拐弯到文字的段长
                                }
                            },
                            itemStyle: { //图例样式
                                normal: {
                                    color: '#97413c',
                                    shadowBlur: 50,//阴影模糊程度
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'//阴影颜色，一般黑
                                }
                            },

                            animationType: 'scale', //初始动画效果，scale是缩放，expansion是展开
                            animationEasing: 'elasticOut', //初始动画缓动效果
                            animationDelay: function (idx) {  //数据更新动画时长，idx限定了每个数据块从无到有的速度
                                return Math.random() * 200;
                            }
                        }
                    ]
                })

                this.$echarts.init(this.$refs.map.$el).setOption({
                    title: {
                        text: '当前数据',
                        textStyle: {
                            fontSize: 30
                        },
                        x: 'center'
                    },
                    tooltip: {
                        show: true,
                        formatter: function(params) {
                            return params.name + '：' + params.data['value'] + '个'
                        },
                    },
                    //工具箱
                    toolbox:{
                        show:true,//显示工具箱
                        feature:{
                            dataView:{show:true}, //以文字形式显示数据
                            restore:{show:true},   //还原
                            //dataZoom:{show:true}, //区域缩放
                            saveAsImage:{show:true},  //保存图片
                            //magicType:{type:['line','bar']}//动态数据切换，数据显示可以在该规定内容中切换显示方式，
                        }
                    },
                    visualMap: {
                        type: 'continuous',
                        text: ['', ''],
                        showLabel: true,
                        seriesIndex: [0],
                        min: 0,
                        max: 5,
                        inRange: {
                            color: ['#edfbfb', '#b7d6f3', '#40a9ed', '#3598c1', '#215096', ]
                        },
                        textStyle: {
                            color: '#000'
                        },
                        bottom: 30,
                        left: 'left',
                    },
                    grid: {
                        right: 10,
                        top: 80,
                        bottom: 30,
                        width: '20%'
                    },
                    xAxis: {
                        type: 'value',
                        scale: true,
                        position: 'top',
                        splitNumber: 1,
                        boundaryGap: false,
                        splitLine: {
                            show: false
                        },
                        axisLine: {
                            show: false
                        },
                        axisTick: {
                            show: false
                        },
                        axisLabel: {
                            margin: 2,
                            textStyle: {
                                color: '#aaa'
                            }
                        }
                    },
                    yAxis: {
                        type: 'category',
                        nameGap: 16,
                        axisLine: {
                            show: false,
                            lineStyle: {
                                color: '#ddd'
                            }
                        },
                        axisTick: {
                            show: false,
                            lineStyle: {
                                color: '#ddd'
                            }
                        },
                        axisLabel: {
                            interval: 0,
                            textStyle: {
                                color: '#999'
                            }
                        },
                        data: [
                            '北京',
                            '天津',
                            '上海',
                            '重庆',
                            '河北',
                            '河南',
                            '云南',
                            '辽宁',
                            '黑龙江',
                            '湖南',
                            '安徽',
                            '山东',
                            '新疆',
                            '江苏',
                            '浙江',
                            '江西',
                            '湖北',
                            '广西',
                            '甘肃',
                            '山西',
                            '内蒙古',
                            '陕西',
                            '吉林',
                            '福建',
                            '贵州',
                            '广东',
                            '青海',
                            '西藏',
                            '四川',
                            '宁夏',
                            '海南',
                            '台湾',
                            '香港',
                            '澳门',
                        ]
                    },
                    geo: {
                        roam: true,
                        map: 'china',
                        left: 'left',
                        right:'300',
                        layoutSize: '80%',
                        label: {
                            emphasis: {
                                show: false
                            }
                        },
                        itemStyle: {
                            emphasis: {
                                areaColor: '#fff464',

                                borderWidth: 0,
                                shadowColor: 'rgba(255,0,0, 1)',
                                shadowBlur: 15
                            }
                        },
                        regions: [{
                            name: '南海诸岛',
                            value: 0,
                            itemStyle: {
                                normal: {
                                    opacity: 0,
                                    label: {
                                        show: false
                                    }
                                }
                            }
                        }],
                    },
                    series: [{
                        name: 'mapSer',
                        type: 'map',
                        roam: false,
                        geoIndex: 0,
                        label: {
                            show: false,
                        },
                        data: [
                            {name: '北京', value: 150},
                            {name: '天津', value: 30},
                            {name: '上海', value: 10},
                            {name: '重庆', value: 0},
                            {name: '河北', value: 410},
                            {name: '河南', value: 100},
                            {name: '云南', value: 20},
                            {name: '辽宁', value: 0},
                            {name: '黑龙江', value: 20},
                            {name: '湖南', value: 0},
                            {name: '安徽', value: 0},
                            {name: '山东', value: 2},
                            {name: '新疆', value: 0},
                            {name: '江苏', value: 1},
                            {name: '浙江', value: 33},
                            {name: '江西', value: 53},
                            {name: '湖北', value: 10},
                            {name: '广西', value: 12},
                            {name: '甘肃', value: 0},
                            {name: '山西', value: 20},
                            {name: '内蒙古', value: 0},
                            {name: '陕西', value: 10},
                            {name: '吉林', value: 8},
                            {name: '福建', value: 2},
                            {name: '贵州', value: 0},
                            {name: '广东', value: 220},
                            {name: '青海', value: 0},
                            {name: '西藏', value: 1},
                            {name: '四川', value: 1},
                            {name: '宁夏', value: 0},
                            {name: '海南', value: 0},
                            {name: '台湾', value: 0},
                            {name: '香港', value: 10},
                            {name: '澳门', value: 0}
                        ]
                    }, {
                        name: 'barSer',
                        type: 'bar',
                        roam: false,
                        visualMap: false,
                        zlevel: 2,
                        barMaxWidth: 20,
                        itemStyle: {
                            normal: {
                                color: '#40a9ed',
                            },
                            emphasis: {
                                color: "#3596c0",
                            }
                        },
                        label: {
                            normal: {
                                show: false,
                                position: 'right',
                                offset: [0, 10]
                            },
                            emphasis: {
                                show: true,
                                position: 'right',
                                offset: [10, 0]
                            }
                        },
                        data: [
                            {name: '北京', value: 150},
                            {name: '天津', value: 30},
                            {name: '上海', value: 10},
                            {name: '重庆', value: 0},
                            {name: '河北', value: 410},
                            {name: '河南', value: 100},
                            {name: '云南', value: 20},
                            {name: '辽宁', value: 0},
                            {name: '黑龙江', value: 20},
                            {name: '湖南', value: 0},
                            {name: '安徽', value: 0},
                            {name: '山东', value: 2},
                            {name: '新疆', value: 0},
                            {name: '江苏', value: 1},
                            {name: '浙江', value: 33},
                            {name: '江西', value: 53},
                            {name: '湖北', value: 10},
                            {name: '广西', value: 12},
                            {name: '甘肃', value: 0},
                            {name: '山西', value: 20},
                            {name: '内蒙古', value: 0},
                            {name: '陕西', value: 10},
                            {name: '吉林', value: 8},
                            {name: '福建', value: 2},
                            {name: '贵州', value: 0},
                            {name: '广东', value: 220},
                            {name: '青海', value: 0},
                            {name: '西藏', value: 1},
                            {name: '四川', value: 1},
                            {name: '宁夏', value: 0},
                            {name: '海南', value: 0},
                            {name: '台湾', value: 0},
                            {name: '香港', value: 10},
                            {name: '澳门', value: 0},
                        ]
                    }]
                })
            },
        }
    }
</script>
