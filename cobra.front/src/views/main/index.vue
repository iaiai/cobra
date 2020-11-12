<style lang="less" scoped>
    @_left_menu_width: 200px;
    @_tag_height: 0;

    @_top_height: 55px;

    .root {
        width: 100% !important;
        height:100% !important;

        .index-title {
            height: @_top_height;
            width: 100%;
            z-index: 3;

            .logo-div {
                width: @_left_menu_width;
                line-height: @_top_height;
                font-size: 20px;
            }

            .top-item {
                height: @_top_height;
                line-height: @_top_height;
                font-size: 13px;
                margin-right: 40px;
                position: relative;
                cursor: pointer;

                .top-item-sel {
                    position: absolute;
                    bottom: 0;
                    left: 0;
                    right: 0;
                    height: 3px;
                }
            }

            .top-item-nosel {
                filter: alpha(Opacity=80);
                -moz-opacity: 0.5;
                opacity: 0.5;
            }

            .top-manager-item {
                right: 20px;
                top: 0;
                bottom: 0;
                line-height: @_top_height;

                .profile-item{
                    width:110px;
                }
            }
        }

        .index-menu {
            z-index: 2;
            position: absolute;
            left: 0;
            top: @_top_height;
            bottom: 0;
            width: @_left_menu_width;
            overflow-x: hidden;

            .menu {
                width: @_left_menu_width + 0.5px;
                overflow-y: auto;
            }
        }

        .index-content {
            position: absolute;
            top: @_top_height;
            right: 0;
            bottom: 0;

            .tag {
                position: absolute;
                top: 0;
                left: 0;
                right: 0;
                height: @_tag_height;
            }

            .root-content {
                position: absolute;
                top: @_tag_height;
                bottom: 0;
                right: 0;
                left: 0;
                height: 100% !important;

                /*background-color: #f7f7f7;*/
                background-color: white;

                overflow-y: auto;
            }
        }
    }

    .pay-img {
        width: 300px;
    }

    .pay-img:first-child {
        margin-right: 10px;
    }
</style>

