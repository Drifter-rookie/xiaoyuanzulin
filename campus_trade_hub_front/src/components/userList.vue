<template>
    <div class="main-border">
        <el-menu default-active="1" class="el-menu-demo" mode="horizontal" @select="handleSelect">
            <el-menu-item index="1" style="font-size: 20px">正常用户</el-menu-item>
            <el-menu-item index="2" style="font-size: 20px">违规用户</el-menu-item>
            <el-menu-item index="3" style="font-size: 20px">管理员</el-menu-item>

            <div v-show="this.mode == 3" class="addAdminButton">
                <el-button size="mini" type="success" @click="adminRegVisible = true"
                    :disabled="button_status">添加管理员</el-button>
                <el-dialog title="添加管理员" :visible.sync="adminRegVisible" width="25%">
                    <span style="margin-left: 10px">新增管理员名称</span>
                    <el-input v-model="adminName" maxlength="8" placeholder="请输入管理员名称" style="padding: 10px 0" clearable
                        required></el-input>
                    <span style="margin-left: 10px">新增管理员账户</span>
                    <el-input v-model="adminAccount" minlength="8" maxlength="10" placeholder="请输入管理员账户"
                        style="padding: 10px 0" clearable required></el-input>
                    <span style="margin-left: 10px">新增管理员密码</span>
                    <el-input v-model="adminPassword" minlength="8" placeholder="请输入管理员密码" style="padding: 10px 0"
                        show-password required></el-input>
                    <span style="margin-left: 10px">确认管理员密码</span>
                    <el-input v-model="adminRePassword" minlength="10" placeholder="请再次输入管理员密码" style="padding: 10px 0"
                        show-password required></el-input>
                    <span slot="footer" class="dialog-footer">
                        <el-button type="primary" @click="regAdmin">添加</el-button>
                    </span>
                </el-dialog>

            </div>
        </el-menu>
        <el-table v-show="this.mode == 1" :data="userData" stripe style="width: 100%;color: #000;font-size: 18px">
            <el-table-column label="头像" width="80">
                <template slot-scope="scope">
                    <el-avatar shape="square" :size="23" :src="scope.row.avatar"></el-avatar>
                </template>
            </el-table-column>
            <el-table-column prop="accountNumber" label="用户账号" show-overflow-tooltip min-width="150" width="150">
            </el-table-column>
            <el-table-column prop="nickname" label="用户昵称" show-overflow-tooltip width="150">
            </el-table-column>
            <el-table-column prop="sex" label="性别" show-overflow-tooltip min-width="80" width="100">
            </el-table-column>
            <el-table-column prop="age" label="年龄" show-overflow-tooltip min-width="80" width="100">
            </el-table-column>
            <el-table-column prop="phone" label="电话号码" show-overflow-tooltip min-width="150" width="150">
            </el-table-column>
            <el-table-column prop="campus" label="所在校区" show-overflow-tooltip min-width="100" width="150">
            </el-table-column>
            <el-table-column prop="signInTime" label="注册时间" show-overflow-tooltip width="250">
            </el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button size="mini" type="danger" @click="sealUser(scope.$index)">封禁</el-button>
                    <el-button size="mini" type="danger" @click="changepassword(scope.$index)">重置密码</el-button>
                    <!-- <el-button size="mini" type="danger" @click="z = true,k=scope.$index">修改密码</el-button>
                <el-dialog
                        title="修改密码"
                        :visible.sync="z"
                        width="40%"
                       >
                    <span style="margin-left: 10px">密码</span>
                    <el-input v-model="password" show-password></el-input> 
                    <span slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="changepassword(k)">确定</el-button>
                    </span> 
                </el-dialog>  -->
                </template>
            </el-table-column>
        </el-table>
        <el-table v-show="this.mode == 2" :data="badUserData" stripe style="width: 100%;color: #000;font-size: 18px">
            <el-table-column label="头像" width="80">
                <template slot-scope="scope">
                    <el-avatar shape="square" :size="23" :src="scope.row.avatar"></el-avatar>
                </template>
            </el-table-column>
            <el-table-column prop="accountNumber" label="用户账号" show-overflow-tooltip min-width="150" width="150">
            </el-table-column>
            <el-table-column prop="nickname" label="用户昵称" show-overflow-tooltip width="150">
            </el-table-column>
            <el-table-column prop="sex" label="性别" show-overflow-tooltip min-width="80" width="80">
            </el-table-column>
            <el-table-column prop="age" label="年龄" show-overflow-tooltip min-width="80" width="80">
            </el-table-column>
            <el-table-column prop="phone" label="电话号码" show-overflow-tooltip min-width="150" width="150">
            </el-table-column>
            <el-table-column prop="campus" label="所在校区" show-overflow-tooltip min-width="100" width="100">
            </el-table-column>
            <el-table-column prop="signInTime" label="注册时间" show-overflow-tooltip width="200">
            </el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button size="mini" type="success" @click="unsealUser(scope.$index)">解封</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-table v-show="this.mode == 3" :data="userManage" stripe style="width: 100%;color: #000;font-size: 18px">
            <el-table-column prop="accountNumber" label="管理员账号" show-overflow-tooltip width="200">
            </el-table-column>
            <el-table-column prop="adminName" label="管理名称">
            </el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button size="mini" type="danger" :disabled="button_status"
                        @click="deleteAdmin(scope.$index)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div class="block">
            <el-pagination @current-change="handleCurrentChange" :current-page.sync="nowPage" :page-size="8" background
                layout="prev, pager, next,jumper" :total="total">
            </el-pagination>
        </div>
    </div>
