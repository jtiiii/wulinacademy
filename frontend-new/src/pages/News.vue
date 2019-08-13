<template>
    <div class="page-news">
        <!-- 删除确认框 -->
        <v-confirm-modal :show="modal.confirm" @confirm="confirmDelete">
            确认删除 {{ getSelected.title }} 此条新闻？
        </v-confirm-modal>
        <div class="news">
            <!-- 工具栏 -->
            <div class="toolbar">
                <!-- 搜索框 -->
                <label>
                    <input class="searchBox" v-model="news.search"  placeholder="搜索..." type="text" />
                </label>
                <!-- 添加新闻按钮 -->
                <label v-if="manage" class="toolBtn">
                    <button class="tool"><img class="btnImg" :src="icon.add" @click="openAddModal"></button>
                </label>
            </div>
            <div v-show="!news.list.length"> There is no news....</div>
            <!-- 新闻列表 -->
            <div v-for="newsItem in news.list" class="news-items" >
                <div v-show="manage" class="toolbar-news">
                    <label class="toolBtn">
                        <button class="tool"><img class="btnImg" :src="icon.edit" @click="openUpdateModal(newsItem)"></button>
                    </label>
                </div>
                <label v-show="manage" class="deleteBtn">
                    <button class="tool-sm"><img class="btnImg" :src="icon.delete" @click="openDeleteModal(newsItem)" /></button>
                </label>
                <v-article :news="newsItem" @click="openShowNews" />
            </div>
            <button v-if="!news.last" @click="nextPage" type="button" class="btn">see more...</button>
        </div>
        <!-- 添加、编辑新闻内容框 -->
        <v-news-modal class="news-modal" :canClose="true" :show="modal.editor" >
            <label class="closeBtn">
                <button class="tool"><img class="btnImg" :src="icon.close" @click="modal.editor = false"></button>
            </label>
            <!-- 新闻编辑器 -->
            <v-news-editor :mode="mode" @load="getLoader" @submit="submit"/>
        </v-news-modal>
    </div>
</template>
<script type="text/javascript">
    import editIcon from '../icons/edit.png';
    import addIcon from '../icons/add.png';
    import pickUpIcon from '../icons/caret-up.png';
    import pickDownIcon from '../icons/caret-down.png';
    import deleteIcon from '../icons/delete.png';
    import closeIcon from '../icons/close.png';
    import StringUtils from '../scripts/utils/StringUtils';

    import noPic from '../images/news/no-pic.png';

    import BaseModal from '../scripts/components/BaseModal.vue';
    import NewsEditor from '../scripts/components/news-editor/NewsEditor.vue';
    import ConfirmModal from '../scripts/components/ConfirmModal.vue';

    import SecurityService from '../scripts/api/SecurityService';
    import NewsService from '../scripts/api/NewsService';

    import Article from '../scripts/components/Article.vue';

    import {mapState} from 'vuex';


    function NewsItem({id,title,eventDate,status,preview,thumbnail, content}){
        this.id = id;
        this.title = title;
        this.eventDate = eventDate;
        this.status = status;
        this.preview = preview;
        this.thumbnail = thumbnail;
        this.time = eventDate.substr(0,10);
        this.ddClass = { expand: false , shrink: false, select: false};
        this.loaded = false;
        this.content = content? content: null;
    }
    NewsItem.prototype = {
        constructor: NewsItem,
        loadContent(){
            if(this.loaded){
                return new Promise( resolve => {
                    resolve(this.content);
                });
            }
            return NewsService.getContent(this.id).then( data => {
                this.content = data.content;
                this.loaded = true;
                return this.content;
            });
        }
    };
    NewsItem.of = obj => obj instanceof NewsItem ? obj : new NewsItem(obj);

    let currentTimeout = null;
    export default {
        components:{
            "v-article": Article,
            "v-news-modal": BaseModal,
            'v-news-editor': NewsEditor,
            "v-confirm-modal": ConfirmModal,
        },
        props:{
            newsId: {
                type: String,
                required: false
            }
        },
        data: function() {
            return {
                isLogin: SecurityService.isLogin,
                modal:{
                    editor: false,
                    confirm: false,
                },
                mode:'editing',
                defaultThumbnail: noPic,
                icon:{
                    edit: editIcon,
                    add: addIcon,
                    delete: deleteIcon,
                    pickUp: pickUpIcon,
                    pickDown: pickDownIcon,
                    close: closeIcon
                },
                news:{
                    loader: null,
                    search: '',
                    list:[],
                    contents: {},
                    pageNum: 0,
                    pageSize: 10,
                    eventDate: null,
                    totalPages: 0,
                    last: true,
                    total: 0,
                    map:{},
                    selected: undefined
                }
            }
        },
        computed: mapState({
            manage: state => state.isLogin,
            getSelected(){
                if(this.news.selected){
                    return this.news.map[this.news.selected];
                }
                return {};
            },
            isUpdate(){
                return Boolean(this.getSelected.id);
            },
            isPreview(){
                return this.mode === 'preview';
            }
        }),
        watch:{
            'news.search'( text, old ){
                if(text === old){
                    return;
                }
                let _vue_ = this;
                clearTimeout( currentTimeout );
                currentTimeout  = setTimeout(_vue_.getNewsPage.bind(_vue_,true),500);
            }
        },
        methods:{
            loadModal(){
                this.news.loader(this.getSelected);
            },
            select( id ){
                this.news.selected = id;
            },
            openShowNews( newsItem ){
                this.select(newsItem.id);
                this.showNewsModal().then( () => {
                    this.mode = 'preview';
                });
            },
            confirmDelete( confirm ){
                if(confirm){
                    NewsService.delete( this.news.selected )
                        .then( () => this.getNewsPage( true ) );
                }
                this.modal.confirm = false;
            },
            openAddModal(){
                this.select(undefined);
                this.mode = 'editing';
                this.showNewsModal().then( () => {
                    // this.mode = 'editing';
                });
            },
            openDeleteModal( newsItem ){
                this.select(newsItem.id);
                this.modal.confirm = true;
            },
            submit( news, text ){
                let forSave = this.isUpdate? Object.assign(this.getSelected,news): NewsItem.of(news);
                forSave.preview = StringUtils.fixLength(text,120,'');
                if(text.length > 120){
                    forSave.preview += '...';
                }
                let saveOrUpdate = new Promise(resolve => {
                    if(this.isUpdate){
                        NewsService.update(forSave).then( () => {
                            resolve(forSave);
                        });
                    }else{
                        NewsService.save(forSave).then( id => {
                            forSave.id = id;
                            resolve(forSave);
                        });
                    }
                });
                saveOrUpdate.then( forSave => {
                    return NewsService.saveContent(forSave);
                }).then( () => {
                    this.closeNewsModal();
                    this.getNewsPage(true);
                });
            },
            openUpdateModal( newsItem ){
                this.select(newsItem.id);
                this.showNewsModal().then( () => {
                    this.mode = 'editing';
                });

            },
            showNewsModal(){
                return new Promise( resolve => {
                    if(this.isUpdate || this.isPreview ){
                        this.getSelected.loadContent().then( () => {
                            resolve();
                        });
                    }else{
                        resolve();
                    }
                }).then( () => {
                    this.loadModal();
                    this.modal.editor = true;
                });

            },
            closeNewsModal(){
                this.modal.editor = false;
            },
            nextPage(){
                this.news.pageNum ++;
                this.getNewsPage();
            },
            getNewsPage(refresh ){
                let _vue_ = this;
                let search = this.news.search;
                let eventDate = this.news.eventDate;
                let pageNum = refresh? 0: this.news.pageNum;
                let pageSize = this.news.pageSize;
                if(refresh){
                    this.select( undefined);
                }
                NewsService.pageSearch({search,pageSize,pageNum,eventDate}).then( data => {
                    _vue_._setNews(data,!refresh);
                });
            },
            _setNews( pageData, append ){
                if(!append){
                    this.news.list = [];
                    this.news.map = {};
                }
                let list = this.news.list;
                this.news.totalPages = pageData.totalPages;
                this.news.total = pageData.totalElements;
                this.news.pageNum = pageData.number;
                this.news.pageSize = pageData.size;
                this.news.last = pageData.last;
                pageData.content.forEach( news => {
                    list.push(news);
                    this.news.map[news.id] = NewsItem.of(news);
                });
            },
            getLoader( loader ){
                this.news.loader = loader;
            }
        },
        mounted(){

        },
        created(){
            this.getNewsPage();
        }
    }
