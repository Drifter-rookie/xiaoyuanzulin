<template>
    <div>
        <app-head></app-head>
        <app-body style="justify-content: center;">
            <div class="idle-details-container">
                <el-page-header class="address-container-back" @back="fanhui()">
                </el-page-header>
                <div class="details-header">
                    <div class="details-header-user-info">
                        <el-image style="width: 80px; height: 80px;border-radius: 5px;" :src="idleItemInfo.user.avatar"
                            fit="contain"></el-image>
                        <div style="margin-left: 10px;">
                            <div class="details-header-user-info-nickandcampus">{{ idleItemInfo.user.nickname }}</div>
                            <div class="details-header-user-info-nickandcampus">{{ idleItemInfo.user.campus }}</div>
                            <div class="details-header-user-info-time">
                                {{ idleItemInfo.user.signInTime.substring(0, 10) }}加入"物物有道"</div>
                        </div>
                    </div>
                    <div class="details-header-buy" :style="'width:' + (isMaster ? '180px;' : '202px;')">
                        <div style="color: red;font-size: 18px; font-weight: 600;">
                            <div v-if="idleItemInfo.attribute === 1">￥{{ idleItemInfo.idlePrice }}</div>
                            <div v-else-if="idleItemInfo.attribute === 2 && idleItemInfo.leaseType === 1">￥{{
                                idleItemInfo.idlePrice }}/日</div>
                            <div v-else-if="idleItemInfo.attribute === 2 && idleItemInfo.leaseType === 2">￥{{
                                idleItemInfo.idlePrice }}/周</div>
                            <div v-else-if="idleItemInfo.attribute === 2 && idleItemInfo.leaseType === 3">￥{{
                                idleItemInfo.idlePrice }}/月</div>
                        </div>
                        <div v-if="!isMaster && idleItemInfo.idleStatus !== 1" style="color: red; font-size: 16px;">
                            闲置已下架或删除
                        </div>
                        <el-button v-if="!isMaster && idleItemInfo.idleStatus === 1 && isFavorite === false"
                            type="primary" plain @click="favoriteButton(idleItemInfo)" style="border: none;">
                            <div style=" font-size: 20px;">
                                <i class="el-icon-star-off"></i>
                            </div>收藏
                        </el-button>
                        <el-button v-else-if="!isMaster && idleItemInfo.idleStatus === 1 && isFavorite === true"
                            type="primary" plain @click="favoriteButton(idleItemInfo)" style="border: none;">
                            <div style=" font-size: 25px; color: orange;">
                                <i class="el-icon-star-on"></i>
                            </div>取消收藏
                        </el-button>
                        <el-button v-if="isMaster && idleItemInfo.idleStatus === 1" type="danger"
                            @click="changeStatus(idleItemInfo, 2)" plain>下架</el-button>
                        <el-button v-if="isMaster && idleItemInfo.idleStatus === 2" type="primary"
                            @click="changeStatus(idleItemInfo, 1)" plain>重新上架</el-button>
                    </div>
                </div>
                <div class="product-container">
                    <div class="product-image">
                        <el-image style="width: 100%;margin-bottom: 2px; height: 400px;" :src="this.picture[0]"
                            :preview-src-list="idleItemInfo.pictureList"></el-image>
                    </div>
                    <div class="product-description">
                        <h2 class="product-name">{{ idleItemInfo.idleName }}</h2>
                        <div class="details-info-main" v-html="idleItemInfo.idleDetails"></div>
                        <div class="product-price">物品原价：￥{{ idleItemInfo.originPrice }}</div>
                        <div class="product-price">
                            <div v-if="idleItemInfo.attribute === 1">物品售价：￥{{ idleItemInfo.idlePrice }}</div>
                            <div v-else-if="idleItemInfo.attribute === 2 && idleItemInfo.leaseType === 1">物品租价：￥{{
                                idleItemInfo.idlePrice }}/日</div>
                            <div v-else-if="idleItemInfo.attribute === 2 && idleItemInfo.leaseType === 2">物品租价：￥{{
                                idleItemInfo.idlePrice }}/周</div>
                            <div v-else-if="idleItemInfo.attribute === 2 && idleItemInfo.leaseType === 3">物品租价：￥{{
                                idleItemInfo.idlePrice }}/月</div>
                        </div>
                        <p class="product-price">新旧程度：{{ idleItemInfo.newDegree }}</p>
                        <div class="product-price">
                            <div v-if="idleItemInfo.attribute === 1">租售类型：出售</div>
                            <div v-else-if="idleItemInfo.attribute === 2">租售类型：出租</div>
                        </div>
                        <el-button v-if="!isMaster && idleItemInfo.idleStatus === 1 && isShopping === false"
                            type="primary" plain @click="shoppingButton(idleItemInfo)">
                            <div style=" font-size: 20px;">
                            </div>加入购物车
                        </el-button>
                        <el-button v-else-if="!isMaster && idleItemInfo.idleStatus === 1 && isShopping === true"
                            type="primary" plain @click="shoppingButton(idleItemInfo)">
                            <div style=" font-size: 25px; color: orange;">
                            </div>移除购物车
                        </el-button>
                        <el-button v-if="!isMaster && idleItemInfo.idleStatus === 1 && idleItemInfo.attribute === 1"
                            type="danger" plain @click="buyButton(idleItemInfo)">立即购买</el-button>
                        <el-button v-if="!isMaster && idleItemInfo.idleStatus === 1 && idleItemInfo.attribute === 2"
                            type="danger" plain @click="buyButton(idleItemInfo)">立即租借</el-button>
                    </div>
                </div>
                <div class="message-container" id="replyMessageLocation">
                    <div class="message-title">全部留言</div>
                    <div class="message-send">
                        <div v-if="isReply" style="padding-bottom: 10px;">
                            <el-button type="info" @click="cancelReply">回复：{{ replyData.toMessage }}
                                @{{ replyData.toUserNickname }} <i class="el-icon-close el-icon--right"></i></el-button>
                        </div>
                        <el-input type="textarea" autosize placeholder="留言提问..." v-model="messageContent"
                            maxlength="200" show-word-limit>
                        </el-input>
                        <div class="upload-and-button-container">
                            <el-upload class="upload-demo" action="http://localhost:8088/file/"
                                :on-success="fileHandleSuccess" accept="image/*">
                                <i class="el-icon-camera" style="margin: 0 10px; font-size: 40px;"></i>
                            </el-upload>
                            <div class="message-send-button">
                                <el-button plain @click="sendMessage">发送留言</el-button>
                            </div>
                        </div>
                    </div>
                    <div>
                        <div v-for="(mes, index) in messageList" class="message-container-list" :key="index">
                            <div class="message-container-list-left">
                                <el-avatar :src="mes.fromU.avatar"></el-avatar>
                                <div class="message-container-list-text">
                                    <div class="message-nickname">{{ mes.fromU.nickname }}
                                        {{ mes.toU.nickname ? ' @' + mes.toU.nickname + '：' +
                                            mes.toM.content.substring(0, 10) +
                                            (mes.toM.content.length > 10 ? '...' : '') : '' }}</div>
                                    <div class="message-time">{{ mes.createTime }}</div>
                                    <div class="message-content" v-html="mes.content"></div>
                                    <el-image v-if="mes.picture" style="width: 100px; height: 100px;border-radius: 5px;"
                                        :src="mes.picture" fit="contain" :preview-src-list="[mes.picture]"></el-image>
                                </div>
                            </div>
                            <div class="message-container-list-right">
                                <el-button style="float: right;" plain @click="replyMessage(index)">回复</el-button>
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
    name: "idle_details",
    components: {
        AppHead,
        AppBody,
        AppFoot
    },
    data() {
        return {
            fileList: [],
            userid: 0,
            reply_data: 0,
            messageContent: '',
            toUser: null,
            toMessage: null,
            isReply: false,
            replyData: {
                toUserNickname: '',
                toMessage: ''
            },
            messageList: [],
            picture: [],
            idleItemInfo: {
                id: '',
                idleName: '',
                idleDetails: '',
                pictureList: [],
                idlePrice: 0,
                idleLabel: '',
                attribute: 0,
                leaseType: 0,
                idleStatus: -1,
                userId: '',
                originPrice: '',
                newDegree: '',
                user: {
                    accountNumber: '',
                    avatar: '',
                    nickname: '',
                    campus: '',
                    signInTime: '',
                },
            },
            isMaster: false,
            isFavorite: true,
            isShopping: false,
            favoriteId: 0,
            shoppingId: 0
        };
    },
    created() {
        let id = this.$route.query.id;
        this.$api.getIdleItem({
            id: id
        }).then(res => {
            if (res.data) {
                let list = res.data.idleDetails.split(/\r?\n/);
                let str = '';
                for (let i = 0; i < list.length; i++) {
                    str += '<p>' + list[i] + '</p>';
                }
                res.data.idleDetails = str;
                res.data.pictureList = JSON.parse(res.data.pictureList);
                this.idleItemInfo = res.data;
                this.picture[0] = this.idleItemInfo.pictureList[0];
                let userId = this.getCookie('shUserId');
                this.userid = userId;
                if (userId == this.idleItemInfo.userId) {
                    this.isMaster = true;
                }
                this.checkFavorite();
                this.getAllIdleMessage();
            }
            $('html,body').animate({
                scrollTop: 0
            }, { duration: 500, easing: "swing" });
        });
    },
    mounted() {
        var pubsub = this.goeasy.constructor.pubsub;
        pubsub.subscribe({
            channel: 'addmessage',
            onMessage: (message) => {
                this.data = parseInt(this.messageList[0].id, 10);
                // 更新组件内部状态
                let data1 = parseInt(message.content, 10);
                this.userid = parseInt(this.userid, 10);
                if (data1 === this.userid && '/details' === this.$route.path) {
                    setTimeout(() => {
                        this.getAllIdleMessage();
                    }, 500);
                    setTimeout(() => {
                        if (this.data < this.messageList[0].id) { this.data = parseInt(this.messageList[0].id, 10); }
                        else {
                            this.data = this.data + 1;
                        }
                        this.$api.update({
                            id: this.data
                        })
                        this.$api.updateMessageStatus1({
                            id: this.userid
                        })
                    }, 3000);
                }
            },
        });
    },
    methods: {
        fanhui() {
            let page = this.$route.query.page;
            if (page !== undefined) { this.$router.replace({ path: '/index', query: { page: page } }); }
            else {
                this.$router.replace({ path: '/index', query: { page: 1 } });
            }
        },
        getAllIdleMessage() {
            this.$api.getAllIdleMessage({
                idleId: this.idleItemInfo.id
            }).then(res => {
                if (res.status_code === 1) {
                    this.messageList = res.data;
                }
            })
        },
        checkFavorite() {
            this.$api.checkFavorite({
                idleId: this.idleItemInfo.id
            }).then(res => {
                if (!res.data) {
                    this.isFavorite = false;
                } else {
                    this.favoriteId = res.data;
                }
            })
        },
        checkShopping() {
            this.$api.checkShopping({
                idleId: this.idleItemInfo.id
            }).then(res => {
                if (!res.data) {
                    this.isShopping = false;
                } else {
                    this.shoppingId = res.data;
                }
            })
        },
        getCookie(cname) {
            var name = cname + "=";
            var ca = document.cookie.split(';');
            for (var i = 0; i < ca.length; i++) {
                var c = ca[i].trim();
                if (c.indexOf(name) === 0) return c.substring(name.length, c.length);
            }
            return "";
        },
        replyMessage(index) {
            $('html,body').animate({
                scrollTop: $("#replyMessageLocation").offset().top - 600
            }, { duration: 500, easing: "swing" });
            this.isReply = true;
            this.replyData.toUserNickname = this.messageList[index].fromU.nickname;
            this.replyData.toMessage = this.messageList[index].content.substring(0, 10) + (this.messageList[index].content.length > 10 ? '...' : '');
            this.toUser = this.messageList[index].userId;
            this.toMessage = this.messageList[index].id;
            console.log('here1', this.toUser);
        },
        changeStatus(idle, status) {
            this.$api.updateIdleItem({
                id: idle.id,
                idleStatus: status
            }).then(res => {
                if (res.status_code === 1) {
                    this.idleItemInfo.idleStatus = status;
                } else {
                    this.$message.error(res.msg)
                }
            });
        },
        addcount(idle) {
            this.$api.updateIdleItem({
                id: idle.id,
                countNum: idle.countNum + 1,
            });
        },
        reducecount(idle) {
            this.$api.updateIdleItem({
                id: idle.id,
                countNum: idle.countNum - 1,
            });
        },
        buyButton(idleItemInfo) {
            this.$api.addOrder({
                idleId: idleItemInfo.id,
                orderPrice: idleItemInfo.idlePrice,
                seller: idleItemInfo.accountNumber,
            }).then(res => {
                console.log(res);
                if (res.status_code === 1) {
                    this.$router.push({ path: '/order', query: { id: res.data.id } });
                } else {
                    this.$message.error(res.msg)
                    if ('/login' !== this.$route.path) {
                        this.$router.push({ path: '/login' });
                    }
                }
            }).catch(e => {
            })
        },
        favoriteButton(idleItemInfo) {
            if (this.isFavorite) {
                this.$api.deleteFavorite({
                    id: this.favoriteId
                }).then(res => {
                    if (res.status_code === 1) {
                        this.reducecount(idleItemInfo);
                        this.$message({
                            message: '已取消收藏！',
                            type: 'success'
                        });
                        this.isFavorite = false;
                    } else {
                        this.$message.error(res.msg)
                    }
                }).catch(e => {
                })
            } else {
                this.$api.addFavorite({
                    idleId: idleItemInfo.id
                }).then(res => {
                    if (res.status_code === 1) {
                        this.$message({
                            message: '收藏成功！',
                            type: 'success'
                        });
                        this.addcount(idleItemInfo);
                        this.isFavorite = true;
                        this.favoriteId = res.data;
                    } else {
                        this.$message.error(res.msg)
                        this.$message({
                            message: '收藏失败！',
                            type: 'error'
                        });
                    }
                }).catch(e => {
                })
            }
        },
        shoppingButton(idleItemInfo) {
            if (this.isShopping) {
                this.$api.deleteShopping({
                    id: this.shoppingId
                }).then(res => {
                    if (res.status_code === 1) {
                        this.$message({
                            message: '已移除购物车！',
                            type: 'success'
                        });
                        this.isShopping = false;
                    } else {
                        this.$message.error(res.msg)
                    }
                }).catch(e => {
                })
            } else {
                this.$api.addShopping({
                    idleId: idleItemInfo.id
                }).then(res => {
                    console.log(res);
                    if (res.status_code === 1) {
                        this.$message({
                            message: '成功加入购物车！',
                            type: 'success'
                        });
                        this.isShopping = true;
                        this.shoppingId = res.data;
                    } else {
                        this.$message.error(res.msg)
                        this.$message({
                            message: '加入购物车失败！',
                            type: 'error'
                        });
                    }
                }).catch(e => {
                })
            }
        },
        cancelReply() {
            this.isReply = false;
            this.toUser = this.idleItemInfo.userId;
            this.toMessage = null;
            this.replyData.toUserNickname = '';
            this.replyData.toMessage = '';
        },
        fileHandleSuccess(response) {
            let imgUrl = response.data;
            this.fileList = imgUrl;
        },
        sendMessage() {
            let content = this.messageContent.trim();
            if (this.toUser == null) {
                this.toUser = this.idleItemInfo.userId;
            }
            console.log('this.toUser', this.toUser);
            if (content) {
                let contentList = content.split(/\r?\n/);
                let contenHtml = contentList[0];
                for (let i = 1; i < contentList.length; i++) {
                    contenHtml += '<br>' + contentList[i];
                }
                if (this.fileList != '') {
                    this.$api.sendMessage({
                        idleId: this.idleItemInfo.id,
                        content: contenHtml,
                        toUser: this.toUser,
                        toMessage: this.toMessage,
                        picture: this.fileList
                    }).then(res => {
                        if (res.status_code === 1) {
                            this.$message({
                                message: '留言成功！',
                                type: 'success'
                            });
                            if (this.userid !== this.toUser) {
                                this.$api.updateMessageStatus({
                                    id: this.toUser
                                })
                            }
                            this.messageContent = '';
                            this.cancelReply();
                            this.getAllIdleMessage();
                        } else {
                            this.$message.error("留言失败！" + res.msg);
                        }
                    }).catch(() => {
                        this.$message.error("留言失败！");
                    });
                }
                else if (this.fileList == '') {
                    this.$api.sendMessage({
                        idleId: this.idleItemInfo.id,
                        content: contenHtml,
                        toUser: this.toUser,
                        toMessage: this.toMessage,
                    }).then(res => {
                        if (res.status_code === 1) {
                            this.$message({
                                message: '留言成功！',
                                type: 'success'
                            });
                            if (this.userid !== this.toUser) {
                                this.$api.updateMessageStatus({
                                    id: this.toUser
                                })
                            }
                            this.messageContent = '';
                            this.cancelReply();
                            this.getAllIdleMessage();
                        } else {
                            this.$message.error("留言失败！" + res.msg);
                        }
                    }).catch(() => {
                        this.$message.error("留言失败！");
                    });
                }
            }
            else if (this.fileList != '') {
                this.$api.sendMessage({
                    idleId: this.idleItemInfo.id,
                    toUser: this.toUser,
                    toMessage: this.toMessage,
                    picture: this.fileList
                }).then(res => {
                    if (res.status_code === 1) {
                        this.$message({
                            message: '留言成功！',
                            type: 'success'
                        });
                        if (this.userid !== this.toUser) {
                            this.$api.updateMessageStatus({
                                id: this.toUser
                            })
                        }
                        this.messageContent = '';
                        this.cancelReply();
                        this.getAllIdleMessage();
                    } else {
                        this.$message.error("留言失败！" + res.msg);
                    }
                }).catch(() => {
                    this.$message.error("留言失败！");
                });
            }
            else {
                this.$message.error("内容不能为空！");
            }
            this.fileList = '';
        }
    },
}
</script>

