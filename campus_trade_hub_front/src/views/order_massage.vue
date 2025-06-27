<template>
    <div>
        <app-head></app-head>
        <app-body style="justify-content: center;">
            <div class="message-container">
                <div class="message-container-title">我的订单消息</div>
                <div class="idle-container">
                    <div class="idle-container-list">
                        <div v-for="(item, index) in dataList " class="idle-container-list-item"
                            @click="toDetails(item)" :key="index">
                            <div class="idle-container-list-item-detile">
                                <el-image style="width: 100px; height: 100px;" :src="item.imgUrl" fit="cover">
                                    <div slot="error" class="image-slot">
                                        <i class="el-icon-picture-outline">无图</i>
                                    </div>
                                </el-image>
                                <div class="idle-container-list-item-text">
                                    <div class="idle-container-list-title">
                                        {{ item.idleName }}
                                    </div>
                                    <div class="idle-container-list-idle-details" v-html="item.idleDetails">
                                    </div>
                                    <div class="idle-container-list-idle-time">{{ item.timeStr }}</div>

                                    <div class="idle-item-foot">
                                        <div class="idle-price">￥{{ item.idlePrice }}
                                            {{ item.attribute == 1 ? orderStatus[item.orderStatus] :
                            orderStatus1[item.orderStatus] }}
                                        </div>
                                    </div>
                                </div>
                                <el-badge v-if="item.status == 1 && item.thisid == userid" value="new"
                                    class="item"></el-badge>
                            </div>
                        </div>

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

export default {
    name: "message",
    components: {
        AppHead,
        AppBody,
        AppFoot
    },
    data() {
        return {
            userid: 0,
            activeName: '4',
            imgFileList: [],
            dataList: [],
            orderStatus: ['待付款', '待付款', '待发货', '待收货', '已完成', '已取消'],
            orderStatus1: ['待付款', '待付款', '待发货', '待收货', '待归还', '已取消', '已完成'],
            userInfo: {
                accountNumber: "",
                avatar: "",
                nickname: "",
                sex: "",
                age: "",
                phone: "",
                campus: "",
                signInTime: "",
            },
        };
    },
    created() {
        if (!this.$globalData.userInfo.nickname) {
            this.$api.getUserInfo().then(res => {
                if (res.status_code === 1) {
                    res.data.signInTime = res.data.signInTime.substring(0, 10);
                    this.$globalData.userInfo = res.data;
                    this.userInfo = this.$globalData.userInfo;
                    this.userid = this.userInfo.id;
                    console.log(this.userid, 'this.userid');
                }
            })
        } else {
            this.userInfo = this.$globalData.userInfo;
            this.userid = this.userInfo.id;
            console.log(this.userid, 'this.userid');
        }
        this.getMyOrder();
        this.getMySoldIdle();
    },
    mounted() {
        var pubsub = this.goeasy.constructor.pubsub;
        pubsub.subscribe({
            channel: 'order',
            onMessage: (message) => {
                let data = parseInt(message.content, 10);
                if (data === this.userid) {
                    this.getMyOrder();
                    this.getMySoldIdle();
                }
            },
        });
        pubsub.subscribe({
            channel: 'order1',
            onMessage: (message) => {
                this.getMyOrder();
                this.getMySoldIdle();
            },
        });
    },
    methods: {
        change(index) {
            this.$api.updateorder1({
                id: index,
            })
        },
        toDetails(item) {
            if (item.status === 1) { this.change(item.id); }
            this.$router.push({ path: '/order', query: { id: item.id } });
        },
        getMySoldIdle() {
            this.$api.getMySoldIdle().then(res => {
                if (res.status_code === 1) {
                    console.log('getMySoldIdle', res.data);
                    for (let i = 0; i < res.data.length; i++) {
                        let pictureList = JSON.parse(res.data[i].idleItem.pictureList);
                        this.dataList.push({
                            id: res.data[i].id,
                            imgUrl: pictureList.length > 0 ? pictureList[0] : '',
                            idleName: res.data[i].idleItem.idleName,
                            idleDetails: res.data[i].idleItem.idleDetails,
                            timeStr: res.data[i].createTime.substring(0, 10) + " " + res.data[i].createTime.substring(11, 19),
                            idlePrice: res.data[i].orderPrice,
                            orderStatus: res.data[i].orderStatus,
                            attribute: res.data[i].idleItem.attribute,
                            status: res.data[i].status,
                            thisid: res.data[i].seller,
                        });
                    }
                    console.log(this.dataList[0]);
                }
            })
        },
        getMyOrder() {
            this.$api.getMyOrder().then(res => {
                if (res.status_code === 1) {
                    console.log('getMyOrder', res.data);
                    for (let i = 0; i < res.data.length; i++) {
                        let pictureList = JSON.parse(res.data[i].idleItem.pictureList);
                        this.dataList.push({
                            id: res.data[i].id,
                            imgUrl: pictureList.length > 0 ? pictureList[0] : '',
                            idleName: res.data[i].idleItem.idleName,
                            idleDetails: res.data[i].idleItem.idleDetails,
                            timeStr: res.data[i].createTime.substring(0, 10) + " " + res.data[i].createTime.substring(11, 19),
                            idlePrice: res.data[i].orderPrice,
                            orderStatus: res.data[i].orderStatus,
                            attribute: res.data[i].idleItem.attribute,
                            status: res.data[i].status,
                            thisid: res.data[i].seller,
                        });
                    }
                }
            })
        },
    }
}
</script>

<style scoped>
.message-container{
    min-height: 106vh;
    padding: 0 20px;
}
.item {
    top: -40px;
    right: -350px;
}

.idle-container {
    padding: 0 20px;
}

.message-container-title {
    font-size: 16px;
    padding: 20px 0;
    font-weight: 600;
}

.idle-container-list {
    min-height: 55vh;
}

.idle-container-list-item {
    border-bottom: 1px solid #eeeeee;
    cursor: pointer;
}

.idle-container-list-item:last-child {
    border-bottom: none;
}

.idle-container-list-item-detile {
    height: 120px;
    display: flex;
    align-items: center;
}

.idle-container-list-item-text {
    margin-left: 10px;
    height: 100px;
    max-width: 800px;
}

.idle-container-list-title {
    font-weight: 600;
    font-size: 18px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}

.idle-container-list-idle-details {
    font-size: 14px;
    color: #555555;
    padding-top: 5px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}

.idle-container-list-idle-time {
    font-size: 13px;
    padding-top: 5px;
}

.idle-price {
    font-size: 15px;
    padding-top: 5px;
    color: red;
}

.idle-item-foot {
    width: 800px;
    display: flex;
    justify-content: space-between;
}
</style>