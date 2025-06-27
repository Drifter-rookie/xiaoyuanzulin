<template>
    <div class="login-container">
        <div style="margin-left: 900px;">
        <el-card class="box-card">
            <div class="login-body">
                <div class="login-title" @click="toIndex">校园闲置物品租售系统</div>
                <el-form ref="form" :model="userForm">
                    <el-input placeholder="请输入账号..." v-model="userForm.accountNumber" class="login-input">
                        <template slot="prepend">
                            <div class="el-icon-user-solid"></div>
                        </template>
                    </el-input>
                    <el-input placeholder="请输入密码..." v-model="userForm.userPassword" class="login-input"
                        @keyup.enter.native="login" show-password>
                        <template slot="prepend">
                            <div class="el-icon-lock"></div>
                        </template>
                    </el-input>
                    <div class="login-submit">
                        <el-button type="primary" @click="login" style="font-size: 20px;">登录</el-button>
                        <el-button type="warning" autocomplete="off" @click="$router.push('/sign_in')"
                            style="margin-left: 100px;font-size: 20px;">注册</el-button>
                    </div>
                    <div class="other-submit">
                        <router-link to="/login_admin" class="sign-in-text">管理员登录</router-link>
                    </div>
                </el-form>
            </div>
        </el-card>
        </div>
    </div>
</template>

<script>
export default {
    name: "login",
    data() {
        return {
            userForm: {
                accountNumber: '',
                userPassword: ''
            }
        };
    },

    methods: {
        login() {
            // 禁用按钮防止重复点击
            const loading = this.$loading({
                lock: true,
                text: '登录中...',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.7)'
            });
            
            // 清除之前可能存在的token和用户信息
            localStorage.removeItem('token');
            this.$globalData.userInfo = {};
            
            this.$api.userLogin({
                accountNumber: this.userForm.accountNumber,
                userPassword: this.userForm.userPassword
            }).then(res => {
                console.log(res);
                loading.close();
                
                if (res.status_code === 1) {
                    // 保存用户信息
                    const userData = res.data.user || res.data;
                    if (userData.signInTime) {
                        userData.signInTime = userData.signInTime.substring(0, 10);
                    }
                    this.$globalData.userInfo = userData;
                    
                    // 保存 JWT token 到本地存储
                    if (res.data.token) {
                        localStorage.setItem('token', res.data.token);
                    }
                    
                    this.$message.success('登录成功');
                    this.$router.replace({ path: '/index' });
                } else {
                    this.$message.error(res.msg || '登录失败');
                }
            }).catch(err => {
                loading.close();
                console.error('登录错误:', err);
                this.$message.error('网络错误，请稍后重试');
            });
        },
        toIndex() {
            this.$router.replace({ path: '/index' });
        }
    }
}
</script>

<style scoped>
::v-deep .el-input__inner {
  height: 50px;
  line-height: 50px;
}
.login-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 123vh;
    width: 100%;
    background-image: url('../assets/sign.png');
    background-size: cover
}

.login-body {
    padding: 30px;
    width: 400px;
    height: 400px;
    background-size: cover;
}

.login-title {
    padding-bottom: 40px;
    text-align: center;
    font-weight: 600;
    font-size: 30px;
    color: #1279df;
    cursor: pointer;
}

.login-input {
    margin-bottom: 40px;
}
.login-submit {
    display: flex;
    justify-content: center;
}
.sign-in-container {
    padding: 0 10px;
}

.sign-in-text {
    color: #409EFF;
    font-size: 20px;
    text-decoration: none;
    line-height: 28px;
}

.other-submit {
    display: flex;
    justify-content: space-between;
    margin-top: 30px;
    margin-left: 300px;
}
</style>
