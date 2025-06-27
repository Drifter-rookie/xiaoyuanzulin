<template>
  <div class="home">
    <router-view class="right" v-if="isRouterAlive"></router-view>
  </div>
</template>

<script>

export default {
  provide() {
    return {
      reload: this.reload
    }
  },
  data() {
    return {
      isRouterAlive: true
    };
  },
  mounted() {

  },
  methods: {
    reload() {
      this.isRouterAlive = false
      this.$nextTick(() => {
        this.isRouterAlive = true
      })
    }
  }
};
</script>

<style>
html,body,#app {
  zoom: 0.9;
  overflow: visible;
  background-color: #f6f6f6;
}
</style>