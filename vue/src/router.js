import Vue from "vue";
import Router from "vue-router";
import stuUser from "./views/stuUser.vue";
import Layout from "./layout/Layout.vue";
Vue.use(Router);

export default new Router({
  routes: [
    {
      path: "/",
      name: "Layout",
      component: Layout,
      redirect:"/stuUser",
      children: [
        {
          path: 'stuUser',
          name: 'stuUser',
          component: stuUser
        },
        {
          path: "test",
          name: "Test",
          component: () => import("./views/Test")
        },
        {
          path:"person",
          name:"Person",
          component: () => import("./views/Person")
        },
        {
          path:"mytest",
          name:"MyTest",
          component: () => import("./views/MyTest")
        },
        {
          path:"myscore",
          name:"MyScore",
          component: () => import("./views/MyScore")
        },
        {
          path:"stuTest",
          name:"StuTest",
          component: () => import("./views/StuTest")
        }
      ]
    },
    {
      path: "/login",
      name: "Login",
      component: () => import("./views/Login")
    },
    {
      path: "/register",
      name: "Register",
      component: () => import("./views/Register")
    },
    {
      path:"/testList",
      name:"TestList",
      component:() => import("./views/TestList")
    }

  ],
  mode:'history'

});

