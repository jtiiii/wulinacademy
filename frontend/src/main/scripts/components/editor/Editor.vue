<template>
    <div class="editor">
        <v-editor-quill :height="height" @init="initEditor" :handlers="handlers" @change="change"></v-editor-quill>
        <modal :show="imageControl.show" :width="width" :height="'auto'" @close="imageControlClose">
            <image-control @init="load" @image-click="imageClick"></image-control>
        </modal>
    </div>
</template>
<script type="text/javascript">
    import editor from './editor';
    export default editor;
</script>
<style scoped>
    .editor{
        position: relative;
    }
</style>