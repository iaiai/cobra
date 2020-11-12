<style lang="less" scoped>
    .max-size {
        height: 100%;
    }

    .login-bg {
        background-size: cover;
        background-position: 50%;

        .login {
            position: absolute;
            top: 0px;
            bottom: 0px;
            right: 0px;
            width: 500px;
            display: -webkit-box;
            display: -ms-flexbox;
            display: -webkit-flex;
            display: flex;
            -webkit-box-pack: center;
            -ms-flex-pack: center;
            -webkit-justify-content: center;
            justify-content: center;
            -webkit-box-align: center;
            -ms-flex-align: center;
            -webkit-align-items: center;
            align-items: center;

            .ui {
                width: 300px;
                background-color: white;
                border-radius: 6px;

                .content {
                    width: 100%;
                    height: 100%;
                    text-align: center;
                }

                .login-btn {
                    width: 100%;
                    margin-bottom: 40px;

                    .icon {
                        font-size: 12px;
                    }
                }

                .copyright {
                    width: 100%;
                    position: absolute;
                    bottom: -10px;
                    text-align: center;
                    color: #c3c3c3;
                    font-size: 10px;
                }

                .code{
                    width:150px;
                    height:40px;
                    display: block;
                }

                .position{
                    width:260px;
                    height:40px;
                    display: block;
                }
            }
        }
    }
</style>

<template>
    <Row class="max-size login-bg" :style="{backgroundImage:backgroundImage}">
        <vue-particles
                color="#fff"
                :particleOpacity="0.7"
                :particlesNumber="60"
                shapeType="circle"
                :particleSize="4"
                linesColor="#fff"
                :linesWidth="1"
                :lineLinked="true"
                :lineOpacity="0.4"
                :linesDistance="150"
                :moveSpeed="2"
                :hoverEffect="true"
                hoverMode="grab"
                :clickEffect="true"
                clickMode="push">
        </vue-particles>
        <Row class="login">
            <Card class="ui">
                <div slot="title">{{$root.$data.system.description}}</div>
                <Row class="content">
                    <Form ref="loginForm" :model="form" :rules="rules" @keydown.enter.native="submit">
                        <FormItem prop="username">
                            <Input
                                    v-model="form.username"
                                    :disabled="submitLoading"
                                    prefix="md-person"
                                    placeholder="请输入账号"
                                    :maxlength="50" />
                        </FormItem>
                        <FormItem prop="password">
                            <Input
                                    type="password"
                                    v-model="form.password"
                                    :disabled="submitLoading"
                                    :password="true"
                                    prefix="md-unlock"
                                    placeholder="请输入密码"
                                    :maxlength="50" />
                        </FormItem>
                        <FormItem prop="code">
                            <Row type="flex">
                                <Row style="flex:1;" class="p-r-5">
                                    <Input
                                            v-model="form.code"
                                            :disabled="submitLoading"
                                            placeholder="图片验证码"
                                            :maxlength="8" />
                                </Row>
                                <Row v-if="codeImgUrl">
                                    <img :src="codeImgUrl" class="code cursor-pointer" @click="refCode">
                                </Row>
                            </Row>
                        </FormItem>
                        <FormItem>
                            <img :src="positionImgUrl" class="position">
                        </FormItem>
                        <FormItem>
                            <Button @click="submit" :loading="submitLoading" type="primary" long class="login-btn">
                                <span v-if="!submitLoading"><i class="iconfont iaiai-login icon"/> 立即登录</span>
                                <span v-else>正在登录，请稍候...</span>
                            </Button>
                        </FormItem>
                    </Form>
                    <div class="copyright">
                        {{$root.system.copyright}}<br/>
                        {{$root.system.companyName}}
                    </div>
                </Row>
            </Card>
        </Row>
    </Row>
</template>

