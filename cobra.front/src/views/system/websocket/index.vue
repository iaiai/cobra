<style lang="less" scoped>

</style>

<template>
    <Row class="max-height pr">
        <Row class="breadcrumb center-vertical">
            <Breadcrumb class="f12">
                <BreadcrumbItem>
                    <Icon type="ios-chatbubbles" /> websocket在线用户
                </BreadcrumbItem>
            </Breadcrumb>
        </Row>
        <Row class-name="page-content p-lr-16 p-t-15 p-b-10">
            <Row class-name="center-vertical">
                <Col span="12" class="f12">websocket在线数：{{count}}</Col>
                <Col span="12" class="text-right">
                    <Button type="text" icon="md-refresh" @click="onLoad" />
                </Col>
            </Row>
            <Table :data="datas" border stripe class="table-small" size="small" :columns="columns">
                <template slot-scope="{ row }" slot="size">{{row.size|showSize}}</template>
                <template slot-scope="{ row }" slot="createTime">{{row.createTime|formatTimestamp}}</template>
                <template slot-scope="{ row,index }" slot="fun">
                    <Row>
                        <a href="javascript:;" @click="send(index)">
                            <Icon type="ios-paper-plane" /> 发送消息
                        </a>
                    </Row>
                </template>
            </Table>
        </Row>
    </Row>
</template>

<script>
    export default {
        data() {
            return {
                datas:[],
                columns: [
                    {title: '时间', key: 'time', width:200},
                    {title: '地区', key: 'ipAddress', width:220},
                    {title: 'ip', key: 'ip', width:130},
                    {title: 'port', key: 'port', width:70},
                    {title: 'url', key: 'url', minWidth:450},
                    {title: 'sessionId', key: 'sessionId', width:260},
                    {title: 'token', key: 'token', width:260},
                    {title: '操作', slot: 'fun',fixed:'right', width:120,align:'center'},
                ],
                count:0,
            }
        },
        mounted() {
            this.onLoad()
        },
        methods: {
            onLoad() {
                this.$http('websocket_index').then(res => {
                    this.datas = res.result.connections
                    this.count = res.result.count
                }).catch(error => {})
            },
            send(index){
                this.$router.push({
                    name: 'websocketSend',
                    query: {
                        sessionId:this.datas[index].sessionId,
                    },
                })
            },
        }
    }
</script>
