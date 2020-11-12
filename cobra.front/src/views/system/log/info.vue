<style lang="less" scoped>
    .log-info-row{
        margin-bottom: 5px;
    }

    .log-info-title{
        padding-left: 20px;
    }
</style>

<template>
    <Drawer width="1200" v-model="isShow" :closable="true">
        <Row class-name="fbold" slot="header">日志详情</Row>

        <Row class="p-content">
            <Row class="log-info-row">
                <Col :span="4" class="log-info-title">地址</Col>
                <Col :span="20">{{data.url}}</Col>
            </Row>
            <Row class="log-info-row">
                <Col :span="4" class="log-info-title">请求时间</Col>
                <Col :span="20">{{data.startTime|formatTimestamp('yyyy-MM-dd hh:mm:ss')}}</Col>
            </Row>
            <Row class="log-info-row">
                <Col :span="4" class="log-info-title">请求时长(毫秒)</Col>
                <Col :span="20">{{data.duration}}</Col>
            </Row>
            <Row class="log-info-row">
                <Col :span="4" class="log-info-title">IP</Col>
                <Col :span="20">
                    {{data.ip}}<br/>
                    {{data.ipForward}}<br/>
                </Col>
            </Row>
            <Row class="log-info-row">
                <Col :span="4" class="log-info-title">地区</Col>
                <Col :span="20">{{data.ipAddress}}</Col>
            </Row>
            <Row class="log-info-row">
                <Col :span="4" class="log-info-title">提交方式</Col>
                <Col :span="20">{{data.method}}</Col>
            </Row>
            <Row class="log-info-row">
                <Col :span="4" class="log-info-title">User-Agent</Col>
                <Col :span="20">{{data.userAgent}}</Col>
            </Row>
            <Row class="log-info-row">
                <Col :span="4" class="log-info-title">创建时间</Col>
                <Col :span="20">{{data.createTime|formatTimestamp}}</Col>
            </Row>
            <Row class="log-info-row">
                <Col :span="4" class="log-info-title">请求/头信息</Col>
                <Col :span="20">
                    <codemirror :value="data.headers" :options="cmOptions"></codemirror>
                </Col>
            </Row>
            <Row class="log-info-row">
                <Col :span="4" class="log-info-title">请求/参数</Col>
                <Col :span="20">{{data.params}}</Col>
            </Row>
            <Row class="log-info-row">
                <Col :span="4" class="log-info-title">请求/body体</Col>
                <Col :span="20"><codemirror :value="data.paramsBody" :options="cmOptions"></codemirror></Col>
            </Row>
            <Row class="log-info-row">
                <Col :span="4" class="log-info-title">返回/头信息</Col>
                <Col :span="20"><codemirror :value="data.resultHeaders" :options="cmOptions"></codemirror></Col>
            </Row>
        </Row>
    </Drawer>
</template>

<script>
    import { codemirror } from 'vue-codemirror'
    require("codemirror/mode/python/python.js")
    // require("codemirror/mode/javascript/javascript")
    require('codemirror/addon/fold/foldcode.js')
    require('codemirror/addon/fold/foldgutter.js')
    require('codemirror/addon/fold/brace-fold.js')
    require('codemirror/addon/fold/xml-fold.js')
    require('codemirror/addon/fold/indent-fold.js')
    require('codemirror/addon/fold/markdown-fold.js')
    require('codemirror/addon/fold/comment-fold.js')

    export default {
        components:{
            codemirror
        },
        data() {
            return {
                isShow:false,
                data:{},

                cmOptions:{
                    tabSize:4,
                    mode: "python",
                    theme:"darcula",
                    lineNumbers:true,
                    lineWiseCopyCut:true,
                    readOnly: true,   //只读
                    showCursorWhenSelecting:false,
                },
            }
        },
        methods:{
            show(obj){
                this.isShow = true
                this.data = obj
            },
            hidden(){
                this.isShow = false
            },
        }
    }
</script>
