<template>
  <div class="app-card" :class="{ 'app-card--featured': featured }">
    <div class="app-preview">
      <img v-if="app.cover" :src="app.cover" :alt="app.appName" class="app-cover" />
      <div v-else class="app-placeholder">
        <div class="placeholder-icon">🤖</div>
        <span class="placeholder-text">AI 生成</span>
      </div>
      
      <!-- 特色标签 -->
      <div v-if="featured" class="featured-badge">
        <span class="badge-icon">⭐</span>
        <span>精选</span>
      </div>
      
      <!-- 悬停操作区域 -->
      <div class="app-overlay">
        <div class="overlay-content">
          <div class="overlay-actions">
            <a-button type="primary" @click="handleViewChat" class="action-btn">
              <template #icon>
                <span class="btn-icon">💬</span>
              </template>
              查看对话
            </a-button>
            <a-button 
              v-if="app.deployKey" 
              type="default" 
              @click="handleViewWork" 
              class="action-btn secondary"
            >
              <template #icon>
                <span class="btn-icon">🌐</span>
              </template>
              查看作品
            </a-button>
          </div>
        </div>
      </div>
    </div>
    
    <div class="app-info">
      <div class="app-header">
        <div class="app-avatar">
          <a-avatar 
            :src="app.user?.userAvatar" 
            :size="40"
            class="user-avatar"
          >
            {{ app.user?.userName?.charAt(0) || 'U' }}
          </a-avatar>
        </div>
        <div class="app-details">
          <h3 class="app-title">{{ app.appName || '未命名应用' }}</h3>
          <p class="app-author">
            {{ app.user?.userName || (featured ? '官方推荐' : '未知用户') }}
          </p>
        </div>
      </div>
      
      <!-- 应用状态 -->
      <div class="app-status">
        <div class="status-item">
          <span class="status-icon">📅</span>
          <span class="status-text">已创建</span>
        </div>
        <div v-if="app.deployKey" class="status-item deployed">
          <span class="status-icon">🚀</span>
          <span class="status-text">已部署</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
interface Props {
  app: API.AppVO
  featured?: boolean
}

interface Emits {
  (e: 'view-chat', appId: string | number | undefined): void
  (e: 'view-work', app: API.AppVO): void
}

const props = withDefaults(defineProps<Props>(), {
  featured: false,
})

const emit = defineEmits<Emits>()

const handleViewChat = () => {
  emit('view-chat', props.app.id)
}

const handleViewWork = () => {
  emit('view-work', props.app)
}
</script>

<style scoped>
.app-card {
  background: var(--bg-primary);
  border-radius: var(--radius-xl);
  overflow: hidden;
  box-shadow: var(--shadow-md);
  border: 1px solid var(--border-light);
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  cursor: pointer;
  position: relative;
}

.app-card:hover {
  transform: translateY(-8px);
  box-shadow: var(--shadow-xl);
  border-color: var(--primary-color);
}

.app-card--featured {
  border-color: var(--warning-color);
  box-shadow: 0 0 0 1px var(--warning-color), var(--shadow-md);
}

.app-card--featured:hover {
  box-shadow: 0 0 0 1px var(--warning-color), var(--shadow-xl);
}

.app-preview {
  height: 200px;
  background: linear-gradient(135deg, var(--bg-secondary) 0%, var(--bg-tertiary) 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
  position: relative;
}

.app-cover {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s ease;
}

.app-card:hover .app-cover {
  transform: scale(1.05);
}

.app-placeholder {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: var(--spacing-sm);
  color: var(--text-tertiary);
}

.placeholder-icon {
  font-size: 48px;
  opacity: 0.6;
}

.placeholder-text {
  font-size: 14px;
  font-weight: 500;
  opacity: 0.8;
}

.featured-badge {
  position: absolute;
  top: var(--spacing-md);
  right: var(--spacing-md);
  background: linear-gradient(135deg, var(--warning-color) 0%, #f59e0b 100%);
  color: white;
  padding: var(--spacing-xs) var(--spacing-sm);
  border-radius: var(--radius-lg);
  font-size: 12px;
  font-weight: 600;
  display: flex;
  align-items: center;
  gap: var(--spacing-xs);
  box-shadow: var(--shadow-md);
}

.badge-icon {
  font-size: 14px;
}

.app-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.7);
  backdrop-filter: blur(4px);
  display: flex;
  align-items: center;
  justify-content: center;
  opacity: 0;
  transition: all 0.3s ease;
}

