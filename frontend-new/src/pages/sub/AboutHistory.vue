<template>
    <div class="history">
        <p>{{ $t('about.history.h1') }}</p>
        <p>{{ $t('about.history.h2') }}</p>
        <p>{{ $t('about.history.h3')}}</p>
        <div class="img">
            <img :alt="$t('about.history.t1')" :src="pics[1]"/>
            <span>{{ $t('about.history.t1') }}</span>
        </div>
        <div class="img">
            <img :alt="$t('about.history.t2')" :src="pics[2]"/>
            <span>{{ $t('about.history.t2') }}</span>
        </div>
        <div class="img">
            <img :alt="$t('about.history.t3')" :src="pics[4]"/>
            <span>{{ $t('about.history.t3') }}</span>
        </div>
        <div class="img">
            <img :alt="$t('about.history.t4')" :src="pics[3]"/>
            <span>{{ $t('about.history.t4') }}</span>
        </div>
    </div>
</template>

<script>
    import pic1 from '../../assets/images/about-pic/photo2.jpg';
    import pic2 from '../../assets/images/about-pic/photo3.jpg';
    import pic3 from '../../assets/images/about-pic/photo4.jpg';
    import pic4 from '../../assets/images/about-pic/photo5.jpg';
    import pic5 from '../../assets/images/about-pic/photo6.jpg';

    export default {
        name: "AboutHistory",
        data() {
            return {
                pics: [
                    pic1, pic2, pic3, pic4, pic5
                ]
            };
        }
    }
</script>

<style scoped>

</style>