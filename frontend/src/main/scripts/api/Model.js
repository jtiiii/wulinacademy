function News({newsId,title,eventDate,status,preview,thumbnail}){
    this.newsId = newsId;
    this.title = title;
    this.eventDate = eventDate;
    this.status = status;
    this.preview = preview;
    this.thumbnail = thumbnail;
}
News.prototype.ofSave = function(){
    return {title: this.title, eventDate: this.eventDate, status: this.status, preview: this.preview, thumbnail: this.thumbnail};
};
News.of = obj => obj instanceof News? obj: new News(obj);

function NewsContent({newsId,header,content,simple}){
    this.newsId = newsId;
    this.content = content;
    this.header = header;
    this.simple = simple;
}
NewsContent.prototype.ofSave = function(){ return {content: this.content};};
NewsContent.of = obj => obj instanceof NewsContent? obj: new NewsContent(obj);

function PageData({content,number,size,first,last,totalElements,totalPages,numberOfElements,empty}){
    this.content = content;
    this.number = number;
    this.size = size;
    this.first = first;
    this.last = last;
    this.totalElements = totalElements;
    this.totalPages = totalPages;
    this.numberOfChannels = numberOfElements;
    this.empty = empty;
}

PageData.of = data => data instanceof PageData? data: new PageData(data);

const Model = {
    News,NewsContent,PageData
};
export default Model;