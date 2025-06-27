import request from '../utils/request';

const api = {
    userLogin(query) {
        return request({
            url: '/user/login',
            method: 'get',
            params: query
        });
    },
    logout(query) {
        return request({
            url: '/user/logout',
            method: 'get',
            params: query
        });
    },
    signIn(data) {
        return request({
            url: '/user/sign-in',
            method: 'post',
            data: data
        });
    },
    getUserInfo(query) {
        return request({
            url: '/user/info',
            method: 'get',
            params: query
        });
    },
    getoneUserInfo(query) {
        return request({
            url: '/user/userinfo',
            method: 'get',
            params: query
        });
    },
    updateUserPublicInfo(data) {
        return request({
            url: '/user/info',
            method: 'post',
            data: data
        });
    },
    updatePassword(query) {
        return request({
            url: '/user/password',
            method: 'get',
            params: query
        });
    },
    updateMessageStatus(query){
        return request({
            url: '/user/updateMessageStatus',
            method: 'get',
            params: query
        });
    },
    updateMessageStatus1(query){
        return request({
            url: '/user/updateMessageStatus1',
            method: 'get',
            params: query
        });
    },
    addAddress(data) {
        return request({
            url: '/address/add',
            method: 'post',
            data: data
        });
    },
    getAddress(query) {
        return request({
            url: '/address/info',
            method: 'get',
            params: query
        });
    },
    updateAddress(data) {
        return request({
            url: '/address/update',
            method: 'post',
            data: data
        });
    },
    deleteAddress(data) {
        return request({
            url: '/address/delete',
            method: 'post',
            data: data
        });
    },
    addIdleItem(data) {
        return request({
            url: '/idle/add',
            method: 'post',
            data: data
        });
    },
    getIdleItem(query) {
        return request({
            url: '/idle/info',
            method: 'get',
            params: query
        });
    },
    getAllIdleItem(query) {
        return request({
            url: '/idle/all',
            method: 'get',
            params: query
        });
    },
    findhotItem(query) {
        return request({
            url: '/idle/findhot',
            method: 'get',
            params: query
        });
    },
    findrecommendItem(query) {
        return request({
            url: '/idle/findrecommend',
            method: 'get',
            params: query
        });
    },
    findIdleItem(query) {
        return request({
            url: '/idle/find',
            method: 'get',
            params: query
        });
    },
    findIdleItemByLable(query) {
        return request({
            url: '/idle/label',
            method: 'get',
            params: query
        });
    },
    findAllIdleItem(query) {
        return request({
            url: '/idle/findAll',
            method: 'get',
            params: query
        });
    },
    findAllIdleItemByLable(query) {
        return request({
            url: '/idle/labelAll',
            method: 'get',
            params: query
        });
    },
    updateIdleItem(data) {
        return request({
            url: '/idle/update',
            method: 'post',
            data: data
        });
    },
    addOrder(data) {
        return request({
            url: '/order/add',
            method: 'post',
            data: data
        });
    },
    getOrder(query) {
        return request({
            url: '/order/info',
            method: 'get',
            params: query
        });
    },
    updateOrder(data) {
        return request({
            url: '/order/update',
            method: 'post',
            data: data
        });
    },
    updateorder(query) {
        return request({
            url: '/order/updatethis',
            method: 'get',
            params:query
        });
    },
    updateorder1(query) {
        return request({
            url: '/order/updatethis1',
            method: 'get',
            params:query
        });
    },
    countorder(query) {
        return request({
            url: '/order/count',
            method: 'get',
            params:query
        });
    },
    update(query) {
        return request({
            url: '/message/updatethis',
            method: 'get',
            params:query
        });
    },
    getMyOrder(query) {
        return request({
            url: '/order/my',
            method: 'get',
            params: query
        });
    },
    getMySoldIdle(query) {
        return request({
            url: '/order/my-sold',
            method: 'get',
            params: query
        });
    },
    addOrderAddress(data) {
        return request({
            url: '/order-address/add',
            method: 'post',
            data: data
        });
    },
    updateOrderAddress(data) {
        return request({
            url: '/order-address/update',
            method: 'post',
            data: data
        });
    },
    getOrderAddress(query) {
        return request({
            url: '/order-address/info',
            method: 'get',
            params: query
        });
    },
    addFavorite(data) {
        return request({
            url: '/favorite/add',
            method: 'post',
            data: data
        });
    },
    getMyFavorite(query) {
        return request({
            url: '/favorite/my',
            method: 'get',
            params: query
        });
    },
    deleteFavorite(query) {
        return request({
            url: '/favorite/delete',
            method: 'get',
            params: query
        });
    },
    checkFavorite(query) {
        return request({
            url: '/favorite/check',
            method: 'get',
            params: query
        });
    },
    sendMessage(data) {
        return request({
            url: '/message/send',
            method: 'post',
            data: data
        });
    },
    update(query) {
        return request({
            url: '/message/updatethis',
            method: 'get',
            params:query
        });
    },
    getMessage(query) {
        return request({
            url: '/message/info',
            method: 'get',
            params: query
        });
    },
    getAllIdleMessage(query) {
        return request({
            url: '/message/idle',
            method: 'get',
            params: query
        });
    },
    getAllMyMessage(query) {
        return request({
            url: '/message/my',
            method: 'get',
            params: query
        });
    },
    deleteMessage(query) {
        return request({
            url: '/message/delete',
            method: 'get',
            params: query
        });
    },
    getGoods(query) {
        return request({
            url: '/admin/idleList',
            method: 'get',
            params: query
        });
    },
    updateGoods(query) {
        return request({
            url: '/admin/updateIdleStatus',
            method: 'get',
            params: query
        });
    },

    getOrderList(query) {
        return request({
            url: '/admin/orderList',
            method: 'get',
            params: query
        });
    },
    deleteOrder(query) {
        return request({
            url: '/admin/deleteOrder',
            method: 'get',
            params: query
        });
    },
    deleteAdmin(query) {
        return request({
            url: '/admin/deleteAdmin',
            method: 'get',
            params: query
        });
    },
    getUserData(query) {
        return request({
            url: '/admin/userList',
            method: 'get',
            params: query
        });
    },
    getUserManage(query) {
        return request({
            url: '/admin/list',
            method: 'get',
            params: query
        });
    },
    updateUserStatus(query){
        return request({
            url: '/admin/updateUserStatus',
            method: 'get',
            params: query
        });
    },
    regAdministrator(data){
        return request({
            url: '/admin/add',
            method: 'post',
            data: data
        });
    },
    adminLogin(query) {
        return request({
            url: '/admin/login',
            method: 'get',
            params: query
        });
    },
    loginOut(query) {
        return request({
            url: '/admin/loginOut',
            method: 'get',
            params: query
        });
    },
    updatepassword(query){
        return request({
            url: '/admin/updateUserpassword',
            method: 'get',
            params: query
        });
    },
    getAdminInfo(query) {
        return request({
            url: '/admin/info',
            method: 'get',
            params: query
        });
    },
    getNoticeInfo(query) {
        return request({
            url: '/notice/noticeList',
            method: 'get',
            params: query
        });
    },
    deleteNotice(query) {
        return request({
            url: '/notice/deleteNotice',
            method: 'get',
            params: query
        });
    },
    addNotice(data) {
        return request({
            url: '/notice/addNotice',
            method: 'post',
            data: data
        });
    },
    getAllNotice(query) {
        return request({
            url: '/notice/allNotice',
            method: 'get',
            params: query
        });
    },
    addShopping(data) {
        return request({
            url: '/shoppingCart/add',
            method: 'post',
            data: data
        });
    },
    getMyShopping(query) {
        return request({
            url: '/shoppingCart/my',
            method: 'get',
            params: query
        });
    },
    deleteShopping(query) {
        return request({
            url: '/shoppingCart/delete',
            method: 'get',
            params: query
        });
    },
    checkShopping(query) {
        return request({
            url: '/shoppingCart/check',
            method: 'get',
            params: query
        });
    },
    getRIdleData(query) {
        return request({
            url: '/admin/report1List',
            method: 'get',
            params: query
        });
    },
    getRUserData(query) {
        return request({
            url: '/admin/report2List',
            method: 'get',
            params: query
        });
    },
    getROrderData(query) {
        return request({
            url: '/admin/report3List',
            method: 'get',
            params: query
        });
    },
    addReportItem(data) {
        return request({
            url: '/reportItem/add',
            method: 'post',
            data: data
        });
    },
    addReportUser(data) {
        return request({
            url: '/reportUser/add',
            method: 'post',
            data: data
        });
    },
    addReportOrder(data) {
        return request({
            url: '/reportOrder/add',
            method: 'post',
            data: data
        });
    },
   updateRIdleStatus(query){
        return request({
            url: '/admin/updateRIdleStatus',
            method: 'get',
            params: query
        });
    },
    updateRUserStatus(query){
        return request({
            url: '/admin/updateRUserStatus',
            method: 'get',
            params: query
        });
    },
    updateROrderStatus(query){
        return request({
            url: '/admin/updateROrderStatus',
            method: 'get',
            params: query
        });
    },
    addReport1(data) {
        return request({
            url: '/reportItem/add',
            method: 'post',
            data: data
        });
    },
    addReportUser(data) {
        return request({
            url: '/reportUser/add',
            method: 'post',
            data: data
        });
    },
    deleteReportIdle(query){
        return request({
            url: '/admin/deleteReportItem',
            method: 'get',
            params: query
        });
    },
    deleteReportUser(query){
        return request({
            url: '/admin/deleteReportUser',
            method: 'get',
            params: query
        });
    },
    deleteReportOrder(query){
        return request({
            url: '/admin/deleteReportOrder',
            method: 'get',
            params: query
        });
    },
};
export default api;