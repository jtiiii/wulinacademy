<template>
    <div class="folder-control">

        <!-- 文件夹控制 -->
        <v-panel class="folder-panel" :width="'210px'" :height="'600px'">
            <div class="folder-tool">
                <v-button @click="openFolderModal('add')" :width="'50px'" :type="'info'">新增</v-button>
<!--                <v-button @click="openFolderModal('update')" :width="'50px'" :type="'warning'">修改</v-button>-->
<!--                <v-button @click="openFolderModal('delete')" :width="'50px'" :type="'danger'">删除</v-button>-->
            </div>
            <hr/>
            <v-tree :list="$data._folders" :emotion="'info'" @itemClick="selectFolder" >
                <template #item="{index,item}">
                    {{item.text}}
                </template>
            </v-tree>
        </v-panel>
        <!-- 图片预览 -->
        <v-panel class="file-panel" :width="'480px'" :height="'600px'">
            <div>
                {{ folderPath }}
                <v-button v-show="this.selected" @click="openUploadModal" :type="'info'">上传图片</v-button>
            </div>
            <hr/>
            <div>
                <thumbnail v-for="t in thumbnails" @image-click="imageClick(t)" :key="t.id" :src="t.src">
                    {{t.name}} {{t.suffix}}
                </thumbnail>
            </div>
        </v-panel>
        <!-- 上传控件 -->
        <image-upload class="image-upload-modal" @submit="uploadImage" :width="'670px'" :height="'500px'" :show="uploadModal.show" @close="uploadModal.show = false"></image-upload>
        <!-- 文件夹编辑 -->
        <v-modal :show="folderModal.show" :width="folderModal.width" :type="folderModal.type" :height="folderModal.height" @close="folderModal.show = false" >
            <template #title>{{ folderModal.title }}</template>
            名称：<input-text :disabled="folderModal.mode === 'delete'" v-model="folderModal.folder.name" ></input-text>
            <br/>
            位置：根目录 / {{ folderModal.folder.name }}
            <br/>
            <v-button class="submit" @click="saveFolder" :width="'100%'" :type="'success'">Submit</v-button>
        </v-modal>
    </div>
