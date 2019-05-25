// import Api from './Api';
import Api from './FetchApi';

const FolderService = {
    __context__: '/folder',
    getRootFolders(){
        return Api.Get(this.__context__+'/'+'root');
    },
    getSonFolders( folderId ){
        return Api.Get(this.__context__ + '/'+folderId+'/son');
    },
    addFolder(name , parentId){
        parentId = parentId || 0;
        return Api.Post(this.__context__+'/',
            {name: name, parent: parentId});
    },
    isRoot( folderId ){
        return folderId === 0;
    }
};

export default FolderService;