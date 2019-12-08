// import Api from './Api';
import ServiceApi from "./ServiceApi";

const FolderService = {
    __context__: '/folder',
    __api__: new ServiceApi("文件夹服务"),
    getRootFolders(){
        return this.__api__.Get(this.__context__+'/root',{});
    },
    getSonFolders( folderId ){
        return this.__api__.Get(this.__context__ + '/{folderId}/son', {urlData: { folderId }});
    },
    addFolder(name , parentId){
        parentId = parentId || 0;
        return this.__api__.Post(this.__context__+'/', {body:{name: name, parent: parentId}});
    },
    isRoot( folderId ){
        return folderId === 0;
    }
};

export default FolderService;