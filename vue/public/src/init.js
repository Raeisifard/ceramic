//Add necessary codecs
function VueComponent() {

}

//VueComponent.prototype.name = "VueComponent";
mxCodecRegistry.addAlias(mxUtils.getFunctionName(VueComponent), 'VueComponent');
window[ "VueComponent" ] = VueComponent;

let codec = new mxObjectCodec(new VueComponent());
codec.exclude.push('VueComponent');
codec.encode = function(enc, obj) {
  let node = enc.document.createElement('VueComponent');
  node.setAttribute("name", obj.$options.name);
  return node;
};

codec.decode = function(dec, node, into) {
  let cell = dec.objects[ node.parentNode.getAttribute("id") ];
  /*if(cell.getType() === 'chart'){
    let state = graph.view.getState(cell);
    state.style[ mxConstants.STYLE_FILLCOLOR ] = cell.getData().config.fillColor;
    state.shape.apply(state);
    state.shape.redraw();
  }*/
  return store.getters.getVueComponentByObject({ $store: store }, cell);
};
mxCodecRegistry.register(codec);

function JsonObject() {
  this.type = null;
  this.config = new function Config() {
  };
  this.setting = new function Setting() {
  };
}

JsonObject.prototype.name = "JsonObject";
mxCodecRegistry.addAlias(mxUtils.getFunctionName(JsonObject), 'JsonObject');
window[ "JsonObject" ] = JsonObject;
codec = new mxObjectCodec(new JsonObject());
codec.encode = function(enc, obj) {
  let node = enc.document.createElement('JsonObject');
  mxUtils.setTextContent(node, JSON.stringify(obj));

  return node;
};

codec.decode = function(dec, node, into) {
  let obj = JSON.parse(mxUtils.getTextContent(node));
  obj.constructor = JsonObject;

  return obj;
};
mxCodecRegistry.register(codec);

let mxObjectCodecOld = mxObjectCodec.prototype.decodeNode;
mxObjectCodec.prototype.decodeNode = function(dec, node, obj) {
  if (node != null) {
    if (node.getAttribute('id') != null && node.getAttribute('vertex') === '1') {
      let ref = node.getAttribute('id');
      let vertx = dec.objects[ ref ] || dec.lookup(ref);
      if (vertx != null && vertx.isVertex())
        return;
    }
    mxObjectCodecOld.apply(this, [dec, node, obj]);
  }
};