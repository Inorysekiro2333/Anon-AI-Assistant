<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { message } from 'ant-design-vue'
import { useLoginUserStore } from '@/stores/loginUser'
import { addApp, listMyAppVoByPage, listGoodAppVoByPage } from '@/api/appController'
import { getDeployUrl } from '@/config/env'
import AppCard from '@/components/AppCard.vue'

const router = useRouter()
const loginUserStore = useLoginUserStore()

// 用户提示词
const userPrompt = ref('')
const creating = ref(false)

// 模板数据
const templates = [
  {
    icon: '📝',
    name: '个人博客',
    description: '现代化的个人博客网站，支持文章管理、分类标签和搜索功能',
    prompt: '创建一个现代化的个人博客网站，包含文章列表、详情页、分类标签、搜索功能、评论系统和个人简介页面。采用简洁的设计风格，支持响应式布局，文章支持Markdown格式，首页展示最新文章和热门推荐。'
  },
  {
    icon: '🏢',
    name: '企业官网',
    description: '专业的企业官网，展示公司信息、产品服务和团队介绍',
    prompt: '设计一个专业的企业官网，包含公司介绍、产品服务展示、新闻资讯、联系我们等页面。采用商务风格的设计，包含轮播图、产品展示卡片、团队介绍、客户案例展示，支持多语言切换和在线客服功能。'
  },
  {
    icon: '🛒',
    name: '在线商城',
    description: '功能完整的电商网站，支持商品展示、购物车和订单管理',
    prompt: '构建一个功能完整的在线商城，包含商品展示、购物车、用户注册登录、订单管理、支付结算等功能。设计现代化的商品卡片布局，支持商品搜索筛选、用户评价、优惠券系统和会员积分功能。'
  },
  {
    icon: '🎨',
    name: '作品展示',
    description: '精美的作品展示网站，适合设计师和艺术家展示作品',
    prompt: '制作一个精美的作品展示网站，适合设计师、摄影师、艺术家等创作者。包含作品画廊、项目详情页、个人简历、联系方式等模块。采用瀑布流或网格布局展示作品，支持图片放大预览和作品分类筛选。'
  }
]

// 我的应用数据
const myApps = ref<API.AppVO[]>([])
const myAppsPage = reactive({
  current: 1,
  pageSize: 6,
  total: 0,
})

// 精选应用数据
const featuredApps = ref<API.AppVO[]>([])
const featuredAppsPage = reactive({
  current: 1,
  pageSize: 6,
  total: 0,
})

// 设置提示词
const setPrompt = (prompt: string) => {
  userPrompt.value = prompt
}

// 创建应用
const createApp = async () => {
  if (!userPrompt.value.trim()) {
    message.warning('请输入应用描述')
    return
  }

  if (!loginUserStore.loginUser.id) {
    message.warning('请先登录')
    await router.push('/user/login')
    return
  }

  creating.value = true
  try {
    const res = await addApp({
      initPrompt: userPrompt.value.trim(),
    })

    if (res.data.code === 0 && res.data.data) {
      message.success('应用创建成功')
      // 跳转到对话页面，确保ID是字符串类型
      const appId = String(res.data.data)
      await router.push(`/app/chat/${appId}`)
    } else {
      message.error('创建失败：' + res.data.message)
    }
  } catch (error) {
    console.error('创建应用失败：', error)
    message.error('创建失败，请重试')
  } finally {
    creating.value = false
  }
}

// 加载我的应用
const loadMyApps = async () => {
  if (!loginUserStore.loginUser.id) {
    return
  }

  try {
    const res = await listMyAppVoByPage({
      pageNum: myAppsPage.current,
      pageSize: myAppsPage.pageSize,
      sortField: 'createTime',
      sortOrder: 'desc',
    })

    if (res.data.code === 0 && res.data.data) {
      myApps.value = res.data.data.records || []
      myAppsPage.total = res.data.data.totalRow || 0
    }
  } catch (error) {
    console.error('加载我的应用失败：', error)
  }
}

