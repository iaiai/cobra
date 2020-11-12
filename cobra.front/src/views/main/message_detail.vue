<style lang="less" scoped>

</style>

<template>
    <Drawer width="700" v-model="isShow" :mask-closable="false" :closable="true">
        <Row class-name="fbold" slot="header">消息详情</Row>
        <Row>
            <Row class-name="center fbold">{{msg.title}}</Row>
            <Row class-name="center">{{msg.createTime|formatTimestamp}}</Row>
            <Row class-name="p-t-10">{{msg.content}}</Row>
        </Row>
    </Drawer>
</template>

<script>
    import {MESSAGE_READ} from '@/store/module/msg'

    export default {
        data() {
            return {
                isShow: false,
                loading: false,  //确定按钮

                msg:{},
            }
        },
        methods: {
            show(id) {
                this.isShow = true
                this.onLoad(id)
            },
            hidden() {
                this.isShow = false
            },

            onLoad(id){
                this.$http('message_detail', {
                    id: id,
                }).then(res => {
                    this.msg = res.result

                    //把本地如果有未读取的设置为已读取
                    this.$store.dispatch(MESSAGE_READ, this.msg.id)
                })
            },
        }
    }
</script>