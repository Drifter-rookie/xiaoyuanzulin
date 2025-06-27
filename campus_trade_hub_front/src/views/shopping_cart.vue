<template>
    <div>
        <app-head></app-head>
        <app-body style="justify-content: center;">
            <div class="container">
                <div class="container-title">我的购物车</div>
                <el-table :data="shoppingCartData" stripe style="width: 100%;color: #000;font-size: 18px;">
                    <el-table-column label="商品图片" width="300">
                        <template slot-scope="scope" >
                            <el-image shape="square" :size="100" :src="scope.row.imgUrl" @click="toDetails(scope.$index)">
                            </el-image>
                        </template>
                    </el-table-column>
                    <el-table-column prop="idleName" label="商品名称" width="120">
                    </el-table-column>
                    <el-table-column prop="idleDetails" label="商品详情描述" show-overflow-tooltip min-width="100" width="300">
                    </el-table-column>
                    <el-table-column prop="newDegree" label="商品新旧程度" show-overflow-tooltip min-width="100" width="150">
                    </el-table-column>
                    <el-table-column prop="idlePrice" label="商品价格" show-overflow-tooltip min-width="100" width="150">
                        <template slot-scope="scope">
                            <template v-if="scope.row.attribute === 2&&scope.row.leaseType===1">
                                ￥{{ scope.row.idlePrice }}/日</template>
                            <template v-else-if="scope.row.attribute === 2&&scope.row.leaseType===2">
                                ￥{{ scope.row.idlePrice }}/周</template>
                            <template v-else-if="scope.row.attribute === 2&&scope.row.leaseType===3">
                                ￥{{ scope.row.idlePrice }}/月</template>
                             <template v-else-if="scope.row.attribute === 1">
                                ￥{{ scope.row.idlePrice }}</template>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作">
                        <template slot-scope="scope">
                            <el-button size="mini" type="success" @click="buyShoppingCart(scope.$index)">立即付款</el-button>
                            <el-button size="mini" type="danger" @click="deleteShoppingCart(scope.$index)">移除购物车</el-button>
                        </template>
                    </el-table-column>
                </el-table>
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
    name: "shopping_cart",
    components: {
        AppHead,
        AppBody,
        AppFoot
    },
    data() {
        return {
            shoppingCartData: [],
        };
    },
    created() {
        this.getMyShoppingCart();
    },
    methods: {
        getMyShoppingCart() {
            this.$api.getMyShopping().then(res => {
                console.log('getMyShopping', res);
                if (res.status_code === 1) {
                    for (let i = 0; i < res.data.length; i++) {
                        let pictureList = JSON.parse(res.data[i].idleItem.pictureList);
                        this.shoppingCartData.push({
                            shoppingId: res.data[i].id,
                            id: res.data[i].idleItem.id,
                            imgUrl: pictureList.length > 0 ? pictureList[0] : '',
                            idleName: res.data[i].idleItem.idleName,
                            idleDetails: res.data[i].idleItem.idleDetails,
                            timeStr: res.data[i].createTime.substring(0, 10) + " " + res.data[i].createTime.substring(11, 19),
                            idlePrice: res.data[i].idleItem.idlePrice,
                            attribute: res.data[i].idleItem.attribute,
                            newDegree: res.data[i].idleItem.newDegree,
                            leaseType: res.data[i].idleItem.leaseType,
                            seller: res.data[i].idleItem.accountNumber,
                            status: res.data[i].idleItem.idleStatus
                        });
                    }
                }
            })
        },
        buyButton(i) {
            this.$api.addOrder({
                idleId: this.shoppingCartData[i].id,
                orderPrice: this.shoppingCartData[i].idlePrice,
                seller: this.shoppingCartData[i].seller,
            }).then(res => {
                console.log(res);
                if (res.status_code === 1) {
                    this.$router.push({ path: '/order', query: { id: res.data.id } });
                } else {
                    this.$message.error(res.msg)
                }
            }).catch(e => {
            })
        },
        deleteButton(i) {
            console.log(i);
            console.log( this.shoppingCartData[i].shoppingId);
            this.$api.deleteShopping({
                    id:  this.shoppingCartData[i].shoppingId
                }).then(res => {
                    console.log(res);
                    if (res.status_code === 1) {
                        this.$message({
                            message: '已移除购物车！',
                            type: 'success'
                        });
                        this.isShopping= false;
                    } else {
                        this.$message.error(res.msg)
                    }
                }).catch(e => {
                })
        },
        buyShoppingCart(i){
            if(this.shoppingCartData[i].status===1){
            this.buyButton(i);
            this.deleteButton(i);        
            }else{
                this.notice(i);
            }       
        },
        deleteShoppingCart(i){
            this.deleteButton(i);
            location.reload();  
            this.$message({
            type: 'success',
            message: '移除成功!'
          });       
        },
        toDetails(i) {
            this.$router.push({ path: '/details', query: { id: this.shoppingCartData[i].id } });
        },
        notice(i) {
        this.$confirm('该商品已经下架，将会将商品移除购物车, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteShoppingCart(i);
          this.$message({
            type: 'success',
            message: '移除成功!'
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消移除'
          });          
        });
      }
    }
}
</script>
    
<style scoped>
.container {
    min-height: 106vh;
    padding: 0 20px;
}

.container-title {
    font-size: 20px;
    padding: 20px 0;
    font-weight: 600;
}
</style>
    