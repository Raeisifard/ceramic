<template>
    <v-app style="width: 200px; height: 200px;">
        <v-content>
            <div class="text-xs-center">
                <v-dialog
                        v-model="dialog"
                        width="500"
                >
                    <template v-slot:activator="{ on }">
                        <v-btn
                                color="red lighten-2"
                                dark
                                v-on="on"
                        >
                            Click Me
                        </v-btn>
                    </template>

                    <v-card>
                        <v-card-title
                                class="headline grey lighten-2"
                                primary-title
                        >
                            Privacy Policy
                        </v-card-title>

                        <v-card-text>
                            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                        </v-card-text>

                        <v-divider></v-divider>

                        <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn
                                    color="primary"
                                    flat
                                    @click="dialog = false"
                            >
                                I accept
                            </v-btn>
                        </v-card-actions>
                    </v-card>
                </v-dialog>
            </div>
        </v-content>
    </v-app>
</template>

<script>
  import Vuetify from 'vuetify'
  import Vue from 'vue'

  Vue.use(Vuetify);
  export default {
    name: "vxAlert",
    data() {
      return {
        dialog: false
      }
    }
  }
</script>

<style scoped>
    /*@import "~vuetify/dist/vuetify.css";*/ /*'vuetify/dist/vuetify.min.css'*/
</style>