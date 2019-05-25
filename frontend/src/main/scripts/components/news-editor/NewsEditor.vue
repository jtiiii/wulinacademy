<script type="text/javascript">
    import Option from './news-editor';
    export default Option;
</script>
<template>
    <div>
        <v-input-text v-show="isEditing" class="title" v-model="news.title" :width="'100%'" :placeholder="'Enter the title here...'" :height="'35px'" />
        <v-input-text v-show="isEditing" class="event-date" v-model="news.eventDate" :placeholder="'Enter the date of event here...'" />
        <v-editor v-show="isEditing" @init="initEditor" :height="'600px'" v-model="news.content" />
        <v-button v-show="isEditing" class="btn" @click="submit" :type="'success'" :width="'200px'" >{{ $t('button.submit') }}</v-button>
        <v-button v-show="isEditing" class="btn" @click="reset" :type="'warning'" :width="'200px'" >{{ $t('button.reset') }}</v-button>
        <div v-if="isPreview" style="border:none;" class="editor ql-container ql-snow">
            <h2>{{ news.title }}</h2>
            <p>{{ news.eventDate }}</p>
            <div class="ql-editor" style="border:none;" v-html="html">
            </div>
        </div>
    </div>
</template>
<style scoped>
    .title{
        margin: 10px 0;
        font-size: 20px;
        font-weight: bolder;
        box-sizing: border-box;
    }
    .title::placeholder{
        color: #eee;
    }
    .btn{
        margin: 5px;
    }
    .event-date{

    }
</style>