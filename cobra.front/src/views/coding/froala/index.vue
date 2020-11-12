<style lang="less" scoped>

</style>

<template>
    <Row class="max-height">
        <Row class="breadcrumb center-vertical">
            <Breadcrumb class="f12">
                <BreadcrumbItem>froala</BreadcrumbItem>
            </Breadcrumb>
        </Row>
        <Row class-name="p-content">
<!--            <froala :tag="'textarea'" :config="config" v-model="model"></froala>-->
            因为收费所有不用这个了
        </Row>
    </Row>
</template>

<script>
    // import VueFroala from 'vue-froala-wysiwyg'

    export default {
        data() {
            return {
                config: {
                    events: {
                        'froalaEditor.initialized': function () {
                            console.log('initialized')
                        }
                    }
                },
                model: 'Edit Your Content Here!'
            }
        },
        methods: {
            onLoad() {

            }
        }
    }
</script>
