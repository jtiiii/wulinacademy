<script>
    import ImageControl from './imageControl';
    export default ImageControl;
</script>
<template>
    <div class="folder-control">
        <!-- 文件夹编辑 -->
        <v-modal :show="folderModal.show" :width="folderModal.width" :type="folderModal.type" :height="folderModal.height" @close="folderModal.show = false" >
            <template #title>{{ folderModal.title }}</template>
            名称：<input-text :disabled="folderModal.mode === 'delete'" v-model="folderModal.folder.name" ></input-text>
            <br/>
            位置：根目录 / {{ folderModal.folder.name }}
            <br/>
            <v-button class="submit" @click="saveFolder" :width="'100%'" :type="'success'">Submit</v-button>
        </v-modal>
        <!-- 文件夹控制 -->
        <v-panel class="folder-panel" :width="'210px'" :height="'600px'">
            <div class="folder-tool">
                <v-button @click="openFolderModal('add')" :width="'50px'" :type="'info'">新增</v-button>
<!--                <v-button @click="openFolderModal('update')" :width="'50px'" :type="'warning'">修改</v-button>-->
<!--                <v-button @click="openFolderModal('delete')" :width="'50px'" :type="'danger'">删除</v-button>-->
            </div>
            <hr/>
            <v-tree v-for="node in folders" @expand="expand" @node-click="selectFolder" :key="node.id" :node="node"></v-tree>
        </v-panel>
        <!-- 图片预览 -->
        <v-panel class="file-panel" :width="'480px'" :height="'600px'">
            <div>
                {{ folderPath }}
                <v-button v-show="this.selected" @click="openUploadModal" :type="'info'">上传图片</v-button>
            </div>
            <hr/>
            <div>
                <thumbnail v-for="thumbnail in thumbnails" @image-click="imageClick(thumbnail)" :key="thumbnail.id" :thumbnail="thumbnail"></thumbnail>
            </div>
        </v-panel>
        <!-- 上传控件 -->
        <image-upload @submit="uploadImage" :width="'670px'" :height="'500px'" :show="uploadModal.show" @close="uploadModal.show = false"></image-upload>
    </div>
</template>
<style scoped>
    /*.folder-tool{*/
    /*    width: 100%;*/
    /*}*/
    .folder-panel{
        float: left;
        width: 30%;
        height: 100%;
        text-align: left;
    }
    .file-panel{
        float: left;
        width: 70%;
        height: 100%;
    }
    .folder-control{
        width: 100%;
        position: relative;
        text-align: left;
    }
    .submit{
        margin: 10px 0;
    }
</style>