</script>
<style>
    @import url('../styles/button.css');
</style>
<style scoped>
    .page-news{
        min-height: 400px;
    }
    .toolbar-news{
        width: 30px;
        position: absolute;
        left: -35px;
        top: 10px;
        border: none;
    }
    .toolBtn{
        margin: 5px;
    }

    .btnImg{
        display: inline-block;
        width: 100%;
        height: 100%;
        position: relative;
    }
    .tool{
        display: inline-block;
        width: 30px;
        height: 30px;
        border: none;
        padding: 0;
    }
    .tool-sm{
        display: inline-block;
        width: 20px;
        height: 20px;
        border: none;
        padding: 0;
    }
    .news{
        position: relative;
        display:flex;
        flex-flow: column wrap;
        align-items: center;
    }
    @media screen and (min-width:700px) {
        .news-modal >>> dialog{
            width: 700px;
        }
        .news{
            min-width: 700px;
        }
        .toolbar{
            width: 700px;
            clear: both;
            display: inline-block;
        }
        .searchBox{
            width: 250px;
        }
        .toolbar > label{
            float: left;
            margin: 10px 10px;
        }
    }

    @media screen and (max-width:700px) {
        .news-modal >>> dialog{
            width: 100%;
        }
        .toolbar{
            display: flex;
            flex-flow: row wrap;
            align-items: center;
            flex:1;
            width: 100%;
        }
        .searchBox{
            width: 100%;
        }
        .toolbar > label{
            width: 100%;
            margin: 10px;
        }
    }

    .searchBox{
        border-bottom-left-radius: 5px;
        border-bottom-right-radius: 5px;
        border: 2px solid #aaa;
        border-top: 0;
        height: 30px;
        outline: none;
        font-size: 20px;
        box-sizing: border-box;
    }
    .searchBox::placeholder{
        color: #efefef;
    }
    .select{
        background: #daecff;
    }
    .closeBtn{
        display: block;
        position: absolute;
        right: 5px;
        top: 5px;
        z-index: 9;
    }
    .deleteBtn{
        display: inline-block;
        position: absolute;
        right: -8px;
        top: 0;
        z-index: 9;
    }
    /*.news-items{*/
    /*    display: inline-block;*/
    /*    position:relative;*/
    /*}*/

</style>