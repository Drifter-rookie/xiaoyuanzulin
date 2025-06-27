<template>
    <div>
        <app-head></app-head>
        <app-body style="justify-content: center;">
            <div class="order-page-container">
                <div class="idle-info-container" @click="toDetails(orderInfo.idleItem.id)">
                    <el-image style="width: 150px; height: 150px;" :src="orderInfo.idleItem.imgUrl"
                        fit="cover"></el-image>
                    <div class="idle-info-title">{{ orderInfo.userId == userId ? '买到的' : '卖出的' }}：{{
                    orderInfo.idleItem.idleName }}
                    </div>
                    <div class="idle-info-price">￥{{ orderInfo.orderPrice }}</div>

                </div>
                <div class="address-container" @click.stop="selectAddressDialog"
                    :style="orderInfo.userId == userId && orderInfo.orderStatus === 0 ? 'cursor: pointer;' : ''">
                    <div class="address-title">收货地址: {{ addressInfo.consigneeName }} {{ addressInfo.consigneePhone }}
                    </div>
                    <div class="address-detials">{{ addressInfo.detailAddress }}</div>
                    <el-button v-if="!addressInfo.detailAddress" @click.stop="selectAddressDialog" type="primary"
                        plain>选择收货地址</el-button>
                </div>
                <el-dialog title="选择地址" :visible.sync="addressDialogVisible" width="800px">
                    <el-table stripe empty-text="无地址信息，请先在个人中心添加地址" :data="addressData" style="width: 100%">
                        <el-table-column prop="consigneeName" label="收货人姓名" width="120">
                        </el-table-column>
                        <el-table-column prop="consigneePhone" label="手机号" width="140">
                        </el-table-column>
                        <el-table-column prop="detailAddressText" label="地址">
                        </el-table-column>
                        <el-table-column label=" " width="120">
                            <template slot-scope="scope">
                                <el-button size="mini" @click="selectAddress(scope.$index, scope.row)">选择
                                </el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-dialog>
                <div class="order-info-container">
                    <div class="order-info-title">订单信息（{{ orderInfo.idleItem.attribute == 1 ?
                    orderStatus[orderInfo.orderStatus] :
                    orderStatus1[orderInfo.orderStatus] }}）：</div>
                    <div class="order-info-item">订单编号：{{ orderInfo.orderNumber }}</div>
                    <div class="order-info-item">卖家账号：{{ this.userinfo.accountNumber }}</div>
                    <div class="order-info-item">支付状态：{{ orderInfo.paymentStatus === 1
                    ?
                    '已支付' : '未支付'
                        }}</div>
                    <div class="order-info-item">创建时间：{{ orderInfo.createTime.substring(0, 10) + ' ' +
                    orderInfo.createTime.substring(11, 19) }}
                    </div>
                    <div class="order-info-item">支付时间：{{ orderInfo.paymentTime ? orderInfo.paymentTime.substring(0, 10)
                    + ' ' +
                    orderInfo.paymentTime.substring(11, 19) : '' }}
                    </div>
                </div>
                <div class="menu">
                    <el-button v-if="userId == orderInfo.userId && orderInfo.orderStatus === 0" type="danger" plain
                        @click="changeOrderStatus(5, orderInfo)">取消订单</el-button>
                    <el-button v-if="userId == orderInfo.userId && orderInfo.orderStatus === 0" type="primary" plain
                        @click="showContract(orderInfo)">立即支付</el-button>
                    <el-button v-if="userId == orderInfo.userId && orderInfo.orderStatus === 1" type="primary" plain
                        @click="changeOrderStatus(2, orderInfo)">已支付</el-button>
                    <el-button v-if="userId == orderInfo.idleItem.userId && orderInfo.orderStatus === 2" type="primary"
                        plain @click="changeOrderStatus(3, orderInfo)">发货</el-button>
                    <el-button v-if="userId == orderInfo.userId && orderInfo.orderStatus === 3" type="primary" plain
                        @click="changeOrderStatus(4, orderInfo)">确认收货</el-button>
                    <el-button
                        v-if="userId == orderInfo.idleItem.userId && orderInfo.orderStatus === 4 && orderInfo.idleItem.attribute == 2"
                        type="primary" plain @click="changeOrderStatus(6, orderInfo)">确认归还</el-button>
                </div>
                
                <!-- 租赁合同对话框 -->
                <el-dialog :visible.sync="showContractDialog" title="校园闲置物品租赁合同" width="800px" :close-on-click-modal="false" :close-on-press-escape="false">
                    <div class="contract-container">
                        <div class="contract-content">
                            <p><strong>合同编号：</strong>{{ orderInfo.orderNumber }}</p>
                            <p><strong>出租方（甲方）：</strong></p>
                            <p>姓名：{{ userinfo.nickname }}</p>
                            <p>学号：{{ userinfo.accountNumber }}</p>
                            <p>学院：{{ userinfo.campus }}</p>
                            <p>联系电话：{{ userinfo.phone || '未提供' }}</p>
                            <p>身份证号：{{ userinfo.idCard || '未提供' }}</p>
                            
                            <p><strong>承租方（乙方）：</strong></p>
                            <p>姓名：{{ $globalData.userInfo.nickname }}</p>
                            <p>学号：{{ $globalData.userInfo.accountNumber }}</p>
                            <p>学院：{{ $globalData.userInfo.campus }}</p>
                            <p>联系电话：{{ $globalData.userInfo.phone || '未提供' }}</p>
                            <p>身份证号：{{ $globalData.userInfo.idCard || '未提供' }}</p>
                            
                            <p><strong>一、租赁物品详情</strong></p>
                            <p>1.物品名称及规格：{{ orderInfo.idleItem.attribute === 2 ? '□电子产品：' : '□其他物品：' }}{{ orderInfo.idleItem.idleName }}</p>
                            <p>2.附属配件：{{ orderInfo.idleItem.idleDetails || '无' }}</p>
                            <p>3.物品价值：人民币{{ orderInfo.idleItem.originPrice || orderInfo.idleItem.idlePrice }}元</p>
                            <p>4.用途限制：仅限用于正常学习生活 禁止转借他人、违法用途</p>
                            
                            <p><strong>二、租赁期限</strong></p>
                            <p>1.租期：自{{ getDateStr(0) }}起至{{ orderInfo.idleItem.attribute === 2 && orderInfo.idleItem.leaseType === 1 ? getDateStr(7) : (orderInfo.idleItem.attribute === 2 && orderInfo.idleItem.leaseType === 2 ? getDateStr(30) : getDateStr(90)) }}止，共计{{ orderInfo.idleItem.attribute === 2 && orderInfo.idleItem.leaseType === 1 ? '7天' : (orderInfo.idleItem.attribute === 2 && orderInfo.idleItem.leaseType === 2 ? '30天' : '90天') }}。</p>
                            <p>2.续租约定：租赁期满前7日，乙方如需续租，需通过平台提交书面申请，经甲方同意后签订补充协议。</p>
                            
                            <p><strong>三、租金及押金条款</strong></p>
                            <p>1.租金：</p>
                            <p>金额：人民币{{ orderInfo.idleItem.idlePrice }}元{{ orderInfo.idleItem.attribute === 2 && orderInfo.idleItem.leaseType === 1 ? '/日' : (orderInfo.idleItem.attribute === 2 && orderInfo.idleItem.leaseType === 2 ? '/周' : '/月') }}，合计租金{{ orderInfo.orderPrice }}元</p>
                            <p>2.押金：</p>
                            <p>金额：人民币{{ orderInfo.idleItem.attribute === 2 ? orderInfo.idleItem.originPrice || (orderInfo.idleItem.idlePrice * 2) : 0 }}元，于合同签订时一次性支付。</p>
                            <p>退还条件：租赁期满且物品完好返还、无欠费及违约行为，甲方需在3个工作日内通过原支付渠道退还押金。</p>
                            <p>3.费用承担：租赁期间物品日常维护费用由乙方承担，因甲方交付时存在质量问题产生的维修费用由甲方承担。</p>
                            
                            <p><strong>四、双方权利与义务</strong></p>
                            <p>（一）甲方权利与义务</p>
                            <p>1.权利：</p>
                            <p>按约定收取租金及押金；</p>
                            <p>定期检查物品使用情况；</p>
                            <p>乙方违约时，有权终止合同并要求赔偿损失。</p>
                            <p>2.义务：</p>
                            <p>保证对租赁物品拥有合法所有权，无抵押、查封等权利瑕疵；</p>
                            <p>交付时提供物品使用说明及完整配件，确保功能正常；</p>
                            <p>不得擅自收回租赁物。</p>
                            <p>（二）乙方权利与义务</p>
                            <p>1.权利：</p>
                            <p>按约定使用租赁物品；</p>
                            <p>租赁期满后，有权要求甲方退还押金。</p>
                            <p>2.义务：</p>
                            <p>严格按约定用途使用物品，不得转租、拆解或改装；</p>
                            <p>妥善保管物品，承担因保管不当造成的损坏赔偿责任；</p>
                            <p>租赁期满后，按交付时状态返还物品。</p>
                            
                            <p><strong>五、合同变更与解除</strong></p>
                            <p>1.变更：双方协商一致可变更合同条款，需通过平台签订书面补充协议。</p>
                            <p>2.解除：</p>
                            <p>乙方逾期5日未支付租金，甲方有权解除合同并扣除部分押金；</p>
                            <p>甲方交付的物品存在重大质量问题影响使用，乙方有权解除合同并要求全额退还押金及已付租金。</p>
                            
                            <p><strong>六、违约责任</strong></p>
                            <p>1.甲方违约：</p>
                            <p>未按约定时间交付物品，每逾期1日按租金的1%支付违约金；</p>
                            <p>擅自收回租赁物，需退还全部费用并按物品价值的10%支付违约金。</p>
                            <p>2.乙方违约：</p>
                            <p>未按约定用途使用物品，甲方有权立即收回物品，押金不予退还；</p>
                            <p>造成物品损坏或配件缺失，需按市场价赔偿；</p>
                            <p>逾期返还物品，每逾期1日按日租金的2倍支付违约金。</p>
                            
                            <p><strong>七、争议解决</strong></p>
                            <p>本合同履行过程中如发生争议，双方应首先通过平台客服仲裁解决；协商不成的，任何一方均有权向物品交付地有管辖权的人民法院提起诉讼。</p>
                            
                            <p><strong>八、其他条款</strong></p>
                            <p>1.生效条件：本合同自双方签字并通过平台实名认证后生效，一式两份，甲乙双方各执一份。</p>
                            <p>2.补充说明：</p>
                            <p>物品交付时需填写《交接清单》，双方签字确认状态；</p>
                            <p>本合同涉及的"书面通知"均通过平台系统消息或注册手机号短信发送，视为有效送达。</p>
                        </div>
                        
                        <div class="signature-section">
                            <div class="signature-row">
                                <div class="signature-label">甲方（签字/电子签名）：</div>
                                <div class="signature-value">{{ userinfo.nickname }}</div>
                                <div class="signature-date">日期：{{ getDateStr(0) }}</div>
                            </div>
                            <div class="signature-row">
                                <div class="signature-label">乙方（签字/电子签名）：</div>
                                <div v-if="!isSigned" class="signature-pad">
                                    <canvas ref="signatureCanvas" width="300" height="100" class="signature-canvas"></canvas>
                                    <div class="signature-actions">
                                        <el-button size="mini" @click="clearSignature">清空</el-button>
                                    </div>
                                </div>
                                <div v-else class="signature-value">{{ $globalData.userInfo.nickname }}</div>
                                <div class="signature-date">日期：{{ getDateStr(0) }}</div>
                            </div>
                        </div>
                    </div>
                    <div slot="footer" class="dialog-footer">
                        <el-button @click="cancelContract">取消</el-button>
                        <el-button type="primary" :disabled="!isSigned" @click="confirmContract">我已阅读并同意合同条款</el-button>
                    </div>
                </el-dialog>
                
                <!-- 收款码对话框 -->
                <el-dialog :visible.sync="ispay" title="收款码">
                    <el-form>
                        <el-form-item>
                            <div style="display: flex; justify-content: center; align-items: center;">
                                <el-image style="width: 400px; height: 400px;border-radius: 10px;"
                                    :src="userinfo.picture" fit="contain"></el-image>
                            </div>
                        </el-form-item>
                    </el-form>
                    <span slot="footer" class="dialog-footer">
                        <div style="display: flex; justify-content: center; align-items: center;">
                            <el-button @click="ispay = false">确定</el-button>
                        </div>
                    </span>
                </el-dialog>
            </div>
            <app-foot></app-foot>
        </app-body>
    </div>
