<template>
    <div>
        <el-backtop></el-backtop>
        <app-head></app-head>
        <div class="ccontainer">
            <div class="left-content">
                <app-body style="justify-content: center; margin-top: 92px;">
                    <div class="app-header">
                        <div class="app-name">
                            <router-link to="/hire">
                                <b style="color: #000;">可租用商品</b>
                            </router-link>
                        </div>
                        <div class="container">
                            <div class="search-bar">
                                <input type="text" class="search-input" placeholder="搜索商品..." v-model="searchValue"
                                    @keyup.enter.native="searchIdle">
                                <button class="search-button" @click="searchIdle">&#x1F50D;</button>
                            </div>
                        </div>
                    </div>
                    <el-row :gutter="20">
                        <el-col :span="24">
                            <el-tabs v-model="labelName" type="card" @tab-click="handleClick"
                                style="margin-top: 10px;background: #fdfeff;">
                                <el-tab-pane name="0">
                                    <template #label>
                                        <div class="tab"><i class="el-icon-goods"></i>全部</div>
                                    </template>
                                </el-tab-pane>
                                <!-- <el-tab-pane name="1">
                                    <template #label>
                                        <div class="tab"><i class="el-icon-food"></i>食品</div>
                                    </template>
                                </el-tab-pane> -->
                                <el-tab-pane name="2">
                                    <template #label>
                                        <div class="tab"><i class="el-icon-suitcase"></i>服饰</div>
                                    </template>
                                </el-tab-pane>
                                <el-tab-pane name="3">
                                    <template #label>
                                        <div class="tab"><i class="el-icon-watch-1"></i>美妆</div>
                                    </template>
                                </el-tab-pane>
                                <el-tab-pane name="5">
                                    <template #label>
                                        <div class="tab"><i class="el-icon-basketball"></i>运动</div>
                                    </template>
                                </el-tab-pane>
                                <el-tab-pane name="4">
                                    <template #label>
                                        <div class="tab"><i class="el-icon-reading"></i>图书</div>
                                    </template>
                                </el-tab-pane>
                                <!-- <el-tab-pane name="6">
                                    <template #label>
                                        <div class="tab"><i class="el-icon-first-aid-kit"></i>医药</div>
                                    </template>
                                </el-tab-pane> -->
                                <el-tab-pane name="7">
                                    <template #label>
                                        <div class="tab"><i class="el-icon-video-camera"></i>数码产品</div>
                                    </template>
                                </el-tab-pane>
                                <el-tab-pane name="8">
                                    <template #label>
                                        <div class="tab"><i class="el-icon-school"></i>学习用品</div>
                                    </template>
                                </el-tab-pane>
                                <el-tab-pane name="9">
                                    <template #label>
                                        <div class="tab"><i class="el-icon-present"></i>其它</div>
                                    </template>
                                </el-tab-pane>
                            </el-tabs>
                        </el-col>
                    </el-row>
                    <el-carousel :interval="4000" type="card" height="400px">
                        <el-carousel-item v-for="(idle, index) in idleList" :key="index">
                            <el-image style="width: 100%; height: 400px" :src="idle.imgUrl" fit="contain"
                                @click="toDetails(idle)">
                                <div slot="error" class="image-slot">
                                    <i class="el-icon-picture-outline">无图</i>
                                </div>
                            </el-image>
                        </el-carousel-item>
                    </el-carousel>
                    <div style="min-height: 85vh;">
                        <div style="min-height: 80vh;">
                            <div style="margin: 10px 100px;">
                                <el-row :gutter="30">
                                    <el-col :span="6" v-for="(idle, index) in idleList" :key="index">
                                        <el-card class="card" :body-style="{ padding: '0px' }">
                                            <div class="idle-card" @click="toDetails(idle)">
                                                <el-image style="width: 100%; height: 200px" :src="idle.imgUrl"
                                                    fit="contain">
                                                    <div slot="error" class="image-slot">
                                                        <i class="el-icon-picture-outline">无图</i>
                                                    </div>
                                                </el-image>
                                                <div  v-if="idle.attribute === 1" class="idle-title">
                                                    <div style="display: flex; align-items: center;">
                                                    <img style="width:40px; height: 40px" src="../assets/sell.png">
                                                    {{ idle.idleName }}
                                                    </div>
                                                </div>
                                                <div  v-else-if="idle.attribute === 2" class="idle-title">
                                                    <div style="display: flex; align-items: center;">
                                                    <img style="width:40px; height: 40px" src="../assets/hire.png">
                                                    {{ idle.idleName }}
                                                    </div>
                                                </div>
                                                <el-row style="margin: 5px 10px;">
                                                    <el-col :span="12">
                                                        <div v-if="idle.attribute === 1">
                                                            <div class="idle-price">￥{{ idle.idlePrice }}</div>
                                                        </div>
                                                        <div v-else-if="idle.attribute === 2 && idle.leaseType === 1">
                                                            <div class="idle-price">￥{{ idle.idlePrice }}/日</div>
                                                        </div>
                                                        <div v-else-if="idle.attribute === 2 && idle.leaseType === 2">
                                                            <div class="idle-price">￥{{ idle.idlePrice }}/周</div>
                                                        </div>
                                                        <div v-else-if="idle.attribute === 2 && idle.leaseType === 3">
                                                            <div class="idle-price">￥{{ idle.idlePrice }}/月</div>
                                                        </div>
                                                    </el-col>
                                                </el-row>
                                                <div class="user-info">
                                                    <el-image style="width: 30px; height: 30px" :src="idle.user.avatar"
                                                        fit="contain">
                                                        <div slot="error" class="image-slot">
                                                            <i class="el-icon-picture-outline">无图</i>
                                                        </div>
                                                    </el-image>
                                                    <div class="user-nickname">{{ idle.user.nickname }}</div>
                                                </div>
                                            </div>
                                        </el-card>
                                    </el-col>
                                </el-row>
                            </div>
                        </div>
                        <div class="fenye">
                            <el-pagination background @current-change="handleCurrentChange" :current-page.sync="currentPage"
                                :page-size="12" layout="prev, pager, next, jumper" :total="totalItem">
                            </el-pagination>
                        </div>
                    </div>
                    <app-foot></app-foot>
                </app-body>
            </div>
            <div class="right-navigation">
                <app-aside></app-aside>
            </div>
        </div>
    </div>
