<template>
    <div>
        <app-head></app-head>
        <app-body style="justify-content: center;">
            <div style="min-height: 100vh;">
                <div style="min-height: 80vh;">
                    <div style="margin: 0 20px;padding-top: 25px;">
                        <el-divider content-position="left">
                        <b style="color: #000;font-size: 20px;">搜索到的商品</b></el-divider>
                        <div style="text-align: center;color: #555555;padding: 30px;" v-if="idleList.length === 0">暂无匹配的商品
                        </div>
                        <el-row :gutter="30">
                            <el-col :span="6" v-for="(idle, index) in idleList" :key="index">
                                <div class="idle-card" @click="toDetails(idle)">
                                    <el-image style="width: 100%; height: 160px" :src="idle.imgUrl" fit="contain">
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
                                            <div v-if="idle.attribute === 1"><div class="idle-price">￥{{ idle.idlePrice }}</div></div>
                                                    <div v-else-if="idle.attribute === 2 && idle.leaseType===1"><div class="idle-price">￥{{ idle.idlePrice }}/日</div></div>
                                                    <div v-else-if="idle.attribute === 2 && idle.leaseType===2"><div class="idle-price">￥{{ idle.idlePrice }}/周</div></div>
                                                    <div v-else-if="idle.attribute === 2 && idle.leaseType===3"><div class="idle-price">￥{{ idle.idlePrice }}/月</div></div>
                                        </el-col>
                                        <el-col :span="12">
                                            <div class="idle-place">{{ idle.idlePlace }}</div>
                                        </el-col>
                                    </el-row>
                                    <div class="idle-time">{{ idle.timeStr }}</div>
                                    <div class="user-info">
                                        <el-image style="width: 30px; height: 30px" :src="idle.user.avatar" fit="contain">
                                            <div slot="error" class="image-slot">
                                                <i class="el-icon-picture-outline">无图</i>
                                            </div>
                                        </el-image>
                                        <div class="user-nickname">{{ idle.user.nickname }}</div>
                                    </div>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                </div>
                <div class="fenye">
                    <el-pagination background @current-change="handleCurrentChange" :current-page.sync="currentPage"
                        :page-size="8" layout="prev, pager, next, jumper" :total="totalItem">
                    </el-pagination>
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
import AppAside from '../components/AppAside.vue'

export default {
    name: "search",
    components: {
        AppHead,
        AppBody,
        AppFoot,
        AppAside
    },
    data() {
        return {
            idleList: [],
            currentPage: 1,
            searchValue: '',
            totalItem: 0,
        };
    },
    created() {
        this.findAllIdleItem(1, this.$route.query.searchValue);
        this.searchValue = this.$route.query.searchValue;
    },
    watch: {
        $route(to, from) {
            this.searchValue = to.query.searchValue;
            this.findAllIdleItem(to.query.page, to.query.searchValue);
        }
    },
    methods: {
        findAllIdleItem(page, findValue) {
            this.$api.findAllIdleItem({
                page: page,
                nums: 8,
                findValue: findValue
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
                console.log('total:', this.totalItem) ;
            }).catch(e => {
                console.log(e)
            })
        },
        handleClick(tab, event) {
            console.log(tab, event);
            this.$router.replace({ query: { page: 1, searchValue: this.searchValue } });
        },
        handleCurrentChange(val) {
            console.log(`当前页: ${val}`);
            this.$router.replace({ query: { page: val, searchValue: this.searchValue } });
        },
        addcount(idle) {
            this.$api.updateIdleItem({
                id: idle.id,
                count: idle.count+1,
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
.idle-card {
    height: 305px;
    border: #000000 solid 2px;
    margin-bottom: 15px;
    cursor: pointer;
}
.idle-card:hover {
    border: #d82e2e solid 2px;
}
.fenye {
    display: flex;
    justify-content: center;
    height: 70px;
    align-items: center;
}

.idle-title {
    font-size: 18px;
    font-weight: 600;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    margin: 10px;
}

.idle-price {
    font-size: 16px;
    color: red;
}

.idle-place {
    font-size: 13px;
    color: #666666;
    float: right;
    padding-right: 20px;

}

.idle-time {
    color: #666666;
    font-size: 12px;
    margin: 0 10px;
}

.user-nickname {
    color: #999999;
    font-size: 12px;
    display: flex;
    align-items: center;
    height: 30px;
    padding-left: 10px;
}

.user-info {
    padding: 5px 10px;
    height: 30px;
    display: flex;
}
</style>