</template>

<script>
import AppHead from '../components/Header.vue';
import AppBody from '../components/AppPageBody.vue'
import AppFoot from '../components/AppFoot.vue'

export default {
    name: "order",
    components: {
        AppHead,
        AppBody,
        AppFoot
    },
    data() {
        return {
            addressDialogVisible: false,
            ispay: false,
            showContractDialog: false,
            isSigned: false,
            signatureCanvas: null,
            addressData: [],
            orderStatus: ['待付款', '待付款', '待发货', '待收货', '已完成', '已取消'],
            orderStatus1: ['待付款', '待付款', '待发货', '待收货', '待归还', '已取消', '已完成'],
            orderInfo: {
                createTime: "",
                id: 0,
                idleId: 0,
                seller: '',
                idleItem: {
                    id: '',
                    idleName: '',
                    idleDetails: '',
                    pictureList: [],
                    idlePrice: 0,
                    seller: '',
                    idleLabel: '',
                    idleStatus: -1,
                    userId: '',
                },
                orderNumber: "",
                orderPrice: 0,
                orderStatus: 0,
                paymentStatus: 0,
                paymentTime: "",
                paymentWay: "",
                userId: 0
            },
            addressInfo: {
                id: '',
                update: false,
                consigneeName: '',
                consigneePhone: '',
                detailAddress: ''
            },
            userId: '',
            userinfo: {},
        };
    },
    created() {
        this.userId = this.getCookie('shUserId');
        console.log('userId', this.userId, this.getCookie('shUserId'));
        let orderId = this.$route.query.id;
        this.$api.getOrder({
            id: orderId
        }).then(res => {
            if (res.status_code === 1) {
                if (res.data.idleItem) {
                    let imgList = JSON.parse(res.data.idleItem.pictureList);
                    if (imgList.length > 0) {
                        res.data.idleItem.imgUrl = imgList[0];
                    } else {
                        res.data.idleItem.imgUrl = '';
                    }
                } else {
                    res.data.idleItem = {
                        idleName: '',
                        imgUrl: ''
                    }
                }
                this.orderInfo = res.data;
                console.log('user', this.orderInfo);
                this.$api.getoneUserInfo({
                    id: this.orderInfo.idleItem.userId
                }).then(res => {
                    this.userinfo = res.data;
                    console.log('user2', this.userinfo);
                }),
                    this.$api.getOrderAddress({
                        orderId: this.orderInfo.id
                    }).then(res => {
                        if (res.data) {
                            this.addressInfo = res.data;
                            this.addressInfo.update = true;
                        } else {
                            this.getAddressData();
                        }
                    })
            }
        })
    },
    mounted() {
        // 在合同对话框打开后初始化签名画板
        this.$nextTick(() => {
            if (this.$refs.signatureCanvas) {
                this.initSignatureCanvas();
            }
        });
    },
    methods: {
        getCookie(cname) {
            var name = cname + "=";
            var ca = document.cookie.split(';');
            for (var i = 0; i < ca.length; i++) {
                var c = ca[i].trim();
                if (c.indexOf(name) === 0) return c.substring(name.length, c.length);
            }
            return "0";
        },
        // 格式化日期
        getDateStr(addDays) {
            const date = new Date();
            date.setDate(date.getDate() + addDays);
            const year = date.getFullYear();
            const month = date.getMonth() + 1;
            const day = date.getDate();
            return `${year}年${month}月${day}日`;
        },
        toDetails(id) {
            this.$router.replace({ path: 'details', query: { id: id } });
        },
        // 显示合同对话框
        showContract(orderInfo) {
            if (!this.addressInfo.detailAddress) {
                this.$message.error('请先选择地址！');
                return;
            }
            this.showContractDialog = true;
            // 初始化签名画板
            this.$nextTick(() => {
                this.initSignatureCanvas();
            });
        },
        // 初始化签名画布
        initSignatureCanvas() {
            const canvas = this.$refs.signatureCanvas;
            if (!canvas) return;

            const ctx = canvas.getContext('2d');
            ctx.lineWidth = 2;
            ctx.lineCap = 'round';
            ctx.strokeStyle = '#000';

            let drawing = false;

            // 鼠标/触摸事件
            const startDrawing = (e) => {
                drawing = true;
                const pos = this.getPosition(e, canvas);
                ctx.beginPath();
                ctx.moveTo(pos.x, pos.y);
                e.preventDefault();
            };

            const draw = (e) => {
                if (!drawing) return;
                const pos = this.getPosition(e, canvas);
                ctx.lineTo(pos.x, pos.y);
                ctx.stroke();
                e.preventDefault();
            };

            const stopDrawing = () => {
                if (drawing) {
                    drawing = false;
                    this.checkSignature();
                }
            };

            // 触摸事件
            canvas.addEventListener('touchstart', startDrawing);
            canvas.addEventListener('touchmove', draw);
            canvas.addEventListener('touchend', stopDrawing);

            // 鼠标事件
            canvas.addEventListener('mousedown', startDrawing);
            canvas.addEventListener('mousemove', draw);
            canvas.addEventListener('mouseup', stopDrawing);
            canvas.addEventListener('mouseout', stopDrawing);

            this.signatureCanvas = canvas;
        },
        // 获取触摸/鼠标位置
        getPosition(e, canvas) {
            const rect = canvas.getBoundingClientRect();
            const event = e.touches ? e.touches[0] : e;
            return {
                x: event.clientX - rect.left,
                y: event.clientY - rect.top
            };
        },
        // 检查是否签名
        checkSignature() {
            const canvas = this.$refs.signatureCanvas;
            if (!canvas) return;

            const ctx = canvas.getContext('2d');
            const pixelData = ctx.getImageData(0, 0, canvas.width, canvas.height).data;
            
            // 检查是否有非透明像素
            for (let i = 3; i < pixelData.length; i += 4) {
                if (pixelData[i] > 0) {
                    this.isSigned = true;
                    return;
                }
            }
            this.isSigned = false;
        },
        // 清空签名
        clearSignature() {
            const canvas = this.$refs.signatureCanvas;
            if (!canvas) return;
            const ctx = canvas.getContext('2d');
            ctx.clearRect(0, 0, canvas.width, canvas.height);
            this.isSigned = false;
        },
        // 取消签约
        cancelContract() {
            this.showContractDialog = false;
            this.isSigned = false;
        },
        // 确认签约
        confirmContract() {
            if (!this.isSigned) {
                this.$message.error('请先在合同上签名！');
                return;
            }
            
            this.showContractDialog = false;
            // 显示收款码
            this.changeOrderStatus(1, this.orderInfo);
        },
        selectAddressDialog() {
            if (this.orderInfo.userId == this.userId && this.orderInfo.orderStatus === 0) {
                this.addressDialogVisible = true;
                if (this.addressData.length === 0) {
                    this.getAddressData();
                }
            }
        },
        getAddressData() {
            this.$api.getAddress().then(res => {
                if (res.status_code === 1) {
                    let data = res.data;
                    for (let i = 0; i < data.length; i++) {
                        data[i].detailAddressText = data[i].schoolName + data[i].campusName + data[i].dormitoryName;
                    }
                    console.log(data);
                    this.addressData = data;
                    if (!this.addressInfo.update) {
                        for (let i = 0; i < data.length; i++) {
                            if (data[i].defaultFlag) {
                                this.selectAddress(i, data[i]);
                            }
                        }
                    }
                }
            })
        },
        selectAddress(i, item) {
            this.addressDialogVisible = false;
            console.log(item, this.addressInfo);
            this.addressInfo.consigneeName = item.consigneeName;
            this.addressInfo.consigneePhone = item.consigneePhone;
            this.addressInfo.detailAddress = item.detailAddressText;
            if (this.addressInfo.update) {
                this.$api.updateOrderAddress({
                    id: this.addressInfo.id,
                    consigneeName: item.consigneeName,
                    consigneePhone: item.consigneePhone,
                    detailAddress: item.detailAddressText
                })
            } else {
                this.$api.addOrderAddress({
                    orderId: this.orderInfo.id,
                    consigneeName: item.consigneeName,
                    consigneePhone: item.consigneePhone,
                    detailAddress: item.detailAddressText
                }).then(res => {
                    if (res.status_code === 1) {
                        this.addressInfo.update = true;
                        this.addressInfo.id = res.data.id;
                    } else {
                        this.$message.error(res.msg)
                    }
                })
            }

        },
        changeOrderStatus(orderStatus, orderInfo) {
            if (orderStatus === 1) {
                this.ispay = true
                if (!this.addressInfo.detailAddress) {
                    this.$message.error('请选择地址！')
                } else {
                    this.$api.updateOrder({
                        id: orderInfo.id,
                        orderStatus: orderStatus,
                    })
                    this.orderInfo.orderStatus = orderStatus;
                }
            }
            else {
                if (orderStatus === 2) {
                    this.$api.updateOrder({
                        id: orderInfo.id,
                        orderStatus: orderStatus,
                        paymentStatus: 1,
                    }).then(res => {
                        if (res.status_code === 1) {
                            this.$message({
                                message: '操作成功！',
                                type: 'success'
                            });
                            this.orderInfo.orderStatus = orderStatus;
                            this.orderInfo.paymentStatus = 1;
                            this.orderInfo.paymentTime = res.data.paymentTime;
                        }
                    })
                }
                this.$api.updateOrder({
                    id: orderInfo.id,
                    orderStatus: orderStatus,
                }).then(res => {
                    if (res.status_code === 1) {
                        this.orderInfo.orderStatus = orderStatus;
                    }
                })
                if (orderStatus === 2 || orderStatus === 4 || orderStatus === 6) {
                    console.log("orderInfo.id", orderInfo.id);
                    this.$api.updateorder({
                        id: orderInfo.id,
                        userid: this.userinfo.id
                    })
                }
                if (orderStatus === 3 || orderStatus === 5 || orderStatus === 4) {
                    this.$api.updateorder({
                        id: orderInfo.id,
                        userid: this.orderInfo.userId
                    })
                }
            }
        },
    }
}
</script>

