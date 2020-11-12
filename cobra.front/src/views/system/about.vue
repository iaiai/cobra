<style lang="less" scoped>
    .p-content{
        top:0;
        padding-left: 50px;
        padding-top: 40px;

        .pay-img{
            width:300px;
            margin-right: 10px;
        }
    }
</style>

<template>
    <Row class="max-height">
        <Row class-name="p-content">
            <Row class-name="f30">关于眼镜蛇</Row>

            <Row class-name="m-t-20">眼镜蛇是一个基于最新 Web 技术的企业级通用管理系统快速开发框架，可以帮助企业极大的提高工作效率，节省开发成本，提升品牌形象。</Row>
            <Row>眼镜蛇是基于iview做的一套前端+后台的基础框架(之前是用element-ui写的，但element-ui已经不再更新，所以全部改为iview)，在创建新项目时总是没有合适的基础框架，故本人所写一套基础框架，在项目中直接拿来就用，所用的技术有:</Row>
            <Row class-name="p-l-50 p-t-10">
                <ul>
                    <li>vue+vuex+iview+动态生成路由</li>
                    <li>springboot</li>
                    <li>mybatis</li>
                    <li>redis</li>
                </ul>
            </Row>
            <Row class-name="m-t-20">实现的功能有:</Row>
            <Row class-name="p-l-50 p-t-10">
                <ul>
                    <li>用户、权限、角色、部门、岗位</li>
                    <li>权限配置可配置到controller层，也可以在页面上配置，按钮上或组件上，细粒度的控制权限</li>
                    <li>websocket发送、接收消息、查看websocket在线人数</li>
                    <li>日志、参数配置、在线用户</li>
                    <li>HTML大文本编辑器，系统里加了几个常用的neditor、ueditor、wangeditor、tinymce、CKEditor、mavoneditor</li>
                </ul>
            </Row>
            <Row class-name="m-t-20">眼镜蛇项目完全免费开源，大家可以随便使用。</Row>
            <Row class-name="m-t-20">作者信息：</Row>
            <Row class-name="m-t-10 p-l-30">
                <Row>QQ: 176291935</Row>
                <Row>email: 176291935@qq.com</Row>
            </Row>
            <Row class-name="m-t-20">赞助：</Row>
            <Row class-name="m-t-10 p-l-30">
                <img class="pay-img" src="../../assets/alipay.jpg"/>
                <img class="pay-img" src="../../assets/wx.jpg"/>
            </Row>
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
