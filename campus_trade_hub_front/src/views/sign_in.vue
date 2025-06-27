<template>
    <div class="sign-in-container">
        <div style="margin-left: 900px;">
        <el-card class="box-card">
            <div class="sign-in-body">
                <div class="sign-in-title">注册账号</div>
                <el-input placeholder="请输入昵称..." maxlength="30" v-model="userInfo.nickname" class="sign-in-input" clearable>
                    <template slot="prepend">
                        <div class="el-icon-user-solid"></div>
                    </template>
                </el-input>
                <el-input placeholder="请输入帐号..." maxlength="11" v-model="userInfo.accountNumber" class="sign-in-input"
                    clearable>
                    <template slot="prepend">
                        <div class="el-icon-phone"></div>
                    </template>
                </el-input>
                <el-input placeholder="请输入密码..." show-password maxlength="16" v-model="userInfo.userPassword"
                    class="sign-in-input" clearable>
                    <template slot="prepend">
                        <div class="el-icon-lock"></div>
                    </template>
                </el-input>
                <el-input placeholder="请再次输入密码..." show-password maxlength="16" v-model="userPassword2"
                    @keyup.enter.native="signIn" class="sign-in-input" clearable>
                    <template slot="prepend">
                        <div class="el-icon-lock"></div>
                    </template>
                </el-input>
                <div class="sign-in-submit">
                    <el-button type="primary" @click="signIn" style="font-size: 20px;">提交</el-button>
                    <el-button type="warning" @click="toLogin" style="margin-left: 100px;font-size: 20px;">返回登录</el-button>
                </div>
            </div>
        </el-card>
    </div>
    </div>
</template>

<script>
export default {
    name: "sign-in",
    data() {
        return {
            userPassword2: '',
            userInfo: {
                accountNumber: '',
                userPassword: '',
                nickname: ''
            }
        };
    },
    methods: {
        toLogin() {
            this.$router.replace({ path: '/login' });
        },
        signIn() {
            console.log(this.userInfo.nickname);
            if (this.userInfo.accountNumber && this.userInfo.userPassword && this.userInfo.nickname) {
                if (this.userInfo.userPassword !== this.userPassword2) {
                    this.$message.error('两次输入的密码不相同！');
                } else {
                    this.$api.signIn(this.userInfo).then(res => {
                        if (res.status_code === 1) {
                            this.$message({
                                message: '注册成功！',
                                type: 'success'
                            });
                            this.$router.replace({ path: '/login' });
                        } else {
                            this.$message.error('注册失败，用户已存！');
                        }
                    }).catch(e => {
                        console.log(e);
                        this.$message.error('注册失败，网络异常！');
                    })
                }
            } else {
                this.$message.error('注册信息未填写完整！');
            }
        }
    }
}
</script>

<style scoped>
.sign-in-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 123vh;
    width: 100%;
    background-color: #f1f1f1;
    background-image: url('../assets/sign.png');
    background-size: cover;
}

.sign-in-body {
    padding: 30px;
    width: 400px;
    height: 400px;
    background-size: cover;
}

.sign-in-title {
    padding-bottom: 30px;
    text-align: center;
    font-weight: 600;
    font-size: 30px;
    color: #409EFF;
}

.sign-in-input {
    margin-bottom: 20px;
}

.sign-in-submit {
    margin-top: 20px;
    display: flex;
    justify-content: center;
}

.login-container {
    padding: 0 10px;
}

.login-text {
    color: #409EFF;
    font-size: 16px;
    cursor: pointer;
}
</style>