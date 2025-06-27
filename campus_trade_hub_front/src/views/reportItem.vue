<template>
    <div>
        <Appheader></Appheader>
        <app-body style="justify-content: center;">
            <div class="reportItem-container">
                <div class="reportItem-container-title">"idlePlanet"商品举报平台</div>
                <div class="reportItem-container-form">
                    <el-input placeholder="请输入举报物品的名称" v-model="reportInfo.idleName" maxlength="20" show-word-limit
                        clearable>
                    </el-input>
                    <el-input class="reportItem-user-text" placeholder="请输入举报物品所属用户" v-model="reportInfo.idleUser"
                        maxlength="20" show-word-limit clearable>
                    </el-input>
                    <div style="display: flex; justify-content: space-between;">
                        <div>
                            <div class="reportItem-tip">举报原因</div>
                            <el-select v-model="reportInfo.reportLabel" placeholder="请选择类别">
                                <el-option v-for="item in options1" :key="item.value" :label="item.label"
                                    :value="item.value">
                                </el-option>
                            </el-select>
                        </div>
                    </div>
                    <el-input style="margin-top: 30px;" class="reportItem-details-text" type="textarea"
                        :autosize="{ minRows: 6, maxRows: 8 }" placeholder="请输入举报该商品的详细（主观）原因"
                        v-model="reportInfo.reportDetails" maxlength="1000" show-word-limit clearable>
                    </el-input>

                    <div style="margin-top: 10px;" class="reportItem-container-picture">
                        <div style="margin-top: 10px;" class="reportItem-container-picture-title">在此处上传相关图片或截图</div>
                        <el-upload action="http://localhost:8088/file/" :on-preview="fileHandlePreview"
                            :on-remove="fileHandleRemove" :on-success="fileHandleSuccess" :show-file-list="showFileList"
                            :limit="10" :on-exceed="handleExceed" accept="image/*" list-type="picture">
                            <el-button size="small" type="primary" class="up-button">点击上传</el-button>
                            <div slot="tip" class="el-upload__tip">(只能上传jpg/png文件)</div>
                        </el-upload>
                        <el-dialog :visible.sync="imgDialogVisible">
                            <img width="100%" :src="dialogImageUrl" alt="">
                        </el-dialog>
                    </div>

                    <div style="display: flex;justify-content: center;margin-top: -55px;margin-bottom: 10px;">
                        <el-button type="primary" plain @click="report1Button">确认提交</el-button>
                    </div>
                </div>
            </div>
            <app-foot></app-foot>
        </app-body>
    </div>
</template>

<script>
import Appheader from '../components/AppHeader.vue';
import AppBody from '../components/AppPageBody.vue'
import AppFoot from '../components/AppFoot.vue'
import options from '../components/address-data.js'

export default {
    name: "reportItem",
    components: {
        Appheader,
        AppBody,
        AppFoot
    },
    data() {
        return {
            imgDialogVisible: false,
            dialogImageUrl: '',
            showFileList: true,
            options: options,
            selectedOptions: [],

            options1: [{
                value: '毒品类',
                label: '毒品类'
            }, {
                value: '枪支弹药类',
                label: '枪支弹药类'
            }, {
                value: '色情、暴力、低俗类',
                label: '色情、暴力、低俗类'
            }, {
                value: '对校园秩序可能造成影响',
                label: '对校园秩序可能造成影响'
            }, {
                value: '无需实名的手机卡、上网卡',
                label: '无需实名的手机卡、上网卡'
            }, {
                value: '非法抽奖、赌博、侵犯个人隐私',
                label: '非法抽奖、赌博、侵犯个人隐私'
            }, {
                value: '历史虚无主义（扭曲党史国史等）',
                label: '历史虚无主义（扭曲党史国史等）'
            }, {
                value: '其它',
                label: '其它'
            }],


            imgList: [],
            reportInfo: {
                idleName: '',
                reportDetails: '',
                pictureList: '',
                idleUser: '',
                reportLabel: '',
                reporterId: '',
                reportTime: '',
                handleStatus: '',
            }
        };
    },
    methods: {
        fileHandleRemove(file, fileList) {
            console.log(file, fileList);
            for (let i = 0; i < this.imgList.length; i++) {
                if (this.imgList[i] === file.response.data) {
                    this.imgList.splice(i, 1);
                }
            }
        },
        fileHandlePreview(file) {
            console.log(file);
            this.dialogImageUrl = file.response.data;
            this.imgDialogVisible = true;
        },
        fileHandleSuccess(response, file, fileList) {
            console.log("file:", response, file, fileList);
            this.imgList.push(response.data);
        },
        report1Button() {
            this.reportInfo.pictureList = JSON.stringify(this.imgList);
            console.log(this.reportInfo);
            if (this.reportInfo.idleName &&
                this.reportInfo.reportDetails &&
                this.reportInfo.reportLabel &&
                this.reportInfo.idleUser) {
                this.$api.addReportItem(this.reportInfo).then(res => {
                    if (res.status_code === 1) {
                        this.$message({
                            message: '提交成功！，待管理员审核通过后将做出下一步处理',
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
        handleExceed(files, fileList) {
            this.$message.warning(`限制10张图片,本次选择了 ${files.length} 张图，共选择了 ${files.length + fileList.length} 张图`);
        },
    }
}
</script>

<style scoped>
.reportItem-container {
    min-height: 106vh;
}

.reportItem-container-title {
    font-size: 24px;
    padding: 60px 0;
    font-weight: 600;
    width: 100%;
    text-align: center;
}

.reportItem-container-form {
    padding: 0 180px;
}

.reportItem-user-text {
    margin: 20px 0;
}

.reportItem-details-text {
    margin: 40px 0;
}

.reportItem-tip {
    color: #000;
    float: left;
    padding-right: 5px;
    height: 36px;
    line-height: 36px;
    font-size: 16px;
}

.reportItem-container-picture {
    width: 500px;
    height: 255px;
    margin: 20px 0;
}

.reportItem-container-picture-title {
    margin: 10px 0;
    color: #000;
    font-size: 16px;
}

.picture-list {
    margin: 20px 0;
    display: flex;
    flex-direction: column;
    align-items: center;
    height: 100px;
}

.el-upload__tip {
    font-size: 14px;
    color: #ff0000;
    font-family: Arial, sans-serif;
}

.up-button {
    width: 120px;
    background-color: #409eff;
    color: #fff;
    border: none;
    border-radius: 4px;
    font-size: 14px;
    padding: 8px 16px;
}
</style>