</template>

<script>
import AppHead from '../components/Header.vue'
import AppBody from '../components/AppPageBody.vue'
import AppFoot from '../components/AppFoot.vue'
import AppAside from '../components/AppAside.vue'

export default {
    name: "index",
    components: {
        AppHead,
        AppBody,
        AppFoot,
        AppAside
    },
    data() {
        return {
            labelName: '0',
            idleList: [],
            currentPage: 1,
            totalItem: 0
        };
    },
    created() {
        this.findIdleItem(1)
    },
    watch: {
        $route(to, from) {
            this.labelName = to.query.labelName;
            let val = parseInt(to.query.page) ? parseInt(to.query.page) : 1;
            // let totalPage=parseInt(this.totalItem/8)+1;
            // val=parseInt(val%totalPage);
            // val=val===0?totalPage:val;
            this.currentPage = parseInt(to.query.page) ? parseInt(to.query.page) : 1;
            this.findIdleItem(val);
        }
    },
    methods: {
        searchIdle() {
            if ('/search' !== this.$route.path) {
                this.$router.push({ path: '/search', query: { searchValue: this.searchValue } });
            } else {
                this.$router.replace({ path: '/search', query: { searchValue: this.searchValue } });
                this.$router.go(0);
            }
        },
        findIdleItem(page) {
            const loading = this.$loading({
                lock: true,
                text: '加载数据中',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0)'
            });
            if (this.labelName > 0) {
                this.$api.findIdleItemByLable({
                    attribute: 2,
                    idleLabel: this.labelName,
                    page: page,
                    nums: 12
                }).then(res => {
                    console.log(res);
                    let list = res.data.list;
                    for (let i = 0; i < list.length; i++) {
                        list[i].timeStr = list[i].releaseTime.substring(0, 10) + " " + list[i].releaseTime.substring(11, 19);
                        let pictureList = JSON.parse(list[i].pictureList);
                        list[i].imgUrl = pictureList.length > 0 ? pictureList[0] : '';
                    }
                    this.idleList = list;
                    this.totalItem = res.data.count;
                    console.log(this.totalItem);
                }).catch(e => {
                    console.log(e)
                }).finally(() => {
                    loading.close();
                })
            } else {
                this.$api.findIdleItem({
                    attribute: 2,
                    page: page,
                    nums: 12
                }).then(res => {
                    console.log(res);
                    let list = res.data.list;
                    for (let i = 0; i < list.length; i++) {
                        list[i].timeStr = list[i].releaseTime.substring(0, 10) + " " + list[i].releaseTime.substring(11, 19);
                        let pictureList = JSON.parse(list[i].pictureList);
                        list[i].imgUrl = pictureList.length > 0 ? pictureList[0] : '';
                    }
                    this.idleList = list;
                    this.totalItem = res.data.count;
                    console.log(this.totalItem);
                }).catch(e => {
                    console.log(e)
                }).finally(() => {
                    loading.close();
                })
            }
        },
        handleClick(tab, event) {
            // console.log(tab,event);
            console.log(this.labelName);
            this.$router.replace({ query: { page: 1, labelName: this.labelName } });
        },
        handleCurrentChange(val) {
            console.log(`当前页: ${val}`);
            this.$router.replace({ query: { page: val, labelName: this.labelName } });
        },
        addcount(idle) {
            this.$api.updateIdleItem({
                id: idle.id,
                countNum: idle.countNum+1,
            }).then(res => {
                console.log(idle);
            });
        },
        toDetails(idle) {
            this.addcount(idle);
            this.$router.push({ path: '/details', query: { id: idle.id } });
        }
    }
}
</script>