// 加载精选应用
const loadFeaturedApps = async () => {
  try {
    const res = await listGoodAppVoByPage({
      pageNum: featuredAppsPage.current,
      pageSize: featuredAppsPage.pageSize,
      sortField: 'createTime',
      sortOrder: 'desc',
    })

    if (res.data.code === 0 && res.data.data) {
      featuredApps.value = res.data.data.records || []
      featuredAppsPage.total = res.data.data.totalRow || 0
    }
  } catch (error) {
    console.error('加载精选应用失败：', error)
  }
}

// 查看对话
const viewChat = (appId: string | number | undefined) => {
  if (appId) {
    router.push(`/app/chat/${appId}?view=1`)
  }
}

// 查看作品
const viewWork = (app: API.AppVO) => {
  if (app.deployKey) {
    const url = getDeployUrl(app.deployKey)
    window.open(url, '_blank')
  }
}

// 页面加载时获取数据
onMounted(() => {
  loadMyApps()
  loadFeaturedApps()
})
</script>

<template>
  <div id="homePage">
    <!-- 英雄区域 -->
    <section class="hero-section">
      <div class="hero-container">
        <div class="hero-content">
          <h1 class="hero-title animate-fade-in-up">
            AI 驱动，一键生成
            <span class="gradient-text">专业网站</span>
          </h1>
          <p class="hero-description animate-fade-in-up">
            无需编程知识，用自然语言描述你的需求，AI 将为你生成完整的网站应用
          </p>
          
          <!-- 主要输入区域 -->
          <div class="main-input-section animate-fade-in-up">
            <div class="input-wrapper">
              <a-textarea
                v-model:value="userPrompt"
                placeholder="描述你想要的网站，例如：创建一个现代化的个人博客，包含文章列表、分类标签和搜索功能..."
                :rows="4"
                :maxlength="1000"
                class="main-input"
                :disabled="creating"
              />
              <div class="input-actions">
                <a-button 
                  type="primary" 
                  size="large" 
                  @click="createApp" 
                  :loading="creating"
                  class="create-btn"
                >
                  <template #icon>
                    <span class="btn-icon">🚀</span>
                  </template>
                  {{ creating ? '生成中...' : '开始生成' }}
                </a-button>
              </div>
            </div>
          </div>
        </div>
        
        <!-- 装饰元素 -->
        <div class="hero-decoration">
          <div class="floating-card card-1">
            <div class="card-icon">🎨</div>
            <span>设计美观</span>
          </div>
          <div class="floating-card card-2">
            <div class="card-icon">⚡</div>
            <span>快速生成</span>
          </div>
          <div class="floating-card card-3">
            <div class="card-icon">🔧</div>
            <span>功能完整</span>
          </div>
        </div>
      </div>
    </section>

    <!-- 快捷模板区域 -->
    <section class="templates-section">
      <div class="container">
        <h2 class="section-title">热门模板</h2>
        <div class="templates-grid">
          <button
            v-for="(template, index) in templates"
            :key="index"
            class="template-card hover-lift"
            @click="setPrompt(template.prompt)"
          >
            <div class="template-icon">{{ template.icon }}</div>
            <h3 class="template-name">{{ template.name }}</h3>
            <p class="template-desc">{{ template.description }}</p>
          </button>
        </div>
      </div>
    </section>

    <!-- 我的作品区域 -->
    <section v-if="loginUserStore.loginUser.id" class="my-works-section">
      <div class="container">
        <h2 class="section-title">我的作品</h2>
        <div v-if="myApps.length > 0" class="app-grid">
          <AppCard
            v-for="app in myApps"
            :key="app.id"
            :app="app"
            @view-chat="viewChat"
            @view-work="viewWork"
          />
        </div>
        <div v-else class="empty-state">
          <div class="empty-icon">🎯</div>
          <h3>还没有作品</h3>
          <p>开始创建你的第一个 AI 应用吧！</p>
        </div>
        
        <div v-if="myApps.length > 0" class="pagination-wrapper">
          <a-pagination
            v-model:current="myAppsPage.current"
            v-model:page-size="myAppsPage.pageSize"
            :total="myAppsPage.total"
            :show-size-changer="false"
            :show-total="(total: number) => `共 ${total} 个应用`"
            @change="loadMyApps"
          />
        </div>
      </div>
    </section>

    <!-- 精选案例区域 -->
    <section class="featured-section">
      <div class="container">
        <h2 class="section-title">精选案例</h2>
        <div v-if="featuredApps.length > 0" class="featured-grid">
          <AppCard
            v-for="app in featuredApps"
            :key="app.id"
            :app="app"
            :featured="true"
            @view-chat="viewChat"
            @view-work="viewWork"
          />
        </div>
        <div v-else class="loading-state">
          <a-spin size="large" />
          <p>加载中...</p>
        </div>
        
        <div v-if="featuredApps.length > 0" class="pagination-wrapper">
          <a-pagination
            v-model:current="featuredAppsPage.current"
            v-model:page-size="featuredAppsPage.pageSize"
            :total="featuredAppsPage.total"
            :show-size-changer="false"
            :show-total="(total: number) => `共 ${total} 个案例`"
            @change="loadFeaturedApps"
          />
        </div>
      </div>
    </section>
  </div>
