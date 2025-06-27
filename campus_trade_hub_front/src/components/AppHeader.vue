<template>
    <div class="header">
        <div class="header-container">
            <div class="app-name">
                <router-link to="/"><b style="font-size: 40px; color: #409EFF;">校园闲置物品租售系统</b></router-link>
            </div>
            <el-menu :default-active="$route.path" class="el-menu-demo" mode="horizontal" @select="handleSelect"
                text-color="#000000" font-size="30px" active-text-color="rgb(110, 150, 199)" router>
                <el-menu-item index="/reportUser" class="memu"><i class="el-icon-user"></i>举报用户</el-menu-item>
                <el-menu-item index="/reportItem" class="memu"><i class="el-icon-sell"></i>举报商品</el-menu-item>
                <el-menu-item index="/reportOrder" class="memu"><i class="el-icon-box"></i>举报订单</el-menu-item>
            </el-menu>
            <router-link v-if="!isLogin" class="user-name-text" to="/login">登录</router-link>
            <el-dropdown trigger="click" v-else>
                <div style="cursor:pointer;display: flex;align-items: center;">
                    <div style="font-size: 18px;color: #ff0303;padding-right: 5px;">{{ nicknameValue ?
                nicknameValue :
                nickname }}
                    </div>
                    <el-avatar :size="60" :src="avatarValue ? avatarValue : avatar"></el-avatar>
                </div>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item>
                        <div @click="toMe">个人中心</div>
                    </el-dropdown-item>
                    <el-dropdown-item divided style="color: red;">
                        <div @click="loginOut">退出登录</div>
                    </el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
    </div>

</template>

<script>
export default {
    name: 'Header1',
    props: ['searchInput', 'nicknameValue', 'avatarValue'],
    data() {
        return {
            badgeopen: true,
            badgeopen1: true,
            searchValue: this.searchInput,
            nickname: '登录',
            avatar: '../assets/avatar.jpg',
            isLogin: false
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
            if (res.data.orderMessageStatus === 1) { this.badgeopen1 = false; }
        })
    },
    methods: {
        tanchuang() {
            if (!this.$globalData.userInfo.picture) {
                this.$alert('请上传收款二维码后上架商品', '提示', {
                    confirmButtonText: '确定',
                });
            }
        },
        toMe() {
            if ('/personal_center' !== this.$route.path) {
                this.$router.push({ path: '/personal_center' });
            }
        },
        loginOut() {
            this.$api.logout().then(res => {
                if (res.status_code === 1) {
                    this.$globalData.userInfo = {};
                    console.log("login out");
                    if ('/index' === this.$route.path) {
                        this.$router.go(0);
                    } else {
                        this.$router.push({ path: '/index' });
                    }
                } else {
                    this.$message.error('网络或系统异常，退出登录失败！');
                }
            });

        }
    }
};
</script>

<style scoped>
.header {
    position: fixed;
    top: 0px;
    left: 0;
    right: 0;
    height: 90px;
    background: #feffff;
    display: flex;
    justify-content: center;
    border-bottom: #9c9a9a solid 4px;
    z-index: 1000;
}

.header-container {
    width: 1600px;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.memu {
    background: #ffffff;
    font-size: 20px;
    font-weight: 600;
    height: 50px;
}
.app-name a {
    color: #409EFF;
    font-size: 30px;
    text-decoration: none;
}

.user-name-text {
    font-size: 16px;
    font-weight: 600;
    color: #409EFF;
    cursor: pointer;
    text-decoration: none;
}
</style>
