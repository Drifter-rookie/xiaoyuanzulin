import Vue from 'vue';
import Router from 'vue-router';

const originalReplace = Router.prototype.replace;
Router.prototype.replace = function replace(location) {
    return originalReplace.call(this, location).catch(err => err);
};
const originalPush = Router.prototype.push
Router.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
};

Vue.use(Router);

export default new Router({
    mode: 'hash', // 使用hash模式避免服务端配置问题
    routes: [
        {
            path: '/',
            redirect: '/index'
        },
        {
            path: '/index',
            component: () => import('../views/index.vue'),
            meta: { title: '校园闲置物品租售平台', requiresAuth: false }
        },
        {
            path: '/all',
            component: () => import('../views/all.vue'),
            meta: { title: '校园闲置物品租售平台', requiresAuth: false }
        },
        {
            path: '/sell',
            component: () => import('../views/sell.vue'),
            meta: { title: '校园闲置物品租售平台', requiresAuth: false }
        },
        {
            path: '/hire',
            component: () => import('../views/hire.vue'),
            meta: { title: '校园闲置物品租售平台', requiresAuth: false }
        },
        {
            path: '/shopping_cart',
            component: () => import('../views/shopping_cart.vue'),
            meta: { title: '购物车|校园闲置物品租售平台', requiresAuth: true }
        },
        {
            path: '/search',
            component: () => import('../views/search.vue'),
            meta: { title: '闲置物品 | 校园闲置物品租售平台', requiresAuth: false }
        },
        {
            path: '/personal_center',
            component: () => import('../views/personal_center.vue'),
            meta: { title: '个人中心 | 校园闲置物品租售平台', requiresAuth: true }
        },
        {
            path: '/message',
            component: () => import('../views/message.vue'),
            meta: { title: '消息 | 校园闲置物品租售平台', requiresAuth: true }
        },
        {
            path: '/order_massage',
            component: () => import('../views/order_massage.vue'),
            meta: { title: '订单消息 | 校园闲置物品租售平台', requiresAuth: true }
        },
        {
          path: '/reportItem',
          component: () => import('../views/reportItem.vue'),
          meta: { title: ' 举报商品 | 校园闲置物品租售平台', requiresAuth: true }
         },
         {
          path: '/reportUser',
          component: () => import('../views/reportUser.vue'),
          meta: { title: ' 举报用户 | 校园闲置物品租售平台', requiresAuth: true }
        },
        {
            path: '/reportOrder',
            component: () => import('../views/reportOrder.vue'),
            meta: { title: ' 举报订单 | 校园闲置物品租售平台', requiresAuth: true }
        },
        {
            path: '/release',
            component: () => import('../views/release.vue'),
            meta: { title: '上架闲置物品 | 校园闲置物品租售平台', requiresAuth: true }
        },
        {
            path: '/details',
            component: () => import('../views/idle_details.vue'),
            meta: { title: '闲置物品详情 | 校园闲置物品租售平台', requiresAuth: false }
        },
        {
            path: '/idle_details',
            component: () => import('../views/idle_details.vue'),
            meta: { title: '闲置物品详情 | 校园闲置物品租售平台', requiresAuth: false }
        },
        {
            path: '/order',
            component: () => import('../views/order.vue'),
            meta: { title: '订单详情 | 校园闲置物品租售平台', requiresAuth: true }
        },
        {
            path: '/login',
            component: () => import('../views/login.vue'),
            meta: { title: '登录 | 校园闲置物品租售平台', requiresAuth: false }
        },
        {
            path: '/sign_in',
            component: () => import('../views/sign_in.vue'),
            meta: { title: '注册 | 校园闲置物品租售平台', requiresAuth: false }
        },
        {
            path: '/login_admin',
            component: () => import('../views/login_admin.vue'),
            meta: { title: '后台登陆', requiresAuth: false }
        },
        {
            path: '/platform_admin',
            component: () => import('../views/platform_admin.vue'),
            meta: { title: '后台管理', requiresAuth: true }
        },
        {
            path: '*',
            redirect: '/'
        }
    ]
});
