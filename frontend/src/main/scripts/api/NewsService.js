import Api from './FetchApi';
import DateUtils from '../utils/DateUtils';
import Model from './Model';
import StringUtils from '../utils/StringUtils';

const NewsService = {
    __context__: '/news',
    save(obj){
        let news = Model.News.of(obj).ofSave();
        news.eventDate = news.eventDate || DateUtils.getNowDateTimeString();
        if(news.eventDate instanceof Date){
            news.eventDate = DateUtils.getDateTimeString(news.eventDate);
        }
        news.preview = StringUtils.fixLength(news.preview,200,"");
        news.status = 1;
        // return Api.Post(this.__context__,news).then( response => response.data);
        return Api.Post(this.__context__,news);
    },
    update(obj){
        let news = Model.News.of(obj);
        if(news.eventDate instanceof Date){
            news.eventDate = DateUtils.getDateTimeString(news.eventDate);
        }
        return Api.Put(this.__context__+'/'+news.id,news.ofSave());
    },
    delete(newsId){
        return Api.Delete(this.__context__+'/'+newsId);
    },
    saveContent(newsContent){
        newsContent = Model.NewsContent.of(newsContent);
        if(!StringUtils.isString(newsContent.content)){
            newsContent.content = JSON.stringify(newsContent.content);
        }
        return Api.Post(this.__context__+ "/"+newsContent.id+"/content",newsContent.ofSave());
    },
    getContent(newsId){
        return Api.Get(this.__context__+'/'+newsId+'/content').then(data => Model.NewsContent.of(data))
            .then(newsContent => {
                if(newsContent.content){
                    newsContent.content = JSON.parse(newsContent.content);
                }
                return newsContent;
            });
    },
    pageSearch({search, pageNum, pageSize, eventDate}){
        return Api.Get(this.__context__+ '/page-'+pageNum+'-'+pageSize,{search,eventDate}).then( data => Model.PageData.of(data));
    }
};

export default NewsService;