<template>
    <div>
        <v-new-modal class="news-modal" :show="showEditor" :width="700" :height="500">
            <label class="closeBtn">
                <button class="tool"><img class="btnImg" :src="icon.close" @click="showEditor = false"></button>
            </label>
            <br/>
            <label>
                <input class="edit-title" type="text" value="Title" />
            </label>
            <v-new-editor @text-change="textChangeListener"></v-new-editor>
        </v-new-modal>
        <div class="news">
            <div class="toolbar">
                <label>
                    <input class="searchBox" placeholder="搜索..." type="text" />
                </label>
                <label v-show="manage" class="toolBtn">
                    <button class="tool"><img class="btnImg" :src="icon.add" @click="openEditor(true)"></button>
                </label>
            </div>
            <dl>
                <dd v-for="message in news" :key="message.id" @mouseover="expandAnimate(true,message)" @mouseout="expandAnimate(false,message)" >
                    <div v-show="manage" class="toolbar-news">
                        <label class="toolBtn">
                            <button class="tool"><img class="btnImg" :src="icon.edit"></button>
                        </label>
                        <!--<label class="toolBtn">-->
                            <!--<button class="tool"><img class="btnImg" :src="icon.pickUp"></button>-->
                        <!--</label>-->
                        <!--<label class="toolBtn">-->
                            <!--<button class="tool"><img class="btnImg" :src="icon.pickDown"></button>-->
                        <!--</label>-->
                    </div>
                    <label v-show="manage" class="deleteBtn">
                        <button class="tool-sm"><img class="btnImg" :src="icon.delete" @click="Api_deleteNews(message)" ></button>
                    </label>
                    <div class="news-simple":class="message.ddClass">
                        <figure class="thumbnail">
                            <img :src="message.thumbnail" />
                        </figure>
                        <article>
                            <h3>{{ message.title }}</h3>
                            <span style="font-size:14px;">{{ message.time }}</span>
                            <br/>
                            <p>{{ message.content }}</p>
                        </article>
                    </div>
                </dd>
            </dl>
        </div>
    </div>