<style scoped>
.card {
    border:rgb(4, 4, 4)solid 2px;
    margin: 10px 0px;
}

.card:hover {
    border: #d82e2e solid 2px;
}

.app-name a {
    color: rgb(110, 150, 199);
    font-size: 30px;
    text-decoration: none;
}

.app-header {
    margin-top: 10px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    max-width: 1000px;
    margin: 0 auto;
}

.container {
    display: flex;
    color: #151313;
}

.idle-card {
    height: 300px;
    margin-bottom: 15px;
    cursor: pointer;
}

.tab {
    background: #fdfeff;
    font-size: 20px;
    font-weight: 600;
    flood-color: rgb(95, 91, 91);
}

.tab:hover {
    color: rgb(110, 150, 199);
}

.fenye {
    display: flex;
    justify-content: center;
    height: 70px;
    color: #080000;
    align-items: center;
}


.idle-title {
    font-size: 18px;
    font-weight: 600;
    color: #151313;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    margin: 10px;
}

.idle-price {
    font-size: 20px;
    color: red;
}

.idle-time {
    color: #151313;
    font-size: 12px;
    margin: 0 10px;
}

.user-nickname {
    color: #090808;
    font-size: 12px;
    display: flex;
    align-items: center;
    height: 30px;
    padding-left: 10px;
}

.user-info {
    margin-top: 10px;
    float: right;
    padding: 5px 10px;
    height: 30px;
    display: flex;
}

.el-carousel__item:nth-child(2n) {
    background-color: transparent;
}

.el-carousel__item:nth-child(2n+1) {
    background-color: transparent;
}

.hover-border {
    border-color: #973646 solid 5px;
}

.container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 80px;
    background-color: #fdfdfd;
}

.search-bar {
    width: 600px;
    height: 30px;
    border-radius: 25px;
    background-color: #FFFFFF;
    padding: 10px;
    box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
    display: flex;
    justify-content: center;
    align-items: center;
}

.search-input {
    width: 100%;
    height: 100%;
    border: none;
    font-size: 18px;
    outline: none;
}

.search-button {
    width: 50px;
    height: 30px;
    border-radius: 50%;
    background-color:#ffffff;
    color: #FFFFFF;
    cursor: pointer;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 24px;
}
.ccontainer {
  display: flex;
  width: 1600px;
  justify-content: center;
  margin: 0 auto;
}

.left-content {
  flex: 9.5;
  margin: 0 auto;
}

.right-navigation {
  margin-top: 590px;
  flex: 0.5;
}
</style>