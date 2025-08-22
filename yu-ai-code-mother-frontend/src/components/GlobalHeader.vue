<template>
  <a-layout-header class="header">
    <div class="header-container">
      <!-- 左侧：Logo和标题 -->
      <RouterLink to="/" class="header-left">
        <div class="logo-container">
          <img class="logo" src="@/assets/logo.png" alt="Logo" />
          <div class="logo-glow"></div>
        </div>
        <h1 class="site-title">AI 应用生成平台</h1>
      </RouterLink>
      
      <!-- 中间：导航菜单 -->
      <nav class="nav-menu">
        <a-menu
          v-model:selectedKeys="selectedKeys"
          mode="horizontal"
          :items="menuItems"
          @click="handleMenuClick"
          class="custom-menu"
        />
      </nav>
      
      <!-- 右侧：用户操作区域 -->
      <div class="user-section">
        <div v-if="loginUserStore.loginUser.id" class="user-info">
          <div class="user-avatar-wrapper">
            <a-avatar 
              :src="loginUserStore.loginUser.userAvatar" 
              :size="40"
              class="user-avatar"
            />
            <span class="user-name">{{ loginUserStore.loginUser.userName ?? '用户' }}</span>
          </div>
          <a-button type="text" @click="doLogout" class="logout-btn">
            <LogoutOutlined />
            退出登录
          </a-button>
        </div>
        <div v-else class="auth-buttons">
          <a-button type="text" href="/user/login" class="login-btn">
            登录
          </a-button>
          <a-button type="primary" href="/user/register" class="register-btn">
            注册
          </a-button>
        </div>
      </div>
    </div>
  </a-layout-header>
</template>

<script setup lang="ts">
import { computed, h, ref } from 'vue'
import { useRouter } from 'vue-router'
import { type MenuProps, message } from 'ant-design-vue'
import { useLoginUserStore } from '@/stores/loginUser.ts'
import { userLogout } from '@/api/userController.ts'
import { LogoutOutlined, HomeOutlined } from '@ant-design/icons-vue'

const loginUserStore = useLoginUserStore()
const router = useRouter()

// 当前选中菜单
const selectedKeys = ref<string[]>(['/'])

// 监听路由变化，更新当前选中菜单
router.afterEach((to, from, next) => {
  selectedKeys.value = [to.path]
})

// 菜单配置项
const originItems = [
  {
    key: '/',
    icon: () => h(HomeOutlined),
    label: '主页',
    title: '主页',
  },
  {
    key: '/admin/userManage',
    label: '用户管理',
    title: '用户管理',
  },
  {
    key: '/admin/appManage',
    label: '应用管理',
    title: '应用管理',
  },
  {
    key: 'github',
    label: 'Github',
    title: 'Github',
    onClick: () => {
      window.open('https://github.com/Inorysekiro2333', '_blank')
    }
  },
]

// 过滤菜单项
const filterMenus = (menus = [] as MenuProps['items']) => {
  return menus?.filter((menu) => {
    const menuKey = menu?.key as string
    if (menuKey?.startsWith('/admin')) {
      const loginUser = loginUserStore.loginUser
      if (!loginUser || loginUser.userRole !== 'admin') {
        return false
      }
    }
    return true
  })
}

// 展示在菜单的路由数组
const menuItems = computed<MenuProps['items']>(() => filterMenus(originItems))

// 处理菜单点击
const handleMenuClick: MenuProps['onClick'] = (e) => {
  const key = e.key as string
  selectedKeys.value = [key]
  // 跳转到对应页面
  if (key.startsWith('/')) {
    router.push(key)
  }
  // 处理特殊菜单项
  if (key === 'github') {
    window.open('https://github.com/Inorysekiro2333', '_blank')
  }
}

// 退出登录
const doLogout = async () => {
  const res = await userLogout()
  if (res.data.code === 0) {
    loginUserStore.setLoginUser({
      userName: '未登录',
    })
    message.success('退出登录成功')
    await router.push('/user/login')
  } else {
    message.error('退出登录失败，' + res.data.message)
  }
}
</script>

<style scoped>
.header {
  background: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(20px);
  border-bottom: 1px solid rgba(255, 255, 255, 0.2);
  padding: 0;
  position: sticky;
  top: 0;
  z-index: 1000;
  box-shadow: var(--shadow-sm);
}

.header-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 var(--spacing-lg);
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 64px;
}

.header-left {
  display: flex;
  align-items: center;
  gap: var(--spacing-md);
  text-decoration: none;
  color: inherit;
  transition: all 0.3s ease;
}

