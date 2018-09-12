<template>
    <dl>
        <dd v-for="message in news" :key="message.id" >
            <figure class="thumbnail">
                <!--<figcaption>{{ message.title }}</figcaption>-->
                <img :src="message.thumbnail" />
            </figure>
            <article>
                <h3>{{ message.title }}</h3>
                <p>{{ message.content }}</p>
            </article>
        </dd>
    </dl>
</template>
<script type="text/javascript">
    import p1 from '../images/photo1.png';
    export default {
        data: function() {
            return {
                news: [
                    {id: 1,title: 'test-title', content: "It's a beautiful picture.It's a beautiful picture.It's a beautiful picture.It's a beautiful picture.It's a beautiful picture.It's a beautiful picture.It's a beautiful picture.It's a beautiful picture.It's a beautiful picture.It's a beautiful picture.It's a beautiful picture.It's a beautiful picture.It's a beautiful picture.It's a beautiful picture.It's a beautiful picture.It's a beautiful picture.It's a beautiful picture.",thumbnail: p1},
                    {id: 2,title: 'test-title2', content: "It's a beautiful picture.", thumbnail: p1}
                ]
            }
        }
    }
</script>
<style scoped>
    .thumbnail{
        width: 180px;
        height: 180px;
        float: left;
        position: relative;
        margin:10px;
    }
    .thumbnail > img{
        width: 100%;
    }
    dl > dd{
        width: 500px;
        height: 200px;
        margin: 10px;
        background: #efefef;
        border-radius: 5px;
    }
    dl > dd > article{
        text-align: left;
        float: left;
        width: 280px;
        height: calc(100% - 20px);
        overflow: hidden;
        overflow-y: scroll;
        margin: 10px
    }
</style>