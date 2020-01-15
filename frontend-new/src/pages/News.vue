<template>
    <div class="news">
        <div class="news-page">
            <!-- 搜索框 -->
            <label class="tool-bar">
                <input class="searchBox" v-model="news.search"  placeholder="搜索..." type="text" />
                <span class="buttons" v-if="manage">
                    <a class="iconfont wulin-create" @click="createNews"/>
                </span>
            </label>
            <div v-show="!news.list.length">There is no news....</div>
            <!-- 新闻列表 -->
            <div v-for="newsItem in news.list" class="news-item" :key="'news-'+ newsItem.id" @click="selectAndOpenNewsDetail(newsItem)" >
                <v-card :flow="'row'" :cover="newsItem.thumbnail || defaultThumbnail" :coverText="newsItem.title">
                    <h3 class="item-title">{{ newsItem.title }}</h3>
                    <h6 class="item-date">{{ new Date(newsItem.eventDate).toLocaleString() }}</h6>
                    <p class="item-preview">{{ newsItem.preview }}</p>
                </v-card>
            </div>
            <button v-if="!news.last" @click="nextPage" type="button" class="btn">see more...</button>
        </div>
        <!-- 添加、编辑新闻内容框 -->
        <v-dialog :canClose="true" :show="modal.content" :size="'larger'" @close="modal.content = false" >
            <div class="news-content">
                <label class="header-img">
                    <img alt="头图" :src="news.modify.thumbnail || defaultThumbnail"/>
                </label>
                <h2 class="title" v-if="isPreview" >{{ news.modify.title }}</h2>
                <label class="title" v-else >
                    <input type="text" v-model="news.modify.title" />
                </label>
                <label class="date">
                    <span  v-if="isPreview" >{{ news.modify.eventDate.dateStr }}</span>
                    <input v-else type="text" v-model="news.modify.eventDate.dateStr" />
                    <input hidden type="text" v-model="news.modify.eventDate.gmt" />
                </label>
                <hr/>
                <div v-if="isPreview" v-html="news.modify.content" />
                <v-editor v-else @init="initEditor" v-model="news.modify.content"/>
            </div>
            <div class="news-content-tool-bar" v-if="manage">
                <label>
                    <v-button v-show="isPreview" :size="'small'" :emotion="'warning'" @click="switchEditing"><span>编辑</span><i class="iconfont wulin-edit" /></v-button>
                </label>
                <label>
                    <v-button v-show="!isPreview" :size="'small'" :emotion="'success'" @click="saveOrUpdateNews"><span>保存</span><i class="iconfont wulin-save" /></v-button>
                </label>
                <label>
                    <v-button v-show="isPreview" :size="'small'" :emotion="'danger'"><span>删除</span><i class="iconfont wulin-delete" /></v-button>
                </label>
                <label>
                    <v-button v-show="!isPreview" :size="'small'" @click="modal.content = false"><span>取消</span><i class="iconfont wulin-cancel" /></v-button>
                </label>
            </div>
        </v-dialog>
    </div>
</template>
<script type="text/javascript">
    import SecurityService from '../scripts/api/SecurityService';
    import NewsService from '../scripts/api/NewsService';
    import _ from 'lodash';
    import FComponents from 'f-vue-components';
    import noPic from '../assets/images/no-pic.png';
    import FUtils from 'fo-utils';
    const ArrayUtils = FUtils.ArrayUtils;
    const StringUtils = FUtils.StringUtils;

    export default {
        name:'news',
        components:{
            'v-editor': FComponents.Third.Quill,
            'v-card': FComponents.Card,
            "v-dialog": FComponents.Dialog,
            "v-button": FComponents.Button
        },
        props:{
            newsId: {
                type: String,
                required: false
            }
        },
        data: function() {
            return {
                manage: false,
                isLogin: SecurityService.isLogin,
                modal:{
                    confirm: false,
                    content: false,
                },
                mode:'preview',
                modes:['add','edit','delete'],
                defaultThumbnail: noPic,
                editor: null,
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
                        content: '',
                        thumbnail: null,
                        eventDate: {
                            dateStr: '',
                            gmt: '+8',
                            // date: null,
                        }
                    },
                    selected: undefined,
                    content:'',
                    searchDebounce: null,
                }
            }
        },
        computed:{
            getSelected(){
                if(this.news.selected){
                    return this.news.cache[this.news.selected];
                }
                return {};
            },
            isUpdate(){
                return Boolean(this.getSelected.id);
            },
            isPreview(){
                return this.mode === 'preview';
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
                this.news.modify.content = '';
                this.news.modify.eventDate.dateStr = '';
                this.news.modify.previews = '';
                this.news.modify.thumbnail = '';
            },
            setNewsDialog(title, eventDate , thumbnail , content, uuid ){
                this.news.modify.title = title;
                this.news.modify.thumbnail = thumbnail;
                this.news.modify.eventDate.dateStr = new Date(eventDate).toDateString();
                if(uuid){
                    return NewsService.getContent(uuid).then( result => {
                        this.news.modify.content = result;
                    });
                }
                return new Promise( resolve => {
                    this.new.modify.content = content;
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
            },
            nextPage(){
                this.news.pageNum ++;
                this.getNewsPage();
            },
            getNewsPage(refresh ){
                NewsService.pageSearch({keywords: this.news.search,pageSize:this.news.page.pageSize, pageNum: refresh? 0: this.news.page.pageNum}).then( data => {
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
                let preview = StringUtils.fixLength(this.editor.getText(),200,'');
                let eventDate = new Date(this.news.modify.eventDate.dateStr).getTime();
                if(this.isUpdate){
                    let forUpdate = this.getSelected;
                    return NewsService.update(forUpdate.id,{
                        title: this.news.modify.title,
                        preview: preview,
                        eventDate: eventDate,
                        thumbnail: this.news.modify.thumbnail
                    }).then( () => {
                        this.updateSelected(this.getSelected.id, this.news.modify.title, preview, eventDate, this.news.modify.thumbnail);
                    })
                    .then( () => NewsService.updateContent(forUpdate.id, this.news.modify.content))
                    .then( () => {
                        this.switchPreview();
                    });
                }
                return NewsService.save({
                    title: this.news.modify.title,
                    preview: preview,
                    eventDate: eventDate,
                    thumbnail: this.news.modify.thumbnail
                }).then( id => NewsService.updateContent(id, this.news.modify.content) ).then( () => {
                    this.switchPreview();
                    this.getNewsPage(true);
                });
            },
            updateSelected(id, title, preview, eventDate, thumbnail){
                let forUpdate = this.getSelected;
                forUpdate.title = title;
                forUpdate.eventDate = eventDate;
                forUpdate.thumbnail = thumbnail;
                forUpdate.preview = preview;
            },
            deleteNews(){

            },
            initDebounce(){
                this.searchDebounce = _.debounce( this.getNewsPage.bind(this,[true]),500);
            },
            initEditor( editor ){
                this.editor = editor;
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
    @import url('../assets/styles/components/news.less');
</style>