import Api from './FetchApi';

const IMAGE_SITE_PREFIX = Api.__context__ + '/';
const ImageService = {
    __context__: '/image',
    saveImage(file,name,folderId){
        return Api.FormPost(this.__context__,{
            file: file,
            name: name,
            folder: folderId
        });
    },
    getImagesByFolder(folderId){
        return Api.Get(this.__context__,{
            folder: folderId
        });
    },
    getSrc( image ){
        return Api.__context__ + image.site.substring(1);
    }
};
export default ImageService;
export {IMAGE_SITE_PREFIX};