.header-left:hover {
  transform: translateY(-1px);
}

.logo-container {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
}

.logo {
  height: 40px;
  width: 40px;
  border-radius: var(--radius-lg);
  transition: all 0.3s ease;
}

.logo-glow {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: radial-gradient(circle, rgba(99, 102, 241, 0.2) 0%, transparent 70%);
  border-radius: var(--radius-lg);
  opacity: 0;
  transition: opacity 0.3s ease;
}

.header-left:hover .logo-glow {
  opacity: 1;
}

.site-title {
  margin: 0;
  font-size: 20px;
  font-weight: 700;
  background: linear-gradient(135deg, var(--primary-color) 0%, var(--info-color) 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  letter-spacing: -0.5px;
}

.nav-menu {
  flex: 1;
  display: flex;
  justify-content: center;
}

.custom-menu {
  background: transparent;
  border: none;
  font-weight: 500;
}

.custom-menu :deep(.ant-menu-item) {
  border-radius: var(--radius-lg);
  margin: 0 var(--spacing-xs);
  transition: all 0.3s ease;
}

.custom-menu :deep(.ant-menu-item:hover) {
  background: rgba(99, 102, 241, 0.1);
  color: var(--primary-color);
}

.custom-menu :deep(.ant-menu-item-selected) {
  background: var(--primary-color);
  color: white;
}

.custom-menu :deep(.ant-menu-item-selected:hover) {
  background: var(--primary-hover);
}

.user-section {
  display: flex;
  align-items: center;
  gap: var(--spacing-md);
}

.user-info {
  display: flex;
  align-items: center;
  gap: var(--spacing-md);
}

/* .user-avatar-wrapper {
  display: flex;
  align-items: center;
  gap: var(--spacing-sm);
  padding: var(--spacing-sm) var(--spacing-md);
  border-radius: var(--radius-xl);
  background: rgba(255, 255, 255, 0.6);
  border: 1px solid rgba(255, 255, 255, 0.3);
  transition: all 0.3s ease;
} */

.user-avatar {
  border: 2px solid rgba(255, 255, 255, 0.8);
}

.user-name {
  font-weight: 500;
  color: var(--text-primary);
  font-size: 14px;
}

.logout-btn {
  color: var(--error-color);
  border: none;
  background: transparent;
  padding: var(--spacing-sm) var(--spacing-md);
  border-radius: var(--radius-lg);
  height: 36px;
  font-size: 14px;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: var(--spacing-xs);
}

.logout-btn:hover {
  background: rgba(239, 68, 68, 0.1);
  color: var(--error-color);
}



.auth-buttons {
  display: flex;
  gap: var(--spacing-sm);
}

.login-btn {
  color: var(--text-secondary);
  border: 1px solid var(--border-color);
  border-radius: var(--radius-lg);
  height: 36px;
  padding: 0 var(--spacing-lg);
  transition: all 0.3s ease;
}

.login-btn:hover {
  color: var(--primary-color);
  border-color: var(--primary-color);
  background: rgba(99, 102, 241, 0.05);
}

.register-btn {
  background: var(--primary-color);
  border: 1px solid var(--primary-color);
  border-radius: var(--radius-lg);
  height: 36px;
  padding: 0 var(--spacing-lg);
  transition: all 0.3s ease;
}

.register-btn:hover {
  background: var(--primary-hover);
  border-color: var(--primary-hover);
  transform: translateY(-1px);
  box-shadow: var(--shadow-md);
}

/* 响应式设计 */
@media (max-width: 1024px) {
  .header-container {
    padding: 0 var(--spacing-md);
  }
  
  .site-title {
    font-size: 18px;
  }
}

@media (max-width: 768px) {
  .header-container {
    padding: 0 var(--spacing-sm);
  }
  
  .site-title {
    display: none;
  }
  
  .nav-menu {
    display: none;
  }
  
  .user-name {
    display: none;
  }
  
  .auth-buttons {
    gap: var(--spacing-xs);
  }
  
  .login-btn,
  .register-btn {
    padding: 0 var(--spacing-md);
    font-size: 14px;
  }
}

@media (max-width: 480px) {
  .header-container {
    height: 56px;
  }
  
  .logo {
    height: 32px;
    width: 32px;
  }
  
  .auth-buttons {
    flex-direction: column;
    gap: var(--spacing-xs);
  }
  
  .login-btn,
  .register-btn {
    height: 32px;
    font-size: 13px;
  }
}
</style>
