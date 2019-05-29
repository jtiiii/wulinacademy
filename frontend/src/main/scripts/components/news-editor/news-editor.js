import Editor from '../editor/Editor.vue'
import FComponents from 'f-vue-components';

export default {
    components:{
        'v-editor': Editor,
        'v-button': FComponents.Button,
        'v-input-text': FComponents.Input.Text
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
            },
            editor: null,
            html: null,
        };
    },
    methods:{
        submit(){
            this.$emit('submit',this.news,this.editor.getText());
        },
        reset(){
            this.clean();
            this.$emit('reset',{news:this.news,clean:this.clean})
        },
        clean(){
            this.news = {
                content: null,
                title: undefined,
                eventDate: undefined,
            };
            this.editor.setContents(null);
        },
        initEditor(editor){
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
        let load = function({content,title,eventDate}){
            this.news.content = content;
            this.news.title = title;
            this.news.eventDate = eventDate;
            this.editor.setContents(content);
            this.refreshHtml();
        };
        this.$emit('load',load.bind(this));
    }
};