import FComponents,{Model} from 'f-vue-components'
import FolderService from '../../api/FolderService';
import ApiModel from '../../api/Model';
import ImageService from '../../api/ImageService';

function folderToTreeNode( folder ){
    let node = Model.TreeNode.of({ id: folder.id, text: folder.name,canOpen:true});
    node.folder = ApiModel.Folder.of(folder);
    return node;
}

function imageToThumbnail(image){
    let thumbnail = Model.Thumbnail.of(image);
    thumbnail.src = ImageService.getSrc(image);
    return thumbnail;
}


const ImageControl = {
    components:{
        'v-button': FComponents.Button,
        'v-modal': FComponents.Modal,
        'v-tree': FComponents.TreeNode,
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
            folders: [],
            map:{},
            selected: undefined,
            thumbnails: [],
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
                data.forEach( folder => {
                    this.addFolder( folder );
                })
            });
        },
        loadImagesBySelect( folderId ){
            let thumbnails = [];
            ImageService.getImagesByFolder(folderId)
                .then( data => {
                    data.forEach( img => {
                        thumbnails.push(imageToThumbnail(img));
                    });
                });
            this.thumbnails = thumbnails;
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
    created(){
        this.$emit('init',this.loadRootFolders.bind(this));
        // SecurityService
        //     .login('login','login')
        //     .then( () => {
        //         this.loadRootFolders();
        //     });
    }

};
export default ImageControl;