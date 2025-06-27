<template>
    <div>
        <app-head></app-head>
        <app-body style="justify-content: center;">
            <div class="release-idle-container">
                <div class="release-idle-container-title">闲置物品上架</div>
                <div class="release-idle-container-form">
                    <div class="release-tip">物品名称</div>
                    <el-input placeholder="请输入闲置物品名称" v-model="idleItemInfo.idleName" style="width: 900px;">
                    </el-input>
                    <div style="display: flex; justify-content: space-between;margin-top: 10px;">
                        <div>
                            <div class="release-tip">物品类别</div>
                            <el-select v-model="idleItemInfo.idleLabel" placeholder="请选择类别" style="width: 900px;">
                                <el-option v-for="item in options2" :key="item.value" :label="item.label"
                                    :value="item.value">
                                </el-option>
                            </el-select>
                        </div>
                    </div>
                    <div style="display: flex; justify-content: space-between;margin-top: 10px;">
                        <div>
                            <div class="release-tip">物品原价</div>
                            <div style="width: 300px;">
                                <el-input-number v-model="idleItemInfo.originPrice" :precision="2" :step="10"
                                    :max="10000000">
                                    <div slot="prepend">价格</div>
                                </el-input-number>
                            </div>
                        </div>
                    </div>
                    <div style="display: flex; justify-content: space-between;margin-top: 10px;">
                        <div>
                            <div class="release-tip">新旧程度</div>
                            <el-select v-model="idleItemInfo.newDegree" placeholder="请选择物品新旧程度" style="width: 900px;">
                                <el-option v-for="item in options4" :key="item.value" :label="item.label"
                                    :value="item.value">
                                </el-option>
                            </el-select>
                        </div>
                    </div>
                    <div style="margin-top: 10px;">
                        <div class="release-tip">交易方式</div>
                        <el-select v-model="idleItemInfo.attribute" placeholder="请选择交易方式" style="width: 900px;">
                            <el-option v-for="item in options1" :key="item.value" :label="item.label"
                                :value="item.value">
                            </el-option>
                        </el-select>
                    </div>
                    <div v-if="idleItemInfo.attribute === 1">
                        <div style="margin-top: 10px;">
                            <div class="release-tip">物品售价</div>
                            <div style="width: 300px;">
                                <el-input-number v-model="idleItemInfo.idlePrice" :precision="2" :step="10"
                                    :max="10000000">
                                    <div slot="prepend">价格</div>
                                </el-input-number>
                            </div>
                        </div>
                    </div>
                    <div v-else-if="idleItemInfo.attribute === 2">
                        <div style="margin-top: 10px;">
                            <div class="release-tip">出租类型</div>
                            <el-select v-model="idleItemInfo.leaseType" placeholder="请选择出租类型" style="width: 900px;">
                                <el-option v-for="item in options3" :key="item.value" :label="item.label"
                                    :value="item.value">
                                </el-option>
                            </el-select>
                        </div>
                        <div v-if="idleItemInfo.leaseType === 1">
                            <div class="release-tip">物品每日的租价</div>
                        </div>
                        <div v-else-if="idleItemInfo.leaseType === 2">
                            <div class="release-tip">物品每周的租价</div>
                        </div>
                        <div v-else-if="idleItemInfo.leaseType === 3">
                            <div class="release-tip">物品每月的租价</div>
                        </div>
                        <div
                            v-if="idleItemInfo.leaseType === 1 || idleItemInfo.leaseType === 2 || idleItemInfo.leaseType === 3">
                            <div style="width: 300px;">
                                <el-input-number v-model="idleItemInfo.idlePrice" :precision="2" :step="10"
                                    :max="10000000">
                                    <div slot="prepend">价格</div>
                                </el-input-number>
                            </div>
                        </div>
                    </div>
                    <div class="release-tip">详情描述</div>
                    <el-input class="release-idle-detiles-text" type="textarea" :rows="10" placeholder="请输入物品的详细介绍..."
                        v-model="idleItemInfo.idleDetails" maxlength="1000" show-word-limit style="width: 900px;">
                    </el-input>
                    <div class="release-idle-container-picture">
                        <div class="release-idle-container-picture-title">上传闲置物品照片</div>
                        <el-upload action="http://localhost:8088/file/" list-type="picture-card"
                            :on-preview="fileHandlePreview" :on-remove="fileHandleRemove"
                            :on-success="fileHandleSuccess" :show-file-list="showFileList" :limit="10"
                            :on-exceed="handleExceed" accept="image/*">
                            <i class="el-icon-plus"></i>
                        </el-upload>
                        <el-dialog :visible.sync="dialogVisible" size="tiny">
                            <img width="100%" :src="dialogImageUrl" alt="">
                        </el-dialog>
                    </div>
                    <div style="display: flex;justify-content: center;margin-top: 30px;margin-bottom: 30px;">
                        <el-button type="primary" plain @click="releaseButton">确认发布</el-button>
                    </div>
                </div>
            </div>
            <app-foot></app-foot>
        </app-body>
    </div>
