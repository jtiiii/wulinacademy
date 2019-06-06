<script type="text/javascript">
    import Option from './news-editor';
    export default Option;
</script>
<template>
    <div>
        <div v-if="isEditing" class="cover" @mouseenter="show.coverBtn = true" @mouseleave="show.coverBtn = false">
            <div class="cover-image">
                <img :src="news.thumbnail? news.thumbnail: defaultCover"/>
            </div>
            <v-button v-show="show.coverBtn" @click="openCoverModal" class="btn" :type="'info'">select cover</v-button>
            <v-modal style="position:relative" :show="show.imageControl" :width="'710px'" :height="'auto'" @close="show.imageControl = false">
                <image-control @init="imageControlLoad" @image-click="selectCover"></image-control>
            </v-modal>
        </div>
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
    .cover{
        margin-top: 30px;
        width: 100%;
        box-sizing: border-box;
        border-bottom: 2px #eee dashed;
        position: relative;
        padding-bottom: 10px;
        z-index: 2;
    }
    .cover-image{
        width: 100%;
        height: 350px;
        overflow: hidden;
    }
    .cover-image > img{
        width: 100%;
    }
    .cover-image:hover{
        opacity: .7;
    }
    .cover > .btn{
        position: absolute;
        right: 5px;
        bottom: 5px;
    }
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