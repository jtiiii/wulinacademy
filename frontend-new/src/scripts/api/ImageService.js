import ServiceApi from "./ServiceApi";
import FetchUtils from "../utils/FetchUtils";
const ImageService = {
    __context__: '/image',
    __api__: new ServiceApi("图片服务"),
    saveImage(file,name,folderId){
        return this.__api__.Post(this.__context__,{
            body: FetchUtils.toFormData({
                file: file,
                name: name,
                folder: folderId
            })
        });
    },
    getImagesByFolder(folderId){
        return this.__api__.Get(this.__context__ + "/folder/{folderId}",{
            urlData:{folderId},
        });
    }
    // getSrc( image ){
    //     return Api.__context__ + image.site.substring(1);
    // }
};
export const IMAGE_SITE_PREFIX = ImageService.__api__.host + ":"+ImageService.__api__.port + "/assets/images/";
export default ImageService;