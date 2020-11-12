<style>
    .spin-icon-load{
        animation: ani-demo-spin 1s linear infinite;
    }

    @keyframes ani-demo-spin {
        from { transform: rotate(0deg);}
        50%  { transform: rotate(180deg);}
        to   { transform: rotate(360deg);}
    }
</style>

<template>
    <Row id="app" class="index-app" style="width: 100%;height:100%;">
        <router-view></router-view>
    </Row>
</template>
