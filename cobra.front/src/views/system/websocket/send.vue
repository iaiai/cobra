<style lang="less" scoped>

</style>

<template>
    <Row class="max-height pr">
        <Row class="breadcrumb center-vertical">
            <Breadcrumb class="f12">
                <BreadcrumbItem to="/system/websocket/index">
                    <Icon type="ios-chatbubbles" /> websocket在线用户
                </BreadcrumbItem>
                <BreadcrumbItem>
                    <Icon type="ios-chatbubbles" /> 发送消息
                </BreadcrumbItem>
            </Breadcrumb>
        </Row>
        <Row class="p-content">
            <Form ref="msgSendForm" :rules="rules" :model="form" :label-width="100" class="m-t-40">
                <FormItem prop="title" label="标题">
                    <Input v-model.trim="form.title" placeholder="请输入标题" style="width:500px;" />
                </FormItem>

                <FormItem prop="content" label="内容">
                    <Input type="textarea" v-model.trim="form.content" placeholder="请输入内容" style="width:500px;" />
                </FormItem>

                <FormItem>
                    <Button type="primary" @click="send">
                        <Icon type="ios-send" /> 发送
                    </Button>
                </FormItem>
            </Form>
        </Row>
    </Row>
</template>

<script>
    export default {
        data() {
            return {
                form:{
                    sessionId:this.$route.query.sessionId,
                    token:null,
                    title:'',
                    content:'',
                },

                rules:{
                    title: [
                        { required: true, message: '请输入标题', trigger: 'blur' },
                        { type: 'string', max: 100, message: '标题不允许大于100个字', trigger: 'blur' },
                    ],
                    content: [
                        { required: true, message: '请输入内容', trigger: 'blur' },
                    ],
                },
            }
        },
        methods: {
            send() {
                this.$refs.msgSendForm.validate((valid) => {
                    if (valid) {
                        this.$http('websocket_send', this.form).then(res => {
                            this.$Message.success('发送成功')
                        })
                    }
                })
            },
        }
    }
</script>
