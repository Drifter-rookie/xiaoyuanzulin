<template>
    <div class="main-container">
        <div class="main-content">
            <slot></slot>
        </div>
    </div>
</template>

<script>
export default {
    name: "PageBody"
}
</script>

<style scoped>
.main-container {
    margin-top: 90px;
    display: flex;
}

.main-content {
    width: 1400px;
    height: 100%;
    background-color: #ffffff;
    min-height: 120vh;
    background-size: cover;
}
</style>