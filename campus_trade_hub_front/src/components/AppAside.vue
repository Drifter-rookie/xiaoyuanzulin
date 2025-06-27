<template>
    <div>
        <el-drawer :visible="drawerVisible" @close="closeDrawer" :with-header="false" :size="drawerSize"
            direction="ltr">
            <div style="min-height: 85vh;">
                <div class="notice_container">
                    <h1>安全警示</h1>
                    <p class="warning">！！本网站仅作学校学生、教职人员间闲置物品交换，谨防诈骗！！</p>
                    <p>尊敬的用户：</p>
                    <p>为了维护良好的平台秩序和保障所有用户的合法权益，我们鼓励您积极参与平台监管。若您发现任何违规商品或违规用户行为，请立即向管理员举报投诉。</p>
                    <div class="contact-info">
                        <div class="contact-item">
                            <span>管理员联系方式：</span>
                            <a href="tel:19575412315">19575412315</a>
                        </div>
                        <div class="contact-item">
                            <span>警卫处24小时服务热线：</span>
                            <a href="tel:18456741542">18456741542</a>
                        </div>
                    </div>
                    <p>您的每一次反馈都将有助于我们构建更公正、安全的交易环境。感谢您的支持与配合！</p>
                    <p>若您在使用网络服务过程中不幸遭遇任何诈骗行为，请务必第一时间与我们联系。请时刻保持警惕，确保您的网络安全。</p>
                </div>
                <div class="container">
                    <h1>公告与通知</h1>
                    <el-collapse>
                        <el-collapse-item v-for="(notice, index) in notices" :key="index">
                            <template slot="title">
                                <div style="font-size: 20px;">
                                    {{ notice.noticeType }} : {{ notice.keySentence }} {{ notice.announceTime }}
                                </div>
                            </template>
                            <div style="font-size: 15px;">{{ notice.content }}</div>
                        </el-collapse-item>
                    </el-collapse>
                </div>
            </div>
        </el-drawer>
        <div class="floating-nav">
            <div class="navbar">
                <a @click="getNoticeInfo">公告信息</a>
                <router-link v-if="!isLogin" to="/login">
                    <div class="icon-size"><i class="el-icon-edit"></i></div>
                    举报投诉
                </router-link>
                <router-link v-else to="/reportUser">
                    <div class="icon-size"><i class="el-icon-edit"></i></div>
                    举报投诉
                </router-link>
                <router-link v-if="!isLogin" to="/login">
                    <div class="icon-size"><i class="el-icon-shopping-cart-full"></i></div>
                    购物车
                </router-link>
                <router-link v-else to="/shopping_cart">
                    <div class="icon-size"><i class="el-icon-shopping-cart-full"></i></div>
                    购物车
                </router-link>
                <a href="#">
                    <div class="icon-size"><i class="el-icon-arrow-up"></i></div>顶部
                </a>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            badgeopen: true,
            searchValue: this.searchInput,
            nickname: '登录',
            avatar: '../assets/avatar.jpg',
            isLogin: false,
            notices: [],
            drawerVisible: false, 
            drawerSize: '40%', 
        };
    },
    created() {
        this.$api.getUserInfo().then(res => {
            if (res.status_code === 1) {
                this.nickname = res.data.nickname;
                this.avatar = res.data.avatar;
                res.data.signInTime = res.data.signInTime.substring(0, 10);
                this.$globalData.userInfo = res.data;
                this.isLogin = true;
                this.userid = res.data.id;
                console.log('userInfo:', res.data);
            }
            if (res.data.userMessageStatus === 1) { this.badgeopen = false; }
        })
    },
    methods: {
        getNoticeInfo() {
            this.drawerVisible = true;
            //获取公告信息
            this.$api.getAllNotice().then(res => {
                if (res.status_code == 1) {
                    this.notices = res.data.list;
                    this.total = res.data.count;
                } else {
                    this.$message.error(res.msg)
                }
            }).catch(e => {
                console.log(e)
            })
        },
        closeDrawer() {
            this.drawerVisible = false;
        }, 
    },
};
</script>

<style scoped>
.container {
    max-width: 960px;
    margin: 0 auto;
    padding: 20px;
}

h1 {
    text-align: center;
    color: #007bff;
    font-size: 24px;
    margin-bottom: 20px;
}

.notification {
    display: flex;
    align-items: center;
    margin-bottom: 10px;
}

.notification-date {
    width: 100px;
    text-align: right;
    margin-left: 10px;
    color: #6c757d;
}

.notification-title {
    flex-grow: 1;
    font-weight: bold;
    color: #007bff;
    text-decoration: none;
}

.notification-title:hover {
    text-decoration: underline;
}

.more-link {
    display: block;
    text-align: center;
    color: #007bff;
    font-size: 16px;
    margin-top: 20px;
}

.more-link::after {
    content: '>>';
    padding-left: 5px;
}

.floating-nav {
    position: fixed;
    width: 70px;
    height: 330px;
    background-color: #d38989;
    box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
}

body {
    padding-top: 100px;
}

.navbar {
    overflow: hidden;
    background-color: #ffffff;
}

.navbar a {
    float: left;
    display: block;
    color: rgb(14, 1, 1);
    text-align: center;
    padding: 14px 16px;
    text-decoration: none;
}

.navbar a:hover {
    background-color: #e48787;
    color: black;
}

.icon-size {
    font-size: 20px;
}

.notice_container {
    max-width: 690px;
    margin: 0 auto;
    background-color: white;
    border-radius: 5px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    padding: 30px;
}

h1 {
    color: #007BFF;
    text-align: center;
    font-size: 24px;
    margin-bottom: 20px;
}

p {
    font-size: 16px;
    line-height: 1.6;
    color: #333;
}

.warning {
    color: red;
    font-weight: bold;
    text-align: center;
    margin-bottom: 20px;
}

.contact-info {
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
}

.contact-item {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.contact-item a {
    color: #007BFF;
    text-decoration: none;
}
</style>