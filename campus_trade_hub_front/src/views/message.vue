<template>
    <div>
        <app-head></app-head>
        <app-body style="justify-content: center;">
            <div class="message-container">
                <div class="message-container-title">我的留言消息</div>
                <div v-for="(mes,index) in meslist" class="message-container-list" @click="toDetails(mes.idle.id,mes.id,mes.status)" :key="index">
                    <div class="message-container-list-left">
                        <el-image
                                style="width: 55px; height: 55px;border-radius: 5px;"
                                :src="mes.fromU.avatar"
                                fit="cover"></el-image>
                        <div class="message-container-list-text">
                            <div class="message-nickname">{{mes.fromU.nickname}}</div>
                            <div class="message-content">{{mes.content}}</div>
                            <div class="message-time">{{mes.createTime}}</div>
                        </div>
                    </div>
                    <div v-if="mes.status ===1" class="message-container-list-right">
                        <el-image
                                style="width:130px; height: 90px;"
                                :src="mes.idle.imgUrl"
                                fit="contain"></el-image>
                    </div>
                    <div v-else class="message-container-list-right1">
                        <el-image
                                style="width:130px; height: 90px;"
                                :src="mes.idle.imgUrl"
                                fit="contain"></el-image>
                    </div>
                    <el-badge v-if="mes.status ===1" value="new" class="item"></el-badge>
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
        data(){
            return{
                meslist:[],
                myid:0,
            };
        },
        created(){
            this.get_my_message();
        },
        mounted() {
        var pubsub = this.goeasy.constructor.pubsub;
        pubsub.subscribe({
            channel: 'addmessage',
            onMessage: (message) => {
                // 更新组件内部状
                let data=parseInt(message.content,10);
                
                if(this.myid===data)
                {
                    this.get_my_message();
            }
                
            },
        });
    },
        methods:{
            get_my_message()
            {
                this.$api.getAllMyMessage().then(res=>{
                if(res.status_code===1){
                    for(let i=0;i<res.data.length;i++){
                        let imgList=JSON.parse(res.data[i].idle.pictureList);
                        res.data[i].idle.imgUrl=imgList?imgList[0]:'';
                        if(res.data[i].content!==null)
                        {let contentList=res.data[i].content.split('<br>');
                        let contenHtml=contentList[0];
                        for(let i=1;i<contentList.length;i++){
                            contenHtml+='  '+contentList[i];
                        }
                        res.data[i].content=contenHtml;}
                        else
                        {let contentList='[图片]';
                        let contenHtml=contentList[0];
                        for(let i=1;i<contentList.length;i++){
                            contenHtml+='  '+contentList[i];
                        }
                        res.data[i].content=contenHtml;}
                    }
                    this.meslist=res.data;
                    this.myid=this.meslist[0].toUser;
                }
            })
            },
            toDetails(id,index,status){
                this.change(index);
                if(status===1)
                {this.change1();}
                this.$router.push({path: '/details',query:{id:id}});
            },
            change(index)
            {
                this.$api.update({
                 id:index,
                })
            },
            change1()
            {
                this.$api.updateMessageStatus1({
                id: this.myid
            })
            }

        }
    }
</script>

<style scoped>
    .message-container{
        min-height: 106vh;
        padding: 0 20px;
    }
    .message-container-title{
        font-size: 16px;
        padding: 20px 0;
        font-weight: 600;
    }
    .message-container-list{
        cursor:pointer;
        height: 110px;
        border-top: 1px solid #eeeeee;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }
    .message-container-list-left{
        width: 1300px;
        display: flex;
    }
    .message-container-list-right{
        width: 90px;
    }
    .message-container-list-right1{
        width: 90px;
        margin-right:35px;
    }
    .message-container-list-text{
        margin-left: 10px;
    }
    .message-nickname{
        font-weight: 600;
        font-size: 18px;
        padding-bottom: 5px;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }
    .message-content{
        font-size: 16px;
        padding-bottom: 15px;
        color: #555555;
        width: 710px;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }
    .message-time{
        font-size: 13px;
        color: #555555;
    }
    .item {
    top: -30px; 
}

</style>