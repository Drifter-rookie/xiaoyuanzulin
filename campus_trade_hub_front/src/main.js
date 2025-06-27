import Vue from 'vue';
import App from './App.vue';
import router from './router';
import ElementUI from 'element-ui';
import $ from 'jquery'
import 'element-ui/lib/theme-chalk/index.css';
import 'babel-polyfill';
import GoEasy from 'goeasy-lite';
import api from './api/index.js';
Vue.prototype.$api = api;

let globalData={
    userInfo:{
        nickname:''
    }
};
let sta={
    isLogin:false,
    adminName:''
};

let goeasy = GoEasy.getInstance({
    host:"hangzhou.goeasy.io",  
    appkey:"BC-7302d47494b342f3bc28a08c7851e523",
    modules:['pubsub']
  });
Vue.prototype.$sta = sta;

Vue.prototype.$globalData=globalData;

Vue.prototype.goeasy =new GoEasy({
    host:"hangzhou.goeasy.io",  
    appkey:"BC-7302d47494b342f3bc28a08c7851e523",
    modules:['pubsub'],
});

Vue.config.productionTip = false;

Vue.use(ElementUI, {
    size: 'medium'
});

goeasy.connect({
    onSuccess: function () {  //连接成功
      console.log("GoEasy connect successfully.") //连接成功
    },
    onFailed: function (error) { //连接失败
      console.log("Failed to connect GoEasy, code:"+error.code+ ",error:"+error.content);
    },
    onProgress:function(attempts) { //连接或自动重连中
      console.log("GoEasy is connecting", attempts);
    }
});

// 定义需要登录才能访问的路由
const authRoutes = [
    '/personal_center',
    '/message',
    '/order_massage',
    '/release',
    '/order',
    '/shopping_cart',
    '/reportItem',
    '/reportUser',
    '/reportOrder'
];

router.beforeEach((to, from, next) => {
    document.title = `${to.meta.title}`;
    
    // 检查是否是需要登录的页面
    const requiresAuth = to.matched.some(record => record.meta.requiresAuth);
    
    // 检查是否有token
    const hasToken = localStorage.getItem('token');
    
    // 如果是登录页面且已经有token，直接跳转到首页
    if (to.path === '/login' && hasToken) {
        return next('/index');
    }
    
    // 如果页面需要认证且没有token
    if (requiresAuth && !hasToken) {
        // 如果没有用户信息，尝试获取
        if (!Vue.prototype.$globalData.userInfo.nickname) {
            api.getUserInfo().then(res => {
                if (res.status_code === 1) {
                    res.data.signInTime = res.data.signInTime.substring(0, 10);
                    Vue.prototype.$globalData.userInfo = res.data;
                    next();
                } else {
                    next('/login');
                }
            }).catch(e => {
                next('/login');
            });
        } else {
            next('/login');
        }
    } else {
        // 如果不需要认证或者有token，直接通过
        next();
    }
});

new Vue({
    router,
    render: h => h(App)
}).$mount('#app');