</template>
<script>
    import FComponents from 'f-vue-components'
    import FolderService from '../scripts/api/FolderService';
    import ApiModel from '../scripts/api/Model';
    import ImageService,{IMAGE_SITE_PREFIX} from '../scripts/api/ImageService';

    class TreeNode{
        constructor({ text, id, show, children}){
            this.id = id;
            this.text = text;
            this.show = show;
            if(children && children instanceof Array){
                this.children = children.map( TreeNode.of );
            }else{
                this.children = [];
            }
        }

        addChildren( ...childs ){
            if(childs && childs.length){
                childs.forEach( child => {
                    this.children.push(TreeNode.of(child))
                });
            }
        }
        setChildren( ...childs ){
            if(childs && childs.length){
                this.children.splice(0);
                this.addChildren(childs);
            }
        }
    }
    TreeNode.of = obj => obj instanceof TreeNode? obj: new TreeNode(obj);
    TreeNode.ofs = objs => objs.map(TreeNode.of);

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
                    height: 'auto',
                    width: 'auto',
                    title: '',
                    type: 'info',
                    folder: ApiModel.Folder.of({})
                },
                _folders: [],
                map:{},
                selected: undefined,
                _thumbnails: [],
                uploadModal: {
                    show: false
                }
            };
        },
        methods:{
            saveFolder(){
                FolderService.addFolder(this.folderModal.folder.name,this.folderModal.folder.parent).then( () => {
                    this.refreshFolder();
                });
            },
            refreshFolder(){
                this.cleanFolder();
                this.loadRootFolders();
            },
            cleanFolder(){
                this.folders = [];
                this.map = {};
                this.selected = undefined;
            },
            openFolderModal( mode ){
                this.cleanFolderModal();
                if(mode !== 'add' && !this.selected){
                    alert('Please select first');
                    return;
                }
                this.switchFolderModalMode(mode);
                this.folderModal.show = true;
            },
            switchFolderModalMode( mode ){
                this.folderModal.mode = mode;
                switch (mode) {
                    case 'add':
                        this.folderModal.title = '添加文件夹';
                        this.folderModal.folder.parent = this.selected || 0;
                        break;
                    case 'update':
                        this.folderModal.title = '修改文件夹';
                        this.folderModal.type = 'warning';
                        this.folderModal.folder = this.map[this.selected].folder;
                        break;
                    default :
                        this.folderModal.title = '确认删除';
                        this.folderModal.type = 'danger';
                        this.folderModal.folder = this.map[this.selected].folder;
                }
            },
            cleanFolderModal(){
                this.folderModal.mode = '';
                this.folderModal.width = 'auto';
                this.folderModal.height = 'auto';
                this.folderModal.title = '';
                this.folderModal.type = 'info';
                this.folderModal.folder = ApiModel.Folder.of({});
            },
            loadRootFolders(){
                FolderService.getRootFolders().then( data => {
                    this.folders = data.map( n => TreeNode.of({text:n.name, id:n.id, show: false}));
                    this.folders.forEach( f => {
                        this.map[f.id] = f;
                    })
                });
            },
            loadImagesBySelect( folderId ){
                ImageService.getImagesByFolder(folderId)
                    .then( data => {
                        this.thumbnails = data.map( t => {
                            t.src = IMAGE_SITE_PREFIX + t.site;
                            return t;
                        });
                    });
            },
            addFolder( folder ){
                let node = folderToTreeNode(folder);
                this.map[node.id] = node;
                if( FolderService.isRoot( folder.parent )){
                    this.folders.push(node);
                }else{
                    this.map[folder.parent].addChildren(node);
                }
            },
            selectFolder( node ){
                this.unselect();
                this.select( node );
                this.loadImagesBySelect( node.id );
            },
            unselect(){
                if(this.selected){
                    this.map[this.selected].select = false;
                }
                this.selected = undefined;
            },
            select( node ){
                this.selected = node.id;
                node.select = true;
            },
            expand( node ){
                if(node.children.length){
                    return;
                }
                FolderService.getSonFolders(node.id).then( data => {
                    data.forEach( folder => this.addFolder( folder ));
                });
            },
            uploadImage( images ){
                console.info(images);
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
                if(this.selected){
                    this.uploadModal.show = true
                }else{
                    alert('Please select folder');
                }
            },
        },
        computed:{
            folders:{
                set( nodes ){
                    this.$data._folders.splice(0);
                    nodes.forEach( n => this.$data._folders.push(n) );
                },
                get(){
                    return this.$data._folders;
                }
            },
            thumbnails:{
                set( images ){
                    this.$data._thumbnails.splice(0);
                    images.forEach( i => this.$data._thumbnails.push(i));
                },
                get(){
                    return this.$data._thumbnails;
                }
            },
            folderPath: function(){
                if(!this.selected){
                    return '/'
                }
                let node = this.map[this.selected];
                let text = node.text;
                while(node.parent != null){
                    node = node.parent;
                    text = node.text + ' / ' + text;
                }
                return '/ ' + text;
            }
        },
        mounted(){
            this.$emit('init',this.loadRootFolders.bind(this));
        }
    };
</script>
<style scoped>
    /*.folder-tool{*/
    /*    width: 100%;*/
    /*}*/
    .image-upload-modal{
        width: 100%;
        height: 500px;
    }
    .folder-panel{
        float: left;
        width: 30%;
        height: 100%;
        text-align: left;
    }
    .file-panel{
        float: left;
        width: 70%;
        height: 100%;
    }
    .folder-control{
        width: 100%;
        position: relative;
        text-align: left;
    }
    .submit{
        margin: 10px 0;
    }
</style>