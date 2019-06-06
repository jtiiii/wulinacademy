import Editor from '../editor/Editor.vue'
import FComponents from 'f-vue-components';
import noPic from '../../../images/news/no-pic.png';
import ImageControl from '../imageControl/ImageControl.vue';

export default {
    components:{
        'v-editor': Editor,
        'v-button': FComponents.Button,
        'v-input-text': FComponents.Input.Text,
        'v-modal': FComponents.Modal,
        'image-control': ImageControl
    },
    props:{
        mode:{
            type: String,
            required: false,
            validator: value => ['editing','preview'].indexOf(value) !== -1,
            default: 'editing'
        }
    },
    data(){
        return {
            news:{
                content: null,
                title: undefined,
                eventDate: undefined,
                thumbnail: undefined,
            },
            editor: null,
            html: null,
            defaultCover: noPic,
            show:{
                coverBtn: false,
                imageControl: false
            },
            load:{
                imageControl: {
                    callback : null,
                    loaded: false
                },
            }
        };
    },
    methods:{
        submit(){
            this.$emit('submit',this.news,this.editor.getText());
        },
        reset(){
            this.clean();
            this.$emit('reset',{news:this.news,clean:this.clean});
        },
        clean(){
            this.news = {
                content: null,
                title: undefined,
                eventDate: undefined,
            };
            this.editor.setContents(null);
        },
        imageControlLoad( load ){
            this.load.imageControl.callback = load;
        },
        openCoverModal(){
            if(!this.load.imageControl.loaded){
                this.load.imageControl.callback();
                this.load.imageControl.loaded = true;
            }
            this.show.imageControl = true;
        },
        selectCover( image ){
            console.info(image);
            this.news.thumbnail = image.src;
            this.show.imageControl = false;

        },
        initEditor( editor ){
            this.editor = editor;
            if(this.news.content){
                this.editor.setContents(this.news.content);
            }
            this.editor.enable(this.mode === 'editing');
            this.html = editor.container.firstChild.innerHTML;
        },
        refreshHtml(){
            this.html = this.editor.container.firstChild.innerHTML;
        }
    },
    computed:{
        isPreview(){
            return this.mode === 'preview';
        },
        isEditing(){
            return this.mode === 'editing';
        }
    },
    watch:{
        'mode': function( mode ){
            console.log('mode - change ',mode);
            if(this.editor){
                this.editor.enable( mode === 'editing');
            }
        }
    },
    created(){
        let load = function({content,title,eventDate,thumbnail}){
            this.news.content = content;
            this.news.title = title;
            this.news.thumbnail = thumbnail;
            this.news.eventDate = eventDate;
            this.editor.setContents(content);
            this.refreshHtml();
        };
        this.$emit('load',load.bind(this));
    }
};