<style lang="less" scoped>
    .color{
        margin-right: 15px;
        width:15px;
        height: 15px;

        border: 1px solid #efefef;
    }
</style>

<template>
    <Drawer width="600" v-model="isShow" :mask-closable="false" :closable="true">
        <Row class-name="fbold" slot="header">风格</Row>
        <Row>
            <Row class="height40 center-vertical">
                <Col span="5">风格:</Col>
                <Col span="19">
                    <Button class="m-r-10" @click="selTheme('default')">默认</Button>
                    <Button class="m-r-10" @click="selTheme('black')">黑金</Button>
                    <Button class="m-r-10" @click="selTheme('dark')">灰色</Button>
                </Col>
            </Row>
            <Row class="height40 center-vertical">
                <Col span="5">顶部背景色:</Col>
                <Col span="19">
                    <RadioGroup v-model="topBg.key" @on-change="changeTopBgColor">
                        <Row>
                            <Radio label="blue">
                                <div class="color float-r" :style="{backgroundColor:topBg.blue}" />
                            </Radio>
                            <Radio label="black">
                                <div class="color float-r" :style="{backgroundColor:topBg.black}" />
                            </Radio>
                            <Radio label="dark">
                                <div class="color float-r" :style="{backgroundColor:topBg.dark}" />
                            </Radio>
                            <Radio label="other">其它颜色：</Radio>
                            <ColorPicker
                                    v-model="topBg.other"
                                    :disabled="topBg.key!='other'"
                                    size="small"
                                    class="m-r-5"
                                    @on-change="changeTopBgOtherPicker"/>
                        </Row>
                    </RadioGroup>
                </Col>
            </Row>
            <Row class="height40 center-vertical">
                <Col span="5" class="center-vertical">顶部文字色:</Col>
                <Col span="19">
                    <RadioGroup v-model="topText.key" @on-change="changeTopTextColor">
                        <Row>
                            <Radio label="white">
                                <div class="color float-r" :style="{backgroundColor:topText.white}" />
                            </Radio>
                            <Radio label="gold">
                                <div class="color float-r" :style="{backgroundColor:topText.gold}" />
                            </Radio>
                            <Radio label="black">
                                <div class="color float-r" :style="{backgroundColor:topText.black}" />
                            </Radio>
                            <Radio label="other">其它颜色：</Radio>
                            <ColorPicker
                                    v-model="topText.other"
                                    :disabled="topText.key!='other'"
                                    size="small"
                                    class="m-r-5"
                                    @on-change="changeTopTextOtherPicker"/>
                        </Row>
                    </RadioGroup>
                </Col>
            </Row>
            <Row class="height40 center-vertical">
                <Col span="5">左侧菜单背景色:</Col>
                <Col span="19">
                    <RadioGroup v-model="menuBg.key" @on-change="changeMenuBgColor">
                        <Row>
                            <Radio label="white">
                                <div class="color float-r" :style="{backgroundColor:menuBg.white}" />
                            </Radio>
                            <Radio label="blue">
                                <div class="color float-r" :style="{backgroundColor:menuBg.blue}" />
                            </Radio>
                            <Radio label="black">
                                <div class="color float-r" :style="{backgroundColor:menuBg.black}" />
                            </Radio>
                            <Radio label="dark">
                                <div class="color float-r" :style="{backgroundColor:menuBg.dark}" />
                            </Radio>
                            <Radio label="other">其它颜色：</Radio>
                            <ColorPicker
                                    v-model="topBg.other"
                                    :disabled="menuBg.key!='other'"
                                    size="small"
                                    class="m-r-5"
                                    @on-change="changeMenuBgOtherPicker"/>
                        </Row>
                    </RadioGroup>
                </Col>
            </Row>
            <Row class="height40 center-vertical">
                <Col span="5">左侧菜单文字色:</Col>
                <Col span="19">
                    <RadioGroup v-model="menuText.key" @on-change="changeMenuTextColor">
                        <Row>
                            <Radio :label="menuText.white">
                                <div class="color float-r" :style="{backgroundColor:menuText.whiteColor}" />
                            </Radio>
                            <Radio :label="menuText.gold">
                                <div class="color float-r" :style="{backgroundColor:menuText.goldColor}" />
                            </Radio>
                            <Radio :label="menuText.black">
                                <div class="color float-r" :style="{backgroundColor:menuText.blackColor}" />
                            </Radio>
                            <Radio :label="menuText.dark">
                                <div class="color float-r" :style="{backgroundColor:menuText.darkColor}" />
                            </Radio>
                        </Row>
                    </RadioGroup>
                </Col>
            </Row>
        </Row>
    </Drawer>