</template>
<script type="text/javascript">
    import editIcon from '../icons/edit.png';
    import addIcon from '../icons/add.png';
    import pickUpIcon from '../icons/caret-up.png';
    import pickDownIcon from '../icons/caret-down.png';
    import deleteIcon from '../icons/delete.png';
    import closeIcon from '../icons/close.png';

    import newsPic1 from '../images/news/news1.jpeg';
    import newsPic2 from '../images/news/news2.jpeg';
    import newsPic3 from '../images/news/news3.jpeg';
    import newsPic5 from '../images/news/news5.jpeg';
    import noPic from '../images/news/no-pic.png';

    import BaseModal from '../scripts/components/BaseModal.vue';
    import NewsDetail from './news/NewsDetail.vue';
    import BaseEditor from '../scripts/components/BaseEditor.vue';

    import Common from '../scripts/Common';


    export default {
        components:{
            "v-news-detail": NewsDetail,
            "v-new-modal": BaseModal,
            'v-new-editor': BaseEditor
        },
        data: function() {
            return {
                manage: false,
                showEditor: false,
                icon:{
                    edit: editIcon,
                    add: addIcon,
                    delete: deleteIcon,
                    pickUp: pickUpIcon,
                    pickDown: pickDownIcon,
                    close: closeIcon
                },
                news: [
                    {
                        id: 5,
                        title: '陳嘯風先生水墨畫展在東陽博物館開幕',
                        time: '2014-09-19',
                        content: "9月19日上午，由東陽市委宣傳部、東陽市文化廣電新聞出版局主辦，東陽市博物館、杭州武林書畫院承辦的陳嘯風水墨畫展在東陽博物館隆重開幕，共展出作品114件，以人物畫爲主，體現了陳嘯風本人對古代人物生活的理解和思考。\n" +
                            "\n" +
                            " 陳嘯風畢業於南開大學東方藝術系，現任杭州武林書畫院院長、南昌理工學院客座教授、中國美術研究院研究員等職。嘯風先生生於浙江浦江，師從吳山明、範曾、鄭慶衡、楊淑濤等名家，筆法幹練、堅實。其作品以人物爲主，間以山水、花卉、飛禽、走獸，尤其擅長大幅鉅作。畫風清新雅緻、淳樸空靈，一如神來之筆，謂之一奇。\n" +
                            "\n" +
                            " 此次展覽將持續至10月7日，適逢重陽、國慶雙節，大家有空不妨到博物館欣賞下大師筆墨。（東陽市博物館）",
                        thumbnail: newsPic5,
                        ddClass: { expand: false, shrink: false , select: true}
                    },
                    {
                        id: 4,
                        title: '武林書畫院協同浙江省體育局成立省體育局書畫社',
                        time: '2013-12-27',
                        content: " 12月27日，浙江省體育局書畫社舉行了成立儀式，省體育局局長孫光明、副局長李期華、呂林出席儀式。浙江省書協副主席楊西湖、杭州西湖印社社長沈立新、杭州武林畫院院長陳嘯風應邀參加儀式並現場指導。儀式上，書畫社正式聘請了三位書畫家爲特約指導教授。雙方就體育文化與書畫文化結合以及未來合作展開討論，氣氛十分熱烈。\n" +
                            "       據瞭解，在2013年年初，浙江省體育局在黃龍體育中心舉行了著名書畫家與奧運冠軍的聯誼活動，受到了社會各界的廣泛關注。局系統也涌現出了一批優秀的書畫愛好者，大家強烈希望成立書畫社。如今，大家的願望得以實現。   \n" +
                            "       書畫社將採取聘請專家授課、專人輔導、書畫展覽、寫生風采、著名運動員與書畫家聯誼等方式展開活動。從明年起，每年將舉辦一屆書畫展覽，並邀請專家對作品進行點評，前三名將獲得榮譽證書。值得一提的是,書畫社還將利用節假日休息定期舉辦講座。\n" +
                            "       近年來，省體育局在全力推進全民健身、競技體育和體育產業的同時，不斷強化體育文化建設，孕育了具有浙江特色的體育文化底蘊，此次成立書畫興趣小組也是順應發展。不僅培養浙江體育人對文化事業的興趣和愛好，也響應省委、省政府對文化建設方面提出的要求，爲營造良好的文化環境做出積極努力。\n" +
                            "       應邀參加書畫社成立儀式的書畫家也表達了希望通過相互學習和探討，能夠將體育和書畫相互結合，將二者提升到更高的高度。（浙江省體育局）",
                        thumbnail: noPic,
                        ddClass: { expand: false, shrink: false, select: false}
                    },
                    {
                        id: 3,
                        title: '陳嘯風先生中國畫展於浦江美術館舉行',
                        time: '2012-07-04',
                        content: "由浙江省政協書畫社、浦江縣政協聯合舉辦的“陳嘯風——中國畫展”，將於7月4日至10日在浦江美術館開展。\n" +
                            "  浦江籍浙派畫家陳嘯風畢業於南開大學東方藝術系，師從吳山明、範曾、鄭慶衡、楊淑濤等名家，現任杭州武林書畫院院長、南昌理工學院客座教授。其作品多以人物爲主，間或山水、花卉、飛禽、走獸，尤擅大幅巨幀之作。畫風清新高古，道法自然，淳樸空靈。 （錢江晚報）",
                        thumbnail: newsPic3,
                        ddClass: { expand: false, shrink: false, select: false }
                    },
                    {
                        id: 2,
                        title: '“錢塘三傑”書畫印聯展于桐庐美术馆举行 將展出作品120餘件',
                        time: '2010-07-06',
                        content: "7月9日至16日，陳嘯風、沈立新、章建明“錢塘三傑”書畫印聯展將在桐廬美術館展出。桐廬縣文廣新局主辦、武林书画院承办的這次展覽由中國畫、篆刻、書法三部分組成，共120餘件，均爲三人近年來各自領域的得意之作。\n" +
                            "  陳嘯風，杭州武林書畫院院長，作品被北京榮寶齋等處收藏；沈立新，西湖印社社長，其書法、篆刻及刻字作品歷年來多次入選國際級展會；章建明，浙江省書法研究會副會長兼祕書長，其作品傾向於表現清雅之氣，提倡書法作品真實反映作者情性和心態。（浙江在线）",
                        thumbnail: newsPic2,
                        ddClass: { expand: false , shrink: false, select: false}
                    },
                    {
                        id: 1,
                        title: '陳嘯風先生中國畫展在杭州大劇院舉行',
                        time: '2009-09-19',
                        content: "9月19日，由杭州大劇院、武林書畫院主辦陳嘯風先生個人人物畫展，今日在杭州大劇院隆重亮相。本次參展的80多幅作品，均來自嘯風先生對古代人物生活的思考和提煉，擁有很高的藝術價值。\n" +
                            "\n" +
                            "嘯風先生畢業於南開大學，曾師從吳山明、範曾、鄭慶衡等教授，其繪畫作品簡淨雅緻，清新高古，風格獨特。他的許多作品被海內外友人收藏，其在校期間創作的大幅歷史畫卷《勿忘國恥》爲母校南開大學所收藏。這次展出時間將一直持續到10月11日，在此期間，市民都可到大劇院領略國畫大師的神來之筆和精湛的繪畫水平。（杭州日報）",
                        thumbnail: newsPic1,
                        ddClass: { expand: false , shrink: false, select: false}
                    }
                ]
            }
        },
        methods:{
            refreshManage: function(){
                this.manage = Common.data.isManage;
            },
            expandAnimate: function(isExpand, message){
                if(this.manage){
                    return false;
                }
                if(isExpand){
                    return this.amplify(message);
                }
                return this.reduce(message);
            },
            amplify: function( message ){
                message.ddClass.expand = true;
                message.ddClass.shrink = false;
            },
            reduce: function( message ){
                message.ddClass.expand = false;
                message.ddClass.shrink = true;
            },
            openEditor: function( clear ){
                if(clear){
                    //TODO RESET Editor
                }
                this.showEditor = true;
            },
            textChangeListener( content ){
                console.info(JSON.stringify(content));
            },
            Api_deleteNews( news ){
                console.log('Test - delete',news);
            },
            Api_getNews( news ){

            },
            Api_listNews( news ){

            }
        },
        created(){
            Common.addDataResolve('status', ()=>{this.refreshManage();} );
            this.refreshManage();
        }
    }
