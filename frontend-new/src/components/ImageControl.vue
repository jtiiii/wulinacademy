<template>
    <div class="image-control">
        <!-- 文件夹控制 -->
        <v-panel class="folder" :width="'210px'" :height="'600px'">
            <div class="tool">
                <v-button @click="openFolderModal('add')" :size="'small'" :emotion="'success'">新增</v-button>
            </div>
            <v-tree :list="folders" :emotion="'info'" >
                <template #switch="{item}">
                    <a @click="expand(item)" :class="{'expand': item.show }" ><i class="iconfont wulin-caret-down"/></a>
                </template>
                <template #item="{item}">
                    <a :class="{ 'select': item.select}" @click="selectFolder(item)">{{item.text}}</a>
                </template>
            </v-tree>
        </v-panel>
        <!-- 图片预览 -->
        <v-panel class="folder-image" :width="'480px'" :height="'600px'">
            <div>
                {{ selected? folderPath : '' }}
                <br/>
                <v-button v-show="this.selected" @click="openUploadModal" :size="'small'" :emotion="'default'">上传图片</v-button>
            </div>
            <div>
                <thumbnail v-for="t in thumbnails" @image-click="imageClick(t)" :key="'thumbnail-' +t.id" :src="t.src">
                    {{t.name}} {{t.suffix}}
                </thumbnail>
            </div>
        </v-panel>
        <!-- 上传控件 -->
        <image-upload class="image-upload-modal"
                      :title="'上传图片'"
                      :show="uploadModal.show"
                      :images="uploadModal.images"
                      @submit="uploadImage"
                      @close="uploadModal.show = false"
        />
        <!-- 文件夹编辑 -->
        <v-modal :show="folderModal.show" :size="'medium'" :emotion="folderModal.config[folderModal.mode].type" @close="folderModal.show = false" >
            <template #title>{{ folderModal.config[folderModal.mode].title }}</template>
            名称：<input-text :disabled="folderModal.mode === 'delete'" v-model="folderModal.folder.name" />
            <br/>
            位置： {{folderPath}} / {{ folderModal.folder.name }}
            <br/>
            <v-button @click="saveFolder" :size="'small'" :emotion="'success'">新增</v-button>
        </v-modal>
    </div>
</template>
<script>
    import FComponents from 'f-vue-components'
    import FolderService from '../scripts/api/FolderService';
    import FUtils from 'fo-utils';
    import ImageService,{IMAGE_SITE_PREFIX} from '../scripts/api/ImageService';

    const ArrayUtils = FUtils.ArrayUtils;
    class TreeNode{
        constructor({ text, id, show = false,select = false, children = [], parent}){
            this.id = id;
            this.text = text;
            this.show = show;
            this.select = select;
            this.parent = parent;
            if(children && children.length ){
                this.children = children.map( TreeNode.of );
            }else{
                this.children = [];
            }
        }

        static of(obj){
            return obj instanceof TreeNode? obj: new TreeNode(obj);
        }
        static fromFolder( folder, cache){
            let result = TreeNode.of({text:folder.name, id:folder.id, parent: cache[folder.parent]});
            cache[result.id] = result;
            return result;
        }
        static fromFolders( folders , cache){
            return folders.map( folder => TreeNode.fromFolder(folder, cache));
        }
    }

    export default {
        components:{
            'v-button': FComponents.Button,
            'v-modal': FComponents.Modal,
            'v-tree': FComponents.Tree,
            'image-upload': FComponents.Upload.Image,
            'v-panel': FComponents.Panel,
            'input-text': FComponents.Input.Text,
            'thumbnail': FComponents.Thumbnail
        },
        data(){
            return {
                folderModal: {
                    show: false,
                    mode: 'add',
                    modes: ['add','update','delete'],
                    title: '',
                    folder: {
                        id: null,
                        name: null,
                        parent: {},
                    },
                    config:{
                        add: {
                            type: 'info',
                            title: '新建文件夹',
                            requiredSelected: false,
                        },
                        update:{
                            type: 'warning',
                            title: '需改文件夹',
                            requiredSelected: true,
                        },
                        delete:{
                            type: 'danger',
                            title: '删除文件夹',
                            requiredSelected: true,
                        }
                    }
                },
                folders: [],
                cache:{},
                selected: undefined,
                thumbnails: [],
                uploadModal: {
                    show: false,
                    images: [],
                    selectText: '选择图片',
                    resetText: '清空',
                    submitText: '上传',
                }
            };
        },
        methods:{
            saveFolder(){
                let parent = this.selected ? this.cache[this.selected] : null;
                FolderService.addFolder(this.folderModal.folder.name, parent? parent.id : 0 ).then( () => {
                    this.refreshFolder( parent );
                    this.folderModal.show = false;
                });
            },
            refreshFolder( parent ){
                this.cleanFolder( parent );
                this.loadFolders( parent );
            },
            cleanFolder( parent ){
                if(!parent){
                    return;
                }
                ArrayUtils.clean(parent.children);
            },
            openFolderModal( mode ){
                if(this.folderModal.config[mode].requiredSelected && !this.selected ){
                    alert('Please select first');
                    return;
                }
                this.folderModal.mode = mode;
                this.folderModal.show = true;
            },
            loadFolders(parent){
                if(parent){
                    FolderService.getSonFolders(parent.id).then( data => {
                        ArrayUtils.copy(TreeNode.fromFolders(data, this.cache),parent.children);
                    });
                    return;
                }
                FolderService.getRootFolders().then( data => {
                    ArrayUtils.copy(TreeNode.fromFolders(data, this.cache),this.folders);
                });
            },
            loadImagesBySelect( folderId ){
                ImageService.getImagesByFolder(folderId)
                    .then( data => {
                        ArrayUtils.copy(data.map( t => {
                            t.src = IMAGE_SITE_PREFIX + t['sha1Md5'] + "." +t.suffix;
                            return t;
                        }),this.thumbnails);
                    });
            },
            selectFolder( node ){
                this.unselect();
                this.select( node );
                this.loadImagesBySelect( node.id );
            },
            unselect(){
                if(this.selected){
                    this.cache[this.selected].select = false;
                }
                this.selected = undefined;
            },
            select( node ){
                this.selected = node.id;
                node.select = true;
            },
            expand( node ){
                node.show = !node.show;
                if(node.show){
                    this.refreshFolder( node );
                }
            },
            uploadImage( images ){
                let p = new Promise( resolve => resolve());
                images.forEach( image => {
                    p.then(() => {
                        return this.upload(image);
                    });
                });
                console.info('images', images);
            },
            upload( image ){
                return ImageService.saveImage(image.file,image.name,this.selected);
            },
            imageClick( image ){
                console.info(image);
                this.$emit('image-click',image);
            },
            openUploadModal(){
                ArrayUtils.clean(this.uploadModal.images);
                if(this.selected){
                    this.uploadModal.show = true;
                    return;
                }
                alert('Please select folder');
            },
        },
        computed:{
            folderPath(){
                if(!this.selected){
                    return '/'
                }
                let node = this.cache[this.selected];
                let text = node.text;
                while(node.parent != null){
                    node = node.parent;
                    text = node.text + ' / ' + text;
                }
                return '/ ' + text;
            }
        },
        mounted(){
            this.$emit('init',this.refreshFolder.bind(this));
        }
    };
</script>
<style>
    @import url('../assets/styles/components/image-control.css');
</style>