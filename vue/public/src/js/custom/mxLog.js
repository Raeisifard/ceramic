mxLog.init = function() {
  if (mxLog.window == null && document.body != null) {
    var title = mxLog.consoleName + ' - mxGraph ' + mxClient.VERSION;

    // Creates a table that maintains the layout
    var table = document.createElement('table');
    table.setAttribute('width', '100%');
    table.setAttribute('height', '100%');

    var tbody = document.createElement('tbody');
    var tr = document.createElement('tr');
    var td = document.createElement('td');
    td.style.verticalAlign = 'top';

    // Adds the actual console as a textarea
    mxLog.textarea = document.createElement('textarea');
    mxLog.textarea.setAttribute('wrap', 'off');
    mxLog.textarea.setAttribute('readOnly', 'true');
    mxLog.textarea.style.height = '100%';
    mxLog.textarea.style.resize = 'none';
    mxLog.textarea.value = mxLog.buffer;

    // Workaround for wrong width in standards mode
    if (mxClient.IS_NS && document.compatMode != 'BackCompat') {
      mxLog.textarea.style.width = '99%';
    } else {
      mxLog.textarea.style.width = '100%';
    }

    td.appendChild(mxLog.textarea);
    tr.appendChild(td);
    tbody.appendChild(tr);

    // Creates the container div
    tr = document.createElement('tr');
    mxLog.td = document.createElement('td');
    mxLog.td.style.verticalAlign = 'top';
    mxLog.td.setAttribute('height', '30px');

    tr.appendChild(mxLog.td);
    tbody.appendChild(tr);
    table.appendChild(tbody);

    // Adds various debugging buttons
    mxLog.addButton('Info', function(evt) {
      mxLog.info();
    });

    mxLog.addButton('DOM', function(evt) {
      var content = mxUtils.getInnerHtml(document.body);
      mxLog.debug(content);
    });

    mxLog.addButton('Trace', function(evt) {
      mxLog.TRACE = !mxLog.TRACE;

      if (mxLog.TRACE) {
        mxLog.debug('Tracing enabled');
      } else {
        mxLog.debug('Tracing disabled');
      }
    });

    mxLog.addButton('Copy', function(evt) {
      try {
        mxUtils.copy(mxLog.textarea.value);
      } catch (err) {
        mxUtils.alert(err);
      }
    });

    mxLog.addButton('Show', function(evt) {
      try {
        mxUtils.popup(mxLog.textarea.value);
      } catch (err) {
        mxUtils.alert(err);
      }
    });

    mxLog.addButton('Clear', function(evt) {
      mxLog.textarea.value = '';
    });

    //Customize code 2 add cmd input////////////////////////////////
    let input = document.createElement("input");
    input.type = "text";
    input.name = "cmd";
    input.style.width = "99%";
    input.style.paddingLeft = "26px";
    td = document.createElement("td");
    td.append(input);
    let prompt = ">>>";
    let promptInput = document.createElement("input");
    promptInput.type = "text";
    promptInput.name = "prompt";
    promptInput.style.width = "25px";
    promptInput.style.float = "left";
    promptInput.style.position = "absolute";
    promptInput.style.left = "0";
    promptInput.style.border = "0";
    promptInput.style.marginTop = "1px";
    promptInput.style.marginLeft = "4px";
    promptInput.value = prompt;
    promptInput.disabled = true;
    promptInput.style.color = "unset";
    promptInput.style.backgroundColor = "unset";
    td.append(promptInput);
    tr = document.createElement("tr");
    tr.style.height = "12px";
    tr.append(td);
    mxLog.td.parentElement.insertAdjacentElement('beforebegin', tr);
    input.addEventListener('keydown', (e) => {
      if (e.key === 'Enter') {
        validate(e);
      }
    });

    function validate(e) {
      const text = e.target.value;
      //validation of the input...
      let cmdLine = input.value.trim();
      let cmdSplit = cmdLine.split(" ");
      if (cmdSplit.length > 0) {
        let cmd = cmdSplit[ 0 ];
        switch (cmd.toLowerCase()) {
          case "rename":
            //let editor = window.store.getters.getEditor;
            if (cmdSplit[ 1 ]) {
              let oldName = window.store.getters.getGraphName;
              window.store.dispatch("setGraphName", cmdSplit[ 1 ]);
              mxLog.debug(oldName + " => " + window.store.getters.getGraphName);
            }else{
              mxLog.warn("\"Rename\" command: Name parameter is missing!")
            }
            break;
        }
      }
      input.value = "";
    }

    ////////////////////////////////////////////////////////////////

    // Cross-browser code to get window size
    var h = 0;
    var w = 0;

    if (typeof ( window.innerWidth ) === 'number') {
      h = window.innerHeight;
      w = window.innerWidth;
    } else {
      h = ( document.documentElement.clientHeight || document.body.clientHeight );
      w = document.body.clientWidth;
    }

    mxLog.window = new mxWindow(title, table, Math.max(0, w - 320), Math.max(0, h - 210), 300, 200);
    mxLog.window.setMaximizable(true);
    mxLog.window.setScrollable(false);
    mxLog.window.setResizable(true);
    mxLog.window.setClosable(true);
    mxLog.window.destroyOnClose = false;

    // Workaround for ignored textarea height in various setups
    if (( ( mxClient.IS_NS || mxClient.IS_IE ) && !mxClient.IS_GC &&
      !mxClient.IS_SF && document.compatMode != 'BackCompat' ) ||
      document.documentMode == 11) {
      var elt = mxLog.window.getElement();

      var resizeHandler = function(sender, evt) {
        mxLog.textarea.style.height = Math.max(0, elt.offsetHeight - 70) + 'px';
      };

      mxLog.window.addListener(mxEvent.RESIZE_END, resizeHandler);
      mxLog.window.addListener(mxEvent.MAXIMIZE, resizeHandler);
      mxLog.window.addListener(mxEvent.NORMALIZE, resizeHandler);

      mxLog.textarea.style.height = '92px';
    }
  }
}