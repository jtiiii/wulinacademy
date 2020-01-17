<template>
    <div class="image-control">
        <!-- 文件夹控制 -->
        <v-panel class="folder" :width="'210px'" :height="'600px'">
            <div class="tool">
                <v-button @click="openFolderModal('add')" :size="'small'" :emotion="'success'">新增</v-button>
                <v-button @click="openFolderModal('update')" :size="'small'" :emotion="'info'">修改</v-button>
                <v-button @click="openFolderModal('delete')" :size="'small'" :emotion="'danger'">删除</v-button>
            </div>
            <v-tree :list="folders" :emotion="'info'" >
                <template #switch="{ item }">
                    <a @click="expand( item )" :class="{'expand': item.show }" ><i class="iconfont wulin-caret-down"/></a>
                </template>
                <template #item="{ item }">
                    <a :class="{ 'select': item.select }" @click="selectFolder(item)">{{item.text}}</a>
                </template>
            </v-tree>
        </v-panel>
        <!-- 图片预览 -->
        <v-panel class="folder-image" :width="'580px'" :height="'600px'">
            <div>
                {{ selected? folderPath : '' }}
                <br/>
                <v-button v-show="this.selected" @click="openUploadModal" :size="'small'" :emotion="'default'">上传图片</v-button>
            </div>
            <div class="thumbnail-box">
                <thumbnail class="thumbnail-item" v-for="t in thumbnails" :key="'thumbnail-' +t.sha1Md5" @image-click="imageClick(t)"  :src="t.src">
                    {{t.name}} {{t.suffix}}
                    <a class="iconfont wulin-delete thumbnail-delete" @click="deleteImage(t)" />
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
            名称：<span v-if="folderModal.mode === 'delete'">{{ folderModal.folder.name }}</span><input-text v-else v-model="folderModal.folder.name" />
            <br/>
            位置： {{folderPath}} / {{ folderModal.folder.name }}
            <br/>
            <v-button v-show="folderModal.mode === 'add'" @click="saveFolder" :size="'small'" :emotion="'success'">新增</v-button>
            <v-button v-show="folderModal.mode === 'update'" @click="updateFolder" :size="'small'" :emotion="'success'">保存</v-button>
            <v-button v-show="folderModal.mode === 'delete'" @click="deleteFolder" :size="'small'" :emotion="'success'">确认删除</v-button>
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
                        id: undefined,
                        name: '',
                        parent: null,
                    },
                    config:{
                        add: {
                            type: 'info',
                            title: '新建文件夹',
                            requiredSelected: false,
                        },
                        update:{
                            type: 'warning',
                            title: '修改文件夹',
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
            updateFolder(){
                FolderService.updateFolderName(this.folderModal.folder.id, this.folderModal.folder.name)
                .then( () => {
                    this.folderModal.show = false;
                    this.refreshFolder(this.folderModal.folder.parent);
                    this.cleanFolderModelInfo();
                });
            },
            deleteFolder(){
                FolderService.hasSon(this.folderModal.folder.id)
                    .then( hasSon => {
                        if(hasSon){
                            alert("This son exists for this folder.");
                        }else{
                            return FolderService.deleteFolder(this.folderModal.folder.id)
                                .then( () => {
                                    this.folderModal.show = false;
                                    this.refreshFolder(this.folderModal.folder.parent);
                                    this.cleanFolderModelInfo();
                                });
                        }
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
                let requiredSelect = this.folderModal.config[mode].requiredSelected;
                if(requiredSelect && !this.selected ){
                    alert('Please select first');
                    return;
                }
                if(requiredSelect){
                    this.setFolderModelInfo( this.cache[this.selected] );
                }else{
                    this.cleanFolderModelInfo();
                }
                this.folderModal.mode = mode;
                this.folderModal.show = true;
            },
            setFolderModelInfo( folder){
                this.folderModal.folder.name = folder.text;
                this.folderModal.folder.id = folder.id;
                this.folderModal.folder.parent = folder.parent;
            },
            cleanFolderModelInfo(){
                this.folderModal.folder.name = '';
                this.folderModal.folder.id = undefined;
                this.folderModal.folder.parent = null;
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
                            t.folderId = folderId;
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
                p.then( () => {
                    this.closeUploadModal();
                    this.cleanUploadImages();
                    setTimeout(() => {
                        this.loadImagesBySelect(this.selected);
                    },200);
                });
            },
            deleteImage( image ){
                ImageService.deleteImageByFolder(image.folderId, image.name)
                .then( () => {
                    this.loadImagesBySelect( image.folderId );
                });
            },
            cleanUploadImages(){
                ArrayUtils.clean(this.uploadModal.images);
            },
            upload( image ){
                return ImageService.saveImage(image.file,image.name,this.selected);
            },
            imageClick( image ){
                this.$emit('image-click',image);
            },
            closeUploadModal(){
                this.uploadModal.show = false;
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
                let text = this.folderModal.mode === 'add'? ' / ' + node.text : '';
                while(node.parent != null){
                    node = node.parent;
                    text = ' / ' + node.text + text;
                }
                return text;
            }
        },
        created(){
            this.refreshFolder();
        }
    };
</script>
<style>
    @import url('../assets/styles/components/image-control.css');
</style>