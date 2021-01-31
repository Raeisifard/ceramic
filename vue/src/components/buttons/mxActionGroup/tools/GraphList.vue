<template>
    <div>
        <DataGrid :data="data.graphProfiles" @rowExpand="onRowExpand($event)" :dblclickToEdit="true" :virtualScroll="true"
                  selectionMode="cell" editMode="cell" @editEnd="graphNameChanged"
                  style="height:250px">
            <GridColumn align="center" cellCss="datagrid-td-rownumber" width="30">
                <template slot="body" slot-scope="scope">
                    {{scope.rowIndex + 1}}
                </template>
            </GridColumn>
            <GridColumn :expander="true" width="30"></GridColumn>
            <GridColumn field="graph_name" title="Name" :editable="true" :editRules="['required','length[3,16]']"
                        align="left"></GridColumn>
            <GridColumn field="graph_id" title="Graph ID" align="center" width="90"
                        :cellCss="getGraphIdCellCss"></GridColumn>
            <GridColumn field="active" title="Status" align="center" width="50" :cellCss="getStatusCellCss"/>
            <GridColumn title="Load" align="center" width="50">
                <template slot="body" slot-scope="scope">
                    <!--<a href="javascript:void(0);"
                       @click="editor['getGraph'](scope.row.graph_id); wnd.destroy ()">View</a>-->
                    <a href="javascript:void(0);" @click.prevent="getGraph(scope.row.graph_id);">View</a>
                </template>
            </GridColumn>
            <GridColumn field="modifications" title="Deploy Date" width="165" align="center">${modifications[0]}
            </GridColumn>
            <GridColumn field="modifications" title="Last Modify" width="165" align="center">
                ${modifications[modifications.length - 1]}
            </GridColumn>
            <GridColumn field="revision" title="Rev." align="center" width="50"></GridColumn>
            <template slot="detail" slot-scope="scope">
                <div class="item f-row">
                    <div class="f-column" style="width: 100%; padding-bottom: 5px;">
                        <span style="font-style: italic; padding: 3px;">Deployment ID:</span>
                        <div class="item-desp f-full"> {{scope.row.subData}}</div>
                    </div>
                </div>
            </template>
        </DataGrid>
    </div>
</template>

<script>
  export default {
    name: "GraphList",
    data() {
      return {
        data: [],
        editor: this.$store.getters.getEditor,
        graph: this.$store.getters.getEditor.graph
      };
    },
    methods: {
      graphNameChanged(event) {
        let name = event.row.graph_name.trim();
        if (event.originalValue !== name) {
          this.$store.getters.getEb.send('mx.vx', "Set Graph Name", {
              cmd: "set_graph_name",
              uid: event.row.graph_id,
              name: name
            },
            (err, res) => {
              if (err) {
                mxLog.warn(JSON.stringify(err));
              }
            });
          if (this.$store.getters.getGraphId === event.row.graph_id) {
            this.$store.dispatch("setGraphName", name);
          }
        }
      },
      showModalWindow(graph, title, content, width, height) {
        let that = this;
        let background = document.createElement('div');
        background.style.position = 'absolute';
        background.style.left = '0px';
        background.style.top = '0px';
        background.style.right = '0px';
        background.style.bottom = '0px';
        background.style.background = 'black';
        mxUtils.setOpacity(background, 50);
        document.body.appendChild(background);

        if (mxClient.IS_IE) {
          new mxDivResizer(background);
        }

        let x = Math.max(0, document.body.scrollWidth / 2 - width / 2);
        let y = Math.max(10, ( document.body.scrollHeight ||
          document.documentElement.scrollHeight ) / 2 - height * 2 / 3);
        let wnd = new mxWindow(title, content, x, y, width, height, false, true);
        that.wnd = wnd;
        wnd.setClosable(true);
        // Fades the background out after after the window has been closed
        wnd.addListener(mxEvent.DESTROY, function(evt) {
          graph.setEnabled(true);
          mxEffects.fadeOut(background, 50, true,
            10, 30, true);
          that.$emit("closeGraphList");
        });

        graph.setEnabled(false);
        graph.tooltipHandler.hide();
        wnd.setVisible(true);
      },
      getData(item = null) {
        let that = this;
        if (item === null) {
          this.$store.getters.getEb.send('mx.vx', "Get Graph List", {
              cmd: "list",
              uid: this.$store.getters.getGraphId
            },
            (err, res) => {
              if (err == null) {
                that.data = res.body;
              } else {
                mxLog.warn(JSON.stringify(err));
              }
            });
          this.showModalWindow(this.graph, 'Graph List', this.$el, 750, 273);
        } else {
          if (item.deploy_id)
            return item.deploy_id;
          else
            return "No Deployment ID!";
        }
      },
      onRowExpand(row) {
        if (!row.subData) {
          row.subData = this.getData(row);
        }
      },
      getGraphIdCellCss(row, value) {
        return { color: "#0c7cd5", fontSize: "1em", fontStyle: "oblique" };
      },
      getStatusCellCss(row, value) {
        if (value === true) {
          return {
            background: "url(src/images/active.png) no-repeat center center",
            backgroundSize: "55%"
          };
        }
        return {
          background: "url(src/images/passive.png) no-repeat center center",
          backgroundSize: "55%"
        };
      },
      getGraph(graphId) {
        this.wnd.destroy();
        this.editor.execute("get_graph", null, graphId);
      },
    },
    mounted() {
      let that = this;
      this.getData();
    }
  }
</script>

<style scoped>
    .item_id {
        color: #0c7cd5;
        font-size: 2em;
        font-style: oblique;
    }

    .item-desp {
        margin-left: 30px;
        padding: 0 10px 10px 10px;
        font-size: 2em;
        color: #6ab8f7;
        text-align: center;
    }
</style>