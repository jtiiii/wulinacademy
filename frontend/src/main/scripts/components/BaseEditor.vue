<template>
    <div>
<!--        <div id="editor-toolbar"></div>-->
        <div v-show="enable">
            <div id="editor" :style="editorStyle">
            </div>
        </div>
        <div class="ql-container ql-snow" style="border:none;" v-show="!enable" v-html="showHtml">
        </div>
    </div>
</template>
<script type="text/javascript">
    import Quill from 'quill/dist/quill';
    import 'quill/dist/quill.snow.css';
    function editorProcessor( vm ){
        vm.editor = new Quill('#editor', {
            theme: 'snow',
            modules: {
                toolbar: vm.toolbarOptions
            }
        });

        vm.editor.setContents(vm.content);
        vm.editor.enable(vm.enable);
        vm.editor.on('text-change', () => {
            vm.contentWatchStop = true;
            vm.showHtml = vm.editor.container.innerHTML;
            vm.$emit('text-change',vm.editor.getContents(),vm.editor.getText());
        });
        return vm;
    }
    export default {
        name:"BaseEditor",
        props:{
            textHeight: {
                type: Number,
                required: false,
                default: 300
            },
            textWidth:{
                type: Number,
                required: false,
                default: 0
            },
            enable:{
                type: Boolean,
                required: false,
                default: true
            },
            content:{
                type: Object,
                required: false,
                default: {},
            }
        },
        data() {
            return {
                contentWatchStop: false,
                showHtml: "",
                editor: null,
                toolbarOptions: [
                    ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
                    ['blockquote', 'code-block'],
                    [{ 'header': 1 }, { 'header': 2 }],               // custom button values
                    [{ 'list': 'ordered'}, { 'list': 'bullet' }],
                    [{ 'script': 'sub'}, { 'script': 'super' }],      // superscript/subscript
                    [{ 'indent': '-1'}, { 'indent': '+1' }],          // outdent/indent
                    [{ 'direction': 'rtl' }],                         // text direction
                    [{ 'size': ['small', false, 'large', 'huge'] }],  // custom dropdown
                    [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
                    [{ 'color': [] }, { 'background': [] }],          // dropdown with defaults from theme
                    [{ 'font': [] }],
                    [{ 'align': [] }],
                    ['clean']                                         // remove formatting button
                ]
            }
        },
        watch:{
            enable( n, o){
                if(n === o){
                    return;
                }
                this.editor.enable(n);
            },
            content( n, o){
                if(this.contentWatchStop){
                    this.contentWatchStop = false;
                    return;
                }
                this.editor.setContents(n);
            }
        },
        computed:{
            editorStyle(){
                return {
                    width: this.textWidth ? this.textWidth + 'px' : '100%',
                    height: this.textHeight + 'px'
                };
            },

        },
        methods:{
            escapeStringHTML(str) {
                str = str.replace(/&lt;/g,'<');
                str = str.replace(/&gt;/g,'>');
                return str;
            }
        },
        mounted(){
            const vm = this;
            new Promise( resolve => resolve(vm) )
                .then( editorProcessor );
        }
    }
</script>
<style scoped>
</style>