<template>
    <div class="news">
        <div class="news-page">
            <!-- 搜索框 -->
            <label class="tool-bar">
                <input class="searchBox" v-model="news.search"  placeholder="搜索..." type="text" />
                <span class="buttons" v-if="isLogin">
                    <a class="iconfont wulin-create" @click="createNews"/>
                </span>
            </label>
            <div v-show="!news.list.length">There is no news....</div>
            <!-- 新闻列表 -->
            <div v-for="newsItem in news.list" class="news-item" :key="'news-'+ newsItem.id" @click="selectAndOpenNewsDetail(newsItem)" >
                <div class="news-invisible" v-if="isLogin && !newsItem.enable">
                    <a class="iconfont wulin-invisible" />
                </div>
                <v-card :flow="'row'" :cover="getThumbnailSrc(newsItem.thumbnail) || defaultThumbnail" :coverText="newsItem.title">
                    <h3 class="item-title">{{ newsItem.title }}</h3>
                    <h6 class="item-date">{{ new Date(newsItem.eventDate).toLocaleString() }}</h6>
                    <p class="item-preview">{{ newsItem.preview }}</p>
                </v-card>
            </div>
            <a v-if="!news.page.last" @click="nextPage" class="page-next">
                <i class="iconfont wulin-more1" />
            </a>
        </div>
        <!-- 添加、编辑新闻内容框 -->
        <v-dialog :canClose="true" :show="modal.content" :size="'larger'" @close="modal.content = false" >
            <div class="news-content">
                <label class="header-img">
                    <img v-if="isPreview" alt="头图" :src="getThumbnailSrc(news.modify.thumbnail) || defaultThumbnail"/>
                    <img v-else class="header-img-switch" @click="selectThumbnail" alt="头图" :src="getThumbnailSrc(news.modify.thumbnail) || defaultThumbnail"/>
                </label>
                <h2 class="title" v-if="isPreview" >{{ news.modify.title }}</h2>
                <label class="title" v-else >
                    <input type="text" v-model="news.modify.title" />
                </label>
                <label class="date">
                    <span v-if="isPreview">{{ modifyEventDateFormat }}</span>
                    <v-input-date v-else v-model="news.modify.eventDate"/>
                </label>
                <hr/>
                <!-- 编辑器 -->
                <v-editor @init="setEditor" :editing="!isPreview" :registers="registers" :handlers="getEditorHandlers()" v-model="news.modify.content"/>
            </div>
            <div class="news-content-tool-bar" v-if="isLogin">
                <label v-show="isPreview">
                    <v-button :size="'small'" :emotion="'warning'" @click="switchEditing"><span>编辑</span><i class="iconfont wulin-edit" /></v-button>
                </label>
                <label v-show="!isPreview">
                    <v-button :size="'small'" :emotion="'success'" @click="saveOrUpdateNews"><span>保存</span><i class="iconfont wulin-save" /></v-button>
                </label>
                <label v-show="isPreview">
                    <v-button :size="'small'" :emotion="'danger'" @click="deleteNews"><span>删除</span><i class="iconfont wulin-delete" /></v-button>
                </label>
                <label v-show="!isPreview">
                    <v-button :size="'small'" @click="modal.content = false"><span>取消</span><i class="iconfont wulin-cancel" /></v-button>
                </label>
                <label v-if="isUpdate" >
                    <v-button v-if="selected.enable" :size="'small'" :emotion="'warning'" @click="disableNews"><span>撤回</span><i class="iconfont wulin-invisible" /></v-button>
                    <v-button v-else :size="'small'" :emotion="'success'" @click="enableNews"><span>发布</span><i class="iconfont wulin-visible" /></v-button>
                </label>
            </div>
            <!-- 图片选择 -->
            <v-modal v-if="isLogin" :show="modal.imageControl" :width="'815px'" :canClose="true" @close = "modal.imageControl = false">
                <template #title>图片选择</template>
                <image-control @image-click="selectImage" />
            </v-modal>
        </v-dialog>
    </div>
