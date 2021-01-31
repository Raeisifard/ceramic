<template>
    <div class="container">
        <h2 style="margin:0;">Database</h2>
       <!-- <img src="./src/icons/database.png" width="48" height="48">-->
        <img :src="`${imgPreUrl}/${dbName}.png`" width="48" height="48">
        <select name="cars" v-model="dbName">
            <option value="SqlServer">SQL Server</option>
            <option value="ArangoDb">Arango DB</option>
            <option value="Nitrite">Nitrite</option>
        </select>
    </div>
</template>

<script>
  export default {
    name: "dbLabel",
    data() {
      return {
        imgPreUrl:"./src/images/icons48",
        dbName: this.getInitDbName()
      }
    },
    props: ['cell', 'store'],
    methods: {
      getInitDbName: function() {
        return this.cell.getData().dbType;
      }
    },
    watch: {
      dbName: function(val) {
        this.cell.getData().dbType = val;
        switch (val.toLowerCase()) {
          case "arangodb":
            this.cell.getData().theme = "solarized_light";
            break;
          case "nitrite":
            this.cell.getData().theme = "gruvbox";
            break;
          case "sqlserver":
          default:
            this.cell.getData().theme = "sqlserver";
            break;
        }
      }
    }
  }
</script>

<style scoped>
    .container {
        display: flex;
        flex-direction: column;
        /*top: -5px;*/
        position: relative;
        align-items: center;
    }

    select {
        text-align-last: center;
        margin-top: 5px;
        border-radius: 10px;
        -webkit-appearance: searchfield;
        background-color: navajowhite;
    }
</style>