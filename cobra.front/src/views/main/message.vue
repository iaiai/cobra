<style lang="less" scoped>

</style>

<template>
    <Drawer width="700" v-model="isShow" :mask-closable="false" :closable="true">
        <Row class-name="fbold" slot="header">消息</Row>
        <Row>
            <Row>
                <Poptip confirm title="要全部设置为已读状态?" @on-ok="settingRead" placement="right" :transfer="true">
                    <Button type="primary">全部设置为已读</Button>
                </Poptip>
            </Row>
            <Table :data="datas" border stripe class="m-t-10 table-small" size="small" :columns="columns">
                <template slot-scope="{ row }" slot="read">
                    <Tag color="warning" v-if="row.read==0">未读</Tag>
                    <span v-if="row.read==1">已读</span>
                </template>
                <template slot-scope="{ row }" slot="createTime">{{row.createTime|formatTimestamp}}</template>
                <template slot-scope="{ row }" slot="fun">
                    <Row>
                        <a href="javascript:;" @click="detail(row)">
                            <Icon type="md-list-box" /> 详情
                        </a>
                    </Row>
                </template>
            </Table>

            <Row class="center m-t-10">
                <Page
                        class-name="f12"
                        @on-page-size-change="changePageSize"
                        @on-change="chagePage"
                        show-total
                        show-elevator
                        show-sizer
                        :total="pageParams.total"
                        :current="pageParams.page"
                        :page-size="pageParams.limit"
                        :page-size-opts="$root.$data.page.paginations"/>
            </Row>
        </Row>
    </Drawer>
</template>

<script>
    import {MESSAGE_CLEAN} from '@/store/module/msg'

    export default {
        data() {
            return {
                isShow: false,
                loading: false,  //确定按钮

                datas:[],
                columns: [
                    {title: '#', slot: 'read', width:80},
                    {title: '标题', key: 'title', minWidth:200},
                    {title: '创建时间', slot: 'createTime', width:160},
                    {title: '#', slot: 'fun', width:140, align:'center'},
                ],

                pageParams: {
                    page: 1,
                    limit: this.$root.$data.page.defaultLimit,
                    search: '',
                    total: 0,
                },
            }
        },
        methods: {
            show() {
                this.isShow = true
                this.onLoad()
            },
            hidden() {
                this.isShow = false
            },

            onLoad(){
                this.$http('message', {
                    page: this.pageParams.page,
                    limit: this.pageParams.limit,
                }).then(res => {
                    this.datas = res.result.records
                    this.pageParams.total = res.result.total
                })
            },
            changePageSize(limit) {
                this.pageParams.page = 1
                this.pageParams.limit = limit
                this.onLoad()
            },
            chagePage(page) {
                this.pageParams.page = page
                this.onLoad()
            },

            detail(row){
                row.read = 1
                this.$emit("showDetail",row.id)
            },
            settingRead(){
                this.$http('message_setting_read').then(res => {
                    this.onLoad()

                    this.$store.dispatch(MESSAGE_CLEAN)
                })
            },
        }
    }
</script>