<template>
    <div>
        <app-head></app-head>
        <el-carousel style="margin-top: 67px" height="500px">
            <el-carousel-item>
                <img style="width: 100%; height: 500px" src="../assets/1.jpg">
            </el-carousel-item>
            <el-carousel-item>
                <img style="width: 100%; height: 500px" src="../assets/2.jpg">
            </el-carousel-item>
            <el-carousel-item>
                <img style="width: 100%; height: 500px" src="../assets/3.jpg">
            </el-carousel-item>
        </el-carousel>
        <app-body style="justify-content: center;  margin-top: 2px;">
            <div style="min-height: 85vh;">
                <div class="notice_container">
                    <h1>安全警示</h1>
                    <p class="warning">！！本网站仅作学校学生、教职人员间闲置物品交换，谨防诈骗！！</p>
                    <p>尊敬的用户：</p>
                    <p>为了维护良好的平台秩序和保障所有用户的合法权益，我们鼓励您积极参与平台监管。若您发现任何违规商品或违规用户行为，请立即向管理员举报投诉。</p>
                    <div class="contact-info">
                        <div class="contact-item">
                            <span>管理员联系方式：</span>
                            <a href="tel:19575412315">19575412315</a>
                        </div>
                        <div class="contact-item">
                            <span>警卫处24小时服务热线：</span>
                            <a href="tel:18456741542">18456741542</a>
                        </div>
                    </div>
                    <p>您的每一次反馈都将有助于我们构建更公正、安全的交易环境。感谢您的支持与配合！</p>
                    <p>若您在使用网络服务过程中不幸遭遇任何诈骗行为，请务必第一时间与我们联系。请时刻保持警惕，确保您的网络安全。</p>
                </div>
            </div>
            <app-foot></app-foot>
        </app-body>
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
            totalItem: 1
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
        findIdleItem(page) {
            const loading = this.$loading({
                lock: true,
                text: '加载数据中',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0)'
            });
            if (this.labelName > 0) {
                this.$api.findIdleItemByLable({
                    attribute: 1,
                    idleLabel: this.labelName,
                    page: page,
                    nums: 4
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
                    attribute: 1,
                    page: page,
                    nums: 4
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
        toDetails(idle) {
            this.$router.push({ path: '/details', query: { id: idle.id } });
        }
    }
}
</script>

<style scoped>
.notice_container {
    width: 90%;
    max-width: 600px;
    margin: 0 auto;
    background-color: white;
    border-radius: 5px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    padding: 30px;
}

h1 {
    color: #007BFF;
    text-align: center;
    font-size: 24px;
    margin-bottom: 20px;
}

p {
    font-size: 16px;
    line-height: 1.6;
    color: #333;
}

.warning {
    color: red;
    font-weight: bold;
    text-align: center;
    margin-bottom: 20px;
}

.contact-info {
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
}

.contact-item {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.contact-item a {
    color: #007BFF;
    text-decoration: none;
}
</style>