</template>

<style scoped>
#homePage {
  min-height: 100vh;
  background: var(--bg-secondary);
}

/* 英雄区域 */
.hero-section {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: var(--spacing-2xl) 0;
  position: relative;
  overflow: hidden;
}

.hero-section::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: 
    radial-gradient(circle at 20% 80%, rgba(255, 255, 255, 0.1) 0%, transparent 50%),
    radial-gradient(circle at 80% 20%, rgba(255, 255, 255, 0.1) 0%, transparent 50%);
  pointer-events: none;
}

.hero-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 var(--spacing-lg);
  position: relative;
  z-index: 2;
}

.hero-content {
  text-align: center;
  max-width: 800px;
  margin: 0 auto;
  position: relative;
  z-index: 2;
}

.hero-title {
  font-size: clamp(2.5rem, 5vw, 4rem);
  font-weight: 800;
  margin: 0 0 var(--spacing-lg);
  color: white;
  line-height: 1.2;
  letter-spacing: -0.02em;
}

.hero-description {
  font-size: clamp(1.1rem, 2vw, 1.25rem);
  margin: 0 0 var(--spacing-2xl);
  color: rgba(255, 255, 255, 0.9);
  line-height: 1.6;
}

/* 主要输入区域 */
.main-input-section {
  margin-bottom: var(--spacing-2xl);
  position: relative;
  z-index: 2;
}

.input-wrapper {
  position: relative;
  max-width: 700px;
  margin: 0 auto;
}

.main-input {
  border: none;
  border-radius: var(--radius-2xl);
  font-size: 16px;
  padding: var(--spacing-lg) var(--spacing-xl);
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(20px);
  box-shadow: var(--shadow-xl);
  resize: none;
  transition: all 0.3s ease;
}

.main-input:focus {
  background: rgba(255, 255, 255, 1);
  box-shadow: 0 25px 50px rgba(0, 0, 0, 0.25);
  transform: translateY(-4px);
}

.input-actions {
  position: absolute;
  bottom: var(--spacing-md);
  right: var(--spacing-md);
}

.create-btn {
  height: 48px;
  padding: 0 var(--spacing-xl);
  border-radius: var(--radius-xl);
  font-weight: 600;
  font-size: 16px;
  background: linear-gradient(135deg, var(--success-color) 0%, #059669 100%);
  border: none;
  box-shadow: var(--shadow-lg);
  transition: all 0.3s ease;
}

.create-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 20px 40px rgba(16, 185, 129, 0.3);
}

.btn-icon {
  font-size: 18px;
  margin-right: var(--spacing-sm);
}

/* 装饰元素 */
.hero-decoration {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  pointer-events: none;
  z-index: 1;
}

.floating-card {
  position: absolute;
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: var(--radius-xl);
  padding: var(--spacing-md);
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: var(--spacing-sm);
  color: white;
  font-size: 14px;
  font-weight: 500;
  animation: float 6s ease-in-out infinite;
}

.card-icon {
  font-size: 24px;
}

.card-1 {
  top: 15%;
  left: 5%;
  animation-delay: 0s;
}