<template>
    <Row class="root pr">
        <!-- 内容 -->
        <Row class="index-content" :style="{left:(isShowMenu?menuWidth:0)+'px'}">
            <Row class="root-content">
                <!-- 临时这么写把不缓存的这里判断一下 -->
                <keep-alive>
                    <router-view v-if="!isRouterCache()"></router-view>
                </keep-alive>
                <router-view v-if="isRouterCache()"></router-view>
            </Row>

            <!--<el-row class="tag bottom-outer-shadow">-->
            <!--<TagsNav :router="$route"/>-->
            <!--</el-row>-->
        </Row>

        <!-- 左面 -->
        <Row
                class="index-menu right-outer-shadow"
                :style="{width:(isShowMenu?menuWidth:0)+'px',backgroundColor:$root.$data.config.theme.menuBackgroundColor}">
            <Menu :class="['max-height',$root.$data.config.theme.menuTextColor]" :active-name="currentActive">
                <!--最多显示三级，不查递归怎么写了，反正不允许过三级-->
                <span v-for="(menu,index) in leftMenus" :key="menu.id">
                    <MenuItem
                            v-if="menu.children==null || menu.children<=0"
                            :name="menu.url"
                            :to="menu.url"
                            class="f12">
                        <i :class="menu.icon" /> {{menu.name}}
                    </MenuItem>

                    <Submenu v-else :name="menu.code">
                        <template slot="title" class="f12">
                            <i :class="menu.icon" /> {{menu.name}}
                        </template>

                        <span v-for="(menu1,index1) in menu.children" :key="menu1.id">
                            <MenuItem
                                    v-if="menu1.children==null || menu1.children<=0"
                                    :name="menu1.url"
                                    :to="menu1.url"
                                    class="f12">
                                <i :class="menu1.icon" /> {{menu1.name}}
                            </MenuItem>

                            <Submenu v-else :name="menu1.code">
                                <template slot="title" class="f12">
                                    <i :class="menu1.icon" /> {{menu1.name}}
                                </template>

                                <span v-for="(menu2,index2) in menu1.children" :key="menu2.id">
                                    <MenuItem
                                            v-if="menu2.children==null || menu2.children<=0"
                                            :name="menu2.url"
                                            :to="menu2.url"
                                            class="f12">
                                        <i :class="menu2.icon" /> {{menu2.name}}
                                    </MenuItem>
                                </span>
                            </Submenu>
                        </span>
                    </Submenu>
                </span>


                <!--                <el-menu-item index="/home" route>-->
                <!--                    <i class="iconfont iaiai-home-fill" /> 首页-->
                <!--                </el-menu-item>-->
                <!--                <el-submenu index="components">-->
                <!--                    <template slot="title">-->
                <!--                        <i class="iconfont iaiai-buffer" /> 组件-->
                <!--                    </template>-->
                <!--                    <el-menu-item index="/component/froala/index" route>-->
                <!--                        <i class="iconfont iaiai-buffer" /> froala编辑器-->
                <!--                    </el-menu-item>-->
                <!--                    <el-menu-item index="/component/neditor/index" route>-->
                <!--                        <i class="iconfont iaiai-buffer" /> neditor编辑器-->
                <!--                    </el-menu-item>-->
                <!--                    <el-menu-item index="/component/wangeditor/index" route>-->
                <!--                        <i class="iconfont iaiai-buffer" /> wangeditor编辑器-->
                <!--                    </el-menu-item>-->
                <!--                </el-submenu>-->
                <!--                <el-submenu index="system">-->
                <!--                    <template slot="title">-->
                <!--                        <i class="iconfont iaiai-windows-fill" /> 系统管理-->
                <!--                    </template>-->
                <!--                    <el-menu-item index="/system/user/index" route>-->
                <!--                        <i class="iconfont iaiai-ios-people" /> 用户管理-->
                <!--                    </el-menu-item>-->
                <!--                    <el-menu-item index="/system/role/index" route>-->
                <!--                        <i class="iconfont iaiai-ios-people" /> 角色管理-->
                <!--                    </el-menu-item>-->
                <!--                    <el-menu-item index="/system/menu/index" route>-->
                <!--                        <i class="iconfont iaiai-menu" /> 菜单管理-->
                <!--                    </el-menu-item>-->
                <!--                    <el-menu-item index="/system/dept/index" route>-->
                <!--                        <i class="iconfont iaiai-ios-people" /> 部门管理-->
                <!--                    </el-menu-item>-->
                <!--                    <el-menu-item index="/system/post/index" route>-->
                <!--                        <i class="iconfont iaiai-account-box" /> 岗位管理-->
                <!--                    </el-menu-item>-->
                <!--                    <el-menu-item index="systemContactIndex">-->
                <!--                        <i class="iconfont iaiai-contact" /> 通讯录-->
                <!--                    </el-menu-item>-->
                <!--                </el-submenu>-->
            </Menu>
        </Row>

        <!-- 顶部 -->
        <Row
                class="index-title pr"
                :style="{backgroundColor:$root.$data.config.theme.topBackgroundColor}">
            <Row class="logo-div center float-l" :style="{color:$root.$data.config.theme.topTextColor}">眼镜蛇</Row>
            <Row
                    :class="['float-l','top-item',currentTopActive==menu.code?'':'top-item-nosel']"
                    v-for="(menu,index) in menus"
                    :key="menu.id"
                    @click.native="selItem(index,menu)"
                    class="f12 fbold"
                    :style="{color:$root.$data.config.theme.topTextColor}">
                <i :class="[menu.icon,'f12','fbold']" /> {{menu.name}}
                <Row
                        class="top-item-sel"
                        v-if="currentTopActive==menu.code"
                        :style="{backgroundColor:$root.$data.config.theme.topTextColor}"/>
            </Row>

            <div class="pa top-manager-item color-white center-vertical">
                <Button
                        type="text"
                        icon="md-color-palette"
                        ghost class="f16"
                        :style="{color:$root.$data.config.theme.topTextColor}"
                        @click="setTheme" />
                <Button
                        type="text"
                        icon="md-expand"
                        ghost class="f16"
                        :style="{color:$root.$data.config.theme.topTextColor}"
                        @click="setFullScreen" />
                <Dropdown :transfer="true">
                    <Badge dot :count="messagesCount" style="line-height: normal;" :offset="[8,8]">
                        <Button
                                type="text"
                                icon="md-notifications-outline"
                                class="f16"
                                :style="{color:$root.$data.config.theme.topTextColor}"
                                ghost />
                    </Badge>
                    <DropdownMenu slot="list">
                        <CellGroup>
                            <!-- 只显示10个，多的话到详情里看 -->
                            <Cell
                                    v-for="(msg,index) in $store.state.msg.messages"
                                    v-if="index<10" @click.native="messageDetail(msg.id)"
                                    :key="msg.id"
                                    class="f12">
                                <Icon type="ios-chatbubbles-outline" />
                                {{msg.title}}
                            </Cell>
                            <Cell>
                                <Row @click.native="messageMore" class-name="f12">
                                    <Icon type="ios-chatbubbles-outline" />
                                    查看全部
                                </Row>
                            </Cell>
                        </CellGroup>
                    </DropdownMenu>
                </Dropdown>
                <Dropdown :transfer="true" @on-click="selFunction" placement="bottom-end" class="cursor-pointer">
                    <Button type="text" ghost :style="{color:$root.$data.config.theme.topTextColor}">
                        <Icon type="md-person" size="14" />
                        {{$root.$data.user.nickname}}
                        <Icon type="md-arrow-dropdown" size="14" />
                    </Button>
                    <DropdownMenu slot="list">
                        <DropdownItem name="profile">
                            <Row class-name="profile-item f12">
                                <Icon type="md-person" /> 编辑个人资料
                            </Row>
                        </DropdownItem>
                        <DropdownItem name="password">
                            <Row class-name="profile-item f12">
                                <Icon type="md-lock" /> 修改密码
                            </Row>
                        </DropdownItem>
                        <DropdownItem name="exit" divided>
                            <Row class-name="profile-item f12">
                                <Icon type="md-log-out" /> 退出
                            </Row>
                        </DropdownItem>
                    </DropdownMenu>
                </Dropdown>
            </div>
        </Row>

        <profileComp ref="profileComp" />
        <messageComp ref="messageComp" @showDetail="messageDetail" />
        <messageDetailComp ref="messageDetailComp" />
        <editPasswordComp ref="editPasswordComp" />
        <themeComp ref="themeComp" />
    </Row>