</template>
<script type="text/javascript">
    import NewsService from '../scripts/api/NewsService';
    import _ from 'lodash';
    import FComponents from 'f-vue-components';
    import noPic from '../assets/images/no-pic.png';
    import FUtils from 'fo-utils';
    import ServerImage from "../scripts/quill/ServerImage";
    import ImageService from "../scripts/api/ImageService";
    const ImageControl = resolve => require(["../components/ImageControl.vue"],resolve );
    const ArrayUtils = FUtils.ArrayUtils;
    const StringUtils = FUtils.StringUtils;

    export default {
        name:'news',
        components: {
            'v-editor': FComponents.Third.Quill,
            'v-card': FComponents.Card,
            "v-dialog": FComponents.Dialog,
            "v-button": FComponents.Button,
            "v-modal": FComponents.Modal,
            "v-input-date": FComponents.Input.Date,
            'image-control': ImageControl,
        },
        props:{
            newsId: {
                type: String,
                required: false
            }
        },
        data: function() {
            return {
                manage: true,
                modal:{
                    confirm: false,
                    content: false,
                    imageControl: false,
                },
                mode:'preview',
                modes:['add','edit','delete'],
                defaultThumbnail: noPic,
                editor: null,
                imageControlSelectTarget: 'thumbnail' ,
                imageControlSelectTargets: {
                    0:'thumbnail',
                    1:'content'
                },
                registers:[ServerImage],
                news:{
                    page:{
                        content: [],
                        pageNum: 0,
                        pageSize: 10,
                        eventDate: null,
                        totalPages: 0,
                        last: true,
                        total: 0,
                    },
                    search: '',
                    list:[],
                    defaultThumbnail: noPic,
                    cache:{},
                    title:'',
                    modify:{
                        title:'',
                        content: {
                            delta: null,
                            html: '',
                        },
                        thumbnail: null,
                        eventDate: new Date().getTime()
                    },
                    selected: undefined,
                    content:'',
                    searchDebounce: null,
                }
            }
        },
        computed:{
            selected(){
                if(this.news.selected){
                    return this.news.cache[this.news.selected];
                }
                return {};
            },
            isUpdate(){
                return Boolean(this.selected.id);
            },
            isPreview() {
                return this.mode === 'preview';
            },
            isLogin() {
                return this.$store.state.isLogin;
            },
            isThumbnailImageTarget() {
                return this.imageControlSelectTarget === this.imageControlSelectTargets[0];
            },
            modifyEventDateFormat() {
                return new Date(this.news.modify.eventDate).toLocaleString();
            }
        },
        watch:{
            'news.search'( text, old ){
                if(text === old){
                    return;
                }
                this.searchDebounce();
            }
        },
        methods:{
            imageHandler(){
                this.imageControlSelectTarget = this.imageControlSelectTargets[1];
                this.modal.imageControl = true;
            },
            select( newsItem ){
                this.news.selected = newsItem.id;
            },
            unselect(){
                this.news.selected = undefined;
            },
            selectAndOpenNewsDetail(newsItem ){
                this.select(newsItem);
                this.switchPreview();
                this.openNewsDetail(newsItem);
            },
            cleanModify(){
                this.news.modify.title = '';
                this.news.modify.content.delta = null;
                this.news.modify.content.html = '';
                this.news.modify.eventDate = new Date().getTime();
                this.news.modify.previews = '';
                this.news.modify.thumbnail = '';
            },
            setNewsDialog(title, eventDate , thumbnail , content, uuid ){
                this.news.modify.title = title;
                this.news.modify.thumbnail = thumbnail;
                this.news.modify.eventDate = eventDate;
                if(uuid){
                    return NewsService.getContent(uuid).then( result => {
                        this.news.modify.content.delta = result;
                    });
                }
                return new Promise( resolve => {
                    this.new.modify.content.delta = content;
                    resolve();
                });
            },
            openNewsDetail( news ){
                this.setNewsDialog(news.title, news.eventDate,news.thumbnail,null, news.uuid)
                .then( () => {
                    this.modal.content = true;
                });
            },
            switchPreview(){
                this.mode = 'preview';
            },
            switchEditing(){
                this.mode = 'editing';
            },
            cleanNews(){
                ArrayUtils.clean(this.news.list);
                this.cleanPage();
            },
            cleanPage(){
                ArrayUtils.clean(this.news.page.content);
                this.news.page.last = true;
                this.news.page.total = 0;
                this.news.page.pageNum = 0;
                this.news.page.eventDate = null;
            },
            getThumbnailSrc( thumbnail){
                if(thumbnail){
                    let temp = thumbnail.split(".");
                    return ImageService.getImageSrc( temp[0], temp[1]);
                }
                return null;
            },
            nextPage(){
                this.news.pageNum ++;
                this.getNewsPage();
            },
            selectThumbnail(){
                this.modal.imageControl = true;
                this.imageControlSelectTarget = this.imageControlSelectTargets[0];
            },
            getNewsPage( refresh ){
                NewsService.pageSearch({keywords: this.news.search,pageSize:this.news.page.pageSize, pageNum: refresh? 0: this.news.page.pageNum, withInvisible: this.isLogin }).then( data => {
                    if(refresh){
                        this.cleanNews();
                    }
                    ArrayUtils.copy(data.content,this.news.page.content);
                    if(!data.empty){
                        data.content.forEach( news => {
                            this.news.cache[news.id] = news;
                            this.news.list.push(news);
                        });
                    }
                });
            },
            createNews(){
                this.unselect();
                this.cleanModify();
                this.switchEditing();
                this.modal.content = true;
            },
            saveOrUpdateNews(){
                let preview = StringUtils.fixLength(this.editor.getText(), 200, '');
                let eventDate = this.news.modify.eventDate;
                let updateOrSaveTask = null;
                let isUpdate = this.isUpdate;
                if(isUpdate){
                    let forUpdate = this.selected;
                    updateOrSaveTask =  NewsService.update(forUpdate.id,{
                        title: this.news.modify.title,
                        preview: preview,
                        eventDate: eventDate,
                        thumbnail: this.news.modify.thumbnail
                    }).then( () => {
                        this.updateSelected(this.selected.id, this.news.modify.title, preview, eventDate, this.news.modify.thumbnail);
                        return forUpdate.id;
                    });
                }else{
                    updateOrSaveTask = NewsService.save({
                        title: this.news.modify.title,
                        preview: preview,
                        eventDate: eventDate,
                        thumbnail: this.news.modify.thumbnail
                    });
                }
                return updateOrSaveTask.then( id => this.updateNewsContent(id)).then( () => {
                    if(!isUpdate){
                        this.getNewsPage(true);
                    }
                    this.switchPreview();
                });

            },
            updateSelected(id, title, preview, eventDate, thumbnail){
                let forUpdate = this.selected;
                forUpdate.title = title;
                forUpdate.eventDate = eventDate;
                forUpdate.thumbnail = thumbnail;
                forUpdate.preview = preview;
            },
            updateNewsContent(id){
                return NewsService.updateContent(id, this.news.modify.content.delta );
            },
            deleteNews(){
                let id = this.selected.id;
                let i = 0;
                for(; i < this.news.list.length; i++){
                    if(this.news.list[i].id === id){
                        break;
                    }
                }
                return NewsService.delete(id).then( () => {
                    this.news.list.splice(i,1);
                } );
            },
            enableNews(){
                return NewsService.visible(this.selected.id, true)
                .then( () => {
                    this.selected.enable = true;
                })
            },
            disableNews(){
                return NewsService.visible(this.selected.id, false).then( () => {
                    this.selected.enable = false;
                });
            },
            initDebounce(){
                this.searchDebounce = _.debounce( () => {
                    let id = Number(this.news.search);
                    if(id && !isNaN(id) ){
                        this.getNews(id);
                    }else{
                        this.getNewsPage(true);
                    }

                },500);
            },
            getNews( id ){
                return NewsService.getNews(id).then( news => {
                    this.cleanNews();

                });
            },
            setEditor( editor ){
                this.editor = editor;
            },
            getEditorHandlers(){
                return {
                    image: this.imageHandler.bind(this)
                };
            },
            changeThumbnail( image ){
                this.news.modify.thumbnail = image.sha1Md5 + '.' + image.suffix;
            },
            selectImage( image ){
                if(this.isThumbnailImageTarget){
                    this.changeThumbnail( image );
                }else{
                    this.insertImage( image );
                }
                this.modal.imageControl = false;
            },
            insertImage( image ){
                let index = this.editor.getSelection(true).index;
                this.editor.insertEmbed(index,'server_image',image, 'api');
            }
        },
        mounted(){

        },
        created(){
            this.initDebounce();
            this.getNewsPage(true);
        }
    }
</script>
<style lang="less">
    @import url('../assets/styles/pages/news.less');
</style>