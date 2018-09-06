function ModelError(msg){
    this.msg = msg;
}
export default {
    ListItem: function(id, text, select){
        this.id = id;
        this.text = text;
        this.select = select || false;
    },
    GroupMenu: function( name, items){
        this.name = name;
        if(!items instanceof Array){
            throw new ModelError('arg: items must be Array');
        }
        this.items = items;

    }
}