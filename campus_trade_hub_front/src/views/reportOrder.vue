<template>
    <div>
        <app-head></app-head>
        <app-body style="justify-content: center;">
            <div class="report3-container">
                <div class="report3-container-title">"idlePlanet"订单举报平台</div>
                <div class="report3-container-form">
                    <el-input placeholder="请输入举报订单的编号" v-model="reportInfo.orderId" maxlength="18" show-word-limit
                        clearable>
                    </el-input>
                    <div style="margin-top: 20px; justify-content: space-between;">
                        <div>
                            <div class="report3-tip">举报原因</div>
                            <el-select v-model="reportInfo.reportReason" placeholder="请选择类别">
                                <el-option v-for="item in options2" :key="item.value" :label="item.label"
                                    :value="item.value">
                                </el-option>
                            </el-select>
                        </div>
                    </div>
                    <div style="margin-top: 20px;">
                        <div class="report3-tip">交易状态</div>
                        <el-select v-model="reportInfo.tranStatus" placeholder="请选择">
                            <el-option v-for="item in options1" :key="item.value" :label="item.label"
                                :value="item.value">
                            </el-option>
                        </el-select>
                    </div>
                    <div style="margin-top: 20px;">
                        <div class="report3-select-tip">请选择您期待的处理方式：</div>
                        <el-radio-group v-model="reportInfo.dealWay">
                            <el-radio :label="3">退货退款</el-radio>
                            <el-radio :label="6">退款</el-radio>
                            <el-radio :label="9">赔偿部分金额</el-radio>
                        </el-radio-group>
                    </div>
                    <el-input class="report3-details-text" type="textarea" :autosize="{ minRows: 6, maxRows: 8 }"
                        placeholder="请输入举报该订单的详细（主观）原因" v-model="reportInfo.reportDetails" maxlength="1000"
                        show-word-limit clearable>
                    </el-input>

                    <div class="report3-container-picture">
                        <div class="report3-container-picture-title">在此处上传相关图片或截图</div>
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
                        <el-button type="primary" plain @click="reportOrderButton">确认提交</el-button>
                    </div>
                </div>
            </div>
            <app-foot></app-foot>
        </app-body>
    </div>
</template>

<script>
import AppHead from '../components/AppHeader.vue';
import AppBody from '../components/AppPageBody.vue'
import AppFoot from '../components/AppFoot.vue'
import options from '../components/address-data.js'

export default {
    name: "reportOrder",
    components: {
        AppHead,
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
                value: '已收到货',
                label: '已收到货'
            }, {
                value: '未收到货',
                label: '未收到货'
            }],
            options2: [{
                value: '迟迟收不到商品',
                label: '迟迟收不到商品'
            }, {
                value: '商品存在质量问题',
                label: '商品存在质量问题'
            }, {
                value: '卖家存在欺诈行为',
                label: '卖家存在欺诈行为'
            }, {
                value: '货不对板、尺码错误',
                label: '货不对板、尺码错误'
            }, {
                value: '其他',
                label: '其他'
            }],

            imgList: [],
            reportInfo: {
                orderId: '',
                reportReason: '',
                tranStatus: '',
                dealWay: '',
                reportDetails: '',
                pictureList: '',
                handleStatus: '',
                pictureList: ''
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
        reportOrderButton() {
            this.reportInfo.pictureList = JSON.stringify(this.imgList);
            console.log(this.reportInfo);
            if (this.reportInfo.orderId &&
                this.reportInfo.reportReason &&
                this.reportInfo.tranStatus &&
                this.reportInfo.dealWay &&
                this.reportInfo.reportDetails) {
                this.$api.addReportOrder(this.reportInfo).then(res => {
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
.report3-container {
    min-height: 106vh;
}

.report3-container-title {
    font-size: 24px;
    padding: 60px 0;
    font-weight: 600;
    width: 100%;
    text-align: center;
}

.report3-container-form {
    padding: 0 180px;
}


.report3-details-text {
    margin: 20px 0;
}

.report3-tip {
    color: #000;
    float: left;
    padding-right: 5px;
    height: 36px;
    line-height: 36px;
    font-size: 16px;
}

.report3-select-tip {
    color: #000;
    float: left;
    padding-right: 20px;
    height: 35px;
    line-height: 20px;
    font-size: 16px;
}

.report3-container-picture {
    width: 500px;
    height: 240px;
    margin: 20px 0;
}

.report3-container-picture-title {
    margin: 10px 0;
    color: #555555;
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