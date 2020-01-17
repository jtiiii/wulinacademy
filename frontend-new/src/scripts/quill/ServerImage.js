import Quill from 'quill';
let BlockEmbed = Quill.import('blots/block/embed');
import ImageService from "../api/ImageService";

class ServerImage extends BlockEmbed{
    static create( image ){
        let node = super.create();
        //若为普通的src路径值
        if(typeof image === 'string'){
            node.setAttribute('src',image);
            return node;
        }

        node.setAttribute('alt', image.name);
        node.setAttribute('src', image.sha1Md5? ImageService.getImageSrc(image.sha1Md5, image.suffix) : image.src );
        node.setAttribute('sha1Md5', image.sha1Md5);
        node.setAttribute('suffix', image.suffix);
        return node;
    }

    static value(node) {
        let sha1Md5 = node.getAttribute('sha1Md5');
        if(sha1Md5){
            return {
                name: node.getAttribute('alt'),
                suffix: node.getAttribute('suffix'),
                sha1Md5: sha1Md5
            }
        }

        return {
            src: node.getAttribute('src')
        };
    }
}
ServerImage.blotName = 'server_image';
ServerImage.tagName = 'img';
export default ServerImage;