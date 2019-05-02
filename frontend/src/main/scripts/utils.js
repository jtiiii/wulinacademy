const Utils = {
    getMiddleOfWindow(window,width,height){
        return this.getMiddlePosition(window.innerWidth, window.innerHeight, width, height)
    },
    getMiddlePosition(pw,ph,width,height){
        return [ (pw - width) / 2, (ph - height ) / 2 ]
    },
    outsideClick:{
        bind: function(el, binding){
            let documentHandle = function(e){
                if(el.contains(e.target)){
                    return false;
                }
                if(binding.expression && binding.value instanceof Function){
                    if(binding.modifiers.el){
                        binding.value(el);
                    }else{
                        binding.value(e);
                    }
                }
            };
            el._outsideclick_ = documentHandle;
            document.addEventListener("click", documentHandle);
        },
        unbind: function (el) {
            document.removeEventListener("click", el._outsideclick_);
            delete el._outsideclick_;
        }
    }
};
export default Utils;