<style scoped>
::v-deep .address-container-back .el-page-header__title {
    font-size: 16px !important;
}

.upload-and-button-container {
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.idle-details-container {
    min-height: 106vh;
}

.details-header {
    height: 80px;
    border-bottom: 10px solid #f6f6f6;
    display: flex;
    justify-content: space-between;
    padding: 20px;
    align-items: center;
}

.details-header-user-info {
    display: flex;
}

.details-header-user-info-nickandcampus {
    font-weight: 600;
    font-size: 18px;
    margin-bottom: 10px;
}

.details-header-user-info-time {
    font-size: 12px;
    color: #555555;
}

.details-header-buy {
    display: flex;
    align-items: center;
    justify-content: space-between;
    height: 50px;
    width: 280px;
}

.details-info {
    padding: 20px 50px;
}

.details-info-title {
    font-size: 22px;
    font-weight: 600;
    margin-bottom: 20px;

}

.details-info-main {
    font-size: 17px;
    color: #121212;
    line-height: 160%;
}

.details-picture {
    margin: 20px 0;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.message-container {
    min-height: 100px;
    border-top: 10px solid #f6f6f6;
    padding: 20px;
}

.message-title {
    font-size: 20px;
    font-weight: 600;
    margin-bottom: 20px;
}

.message-send {
    min-height: 60px;
}

.message-send-button {
    margin-top: 10px;
    display: flex;
    justify-content: flex-end;
}

.message-container-list {
    min-height: 60px;
    border-top: 1px solid #eeeeee;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 15px 0;
}

.message-container-list:first-child {
    border-top: none;
}

.message-container-list-left {
    width: 850px;
    display: flex;
}

.message-container-list-right {
    width: 100px;
}

.message-container-list-text {
    margin-left: 10px;
}

.message-nickname {
    font-weight: 600;
    font-size: 18px;
    padding-bottom: 5px;
}

.message-content {
    font-size: 16px;
    color: #555555;
    width: 770px;
}

.message-time {
    font-size: 13px;
    color: #555555;
}

.product-container {
    height: 400px;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    padding: 20px;
}

.product-image,
.related-image {
    width: 30%;
    height: auto;
}

.product-description {
    width: 60%;
    text-align: left;
    margin-bottom: 20px;
}

.product-name {
    font-size: 24px;
    margin-bottom: 10px;
}

.product-type {
    font-size: 16px;
    margin-bottom: 10px;
}

.product-price {
    font-size: 18px;
    margin-bottom: 20px;
}

.add-to-cart-button,
.buy-now-button {
    background-color: #007bff;
    color: white;
    border: none;
    padding: 10px 20px;
    font-size: 16px;
    cursor: pointer;
    margin-right: 10px;
}

.add-to-cart-button:hover,
.buy-now-button:hover {
    background-color: #0056b3;
}

.product-related {
    width: 50%;
}

.related-image {
    width: 100%;
    height: auto;
}
</style>