<style scoped>
.order-page-container {
    min-height: 106vh;
}

.idle-info-container {
    width: 100%;
    display: flex;
    border-bottom: 20px solid #f6f6f6;
    padding: 20px;
    cursor: pointer;
}

.idle-info-title {
    font-size: 20px;
    font-weight: 600;
    max-width: 750px;
    margin-left: 10px;
}

.idle-info-price {
    font-size: 18px;
    color: red;
    margin-left: 10px;
}

.address-container {
    min-height: 60px;
    padding: 20px;
    border-bottom: 20px solid #f6f6f6;
}

.address-title {
    font-size: 18px;
    font-weight: 600;
    margin-bottom: 10px;
}

.address-detials {
    font-size: 18px;
    color: #000;
}

.order-info-container {
    padding: 20px;
}

.order-info-title {
    margin: 10px 0;
    font-size: 18px;
    color: #000;
}

.order-info-item {
    margin: 10px 0;
    font-size: 16px;
    color: #000;
}

.menu {
    margin-left: 20px;
}

/* 合同样式 */
.contract-container {
    max-height: 500px;
    overflow-y: auto;
    padding: 10px;
}

.contract-content {
    margin-bottom: 20px;
    font-size: 14px;
    line-height: 1.5;
}

.contract-content p {
    margin: 5px 0;
}

.signature-section {
    margin-top: 30px;
    padding-top: 20px;
    border-top: 1px solid #ccc;
}

.signature-row {
    display: flex;
    align-items: center;
    margin-bottom: 20px;
    flex-wrap: wrap;
}

.signature-label {
    min-width: 150px;
    font-weight: bold;
}

.signature-value {
    min-width: 150px;
    margin: 0 15px;
    text-decoration: underline;
    text-align: center;
}

.signature-date {
    min-width: 180px;
}

.signature-pad {
    margin: 0 15px;
}

.signature-canvas {
    border: 1px solid #ccc;
    border-radius: 4px;
}

.signature-actions {
    margin-top: 10px;
    display: flex;
    justify-content: flex-end;
}
</style>