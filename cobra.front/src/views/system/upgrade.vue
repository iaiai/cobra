<style lang="less" scoped>
    .p-content{
        padding-left: 50px;
        padding-top: 30px;
    }
</style>

<template>
    <Row class="max-height pr">
        <Row class="breadcrumb center-vertical">
            <Breadcrumb class="f12">
                <BreadcrumbItem><Icon type="md-list-box" /> 更新日志</BreadcrumbItem>
            </Breadcrumb>
        </Row>
        <Row class-name="page-content-nobuttom p-l-50 p-t-50 p-b-10">
            <Timeline>
                <TimelineItem>
                    <p class="time">2011年10月5日</p>
                    <p class="content">史蒂夫·乔布斯去世</p>
                </TimelineItem>
                <TimelineItem color="green">
                    <Icon type="ios-trophy" slot="dot" />
                    <Row class-name="f18">1.0.0 新版本发布</Row>
                    <Tag color="orange">2020-20-20</Tag>
                </TimelineItem>
            </Timeline>
        </Row>
    </Row>
</template>

<script>
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

            },
        }
    }
</script>
