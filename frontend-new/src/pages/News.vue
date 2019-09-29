<template>
    <div class="page-news">
        <!-- 删除确认框 -->
<!--        <v-confirm-modal :show="modal.confirm" @confirm="confirmDelete">-->
<!--            确认删除 {{ getSelected.title }} 此条新闻？-->
<!--        </v-confirm-modal>-->
        <div class="news">
            <!-- 工具栏 -->
            <div class="toolbar">
                <!-- 搜索框 -->
                <label>
                    <input class="searchBox" v-model="news.search"  placeholder="搜索..." type="text" />
                </label>
                <!-- 添加新闻按钮 -->
                <label v-if="manage" class="toolBtn">
                    <button class="tool" type="button" @click="openAddModal">
                        <i class="iconfont wulin-news"></i>
                    </button>
                </label>
            </div>
            <div v-show="!news.list.length"> There is no news....</div>
            <!-- 新闻列表 -->
            <div v-for="newsItem in news.list" class="news-item" >
<!--                <div v-show="manage" class="toolbar-news">-->
<!--                    <label class="toolBtn">-->
<!--                        <button class="tool">-->
<!--                            <i class="iconfont wulin-edit" @click="openUpdateModal(newsItem)"></i>-->
<!--                        </button>-->
<!--                    </label>-->
<!--                </div>-->
<!--                <label v-show="manage" class="deleteBtn">-->
<!--                    <button class="tool-sm">-->
<!--                        <i class="iconfont wulin-delete" @click="openDeleteModal(newsItem)"></i>-->
<!--                    </button>-->
<!--                </label>-->
                <v-article :news="newsItem" @click="openShowNews" />
            </div>
            <button v-if="!news.last" @click="nextPage" type="button" class="btn">see more...</button>
        </div>
        <!-- 添加、编辑新闻内容框 -->
        <v-news-modal class="news-modal" :show="modal.editor" :size="'larger'" >
            <div class="news-panel">
            <label class="closeBtn">
                <button class="tool tool-close">
                    <i class="iconfont wulin-close" @click="closeNewsModal"></i>
                </button>
            </label>
            <!-- 新闻编辑器 -->
            <v-news-editor
                    :newsId="news.selected"
                    :title="'test'"
            />
            </div>
        </v-news-modal>
    </div>
</template>
<script type="text/javascript">
    import StringUtils from '../scripts/utils/StringUtils';
    import SecurityService from '../scripts/api/SecurityService';
    import NewsService from '../scripts/api/NewsService';
    import FComponents from 'f-vue-components';
    import noPic from '../assets/images/no-pic.png';
    import Article from '../components/Article.vue';
    import NewsContent from '../components/NewsContent.vue';


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
        name:'news',
        components:{
            "v-article": Article,
            "v-news-modal": FComponents.Modal,
            'v-news-editor': NewsContent,
            "v-confirm-modal": FComponents.Modal,
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
                news:{
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
        computed:{
            manage: () => true,
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
        },
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
                // this.news.loader(this.getSelected);
                // this.modal.show = true;
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
<style scoped>
    .news-panel{
        padding: 1.25rem;
    }
    .tool.tool-close > i{
        font-size: 1.25rem;
    }
    .tool.tool-close{
        background-color: transparent;
    }
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
        width: 1.25rem;
        height: 1.25rem;
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