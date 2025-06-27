<template>
    <div class="main-border">
        <el-menu default-active="1" class="el-menu-demo" mode="horizontal" @select="handleSelect">
            <el-menu-item index="1" style="font-size: 20px">发布公告</el-menu-item>
            <el-menu-item index="2" style="font-size: 20px">全部公告</el-menu-item>
        </el-menu>
        <el-form v-show="this.mode == 1" stripe style="margin-top: 10px;width: 100%;color: #000;font-size: 18px">
            <el-form ref="form" :model="notice" label-width="150px" style="color: #000;font-size: 18px">
                <el-form-item label="公告标题或关键句">
                    <el-input v-model="notice.keySentence"></el-input>
                </el-form-item>
                <el-form-item label="公告类型" prop="resource" >
                    <el-radio-group v-model="notice.noticeType">
                        <el-radio label="公告"></el-radio>
                        <el-radio label="通知"></el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="公告内容" style="font-size: 20px">
                    <el-input type="textarea" :rows="15" v-model="notice.content"></el-input>
                </el-form-item>
                <el-form-item>
                    <div style="display: flex;justify-content: center;margin-top: 30px;margin-bottom: 30px;">
                        <el-button type="primary" plain @click="releaseButton">确认发布</el-button>
                    </div>
                </el-form-item>
            </el-form>
        </el-form>
            <el-table v-show="this.mode == 2" :data="noticeInfo" stripe style="width: 100%;color: #000;font-size: 18px">
                <el-table-column prop="noticeType" label="公告类型" show-overflow-tooltip min-width="50" width="100">
                </el-table-column>
                <el-table-column prop="keySentence" label="标题或关键句" show-overflow-tooltip min-width="150" width="250">
                </el-table-column>
                <el-table-column prop="content" label="公告内容" show-overflow-tooltip width="800">
                </el-table-column>
                <el-table-column prop="announceTime" label="发布时间" show-overflow-tooltip min-width="80" width="180">
                </el-table-column>
                <el-table-column prop="adminName" label="发布管理员" show-overflow-tooltip min-width="80" width="150">
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="mini" type="danger" @click="deleteNotice(scope.$index)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="block" v-show="this.mode == 2">
                <el-pagination @current-change="handleCurrentChange" :current-page.sync="nowPage" :page-size="7" background
                    layout="prev, pager, next,jumper" :total="total">
                </el-pagination>
            </div>
    </div>
</template>

<script>
export default {
    name: "noticeList",
    data() {
        return {
            z: false,
            button_status: true,
            mode: 1,
            total:"",
            nowPage:"",
            admin: {
                id:" ",
                nickname: '管理员',
            },
            notice: {
                keySentence: " ",
                noticeType:" ",
                content: " ",
                announceTime:" ",
                adminId:" ",
                adminName:" ",
            },
            noticeInfo: [],
        }
    },
    created() {
        this.admin.id = this.$sta.id;
        console.log(this.admin.id);
        this.admin.nickname = this.$sta.adminName;
        console.log(this.admin.nickname);
    },
    methods: {
        releaseButton() {
            this.notice.adminId = this.admin.id;
            this.notice.adminName =  this.admin.nickname ;
            if (this.notice.keySentence &&
                this.notice.content &&
                this.notice.noticeType &&
                this.notice.adminId &&
                this.notice.adminName) {
                this.$api.addNotice(this.notice).then(res => {
                    if (res.status_code === 1) {
                        this.$message({
                            message: '发布成功！',
                            type: 'success'
                        });
                        console.log(res.data);
                    } else {
                        this.$message.error('发布失败！' + res.msg);
                    }
                }).catch(e => {
                    this.$message.error('请填写完整信息');
                })
            } else {
                this.$message.error('请填写完整信息！');
            }

        },
        deleteNotice(i) {
            this.$api.deleteNotice({
                id: this.noticeInfo[i].id,
            }).then(res => {
                if (res.status_code == 1) {
                    this.getNoticeInfo();
                } else {
                    this.$message.error(res.msg)
                }
            }).catch(e => {
                console.log(e)
            })
        },
        getNoticeInfo() {
            //获取公告信息
            this.$api.getNoticeInfo({
                page: this.nowPage,
                nums: 8,
            }).then(res => {
                if (res.status_code == 1) {
                    this.noticeInfo = res.data.list;
                    this.total = res.data.count;
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
                this.getNoticeInfo();
            }
            if (this.mode == 2) {
                this.getNoticeInfo();
            }
        },
        handleSelect(val) {
            if (this.mode !== val) {
                this.mode = val
                if (val == 1) {
                    this.nowPage = 1;
                    this.getNoticeInfo();
                }
                if (val == 2) {
                    this.nowPage = 1;
                    this.getNoticeInfo();
                }
            }
        },
    },
}
</script>

<style scoped>
::v-deep .el-form-item__label {
    font-size: 16px;
    color: black;
}
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