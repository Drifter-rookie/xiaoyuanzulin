<template>
    <div>
        <Appheader></Appheader>
        <app-body style="justify-content: center;">
            <div class="report2-container">
                <div class="report2-container-title">"idlePlanet"用户举报平台</div>
                <div class="report2-container-form">
                    <el-input placeholder="请输入举报用户的昵称" v-model="reportInfo.userName"  maxlength="20" show-word-limit clearable>
                    </el-input>
                    <el-input class="report2-user-text" placeholder="请输入举报用户的账号" v-model="reportInfo.userId" 
                     maxlength="20" show-word-limit clearable>
                    </el-input>
                    <div style="display: flex; justify-content: space-between;">
                        <div>
                            <div class="report2-tip">举报原因</div>
                            <el-select v-model="reportInfo.reportLabel" placeholder="请选择类别">
                                <el-option v-for="item in options1" :key="item.value" :label="item.label"
                                    :value="item.value">
                                </el-option>
                            </el-select>
                        </div>
                    </div>
                    <el-input class="report2-details-text" type="textarea" :autosize="{ minRows: 6, maxRows: 8}" 
                    placeholder="请输入举报该用户的详细（主观）原因" v-model="reportInfo.reportDetails"  maxlength="1000" show-word-limit clearable>
                    </el-input>

                    <div class="report2-container-picture">
                        <div class="report2-container-picture-title">在此处上传相关图片或截图</div>
                        <el-upload action="http://101.37.82.64:8089/file/" :on-preview="fileHandlePreview"
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
                        <el-button type="primary" plain @click="reportUserButton">确认提交</el-button>
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
    name: "reportUser",
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
                value: '商家欺诈',
                label: '商家欺诈'
            },{
                value: '虚假发货',
                label: '虚假发货'
            }, {
                value: '虚假宣传',
                label: '虚假宣传'
            },{
                value: '违反平台规则',
                label: '违反平台规则'
            },  {
                value: '态度恶劣、语言暴力',
                label: '态度恶劣、语言暴力'
            }, {
                value: '侵犯个人信息安全、非法收集信息',
                label: '侵犯个人信息安全、非法收集信息'
            },{
                value: '其它',
                label: '其它'
            }],
            imgList: [],
            reportInfo: {
                userName: '',
                userId:'',
                reportDetails: '',
                pictureList: '',
                reportLabel:'',
                handleStatus:'',
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
        reportUserButton() {
            this.reportInfo.pictureList = JSON.stringify(this.imgList);
            console.log(this.reportInfo);
            if (this.reportInfo.userName &&
                this.reportInfo.userId &&
                this.reportInfo.reportLabel &&
                this.reportInfo.reportDetails ) {
                this.$api.addReportUser(this.reportInfo).then(res => {
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
.report2-container {
    min-height: 106vh;
}

.report2-container-title {
    font-size: 24px;
    padding: 60px 0;
    font-weight: 600;
    width: 100%;
    text-align: center;
}

.report2-container-form {
    padding: 0 180px;
}

.report2-user-text {
    margin: 20px 0;
}

.report2-details-text {
    margin: 30px 0;
}

.report2-tip {
    color: #000;
    float: left;
    padding-right: 5px;
    height: 36px;
    line-height: 36px;
    font-size: 16px;
}

.report2-container-picture {
    width: 500px;
    height: 255px;
    margin: 20px 0;
}

.report2-container-picture-title {
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