</template>

<script>
    import profileComp from './profile'
    import messageComp from './message'
    import messageDetailComp from './message_detail'
    import editPasswordComp from './edit_password'
    import themeComp from './theme'

    import screenfull from 'screenfull'

    export default {
        components: {
            profileComp,
            messageComp,
            messageDetailComp,
            editPasswordComp,
            themeComp,
        },
        data() {
            return {
                currentActive: '',   //当前选择的菜单项
                menuWidth: 200,  //菜单宽度
                isShowMenu: true,    //是否显示菜单，true显示，false不显示

                menus: [],   //所有菜单
                leftMenus: [],   //左侧菜单

                currentTopActive: 'home',//当前选择的顶层菜单

                noCacheMenu: [], //不缓存的菜单code列表
            }
        },
        computed: {
            messagesCount() {
                return this.$store.state.msg.messages.length
            },
        },
        watch: {
            $route(to, from) {
                this.currentActive = to.path
            }
        },
        mounted() {
            window.onresize = () => {
                return (() => {
                    //发送广播通知窗口变了
                    this.$bus.$emit('window:onresize')
                })()
            }

            //不缓存的菜单code列表
            this.noCacheMenu = JSON.parse(window.localStorage.getItem("noCacheMenu"))

            this.init()
        },
        methods: {
            //判断当前路由是否缓存
            isRouterCache() {
                let bol = false
                if (this.noCacheMenu && this.noCacheMenu.length > 0) {
                    for (var i = 0; i < this.noCacheMenu.length; i++) {
                        if (this.$route.name == this.noCacheMenu[i]) {
                            bol = true
                            break
                        }
                    }
                }

                return bol
            },
            init() {
                this.initMenu()

                //当前菜单
                this.currentActive = this.$route.path

                this.checkSelTopItem()

                if (this.currentTopActive == 'home') {
                    this.hideMenu()
                } else {
                    this.showMenu()
                }
            },
            //检查顶部菜单是选择的哪个
            checkSelTopItem() {
                let menus = JSON.parse(window.localStorage.getItem("menus"))

                let bol = false //是否获取到当前顶层菜单项
                let parentId = ''   //付id
                while (true) {
                    for (let i = 0; i < menus.length; i++) {
                        if (parentId.length > 0) {
                            if (menus[i].id == parentId) {
                                if (menus[i].parentId) {
                                    parentId = menus[i].parentId
                                } else {
                                    bol = true
                                    this.currentTopActive = menus[i].code
                                }
                                break
                            }
                        } else if (menus[i].url == this.currentActive) {
                            //判断是否有父级
                            if (menus[i].parentId) {
                                parentId = menus[i].parentId
                            } else {
                                bol = true
                                this.currentTopActive = menus[i].code
                            }
                            break
                        }
                    }

                    if (bol) break
                }

                //获取子菜单
                for (let i = 0; i < this.menus.length; i++) {
                    if (this.menus[i].code == this.currentTopActive) {
                        this.selItem(i, this.menus[i])
                        break
                    }
                }
            },
            initMenu() {
                //把menus整理成树菜单
                let menus = JSON.parse(window.localStorage.getItem("treeMenus"))
                // let menus = JSON.parse(window.localStorage.getItem("menus"))
                this.organizeMenu(menus)
                let ms = menus

                //把菜单没用的去掉
                for (let i = 0; i < ms.length; i++) {
                    if (!ms[i].parentId) {
                        this.menus.push(ms[i])
                    }
                }
            },
            organizeMenu(menus, menu) {
                for (let i = 0; i < menus.length; i++) {
                    if (!menu) {
                        if (!menus[i].parentId) {
                            //顶层菜单
                            this.organizeMenu(menus, menus[i])
                            continue
                        }
                    } else {
                        //查子
                        if (menus[i].parentId === menu.id) {
                            if (menu.children) {
                                menu.children.push(menus[i])
                            } else {
                                menu.children = [menus[i]]
                            }

                            //排序
                            menu.children.sort((a, b) => {
                                return a.seq - b.seq
                            })
                            if (menus[i].children && menus[i].children.length > 0) {
                                this.organizeMenu(menus[i].children, menus[i])
                            }
                        }
                    }
                }
            },
            logout() {
                this.$http('logout').then(res => {
                    this.$root.logout()

                    this.$router.push({
                        name: 'login',
                    })
                }).catch(error => {
                    this.$root.logout()

                    this.$router.push({
                        name: 'login',
                    })
                })
            },
            hideMenu() {
                //要隐藏
                this.menuWidth = 0
                this.isShowMenu = false
            },
            showMenu() {
                //要显示
                this.menuWidth = 240
                this.isShowMenu = true
            },
            //选择顶部哪个菜单
            selItem(index, menu) {
                //如果是home单独处理
                if (menu.code == 'home') {
                    this.currentTopActive = menu.code
                    this.hideMenu()

                    this.$router.push({
                        name: menu.code
                    })
                    return
                }

                //要显示
                this.menuWidth = 240
                this.isShowMenu = true

                this.currentTopActive = menu.code
                this.leftMenus = menu.children

                if (menu.children[0].children && menu.children[0].children.length > 0) {
                    this.$router.push({
                        name: menu.children[0].children[0].code
                    })
                } else {
                    this.$router.push({
                        name: menu.children[0].code
                    })
                }
            },
            selFunction(command) {
                if (command == "exit") {
                    this.$Modal.confirm({
                        title: '提示',
                        content: '确定要退出？',
                        onOk: () => {
                            this.logout()
                        },
                    })
                    return
                }

                if(command == 'profile'){
                    this.$refs.profileComp.show()
                    return
                }

                if(command == 'password'){
                    this.$refs.editPasswordComp.show()
                    return
                }
            },

            messageMore(){
                //更多消息
                this.$refs.messageComp.show()
            },
            messageDetail(id){
                this.$refs.messageDetailComp.show(id)
            },

            setFullScreen(){
                if (!screenfull.isEnabled) {
                    this.$Message.error({
                        background: true,
                        content: '您的浏览器不支持',
                    })
                    return false
                }

                screenfull.toggle()
            },
            setTheme(){
                this.$refs.themeComp.show()
            },
        }
    }
</script>