</template>

<script>
import AppHead from '../components/Header.vue';
import AppBody from '../components/AppPageBody.vue'
import AppFoot from '../components/AppFoot.vue'
import options from '../components/address-data.js'

export default {
    name: "release",
    components: {
        AppHead,
        AppBody,
        AppFoot
    },
    data() {
        return {
            // flag:0,
            imgDialogVisible: false,
            dialogImageUrl: '',
            showFileList: true,
            options: options,
            selectedOptions: [],
            options1: [{
                value: 1,
                label: '出售'
            }, {
                value: 2,
                label: '出租'
            }],
            options2: [{
                value: 1,
                label: '食品'
            }, {
                value: 2,
                label: '服饰'
            }, {
                value: 3,
                label: '美妆'
            }, {
                value: 4,
                label: '图书'
            }, {
                value: 5,
                label: '运动'
            }, {
                value: 6,
                label: '医药'
            }, {
                value: 7,
                label: '数码产品'
            }, {
                value: 8,
                label: '学习用品'
            }, {
                value: 9,
                label: '其它'
            }],
            options3: [{
                value: 1,
                label: '按日出租'
            }, {
                value: 2,
                label: '按周出租'
            }, {
                value: 3,
                label: '按月出租'
            }],
            options4: [{
                value: '全新',
                label: '全新'
            }, {
                value: '九成新',
                label: '九成新'
            }, {
                value: '八成新',
                label: '八成新'
            }, {
                value: '七成新',
                label: '七成新'
            }, {
                value: '六成新',
                label: '六成新'
            }, {
                value: '五成新',
                label: '五成新'
            }, {
                value: '二手/旧',
                label: '二手/旧'
            }, {
                value: '闲置',
                label: '闲置'
            }],
            imgList: [],
            idleItemInfo: {
                idleName: '',
                idleDetails: '',
                pictureList: '',
                idlePrice: 0,
                idleLabel: '',
                attribute: '',
                leaseType: '',
                originPrice: '',
                newDegree: ''
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
        releaseButton() {
            this.idleItemInfo.pictureList = JSON.stringify(this.imgList);
            console.log(this.idleItemInfo);
            if (this.idleItemInfo.attribute === 1) {
                this.idleItemInfo.leaseType = 4;
            }
            if (this.idleItemInfo.idleName &&
                this.idleItemInfo.idleDetails &&
                this.idleItemInfo.idleLabel &&
                this.idleItemInfo.idlePrice &&
                this.idleItemInfo.attribute &&
                this.idleItemInfo.leaseType &&
                this.idleItemInfo.originPrice &&
                this.idleItemInfo.newDegree &&
                this.idleItemInfo.pictureList) {
                this.$api.addIdleItem(this.idleItemInfo).then(res => {
                    if (res.status_code === 1) {
                        this.$message({
                            message: '发布成功！',
                            type: 'success'
                        });
                        console.log(res.data);
                        // this.flag=1;
                        this.$router.replace({ path: '/details', query: { id: res.data.id } });
                    } else {
                        this.$message.error('发布失败！' + res.msg);
                    }
                }).catch(e => {
                    this.$message.error('请填写完整信息');
                })
            } else {
                this.$message.error('请填写完整信息！');
            }
            // if (this.flag === 1) {
            // this.$router.replace({ path: '/details', query: { id: res.data.id } });}
        },
        handleExceed(files, fileList) {
            this.$message.warning(`限制10张图片，本次选择了 ${files.length} 张图，共选择了 ${files.length + fileList.length} 张图`);
        },
    }
}
</script>

<style scoped>
.release-idle-container {
    min-height: 106vh;
}

.release-idle-container-title {
    font-size: 20px;
    padding: 30px 0;
    font-weight: 600;
    width: 100%;
    text-align: center;
}

.release-idle-container-form {
    padding: 0 180px;
}

.release-idle-detiles-text {
    margin: 20px 0;
}

.release-tip {
    color: #000;
    float: left;
    padding-right: 5px;
    height: 36px;
    line-height: 36px;
    font-size: 18px;
}

.release-idle-container-picture {
    height: 170px;
    margin: 10px 0;
}

.release-idle-container-picture-title {
    margin: 10px 0;
    color: #000;
    font-size: 18px;
}

.picture-list {
    margin: 20px 0;
    display: flex;
    flex-direction: column;
    align-items: center;
    height: 100px;
}
</style>