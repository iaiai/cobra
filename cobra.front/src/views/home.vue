<style lang="less" scoped>
    .particles{
        position: absolute !important;
        top:0;
        left: 0;
        right: 0;
        bottom:0;
        overflow: hidden;
    }
    .root {
        width: 100%;
        height: 100%;

        background-size: cover;
        background-position: 50%;
        background-image: url("../assets/main_bg.png");

        font-size: 50px;

        color: #2A3746;
    }
    .bottom{
        position: absolute !important;
        bottom: 0;
        left: 0;
        right: 0;
        height: 45px;

        background-color:rgba(255,255,255,0.7);
    }
</style>

<template>
    <Row class="max-size">
        <Row class-name="particles">
            <vue-particles
                    color="#ccc"
                    :particleOpacity="0.7"
                    :particlesNumber="60"
                    shapeType="circle"
                    :particleSize="4"
                    linesColor="#ccc"
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
        </Row>
        <Row class-name="root center">
            <Row>眼镜蛇 <span class="f30">(v1.0.0)</span></Row>
        </Row>

        <Row class="bottom top-outer-shadow center f12 color-bbb">
            {{$root.system.copyright}} {{$root.system.companyName}}
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
                this.$http('home', null, true).then(res => {})
            },
        }
    }
</script>
