<template>
  <a-layout class="basic-layout">
    <!-- 顶部导航栏 -->
    <GlobalHeader />
    <!-- 主要内容区域 -->
    <a-layout-content class="main-content">
      <router-view />
    </a-layout-content>
    <!-- 底部版权信息 -->
    <GlobalFooter />
  </a-layout>
</template>

<script setup lang="ts">
import GlobalHeader from '@/components/GlobalHeader.vue'
import GlobalFooter from '@/components/GlobalFooter.vue'
</script>

<style scoped>
.basic-layout {
  background: none;
  min-height: 100vh;
  position: relative;
}

.basic-layout::before {
  content: '';
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: 
    radial-gradient(circle at 20% 80%, rgba(99, 102, 241, 0.08) 0%, transparent 50%),
    radial-gradient(circle at 80% 20%, rgba(59, 130, 246, 0.06) 0%, transparent 50%),
    radial-gradient(circle at 40% 40%, rgba(16, 185, 129, 0.04) 0%, transparent 50%);
  pointer-events: none;
  z-index: -1;
}

.main-content {
  width: 100%;
  padding: 0;
  background: none;
  margin: 0;
  position: relative;
  z-index: 1;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .main-content {
    padding: 0;
  }
}
</style>