.app-card:hover .app-overlay {
  opacity: 1;
}

.overlay-content {
  text-align: center;
}

.overlay-actions {
  display: flex;
  flex-direction: column;
  gap: var(--spacing-sm);
  align-items: center;
}

.action-btn {
  border-radius: var(--radius-lg);
  height: 40px;
  padding: 0 var(--spacing-lg);
  font-weight: 500;
  transition: all 0.3s ease;
  border: none;
}

.action-btn:hover {
  transform: translateY(-2px);
  box-shadow: var(--shadow-lg);
}

.action-btn.secondary {
  background: rgba(255, 255, 255, 0.9);
  color: var(--text-primary);
  border: 1px solid rgba(255, 255, 255, 0.3);
}

.action-btn.secondary:hover {
  background: white;
  color: var(--primary-color);
}

.btn-icon {
  font-size: 16px;
  margin-right: var(--spacing-xs);
}

.app-info {
  padding: var(--spacing-lg);
}

.app-header {
  display: flex;
  align-items: center;
  gap: var(--spacing-md);
  margin-bottom: var(--spacing-md);
}

.app-avatar {
  flex-shrink: 0;
}

.user-avatar {
  border: 2px solid var(--border-light);
  transition: border-color 0.3s ease;
}

.app-card:hover .user-avatar {
  border-color: var(--primary-color);
}

.app-details {
  flex: 1;
  min-width: 0;
}

.app-title {
  font-size: 16px;
  font-weight: 600;
  margin: 0 0 var(--spacing-xs);
  color: var(--text-primary);
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  line-height: 1.4;
}

.app-author {
  font-size: 14px;
  color: var(--text-secondary);
  margin: 0;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.app-status {
  display: flex;
  gap: var(--spacing-md);
  padding-top: var(--spacing-md);
  border-top: 1px solid var(--border-light);
}

.status-item {
  display: flex;
  align-items: center;
  gap: var(--spacing-xs);
  font-size: 12px;
  color: var(--text-tertiary);
  padding: var(--spacing-xs) var(--spacing-sm);
  background: var(--bg-secondary);
  border-radius: var(--radius-md);
  transition: all 0.3s ease;
}

.status-item:hover {
  background: var(--bg-tertiary);
  color: var(--text-secondary);
}

.status-item.deployed {
  background: rgba(16, 185, 129, 0.1);
  color: var(--success-color);
}

.status-item.deployed:hover {
  background: rgba(16, 185, 129, 0.2);
}

.status-icon {
  font-size: 14px;
}

.status-text {
  font-weight: 500;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .app-preview {
    height: 160px;
  }
  
  .app-info {
    padding: var(--spacing-md);
  }
  
  .app-header {
    margin-bottom: var(--spacing-sm);
  }
  
  .app-status {
    flex-direction: column;
    gap: var(--spacing-sm);
  }
  
  .overlay-actions {
    gap: var(--spacing-xs);
  }
  
  .action-btn {
    height: 36px;
    padding: 0 var(--spacing-md);
    font-size: 14px;
  }
}

@media (max-width: 480px) {
  .app-preview {
    height: 140px;
  }
  
  .placeholder-icon {
    font-size: 36px;
  }
  
  .placeholder-text {
    font-size: 12px;
  }
  
  .featured-badge {
    top: var(--spacing-sm);
    right: var(--spacing-sm);
    padding: var(--spacing-xs);
    font-size: 11px;
  }
  
  .app-title {
    font-size: 15px;
  }
  
  .app-author {
    font-size: 13px;
  }
}
</style>
