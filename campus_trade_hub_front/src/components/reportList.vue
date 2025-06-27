<template>
    <div class="main-border">
        <el-menu default-active="1" class="el-menu-demo" mode="horizontal" @select="handleSelect">
            <el-menu-item index="1" style="font-size: 20px">未处理用户举报</el-menu-item>
            <el-menu-item index="2" style="font-size: 20px">已处理用户举报</el-menu-item>
            <el-menu-item index="3" style="font-size: 20px">未处理商品举报</el-menu-item>
            <el-menu-item index="4" style="font-size: 20px">已处理商品举报</el-menu-item>
            <el-menu-item index="5" style="font-size: 20px">未处理订单举报</el-menu-item>
            <el-menu-item index="6" style="font-size: 20px">已处理订单举报</el-menu-item>

            <el-table v-show="this.mode == 3" :data="rIdleData" stripe style="width: 100%;color: #000;font-size: 18px">
                <el-table-column label="有关证明图片" width="180" prop="pictureList">
                    <template slot-scope="scope">
                        <el-image :src="scope.row.imgUrl" :preview-src-list="scope.row.pictureList"></el-image>
                    </template>
                </el-table-column>
                <el-table-column prop="idleName" label="商品名" show-overflow-tooltip min-width="80" width="80">
                </el-table-column>
                <el-table-column prop="idleUser" label="卖（出租）方" show-overflow-tooltip width="180">
                </el-table-column>
                <el-table-column prop="reporterId" label="举报人" show-overflow-tooltip min-width="200" width="120">
                </el-table-column>
                <el-table-column prop="reportLabel" label="举报类型" show-overflow-tooltip min-width="200" width="200">
                </el-table-column>
                <el-table-column prop="reportDetails" label="具体原因" show-overflow-tooltip min-width="400" width="400">
                </el-table-column>
                <el-table-column prop="reportTime" label="举报时间" show-overflow-tooltip min-width="100" width="200">
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="mini" type="success" @click="toDeal1(scope.$index)">处理</el-button>
                    </template>
                </el-table-column>
            </el-table>

            <el-table v-show="this.mode == 4" :data="badIdleData" stripe style="width: 100%;color: #000;font-size: 18px">
                <el-table-column label="有关证明图片" width="180">
                    <template slot-scope="scope">
                        <el-image shape="square" :size="400" :src="scope.row.imgUrl">
                        </el-image>
                    </template>
                </el-table-column>
                <el-table-column prop="idleName" label="商品名" show-overflow-tooltip min-width="80" width="80">
                </el-table-column>
                <el-table-column prop="idleUser" label="卖（出租）方" show-overflow-tooltip width="180">
                </el-table-column>
                <el-table-column prop="reporterId" label="举报人" show-overflow-tooltip min-width="200" width="120">
                </el-table-column>
                <el-table-column prop="reportLabel" label="举报类型" show-overflow-tooltip min-width="200" width="200">
                </el-table-column>
                <el-table-column prop="reportDetails" label="具体原因" show-overflow-tooltip min-width="400" width="400">
                </el-table-column>
                <el-table-column prop="reportTime" label="举报时间" show-overflow-tooltip min-width="100" width="200">
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="mini" type="success" @click="noDeal1(scope.$index)">撤销</el-button>
                        <el-button size="mini" type="danger" @click="deleteReport1(scope.$index)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>


            <el-table v-show="this.mode == 1" :data="rUserData" stripe style="width: 100%;color: #000;font-size: 18px">
                <el-table-column label="有关证明图片" width="180" prop="pictureList">
                    <template slot-scope="scope">
                        <el-image :src="scope.row.imgUrl" :preview-src-list="scope.row.pictureList"></el-image>
                    </template>
                </el-table-column>
                <el-table-column prop="userName" label="被举报用户昵称" show-overflow-tooltip min-width="150" width="150">
                </el-table-column>
                <el-table-column prop="userId" label="被举报用户账号" show-overflow-tooltip width="150">
                </el-table-column>
                <el-table-column prop="reporterId" label="举报用户" show-overflow-tooltip min-width="100" width="100">
                </el-table-column>
                <el-table-column prop="reportLabel" label="举报类型" show-overflow-tooltip min-width="150" width="100">
                </el-table-column>
                <el-table-column prop="reportDetails" label="具体原因" show-overflow-tooltip min-width="400" width="400">
                </el-table-column>
                <el-table-column prop="reportTime" label="举报时间" show-overflow-tooltip min-width="100" width="200">
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="mini" type="success" @click="toDeal2(scope.$index)">处理</el-button>
                    </template>
                </el-table-column>
            </el-table>


            <el-table v-show="this.mode == 2" :data="badUserData" stripe style="width: 100%;color: #000;font-size: 18px">
                <el-table-column label="有关证明图片" width="180" prop="pictureList">
                    <template slot-scope="scope">
                        <el-image :src="scope.row.imgUrl" :preview-src-list="scope.row.pictureList"></el-image>
                    </template>
                </el-table-column>
                <el-table-column prop="userName" label="被举报用户昵称" show-overflow-tooltip min-width="150" width="150">
                </el-table-column>
                <el-table-column prop="userId" label="被举报用户账号" show-overflow-tooltip width="150">
                </el-table-column>
                <el-table-column prop="reporterId" label="举报用户" show-overflow-tooltip min-width="100" width="100">
                </el-table-column>
                <el-table-column prop="reportLabel" label="举报类型" show-overflow-tooltip min-width="150" width="100">
                </el-table-column>
                <el-table-column prop="reportDetails" label="具体原因" show-overflow-tooltip min-width="400" width="400">
                </el-table-column>
                <el-table-column prop="reportTime" label="举报时间" show-overflow-tooltip min-width="100" width="200">
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="mini" type="danger" @click="noDeal2(scope.$index)">撤销</el-button>
                        <el-button size="mini" type="danger" @click="deleteReport2(scope.$index)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>

            <el-table v-show="this.mode == 5" :data="rOrderData" stripe style="width: 100%;color: #000;font-size: 18px">
                <el-table-column label="有关证明图片" width="180" prop="pictureList">
                    <template slot-scope="scope">
                        <el-image :src="scope.row.imgUrl" :preview-src-list="scope.row.pictureList"></el-image>
                    </template>
                </el-table-column>
                <el-table-column prop="orderId" label="订单号" show-overflow-tooltip min-width="150" width="150">
                </el-table-column>
                <el-table-column prop="reportReason" label="举报类型" show-overflow-tooltip width="150">
                </el-table-column>
                <el-table-column prop="tranStatus" label="交易状态" show-overflow-tooltip width="150">
                </el-table-column>
                <el-table-column prop="reporterId" label="举报用户" show-overflow-tooltip min-width="100" width="100">
                </el-table-column>
                <el-table-column prop="dealWay" label="期待处理方式" show-overflow-tooltip min-width="150" width="150">
                </el-table-column>
                <el-table-column prop="reportDetails" label="具体原因" show-overflow-tooltip min-width="300" width="300">
                </el-table-column>
                <el-table-column prop="reportTime" label="举报时间" show-overflow-tooltip min-width="180" width="180">
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="mini" type="danger" @click="toDeal3(scope.$index)">处理</el-button>
                    </template>
                </el-table-column>
            </el-table>


            <el-table v-show="this.mode == 6" :data="badOrderData" stripe style="width: 100%;color: #000;font-size: 18px">
                <el-table-column label="有关证明图片" width="180" prop="pictureList">
                    <template slot-scope="scope">
                        <el-image :src="scope.row.imgUrl" :preview-src-list="scope.row.pictureList"></el-image>
                    </template>
                </el-table-column>
                <el-table-column prop="orderId" label="订单号" show-overflow-tooltip min-width="150" width="150">
                </el-table-column>
                <el-table-column prop="reportReason" label="举报类型" show-overflow-tooltip width="150">
                </el-table-column>
                <el-table-column prop="tranStatus" label="交易状态" show-overflow-tooltip width="150">
                </el-table-column>
                <el-table-column prop="reporterId" label="举报用户" show-overflow-tooltip min-width="100" width="100">
                </el-table-column>
                <el-table-column prop="dealWay" label="期待处理方式" show-overflow-tooltip min-width="150" width="150">
                </el-table-column>
                <el-table-column prop="reportDetails" label="具体原因" show-overflow-tooltip min-width="300" width="300">
                </el-table-column>
                <el-table-column prop="reportTime" label="举报时间" show-overflow-tooltip min-width="180" width="180">
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="mini" type="danger" @click="noDeal3(scope.$index)">撤销</el-button>
                        <el-button size="mini" type="danger" @click="deleteReport3(scope.$index)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="block">
                <el-pagination @current-change="handleCurrentChange" :current-page.sync="nowPage" :page-size="2"
                    background layout="prev, pager, next,jumper" :total="total">
                </el-pagination>
            </div>
        </el-menu>
    </div>