</script>
<style scoped>
    .edit-title{
        width: 100%;
        height: 30px;
        font-size: 25px;
        font-weight: bolder;
        margin-bottom: 15px;
    }
    .news{
        width: auto;
        position: relative;
        display:inline-block;
    }
    .news-simple{
        width:100%;height: 100%; position: absolute
    }
    .toolbar-news{
        width: 30px;
        position: absolute;
        left: -40px;
        border: none;
    }
    .toolbar > label{
        float: left;
        margin: 10px 10px;
    }
    .toolBtn{
        margin: 5px;
    }

    .btnImg{
        display: inline-block;
        width: 100%;
        height: 100%;
        position: relative;
    }
    .tool{
        display: inline-block;
        width: 30px;
        height: 30px;
        border: none;
        padding: 0;
    }
    .tool-sm{
        display: inline-block;
        width: 20px;
        height: 20px;
        border: none;
        padding: 0;
    }
    .toolbar{
        width: 700px;
        clear: both;
    }

    .searchBox{
        border-bottom-left-radius: 5px;
        border-bottom-right-radius: 5px;
        border: 2px solid #aaa;
        border-top: 0;
        height: 30px;
        outline: none;
        width: 250px;
        font-size: 20px;
    }
    .searchBox::placeholder{
        color: #efefef;
    }
    .thumbnail{
        overflow: hidden;
        width: 280px;
        height: 180px;
        float: left;
        position: relative;
        margin:10px;
    }
    .thumbnail > img{
        width: 100%;
    }
    dl{
        clear: both;
    }
    dl > dd{
        width: 700px;
        height: 200px;
        margin: 15px;
        position: relative;
        border-radius: 5px;
        text-align: center;
    }
    dl > dd > .news-simple{
        background: #efefef;
        position: relative;
        border-radius: 5px;
    }
    dl > dd article{
        text-align: left;
        float: left;
        width: 380px;
        height: calc(100% - 20px);
        overflow: hidden;
        overflow-y: scroll;
        margin: 10px
    }

    @keyframes expand-animate {
        0%{
            padding: 0;
            left: 0;
            top: 0;
        }
        100%{
            padding: 8px 4px;
            left: -4px;
            top: -8px;
        }
    }
    @keyframes shrink-animate {
        0%{
            padding: 8px 4px;
            left: -4px;
            top: -8px;
        }
        100%{
            padding: 0;
            left: 0;
            top: 0;
        }
    }
    .expand{
        padding: 8px 4px;
        animation: expand-animate 0.2s;
        left: -4px;
        top: -8px;
    }
    .shrink{
        animation: shrink-animate 0.2s;
        padding: 0;
        left: 0;
        top: 0;
    }
    .select{
        background: #daecff;
    }
    .closeBtn{
        display: block;
        position: absolute;
        right: 5px;
        top: 5px;
        z-index: 9;
    }
    .deleteBtn{
        display: block;
        position: absolute;
        right: -10px;
        top: -10px;
        z-index: 9;
    }
    .news-modal{
        width: 100%;
        height: 100%;
        position: absolute;
    }


</style>