<template>
    <div style="background-color: #f6f6f6;min-height:100vh;">
        <el-container>
            <el-header>
                <div class="header">
                    <div class="app-name">
                        <router-link to="/platform_admin">后台管理</router-link>
                    </div>
                    <span class="app-title">{{ admin.nickname }}</span>
                    <div class="app-logOut">
                        <el-button style="margin-right: 100px" type="primary" plain @click="logout">退出登录</el-button>
                    </div>
                </div>
            </el-header>
            <el-container>
                <div class="mainBody" background-color="rgb(110, 150, 199)">
                    <el-aside>
                        <el-col :span="24">
                            <el-menu default-active="1" class="el-menu-vertical-demo" @select="handleSelect"
                                background-color="rgb(110, 150, 199)" text-color="#fff" active-text-color="#ffd04b">
                                <el-menu-item index="1" style="font-size: 20px;">
                                    <i class="el-icon-goods"></i>
                                    <span>商品管理</span>
                                </el-menu-item>
                                <el-menu-item index="2" style="font-size: 20px;">
                                    <i class="el-icon-s-goods"></i>
                                    <span slot="title">订单管理</span>
                                </el-menu-item>
                                <el-menu-item index="3" style="font-size: 20px;">
                                    <i class="el-icon-s-custom"></i>
                                    <span slot="title">用户管理</span>
                                </el-menu-item>
                                <el-menu-item index="4" style="font-size: 20px;">
                                   <i class="el-icon-warning-outline"></i>
                                        <span slot="title">举报管理</span>
                                    </el-menu-item>
                                <el-menu-item index="5" style="font-size: 20px;">
                                    <i class="el-icon-message"></i>
                                    <span slot="title">公告管理</span>
                                </el-menu-item>
                            </el-menu>
                        </el-col>
                    </el-aside>
                    <el-main>
                        <IdleGoods v-if="mode == 1"></IdleGoods>
                        <orderList v-if="mode == 2"></orderList>
                        <userList v-if="mode == 3"></userList>
                        <reportList v-if="mode == 4"></reportList>
                        <Notices v-if="mode == 5"></Notices>
                    </el-main>
                </div>
            </el-container>
        </el-container>
        <div class="foot">
            <app-foot></app-foot>
        </div>
    </div>
</template>

<script>
import AppFoot from '../components/AppFoot.vue'
import IdleGoods from '../components/IdleGoods.vue'
import orderList from '../components/orderList.vue'
import userList from '../components/userList.vue'
import reportList from '../components/reportList.vue'
import Notices from '../components/Notices.vue'

export default {
    name: "platform-admin",
    components: {
        AppFoot,
        IdleGoods,
        orderList,
        userList,
        Notices,
        reportList,
    },
    data() {
        return {
            mode: 1,
            admin: {
                id:"",
                nickname: '管理员',
            },
        }
    },
    created() {
        this.admin.id = this.$sta.id;
        console.log(this.admin.id);
        this.admin.nickname = this.$sta.adminName;
        console.log(this.admin.nickname);
    },
    methods: {
        logout() {
            this.$api.loginOut({
            }).then(res => {
                if (res.status_code === 1) {
                    this.$sta.isLogin = false;
                    this.$sta.adminName = '';
                    this.$router.push({ path: '/login-admin' });
                }
                // console.log(res);
                // this.Adminid=res.data.id;
                // this.$emit('logout',Adminid);
                // console.log(this.Adminid);
            }).catch(e => {
                console.log(e)
            })
        },
        handleSelect(val) {
            if (this.mode !== val) {
                this.mode = val
            }
        },
    },
}
</script>

<style scoped>
.header {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    min-width: 100vw;
    height: 58px;
    background: rgb(110, 150, 199);
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-bottom: #ffffff solid 2px;
    z-index: 1000;
}

.app-name {
    display: flex;
    justify-content: center;
    align-items: center;
    min-width: 10vw;
    flex: 1;
    height: 100%;
    border-right: 1px solid #e5e5e5;
    background-color:rgb(110, 150, 199);

}

.app-name a {
    color: #000000;
    font-size: 25px;
    font-weight: 800;
    text-decoration: none;
    padding: 0 20px;
}

.app-title {
    display: flex;
    font-size: 25px;
    justify-content: center;
    flex: 8;
}

.app-logOut {
    display: flex;
    flex: 1;
    justify-content: flex-end;
    align-items: center;
}

.mainBody {
    display: flex;
    width: 100%;
    background-size: cover
}

aside {
    flex: 1;
    box-sizing: content-box;
    min-width: 10vw;
    min-height: 107vh;
    background-color: rgb(110, 150, 199);
    border-bottom: 1px solid #e5e5e5;
    border-right: 1px solid #e5e5e5;
}

main {
    flex: 9;
}

.foot {
    position: absolute;
    left: 0;
    bottom: 0;
    width: 100%;
    height: 58px;
    background-color: rgb(110, 150, 199);
}
</style>