</template>

<script>
export default {
    name: "reportList",
    data() {
        return {
            z: false,
            button_status: true,
            mode: 1,
            k: 0,
            nowPage: 1,
            total: 0,
            rIdleData: [],
            badIdleData: [],
            rUserData: [],
            badUserData: [],
            rOrderData: [],
            badOrderData: [],
        }
    },
    created() {
        this.getRUserData();
        console.log(this.$sta);
        let z = '超级管理员';
        if (z === this.$sta.adminName) {
            this.button_status = false;
        }
    },
    methods: {

        handleCurrentChange(val) {
            this.nowPage = val;
            if (this.mode == 3) {
                this.getRIdleData();
            }
            if (this.mode == 4) {
                this.getBadIdleData();
            }
            if (this.mode == 1) {
                this.getRUserData();
            }
            if (this.mode == 2) {
                this.getBadUserData();
            }
            if (this.mode == 5) {
                this.getROrderData();
            }
            if (this.mode == 6) {
                this.getBadOrderData();
            }

        },

        handleSelect(val) {
            if (this.mode !== val) {
                this.mode = val
                if (val == 3) {
                    this.nowPage = 1;
                    this.getRIdleData();
                }
                if (val == 4) {
                    this.nowPage = 1;
                    this.getBadIdleData();
                }
                if (val == 1) {
                    this.nowPage = 1;
                    this.getRUserData();
                }
                if (val == 2) {
                    this.nowPage = 1;
                    this.getBadUserData();
                }
                if (val == 5) {
                    this.nowPage = 1;
                    this.getROrderData();
                }
                if (val == 6) {
                    this.nowPage = 1;
                    this.getBadOrderData();
                }
            }
        },
        getRIdleData() {
            //正常普通商品
            this.$api.getRIdleData({
                page: this.nowPage,
                nums: 2,
                status: 0
            }).then(res => {
                if (res.status_code == 1) {
                    let list = res.data.list;
                    for (let i = 0; i < list.length; i++) {
                        let pictureList = JSON.parse(list[i].pictureList);
                        list[i].imgUrl = pictureList.length > 0 ? pictureList[0] : '';
                        list[i].pictureList = pictureList;
                    }
                    this.rIdleData = list;
                    this.total = res.data.count;
                } else {
                    this.$message.error(res.msg)
                }
            }).catch(e => {
                console.log(e)
            })
        },
        getBadIdleData() {
            //违规商品
            this.$api.getRIdleData({
                page: this.nowPage,
                nums: 2,
                status: 1
            }).then(res => {
                if (res.status_code == 1) {
                    let list = res.data.list;
                    for (let i = 0; i < list.length; i++) {
                        let pictureList = JSON.parse(list[i].pictureList);
                        list[i].imgUrl = pictureList.length > 0 ? pictureList[0] : '';
                        list[i].pictureList = pictureList;
                    }
                    this.badIdleData = list;
                    this.total = res.data.count;
                } else {
                    this.$message.error(res.msg)
                }
            }).catch(e => {
                console.log(e)
            });
        },
        getRUserData() {
            //正常普通用户
            this.$api.getRUserData({
                page: this.nowPage,
                nums: 2,
                status: 0
            }).then(res => {
                if (res.status_code == 1) {
                    let list = res.data.list;
                    for (let i = 0; i < list.length; i++) {
                        let pictureList = JSON.parse(list[i].pictureList);
                        list[i].imgUrl = pictureList.length > 0 ? pictureList[0] : '';
                        list[i].pictureList = pictureList;
                    }
                    this.rUserData = list;
                    console.log(res.data.count);
                    this.total = res.data.count;
                } else {
                    this.$message.error(res.msg)
                }
            }).catch(e => {
                console.log(e)
            })
        },
        getBadUserData() {
            //违规用户
            this.$api.getRUserData({
                page: this.nowPage,
                nums: 2,
                status: 1
            }).then(res => {
                if (res.status_code == 1) {
                    let list = res.data.list;
                    for (let i = 0; i < list.length; i++) {
                        let pictureList = JSON.parse(list[i].pictureList);
                        list[i].imgUrl = pictureList.length > 0 ? pictureList[0] : '';
                        list[i].pictureList = pictureList;
                    }
                    this.badUserData = list;
                    this.total = res.data.count;
                } else {
                    this.$message.error(res.msg)
                }
            }).catch(e => {
                console.log(e)
            });
        },
        getROrderData() {
            //正常普通用户
            this.$api.getROrderData({
                page: this.nowPage,
                nums: 2,
                status: 0
            }).then(res => {
                if (res.status_code == 1) {
                    let list = res.data.list;
                    for (let i = 0; i < list.length; i++) {
                        let pictureList = JSON.parse(list[i].pictureList);
                        list[i].imgUrl = pictureList.length > 0 ? pictureList[0] : '';
                        list[i].pictureList = pictureList;
                    }
                    this.rOrderData = list;
                    this.total = res.data.count;
                } else {
                    this.$message.error(res.msg)
                }
            }).catch(e => {
                console.log(e)
            })
        },
        getBadOrderData() {
            //违规用户
            this.$api.getROrderData({
                page: this.nowPage,
                nums: 2,
                status: 1
            }).then(res => {
                if (res.status_code == 1) {
                    let list = res.data.list;
                    for (let i = 0; i < list.length; i++) {
                        let pictureList = JSON.parse(list[i].pictureList);
                        list[i].imgUrl = pictureList.length > 0 ? pictureList[0] : '';
                        list[i].pictureList = pictureList;
                    }
                    this.badOrderData = list;
                    this.total = res.data.count;
                } else {
                    this.$message.error(res.msg)
                }
            }).catch(e => {
                console.log(e)
            });
        },
        toDeal1(i) {
            console.log(this.rIdleData[i].id);
            this.$api.updateRIdleStatus({
                id: this.rIdleData[i].id,
                status: 1
            }).then(res => {
                if (res.status_code == 1) {
                    this.getRIdleData();
                } else {
                    this.$message.error(res.msg)
                }
            }).catch(e => {
                console.log(e)
            })
        },
        noDeal1(i) {
            this.$api.updateRIdleStatus({
                id: this.badIdleData[i].id,
                status: 0
            }).then(res => {
                if (res.status_code == 1) {
                    this.getBadIdleData();
                } else {
                    this.$message.error(res.msg)
                }
            }).catch(e => {
                console.log(e)
            })
        },
        toDeal2(i) {
            console.log(this.rUserData[i].id);
            this.$api.updateRUserStatus({
                id: this.rUserData[i].id,
                handleStatus: 1
            }).then(res => {
                if (res.status_code == 1) {
                    this.getRUserData();
                } else {
                    this.$message.error(res.msg)
                }
            }).catch(e => {
                console.log(e)
            })
        },
        noDeal2(i) {
            this.$api.updateRUserStatus({
                id: this.badUserData[i].id,
                handleStatus: 0
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
        toDeal3(i) {
            console.log(this.rOrderData[i].id);
            this.$api.updateROrderStatus({
                id: this.rOrderData[i].id,
                status: 1
            }).then(res => {
                if (res.status_code == 1) {
                    this.getROrderData();
                } else {
                    this.$message.error(res.msg)
                }
            }).catch(e => {
                console.log(e)
            })
        },
        noDeal3(i) {
            this.$api.updateROrderStatus({
                id: this.badOrderData[i].id,
                status: 0
            }).then(res => {
                if (res.status_code == 1) {
                    this.getBadOrderData();
                } else {
                    this.$message.error(res.msg)
                }
            }).catch(e => {
                console.log(e)
            })
        },
        deleteReport1(index) {
            this.$api.deleteReportIdle({
                id: this.badIdleData[index].id
            }).then(res => {
                if (res.status_code == 1) {
                    this.getBadIdleData();
                } else {
                    this.$message.error(res.msg)
                }

            }).catch(e => {
                console.log(e)
            })
        },
        deleteReport2(index) {
            this.$api.deleteReportUser({
                id: this.badUserData[index].id
            }).then(res => {
                if (res.status_code == 1) {
                    this.getBadUserData()();
                } else {
                    this.$message.error(res.msg)
                }

            }).catch(e => {
                console.log(e)
            })
        },
        deleteReport3(index) {
            this.$api.deleteReportOrder({
                id: this.badOrderData[index].id
            }).then(res => {
                if (res.status_code == 1) {
                    this.getBadOrderData();
                } else {
                    this.$message.error(res.msg)
                }

            }).catch(e => {
                console.log(e)
            })
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