</template>

<script>
export default {
    name: "userList",
    data() {
        return {
            z: false,
            button_status: true,
            mode: 1,
            k: 0,
            nowPage: 1,
            total: 0,
            adminRegVisible: false,
            adminAccount: '',
            adminPassword: '',
            adminRePassword: '',
            adminName: '',
            password: '',
            userData: [],
            badUserData: [],
            userManage: [],
        }
    },
    created() {
        this.getUserData();
        console.log(this.$sta);
        let z = '超级管理员';
        if (z === this.$sta.adminName) {
            this.button_status = false;
        }
    },
    methods: {
        deleteAdmin(i) {
            this.$api.deleteAdmin({
                id: this.userManage[i].id,
            }).then(res => {
                if (res.status_code == 1) {
                    this.getUserManage();
                } else {
                    this.$message.error(res.msg)
                }

            }).catch(e => {
                console.log(e)
            })
        },
        changepassword(i) {
            this.$api.updatepassword({
                id: this.userData[i].id,
                password: "123456",
            }).then(res => {
                if (res.status_code == 1) {
                    this.$message({
                        message: '修改成功！',
                        type: 'success'
                    });
                } else {
                    this.$message.error('修改失败！');
                }
                this.z = false
            })
        },
        sealUser(i) {
            console.log(this.userData[i].id);
            this.$api.updateUserStatus({
                id: this.userData[i].id,
                status: 1
            }).then(res => {
                if (res.status_code == 1) {
                    this.getUserData();
                } else {
                    this.$message.error(res.msg)
                }
            }).catch(e => {
                console.log(e)
            })
        },
        handleCurrentChange(val) {
            this.nowPage = val;
            if (this.mode == 1) {
                this.getUserData();
            }
            if (this.mode == 2) {
                this.getBadUserData();
            }
            if (this.mode == 3) {
                this.getUserManage();
            }
        },
        handleSelect(val) {
            if (this.mode !== val) {
                this.mode = val
                if (val == 1) {
                    this.nowPage = 1;
                    this.getUserData();
                }
                if (val == 2) {
                    this.nowPage = 1;
                    this.getBadUserData();
                }
                if (val == 3) {
                    this.nowPage = 1;
                    this.getUserManage();
                }
            }
        },
        getUserData() {
            //正常普通用户
            this.$api.getUserData({
                page: this.nowPage,
                nums: 10,
                status: 0
            }).then(res => {
                if (res.status_code == 1) {
                    this.userData = res.data.list;
                    this.total = res.data.count;
                    console.log(this.total);
                } else {
                    this.$message.error(res.msg)
                }
            }).catch(e => {
                console.log(e)
            })
        },
        getBadUserData() {
            //违规用户
            this.$api.getUserData({
                page: this.nowPage,
                nums: 10,
                status: 1
            }).then(res => {
                if (res.status_code == 1) {
                    this.badUserData = res.data.list;
                    this.total = res.data.count;
                } else {
                    this.$message.error(res.msg)
                }
            }).catch(e => {
                console.log(e)
            });
        },
        getUserManage() {
            //管理员
            this.$api.getUserManage({
                page: this.nowPage,
                nums: 10,
            }).then(res => {
                if (res.status_code == 1) {
                    this.userManage = res.data.list;
                    this.total = res.data.count;
                } else {
                    this.$message.error(res.msg)
                }
            }).catch(e => {
                console.log(e)
            })
        },
        unsealUser(i) {
            this.$api.updateUserStatus({
                id: this.badUserData[i].id,
                status: 0
            }).then(res => {
                if (res.status_code == 1) {
                    this.getBadUserData();
                } else {
                    this.$message.error(res.msg)
                }
            }).catch(e => {
                console.log(e)
            })
        },
        regAdmin() {
            if (this.adminPassword == this.adminRePassword) {
                this.$api.regAdministrator({
                    adminName: this.adminName,
                    accountNumber: this.adminAccount,
                    adminPassword: this.adminPassword,
                }).then(res => {
                    if (res.status_code == 1) {
                        this.total = this.total + 1;
                        this.nowPage = Math.ceil(this.total / 8);
                        console.log(this.nowPage);
                        this.getUserManage();
                    } else {
                        this.$message.error(res.msg)
                    }
                }).catch(e => {
                    console.log(e);
                    this.$message.error("添加失败，账号重复或网络异常")
                });
                this.adminRegVisible = false
            }
            else {
                this.$message.error("两次输入的密码不一致");
            }
        },
    },
}
</script>

<style scoped>
.edit-tip {
    font-size: 14px;
    margin: 10px 5px;
}

.main-border {
    background-color: #FFF;
    padding: 10px 30px;
    box-shadow: 0 1px 15px -6px rgba(0, 0, 0, .5);
    border-radius: 5px;
}

.block {
    display: flex;
    justify-content: center;
    padding-top: 15px;
    padding-bottom: 10px;
    width: 100%;
}

.addAdminButton {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    height: 60px;
    outline: none;
}
</style>