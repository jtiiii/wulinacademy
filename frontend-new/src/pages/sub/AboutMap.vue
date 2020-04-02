<template>
    <div>
        <a class="checkItOut" href="https://uri.amap.com/marker?poiid=B023B17X1K&src=武林书画院&callnative=1"
           target="_blank">{{ $t('about.map.gotoMap') }}</a>
        <f-amap
                style="height: 450px;"
                :api-key="apiKey"
                :basic-control="basicControl"
                :markers="markers"
                @loaded="loaded"
                ref="map"
        />
    </div>
</template>

<script>
    import FComponents from 'f-vue-components';

    export default {
        name: "AboutMap",
        components: {
            "f-amap": FComponents.Third.AMap
        },
        data() {
            return {
                basicControl: ['zoom'],
                markers: [
                    {lng: 120.161036, lat: 30.246365, title: '武林书画院'}
                ],
                apiKey: '1accfe63ebc155603fd447be7681e05d',
                map: null
            };
        },
        methods: {
            loaded(map) {
                this.map = map;
                this.map.setFitView();
            }
        }
    }
</script>
<style lang="less">
    .checkItOut {
        border: 1px solid #eee;
        border-radius: 4px;
        padding: 4px 10px;
        display: inline-block;
        text-decoration: none;
        margin: 10px 0;
    }
</style>