</template>

<script>
    export default {
        data() {
            return {
                isShow: false,
                loading: false,  //确定按钮

                topBg:{
                    blue:'#047ee9',
                    black:'#1d1e23',
                    dark:'#525d6b',
                    other:'#1d1e23',  //顶部背景色

                    key:'blue',    //选择的颜色
                    color:'#047ee9',  //顶部背景色
                },

                topText:{
                    white:'#ffffff',
                    black:'#1d1e23',
                    gold:'#f6ca9d',
                    other:'#1d1e23',

                    key:'white',    //选择的颜色
                    color:'#ffffff',  //顶部文字颜色
                },

                menuBg:{
                    white:'#ffffff',
                    blue:'#047ee9',
                    black:'#1d1e23',
                    dark:'#525d6b',
                    other:'#1d1e23',  //左侧菜单背景色

                    key:'white',    //选择的颜色
                    color:'#ffffff',  //顶部背景色
                },

                menuText:{
                    white:'theme-white',
                    whiteColor:'#ffffff',
                    black:'theme-black',
                    blackColor:'#1d1e23',
                    dark:'theme-dark',
                    darkColor:'#525d6b',
                    gold:'theme-gold',
                    goldColor:'#f6ca9d',

                    key:'theme-white',    //选择的颜色
                    // color:'theme-white',
                    selKey:'theme-white',
                },
            }
        },
        methods: {
            show() {
                this.isShow = true

                switch (this.$root.config.theme.topBackgroundColor) {
                    case this.topBg.blue:
                        this.topBg.key = 'blue'
                        break
                    case this.topBg.black:
                        this.topBg.key = 'black'
                        break
                    case this.topBg.dark:
                        this.topBg.key = 'dark'
                        break
                    default:
                        this.topBg.key = 'other'
                        break
                }
                this.topBg.color = this.$root.config.theme.topBackgroundColor

                switch (this.$root.config.theme.topTextColor) {
                    case this.topText.white:
                        this.topText.key = 'white'
                        break
                    case this.topText.black:
                        this.topText.key = 'black'
                        break
                    case this.topText.gold:
                        this.topText.key = 'gold'
                        break
                    default:
                        this.topText.key = 'other'
                        break
                }
                this.topText.color = this.$root.config.theme.topTextColor

                switch (this.$root.config.theme.menuBackgroundColor) {
                    case this.menuBg.white:
                        this.menuBg.key = 'white'
                        break
                    case this.menuBg.blue:
                        this.menuBg.key = 'blue'
                        break
                    case this.menuBg.black:
                        this.menuBg.key = 'black'
                        break
                    case this.menuBg.dark:
                        this.menuBg.key = 'dark'
                        break
                    default:
                        this.menuBg.key = 'other'
                        break
                }
                this.menuBg.color = this.$root.config.theme.menuBackgroundColor

                this.menuText.key = this.$root.config.theme.menuTextColor
            },
            hidden() {
                this.isShow = false
            },
            changeTopBgColor(key) {
                switch (key) {
                    case 'blue':
                        this.topBg.color = this.topBg.blue
                        break
                    case 'black':
                        this.topBg.color = this.topBg.black
                        break
                    case 'dark':
                        this.topBg.color = this.topBg.dark
                        break
                    case 'other':
                        this.topBg.color = this.topBg.other
                        break
                }
                this.$root.$data.config.theme.topBackgroundColor = this.topBg.color

                this.setStorage()
            },
            changeTopBgOtherPicker(color){
                this.topBg.color = color
                this.$root.$data.config.theme.topBackgroundColor = color

                this.setStorage()
            },

            changeTopTextColor(key) {
                switch (key) {
                    case 'white':
                        this.topText.color = this.topText.white
                        break
                    case 'gold':
                        this.topText.color = this.topText.gold
                        break
                    case 'black':
                        this.topText.color = this.topText.black
                        break
                    case 'other':
                        this.topText.color = this.topText.other
                        break
                }
                this.$root.$data.config.theme.topTextColor = this.topText.color

                this.setStorage()
            },
            changeTopTextOtherPicker(color){
                this.topText.color = color
                this.$root.$data.config.theme.topTextColor = color

                this.setStorage()
            },

            changeMenuBgColor(key) {
                switch (key) {
                    case 'white':
                        this.menuBg.color = this.menuBg.white
                        break
                    case 'blue':
                        this.menuBg.color = this.menuBg.blue
                        break
                    case 'black':
                        this.menuBg.color = this.menuBg.black
                        break
                    case 'dark':
                        this.menuBg.color = this.menuBg.dark
                        break
                    case 'other':
                        this.menuBg.color = this.menuBg.other
                        break
                }
                this.$root.$data.config.theme.menuBackgroundColor = this.menuBg.color

                this.setStorage()
            },
            changeMenuBgOtherPicker(color){
                this.menuBg.color = color
                this.$root.$data.config.theme.menuBackgroundColor = color

                this.setStorage()
            },

            changeMenuTextColor(key) {
                switch (key) {
                    case this.menuText.white:
                        this.menuText.key = this.menuText.white
                        break
                    case this.menuText.black:
                        this.menuText.key = this.menuText.black
                        break
                    case this.menuText.dark:
                        this.menuText.key = this.menuText.dark
                        break
                    case this.menuText.gold:
                        this.menuText.key = this.menuText.gold
                        break
                }
                this.$root.$data.config.theme.menuTextColor = this.menuText.key

                this.setStorage()
            },

            selTheme(name){
                switch (name) {
                    case 'default':
                        this.topBg.key = 'blue'
                        this.topBg.color = this.topBg.blue
                        this.$root.$data.config.theme.topBackgroundColor = this.topBg.color

                        this.topText.key = 'white'
                        this.topText.color = this.topText.white
                        this.$root.$data.config.theme.topTextColor = this.topText.color

                        this.menuBg.key = 'white'
                        this.menuBg.color = this.menuBg.white
                        this.$root.$data.config.theme.menuBackgroundColor = this.menuBg.color

                        this.menuText.key = this.menuText.dark
                        this.$root.$data.config.theme.menuTextColor = this.menuText.key
                        break
                    case 'black':
                        this.topBg.key = 'black'
                        this.topBg.color = this.topBg.black
                        this.$root.$data.config.theme.topBackgroundColor = this.topBg.color

                        this.topText.key = 'gold'
                        this.topText.color = this.topText.gold
                        this.$root.$data.config.theme.topTextColor = this.topText.color

                        this.menuBg.key = 'black'
                        this.menuBg.color = this.menuBg.black
                        this.$root.$data.config.theme.menuBackgroundColor = this.menuBg.color

                        this.menuText.key = this.menuText.gold
                        this.$root.$data.config.theme.menuTextColor = this.menuText.key
                        break
                    case 'dark':
                        this.topBg.key = 'dark'
                        this.topBg.color = this.topBg.dark
                        this.$root.$data.config.theme.topBackgroundColor = this.topBg.color

                        this.topText.key = 'white'
                        this.topText.color = this.topText.white
                        this.$root.$data.config.theme.topTextColor = this.topText.color

                        this.menuBg.key = 'dark'
                        this.menuBg.color = this.menuBg.dark
                        this.$root.$data.config.theme.menuBackgroundColor = this.menuBg.color

                        this.menuText.key = this.menuText.white
                        this.$root.$data.config.theme.menuTextColor = this.menuText.key
                        break
                }

                this.setStorage()
            },

            setStorage(){
                window.localStorage.setItem("theme", JSON.stringify(this.$root.$data.config.theme))
            },
        }
    }
</script>