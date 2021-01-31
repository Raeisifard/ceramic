<template>

</template>

<script>
  import JsonObject from "./data";
  import dbLabel from "./dbLabel";
  import { EventBus } from "../../../event-bus";

  export default {
    name: 'mxDatabase',
    props: {
      sidebar: {
        required: true
      }
    },
    data() {
      return {
        image: "./src/images/icons48/database.png",
        type: "database"
      }
    },
    methods: {
      addSidebarIcon: function(graph, sidebar, ctx, image, type) {
        let label = null;
        let funct = function(graph, evt, cell, x, y) {
          let parent = graph.getDefaultParent();
          let model = graph.getModel();
          let v1 = null;
          model.beginUpdate();
          try {
            v1 = graph.insertVertex(parent, null, label, x, y, 96, 96, type);
            v1.setConnectable(false);
            v1.setType(type);
            //v1.setValue("vueComponent");
            v1.setData(new JsonObject());
            // Presets the collapsed size
            v1.geometry.alternateBounds = new mxRectangle(0, 0, 70, 30);
            // Adds the ports at various relative locations
            let port = graph.insertVertex(v1, null, 'Trigger', 0, 0.25, 12, 12,
              'port;image=editors/images/overlays/flash.png;align=right;imageAlign=right;spacingRight=18', true);
            port.geometry.offset = new mxPoint(-6, 4);
            port.direction = "in";
            port = graph.insertVertex(v1, null, 'Input', 0, 0.75, 12, 12,
              'port;image=editors/images/overlays/check.png;align=right;imageAlign=right;spacingRight=18', true);
            port.geometry.offset = new mxPoint(-6, -4);
            port.direction = "in";
            port = graph.insertVertex(v1, null, 'Error', 1, 0.25, 12, 12,
              'port;image=editors/images/overlays/error.png;spacingLeft=18', true);
            port.geometry.offset = new mxPoint(-6, 2);
            port.direction = "out";
            port = graph.insertVertex(v1, null, 'Result', 1, 0.75, 12, 12,
              'port;image=editors/images/overlays/information.png;spacingLeft=18', true);
            port.geometry.offset = new mxPoint(-6, -4);
            port.direction = "out";
            v1.vueComponent = ctx.$store.getters.getVueComponentByObject(ctx, v1);
          } finally {
            model.endUpdate();
          }
          graph.setSelectionCell(v1);
        };
        let dashStyle = { width: 96, height: 96 };
        // Creates the image which is used as the sidebar icon (drag source)
        let img = document.createElement('img');
        img.setAttribute('src', image);
        img.style.width = '44px';
        img.style.height = '48px';
        img.style.paddingLeft = '4px';
        img.title = 'Drag this to the diagram to create a new vertex';
        sidebar.appendChild(img);

        let dragElt = document.createElement('div');
        dragElt.style.border = 'dashed black 1px';
        dragElt.style.width = `${dashStyle.width}px`;
        dragElt.style.height = `${dashStyle.height}px`;

        // Creates the image which is used as the drag icon (preview)
        let ds = mxUtils.makeDraggable(img, graph, funct, dragElt, 0, 0, true, true);
        ds.setGuidesEnabled(true);
      },
      openConfig: function(menu, cell, evt, cx) {
        let cmd = {};
        cmd.style = { width: '500px', height: '300px' };
        cmd.name = "config";
        switch (cell.getData().dbType) {
          case "ArangoDb":
            cmd.title = "Connection Config for Arangodb Server";
            break;
          case "Nitrite":
            cmd.title = "Connection Config for \"Dizitart\" Nitrite Server";
            break;
          case "SqlServer":
          default:
            cmd.title = "Connection config for SQL Server";
            break;
        }
        cx.$store.dispatch("setCell", cell);
        cx.$store.commit("SET_CMD", cmd);
        window.easyDialog.open();
        window.easyDialog.moveToTop();
        window.easyDialog.center();
      },
      openEditor: function(menu, cell, evt, cx) {
        let data = cell.getData();
        let cmd = {};
        cmd.style = { width: '700px', height: '500px' };
        cmd.name = "editor";
        switch (cell.getData().dbType) {
          case "ArangoDb":
            cmd.title = "AQL Editor for Arangodb Query";
            break;
          case "Nitrite":
            cmd.title = "NoSQL Editor for Nitrite Query";
            break;
          case "SqlServer":
          default:
            cmd.title = "SQL Editor for MS SQL Server Query";
            break;
        }
        cx.$store.dispatch("setCell", cell);
        cx.$store.commit("SET_CMD", cmd);
        window.easyDialog.open();
        window.easyDialog.moveToTop();
        window.easyDialog.center();
      },
      getVueComponent: function(cell, ctx) {
        const _database = {
          components: {
            dbLabel
          },
          props: ['cell', 'store'],
          template: '<db-label :cell="cell" :store="store"></db-label>',
        };
        const ComponentClass = Vue.extend(_database);
        let instance = new ComponentClass({
          propsData: { cell: cell, store: ctx.$store }
        });
        instance.$mount(); // pass nothing
        //that.$refs.container.appendChild(instance.$el)
        function vueComponent(val) {
          this.vueComponent = val;
        }

        return new vueComponent(instance);
      }
    },
    mounted() {
      let that = this;
      let editor = this.$store.getters.getEditor;
      let graph = editor.graph;
      let graphConvertValueToString = graph.convertValueToString;
      graph.convertValueToString = function(cell) {
        if (this.model.isVertex(cell) && cell.getType() === "database" && typeof cell.vueComponent != 'undefined') {
          return cell.vueComponent.$el;
        }
        return graphConvertValueToString.apply(this, arguments);
      };
      EventBus.$on("mx.set_vue_component", function(model) {
        model.beginUpdate();
        try {
          model.getChildVertices(model.root.children[ 0 ]).forEach(c => {
            if (c.getType() === 'database') {
              c.vueComponent = that.getVueComponent(c, that);
            }
          });
        } finally {
          graph.refresh(model.root.children[ 0 ]);
          model.endUpdate();
        }
      });
      this.$nextTick(function() {
        //that.configureStylesheet(graph);
        that.addSidebarIcon(graph, that.sidebar, that, that.image, that.type, that.constants);
      });
      // Installs context menu
      let defaultMenu = graph.popupMenuHandler.factoryMethod;
      graph.popupMenuHandler.factoryMethod = function(menu, cell, evt) {
        defaultMenu(menu, cell, evt);
        if (cell != null && cell.getType() === 'database') {

          menu.addItem('Config', 'editors/images/config.png', function() {
            that.openConfig(menu, cell, evt, that);
          });
          menu.addItem('Query Editor', 'editors/images/editor.png', function() {
            that.openEditor(menu, cell, evt, that);
          });
        }
      };
      const oldDeploy = editor.deploy;
      editor.deploy = function(mxCell) {
        let jModel = oldDeploy(mxCell);
        if (jModel) {
          return jModel;
        } else {
          if (mxCell.getType() === 'database') {
            let database = {
              type: mxCell.getType(),//type = "database";
              id: mxCell.getId(),
              data: mxCell.getData(),
              trigger: [],
              input: [],
              error: [],
              result: []
            };
            //Trigger
            if (mxCell.children[ 0 ].edges)
              mxCell.children[ 0 ].edges.forEach(function(edge, i, arr) {
                if (edge.source.getId() !== mxCell.children[ 0 ].getId()) {
                  database.trigger.push(edge.source.getParent().getType() + '.' +
                    edge.source.getParent().getId() + '.' + edge.source.getValue().toLowerCase());
                }
              }, that);

            //Input
            if (mxCell.children[ 1 ].edges)
              mxCell.children[ 1 ].edges.forEach(function(edge, i, arr) {
                if (edge.source.getId() !== mxCell.children[ 1 ].getId()) {
                  database.input.push(edge.source.getParent().getType() + '.' +
                    edge.source.getParent().getId() + '.' + edge.source.getValue().toLowerCase());
                }
              }, that);

            //Error
            if (mxCell.children[ 2 ].edges)
              mxCell.children[ 2 ].edges.forEach(function(edge, i, arr) {
                if (edge.target.getId() !== mxCell.children[ 2 ].getId()) {
                  database.error.push(edge.target.getParent().getType() + '.' +
                    edge.target.getParent().getId() + '.' + edge.target.getValue().toLowerCase());
                }
              }, that);

            //Result
            if (mxCell.children[ 3 ].edges)
              mxCell.children[ 3 ].edges.forEach(function(edge, i, arr) {
                if (edge.target.getId() !== mxCell.children[ 3 ].getId()) {
                  database.result.push(edge.target.getParent().getType() + '.' +
                    edge.target.getParent().getId() + '.' + edge.target.getValue().toLowerCase());
                }
              }, that);

            return database;
          } else
            return false;
        }
      };
      const _database = {
        name: "vue_database",
        components: {
          dbLabel
        },
        props: ['cell', 'store'],
        template: '<db-label :cell="cell" :store="store"></db-label>',
      };
      this.$store.commit("ADD_VUE_OBJECT", _database);
    }
  }
</script>

