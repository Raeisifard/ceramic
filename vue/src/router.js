import Vue from 'vue'
import Router from 'vue-router'
import Editor from './views/Editor.vue'
import mxWindow from './components/tools/mxComponents/mxWindow.vue'

Vue.use(Router);

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'editor',
      component: Editor,
     /* children: [
        {
          path: 'pattern',
          component: function() {
            return import('./components/tools/mxPattern/blockly/VueBlockly.vue')
          }
        },
        {
          // UserProfile will be rendered inside User's <router-view>
          // when /user/:id/profile is matched
          path: '',
          component: mxWindow
        }
      ]*/
    },
    {
      path: '/ceramic',
      component: Editor,
    },
    {
      path: '/index.html',
      component: Editor,
    },
    {
      path: '/ceramic/index.html',
      component: Editor,
    },
    {
      path: '/pattern',
      name: 'blockly',
      component: function() {
        return import('./components/tools/mxPattern/blockly/BlocklyEditor.vue')
      }
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: function() {
        return import(/* webpackChunkName: "about" */ './views/About.vue')
      }
    },
    {
      path: '/chart',
      name: 'chart',
      component: function() {
        return import('vue-chartjs/src/examples/App.vue')
      }
    },
    {
      path: '/switch',
      name: 'switch',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: function() {
        return import(/* webpackChunkName: "about" */ './views/Switch.vue')
      }
    },
    {
      path: '/parquet',
      name: 'parquet',
      meta: {title: 'Parquet 1.0.0'},
      component: function() {
        return import('./components/tools/mxParquet/blockly/BlocklyEditor.vue')
      },
      beforeEnter: (to, from, next) => {
        document.title = to.meta.title;
        next();
      }
    }/*,
    {
      path: '/mx-window/:type',
      // You could also have named views at tho top
      component: mxWindow,
      children: [{
        path: 'settings',
        component:''
      }, {
        path: 'editor',
        components: {
          default: '',
          helper: ''
        }
      }]
    }*/
  ]
})