.card-2 {
  top: 50%;
  right: 5%;
  animation-delay: 2s;
}

.card-3 {
  bottom: 15%;
  left: 5%;
  animation-delay: 4s;
}

@keyframes float {
  0%, 100% {
    transform: translateY(0px);
  }
  50% {
    transform: translateY(-20px);
  }
}

/* 模板区域 */
.templates-section {
  padding: var(--spacing-2xl) 0;
  background: var(--bg-primary);
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 var(--spacing-lg);
}

.section-title {
  font-size: clamp(1.75rem, 3vw, 2.5rem);
  font-weight: 700;
  text-align: center;
  margin: 0 0 var(--spacing-2xl);
  color: var(--text-primary);
}

.templates-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: var(--spacing-lg);
}

.template-card {
  background: var(--bg-primary);
  border: 1px solid var(--border-light);
  border-radius: var(--radius-xl);
  padding: var(--spacing-xl);
  text-align: center;
  cursor: pointer;
  transition: all 0.3s ease;
  background: linear-gradient(135deg, var(--bg-primary) 0%, var(--bg-secondary) 100%);
}

.template-card:hover {
  border-color: var(--primary-color);
  background: linear-gradient(135deg, var(--bg-primary) 0%, var(--primary-light) 100%);
}

.template-icon {
  font-size: 48px;
  margin-bottom: var(--spacing-md);
}

.template-name {
  font-size: 18px;
  font-weight: 600;
  margin: 0 0 var(--spacing-sm);
  color: var(--text-primary);
}

.template-desc {
  font-size: 14px;
  color: var(--text-secondary);
  margin: 0;
  line-height: 1.5;
}

/* 我的作品区域 */
.my-works-section {
  padding: var(--spacing-2xl) 0;
  background: var(--bg-secondary);
}

.app-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
  gap: var(--spacing-lg);
  margin-bottom: var(--spacing-xl);
}

.empty-state {
  text-align: center;
  padding: var(--spacing-2xl);
  color: var(--text-secondary);
}

.empty-icon {
  font-size: 64px;
  margin-bottom: var(--spacing-md);
}

.empty-state h3 {
  font-size: 20px;
  font-weight: 600;
  margin: 0 0 var(--spacing-sm);
  color: var(--text-primary);
}

.empty-state p {
  margin: 0;
  font-size: 16px;
}

/* 精选案例区域 */
.featured-section {
  padding: var(--spacing-2xl) 0;
  background: var(--bg-primary);
}

.featured-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
  gap: var(--spacing-lg);
  margin-bottom: var(--spacing-xl);
}

.loading-state {
  text-align: center;
  padding: var(--spacing-2xl);
  color: var(--text-secondary);
}

.loading-state p {
  margin-top: var(--spacing-md);
  font-size: 16px;
}

/* 分页 */
.pagination-wrapper {
  display: flex;
  justify-content: center;
  margin-top: var(--spacing-xl);
}

/* 响应式设计 */
@media (max-width: 768px) {
  .hero-section {
    padding: var(--spacing-xl) 0;
  }
  
  .hero-container {
    padding: 0 var(--spacing-md);
  }
  
  .main-input {
    padding: var(--spacing-md);
  }
  
  .input-actions {
    position: static;
    margin-top: var(--spacing-md);
    text-align: center;
  }
  
  .create-btn {
    width: 100%;
    height: 44px;
  }
  
  .floating-card {
    display: none;
  }
  
  .hero-content {
    z-index: 1;
  }
  
  .main-input-section {
    z-index: 1;
  }
  
  .templates-grid {
    grid-template-columns: 1fr;
  }
  
  .app-grid,
  .featured-grid {
    grid-template-columns: 1fr;
  }
  
  .container {
    padding: 0 var(--spacing-md);
  }
}

@media (max-width: 480px) {
  .hero-title {
    font-size: 2rem;
  }
  
  .hero-description {
    font-size: 1rem;
  }
  
  .main-input {
    font-size: 14px;
    padding: var(--spacing-md);
  }
  
  .template-card {
    padding: var(--spacing-lg);
  }
  
  .template-icon {
    font-size: 36px;
  }
}
</style>
