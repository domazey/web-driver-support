package com.xinaiz.wds.js.scripts

val Scripts.CHANGE_TAG_NAME
    get() =
        """var original    = arguments[0];
var replacement = document.createElement(arguments[1]);

for(var i = 0, l = original.attributes.length; i < l; ++i){
    var nodeName  = original.attributes.item(i).nodeName;
    var nodeValue = original.attributes.item(i).nodeValue;

    replacement.setAttribute(nodeName, nodeValue);
}
replacement.innerHTML = original.innerHTML;
original.parentNode.replaceChild(replacement, original);"""