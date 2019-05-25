<template>
    <div class="editor">
        <v-editor-quill :height="height" @init="initEditor" :handlers="handlers" @change="change"></v-editor-quill>
        <modal :show="imageControl.show" :width="width" :height="'auto'" @close="imageControlClose">
            <image-control @image-click="imageClick"></image-control>
        </modal>
    </div>
</template>
<script type="text/javascript">
    import editor from './editor2';
    export default editor;
</script>
<style scoped>
    .editor{
        position: relative;
    }
</style>