<template>
    <div class="header">
        <div class="header-container">
            <div class="app-name">
                <router-link to="/"><b style="font-size: 40px; color: #409EFF;">校园闲置物品租售系统</b></router-link>
            </div>
            <el-menu :default-active="$route.path" class="el-menu-demo" mode="horizontal" @select="handleSelect"
                text-color="#000000" font-size="30px" active-text-color="rgb(110, 150, 199)" router>
                <el-menu-item index="/index" class="memu">首页</el-menu-item>
                <el-menu-item index="/all" class="memu">全部商品</el-menu-item>
                <el-menu-item index="/sell" class="memu"><img style="width:40px; height: 40px"
                        src="../assets/sell.png">购买商品</el-menu-item>
                <el-menu-item index="/hire" class="memu"><img style="width:40px; height: 40px"
                        src="../assets/hire.png">租用商品</el-menu-item>
                <el-menu-item>
                    <el-dropdown trigger="hover">
                        <span class="dropmemu" style="color: black;"><i class="el-icon-message"></i>我的消息
                            <i class="el-icon-arrow-down"></i></span>
                        <el-dropdown-menu slot="dropdown" :append-to-body="false">
                            <el-dropdown-item>
                                <div @click="toMessage" class="dropmemu"><i class="el-icon-chat-dot-square"></i>留言消息
                                    <el-badge class="mark" :value="number" :hidden="badgeopen" />
                                </div>
                            </el-dropdown-item>
                            <el-dropdown-item>
                                <div @click="toMessage1" class="dropmemu"><i class="el-icon-chat-line-square"></i>订单消息
                                    <el-badge class="mark" :value="number1" :hidden="badgeopen1" />
                                </div>
                            </el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </el-menu-item>
                <el-menu-item @click.native=tanchuang() class="memu"><i class="el-icon-sell"></i>上架物品</el-menu-item>
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
                <el-dropdown-menu slot="dropdown" :append-to-body="false">
                    <el-dropdown-item>
                        <div @click="toMe" style="font-size: 14px; color: black;">个人中心</div>
                    </el-dropdown-item>
                    <el-dropdown-item divided style="font-size: 14px; color: red;">
                        <div @click="loginOut">退出登录</div>
                    </el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
    </div>

</template>

<script>
export default {
    name: 'Header',
    props: ['searchInput', 'nicknameValue', 'avatarValue'],
    data() {
        return {
            badgeopen: true,
            badgeopen1: true,
            searchValue: this.searchInput,
            nickname: '登录',
            avatar: '../assets/avatar.jpg',
            isLogin: false,
            number: 0,
            number1: 0
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
                if (res.data.userMessageStatus > 0) { this.badgeopen = false; this.number = res.data.userMessageStatus }
                this.countnumber();
            }
        })
    },

    mounted() {
        var pubsub = this.goeasy.constructor.pubsub;
        pubsub.subscribe({
            channel: 'message_status_updates',
            onMessage: (message) => {
                // 更新组件内部状态
                let data = parseInt(message.content, 10);
                if (data === this.userid) {
                    this.number = this.number + 1;
                    this.badgeopen = false;
                }
            }
        })
        pubsub.subscribe({
            channel: 'message_status_updates1',
            onMessage: (message, message1) => {
                // 更新组件内部状态
                let data = parseInt(message.content, 10);
                if (data === this.userid) {
                    this.number = this.number - 1;
                    if (this.number <= 0) {
                        this.number = 0;
                        this.badgeopen = true;
                    }
                }

            }
        })
        pubsub.subscribe({
            channel: 'order',
            onMessage: (message) => {
                let data = parseInt(message.content, 10);
                if (data === this.userid) {
                    this.delayMethod();
                }
            },
        });
        pubsub.subscribe({
            channel: 'order1',
            onMessage: (message) => {
                this.delayMethod();
            },
        });
    },
    methods: {
        countnumber() {
            this.$api.countorder({
                id: this.userid
            }).
                then(res => {
                    if (res > 0) {
                        this.number1 = res;
                        console.log('哇哦', this.number1);
                        this.badgeopen1 = false;
                    }
                    else {
                        this.badgeopen1 = true;
                    }
                })
        },
        handleSelect(key, keyPath) {
            // 菜单选择处理
            console.log(key, keyPath);
        },
        delayMethod() {
            setTimeout(() => {
                this.countnumber();
            }, 1000);
        },
        tanchuang() {
            if (this.nickname === '登录') {
                this.$message({
                    message: '请先登录！',
                    type: 'warning'
                });
                if ('/login' !== this.$route.path) {
                    this.$router.push({ path: '/login' });
                }
            } else if (!this.$globalData.userInfo.picture) {
                this.$alert('请上传收款二维码后上架商品', '提示', {
                    confirmButtonText: '确定',
                });
                this.toMe();
            } else {
                this.toRelease();
            }
        },
        toMe() {
            if ('/personal_center' !== this.$route.path) {
                this.$router.push({ path: '/personal_center' });
            }
        },
        toMessage() {
            if (this.nickname === '登录') {
                this.$message({
                    message: '请先登录！',
                    type: 'warning'
                });
                if ('/login' !== this.$route.path) {
                    this.$router.push({ path: '/login' });
                }
            }
            this.$router.push({ path: '/message' });
        },
        toMessage1() {
            if (this.nickname === '登录') {
                this.$message({
                    message: '请先登录！',
                    type: 'warning'
                });
                if ('/login' !== this.$route.path) {
                    this.$router.push({ path: '/login' });
                }
            }
            this.$router.push({ path: '/order_massage' });
        },
        toRelease() {
            if ('/release' !== this.$route.path) {
                this.$router.push({ path: '/release' });
            }
        },
        loginOut() {
            this.$api.logout().then(res => {
                if (res.status_code === 1) {
                    // 清除用户信息
                    this.$globalData.userInfo = {};
                    
                    // 清除JWT令牌
                    localStorage.removeItem('token');
                    
                    // 清除Cookie (如果有使用)
                    document.cookie = "shUserId=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
                    
                    console.log("login out");
                    
                    // 重置状态
                    this.isLogin = false;
                    this.nickname = '登录';
                    this.avatar = '../assets/avatar.jpg';
                    
                    if ('/index' === this.$route.path) {
                        this.$router.go(0);
                    } else {
                        this.$router.push({ path: '/index' });
                    }
                } else {
                    this.$message.error('网络或系统异常，退出登录失败！');
                }
            }).catch(err => {
                // 即使API调用失败，也要清除本地登录状态
                localStorage.removeItem('token');
                this.$globalData.userInfo = {};
                this.isLogin = false;
                this.nickname = '登录';
                this.$router.push({ path: '/index' });
            });
        }
    }
};
</script>

<style scoped>
::v-deep .el-dropdown-menu {
    min-width: 100px;
    position: absolute !important;
    top: 63px !important;
    left: -5px !important;
}

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
    color: black;
}

.dropmemu {
    background: #ffffff;
    font-size: 20px;
    font-weight: 600;
    height: 50px;
    color: black;
}

.app-name a {
    color: #ff0303;
    font-size: 30px;
    text-decoration: none;
}

.user-name-text {
    font-size: 20px;
    font-weight: 600;
    color: rgb(110, 150, 199);
    cursor: pointer;
    text-decoration: none;
}
</style>
