import ServiceApi from "./ServiceApi";
const NEWS_CONTENT_PREFIX = "/assets/news";
const NewsService = {
    __context__: '/news',
    __api__: new ServiceApi("新闻服务"),
    save({title, eventDate, status, preview, thumbnail}){
        return this.__api__.Post(this.__context__,{body: {title, eventDate, status, preview, thumbnail}});
    },
    update(id, {title, eventDate, status, preview, thumbnail}){
        return this.__api__.Put(this.__context__+'/{id}',{urlData: {id: id},body: {title, eventDate, status, preview, thumbnail}});
    },
    delete(id){
        return this.__api__.Delete(this.__context__+'/{id}', {urlData:{id: id}});
    },
    updateContent(id ,newsContent){
        return this.__api__.Put(this.__context__+ "/{id}/content",{ urlData: {id: id}, body: newsContent });
    },
    getContent(uuid){
        return this.__api__.Get(NEWS_CONTENT_PREFIX+"/{uuid}.json",{urlData:{uuid: uuid}});
    },
    pageSearch({keywords, pageNum, pageSize}){
        return this.__api__.Get(this.__context__+ '/page/{num}-{size}',{urlData:{num: pageNum, size: pageSize}, query:{ keywords: keywords}});
    }
};

export default NewsService;