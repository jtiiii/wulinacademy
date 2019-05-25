import FComponents from 'f-vue-components';
import ImageControl from '../imageControl/ImageControl.vue';
import i18n from '../../i18n';

export default {
    i18n,
    model:{
        prop: 'content',
        event: 'change',
    },
    components:{
        'v-editor-quill': FComponents.Editor.Quill,
        'image-control': ImageControl,
        'modal': FComponents.Modal
    },
    props:{
        enable:{
            type: Boolean,
            required: false,
            default: false,
        },
        width:{
            type: String,
            required: false,
            default: '710px'
        },
        height:{
            type: String,
            required: false,
            default: '100%'
        },
        content:{
            type: Object,
            required: false,
            default: null,
        }
    },
    data(){
        return {
            handlers: {'image': this.imageHandler },
            editor: null,
            // content: null,
            imageControl:{
                show: false
            }
        };
    },
    methods:{
        imageHandler(){
            this.imageControl.show= true;
        },
        insertImage( src ){
            this.editor.focus();
            let index = this.editor.getSelection().index;
            this.editor.insertEmbed(index,'image',src);

        },
        imageControlClose(){
            this.imageControl.show = false;
        },
        imageClick( image ){
            this.insertImage( image.src);
            this.imageControlClose();
        },
        initEditor( editor){
            this.editor = editor;
            this.$emit('init',this.editor);
        },
        change( content ){
            this.$emit('change',content);
        }
    }
}