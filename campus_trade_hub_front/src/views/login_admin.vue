<template>
    <div class="login-container">
        <div style="margin-left: 900px;">
        <el-card class="box-card">
            <div class="login-body">
                <div class="login-title">后台管理</div>
                <el-form ref="form" :model="userForm">
                    <el-input placeholder="请输入管理员账号" v-model="userForm.accountNumber" class="login-input">
                        <template slot="prepend">
                            <div class="el-icon-user-solid"></div>
                        </template>
                    </el-input>
                    <el-input placeholder="请输入管理员密码" v-model="userForm.adminPassword" class="login-input"
                        @keyup.enter.native="login" show-password>
                        <template slot="prepend">
                            <div class="el-icon-lock"></div>
                        </template>
                    </el-input>
                    <div class="login-submit" style="margin-top: 20px">
                        <el-button type="primary" @click="login"  style="font-size: 20px;">登录</el-button>
                        <el-button type="warning" autocomplete="off" @click="$router.push('/login')"
                        style="margin-left: 100px;font-size: 20px;">返回前台</el-button>
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
                adminPassword: ''
            },
            Adminid: 0,
        };
    },
    methods: {
        login() {
            this.$api.adminLogin({
                accountNumber: this.userForm.accountNumber,
                adminPassword: this.userForm.adminPassword
            }).then(res => {
                if (res.status_code === 1) {
                    this.$sta.isLogin = true;
                    this.$sta.id = res.data.id;
                    this.$sta.adminName = res.data.adminName;
                    this.$router.replace({ path: '/platform_admin' });
                    this.Adminid = res.data.id;
                } else {
                    this.$message.error('登录失败，账号或密码错误！');
                }
            });

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
    background-color: #f1f1f1;
    background-image: url('../assets/platform.png');
    background-size: cover
}

.login-body {
    padding: 30px;
    width: 400px;
    height: 400px;
    background-size: cover;
    /* background-image: url('../assets/background.jpg'); */
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
    height: 50px;
    line-height: 50px;
}

.login-submit {
    display: flex;
    justify-content: center;
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
    margin-top: 10px;
}</style>
