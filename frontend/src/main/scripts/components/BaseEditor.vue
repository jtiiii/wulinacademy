<template>
    <div>
        <div id="editor-toolbar"></div>
        <div id="editor" :style="editorStyle">
        </div>
    </div>
</template>
<script type="text/javascript">
    import Quill from 'quill/dist/quill';
    import 'quill/dist/quill.snow.css';

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
            }
        },
        data() {
            return {
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
        computed:{
            editorStyle(){
                return {
                    width: this.textWidth ? this.textWidth + 'px' : '100%',
                    height: this.textHeight + 'px'
                };
            }
        },
        mounted(){
            this.editor = new Quill('#editor', {
                theme: 'snow',
                modules: {
                    toolbar: this.toolbarOptions
                }
            });
        }
    }
</script>
<style scoped>
</style>