<script>
    import api from '@/http/api'
    import Main from '@/views/main'
    import {checkPasswordRule} from '@/libs/validate.js'

    export default {
        data() {
            return {
                backgroundImage: '',
                rules: {
                    username: [
                        {required: true, message: '账号不能为空', trigger: 'blur'},
                    ],
                    password: [
                        {required: true, message: '密码不能为空', trigger: 'blur'},
                        {validator: checkPasswordRule, trigger: 'blur'},
                    ],
                    code: [
                        {required: true, message: '图片验证码不能为空', trigger: 'blur'},
                    ],
                },
                codeImgUrl: null,
                positionImgUrl: null,
                form: {
                    username: '',
                    password: '',
                    code:'',
                    accessCode:'',
                },
                submitLoading: false,
            }
        },
        mounted() {
            if(window.localStorage.getItem("theme")!=null) {
                this.$root.$data.config.theme = JSON.parse(window.localStorage.getItem("theme"))
                window.localStorage.clear()
                window.localStorage.setItem("theme", JSON.stringify(this.$root.$data.config.theme))
            }else{
                window.localStorage.clear()
            }
            this.backgroundImage = this.getBackageImage()

            this.$http('login_accessCode').then(res => {
                this.form.accessCode = res.result

                //需要拿着请求码来获取图片
                this.refCode()
            })
        },
        methods: {
            refCode(){
                this.$http('login_captcha',{
                    accessCode:this.form.accessCode,
                }).then(res => {//需要拿着请求码来获取图片
                    this.codeImgUrl = api.login_captcha_img.url + "?accessCode=" + this.form.accessCode + "&" + Math.random()
                    this.positionImgUrl = api.login_captcha_position_img.url + "?accessCode=" + this.form.accessCode + "&" + Math.random()
                })
            },
            getBackageImage() {
                return 'url(' + require('../assets/login/login' + (Math.floor(Math.random() * 3) + 1) + '.jpg') + ')'
            },
            //提交数据
            submit() {
                this.$refs.loginForm.validate((valid) => {
                    if (valid) {
                        this.submitLoading = true

                        this.$http('login', this.form, false).then(res => {
                            this.$root.$data.user = res.result.user

                            //存储权限
                            window.localStorage.setItem("permissions", JSON.stringify(res.result.permissions))
                            window.localStorage.setItem("treeMenus", JSON.stringify(res.result.treeMenus))
                            window.localStorage.setItem("token", res.result.token)
                            window.localStorage.setItem("noCacheMenu", JSON.stringify(res.result.noCacheMenu))
                            window.localStorage.setItem("user", JSON.stringify(res.result.user))
                            //动态添加路由
                            this.addRouter(res.result.menus)

                            let that = this
                            setTimeout(function () {
                                //这里要延迟一些，要不读取不到用户信息
                                that.$root.createWebSocket()
                            }, 3000)

                            //动态路由第一次添加进去的不知道为什么会有问题，刷新一下就没事了
                            this.$router.push({
                                name: 'home'
                            })
                        }).catch(error => {
                            this.submitLoading = false
                        })
                    }
                })
            },

            addRouter(menus) {
                //存储菜单(这里必须要存储全部的，不能存储过滤之后的那个数据，因为菜单显示的时候是需要显示全部的)
                window.localStorage.setItem("menus", JSON.stringify(menus))

                let dataRouter = []
                //循环遍历动态路由表的每一个路由
                for (let i = 0; i < menus.length; i++) {
                    if (menus[i].url) {
                        dataRouter.push({
                            path: menus[i].url,
                            name: menus[i].code,
                            meta: {
                                title: menus[i].name,
                                notCache: true,
                            },
                            component: resolve => require(['@/views' + menus[i].filePath], resolve),    //懒加载的方式
                        })
                    }
                }

                //动态添加路由
                this.$router.addRoutes([{
                    path: '/',
                    name: '_index',
                    redirect: '/home',
                    component: Main,
                    children: dataRouter,
                }])
            },
        }
    }
</script>
