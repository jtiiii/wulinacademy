<template>
    <div class="contact">
        <h2>{{ $t('index.title') }}</h2>
        <p><b>{{ $t('about.contact.phoneNumber') }}</b><span>{{ phoneNumber }}</span></p>
        <p><b>{{ $t('about.contact.email') }}</b><a :href="'mailto:' + email">{{ email }}</a></p>
        <p><b>{{ $t('about.contact.address') }}</b><span>{{ $t('about.contact.physicalAddress') }}</span></p>
    </div>
</template>

<script>
    export default {
        name: "AboutContact",
        data() {
            return {
                phoneNumber: '(086) 0571-85150209',
                email: 'wulinacademyarts@gmail.com',
            };
        }
    }
</script>

<style scoped>

</style>