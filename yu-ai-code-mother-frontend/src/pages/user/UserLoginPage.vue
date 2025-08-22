<template>
  <div id="userLoginPage">
    <div class="login-container">
      <!-- 左侧装饰区域 -->
      <div class="login-decoration">
        <div class="decoration-content">
          <div class="decoration-icon">🚀</div>
          <h2 class="decoration-title">AI 应用生成平台</h2>
          <p class="decoration-subtitle">让每个人都能轻松创建专业的网站应用</p>
          <div class="decoration-features">
            <div class="feature-item">
              <span class="feature-icon">✨</span>
              <span>无需编程知识</span>
            </div>
            <div class="feature-item">
              <span class="feature-icon">⚡</span>
              <span>快速生成</span>
            </div>
            <div class="feature-item">
              <span class="feature-icon">🎨</span>
              <span>专业设计</span>
            </div>
          </div>
        </div>
      </div>
      
      <!-- 右侧登录表单 -->
      <div class="login-form-container">
        <div class="login-form-wrapper">
          <div class="form-header">
            <h1 class="form-title">欢迎回来</h1>
            <p class="form-subtitle">登录您的账户，开始创建 AI 应用</p>
          </div>
          
          <a-form 
            :model="formState" 
            name="login" 
            autocomplete="off" 
            @finish="handleSubmit"
            class="login-form"
            layout="vertical"
          >
            <a-form-item 
              name="userAccount" 
              :rules="[{ required: true, message: '请输入账号' }]"
              class="form-item"
            >
              <a-input 
                v-model:value="formState.userAccount" 
                placeholder="请输入账号"
                size="large"
                class="form-input"
              >
                <template #prefix>
                  <span class="input-icon">👤</span>
                </template>
              </a-input>
            </a-form-item>
            
            <a-form-item
              name="userPassword"
              :rules="[
                { required: true, message: '请输入密码' },
                { min: 8, message: '密码长度不能小于 8 位' },
              ]"
              class="form-item"
            >
              <a-input-password 
                v-model:value="formState.userPassword" 
                placeholder="请输入密码"
                size="large"
                class="form-input"
              >
                <template #prefix>
                  <span class="input-icon">🔒</span>
                </template>
              </a-input-password>
            </a-form-item>
            
            <div class="form-actions">
              <a-button 
                type="primary" 
                html-type="submit" 
                size="large"
                class="submit-btn"
                :loading="submitting"
              >
                {{ submitting ? '登录中...' : '登录' }}
              </a-button>
            </div>
          </a-form>
          
          <div class="form-footer">
            <p class="register-tip">
              还没有账号？
              <RouterLink to="/user/register" class="register-link">
                立即注册
              </RouterLink>
            </p>
          </div>
          
          <!-- 其他登录方式 -->
          <div class="other-login">
            <div class="divider">
              <span class="divider-text">或者</span>
            </div>
            <div class="social-login">
              <button class="social-btn github-btn">
                <span class="social-icon">🐙</span>
                <span>GitHub 登录</span>
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { reactive, ref } from 'vue'
import { userLogin } from '@/api/userController.ts'
import { useLoginUserStore } from '@/stores/loginUser.ts'
import { useRouter } from 'vue-router'
import { message } from 'ant-design-vue'

const formState = reactive<API.UserLoginRequest>({
  userAccount: '',
  userPassword: '',
})

const router = useRouter()
const loginUserStore = useLoginUserStore()
const submitting = ref(false)

/**
 * 提交表单
 * @param values
 */
const handleSubmit = async (values: any) => {
  submitting.value = true
  
  try {
    const res = await userLogin(values)
    // 登录成功，把登录态保存到全局状态中
    if (res.data.code === 0 && res.data.data) {
      await loginUserStore.fetchLoginUser()
      message.success('登录成功')
      router.push({
        path: '/',
        replace: true,
      })
    } else {
      message.error('登录失败，' + res.data.message)
    }
  } catch (error) {
    console.error('登录失败：', error)
    message.error('登录失败，请重试')
  } finally {
    submitting.value = false
  }
}
</script>

<style scoped>
#userLoginPage {
  min-height: 100vh;
  background: linear-gradient(135deg, var(--bg-secondary) 0%, var(--bg-tertiary) 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: var(--spacing-lg);
}

.login-container {
  width: 100%;
  max-width: 1000px;
  background: var(--bg-primary);
  border-radius: var(--radius-2xl);
  box-shadow: var(--shadow-xl);
  overflow: hidden;
  display: flex;
  min-height: 600px;
}

/* 左侧装饰区域 */
.login-decoration {
  flex: 1;
  background: linear-gradient(135deg, var(--primary-color) 0%, var(--info-color) 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: var(--spacing-2xl);
  position: relative;
  overflow: hidden;
}

.login-decoration::before {
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

.decoration-content {
  text-align: center;
  color: white;
  position: relative;
  z-index: 2;
}

.decoration-icon {
  font-size: 64px;
  margin-bottom: var(--spacing-lg);
  display: block;
}

.decoration-title {
  font-size: 28px;
  font-weight: 700;
  margin: 0 0 var(--spacing-md);
  line-height: 1.3;
}

.decoration-subtitle {
  font-size: 16px;
  margin: 0 0 var(--spacing-xl);
  opacity: 0.9;
  line-height: 1.6;
}

.decoration-features {
  display: flex;
  flex-direction: column;
  gap: var(--spacing-md);
}

.feature-item {
  display: flex;
  align-items: center;
  gap: var(--spacing-sm);
  font-size: 14px;
  opacity: 0.9;
}

.feature-icon {
  font-size: 18px;
}

/* 右侧登录表单 */
.login-form-container {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: var(--spacing-2xl);
}

.login-form-wrapper {
  width: 100%;
  max-width: 400px;
}

.form-header {
  text-align: center;
  margin-bottom: var(--spacing-2xl);
}

.form-title {
  font-size: 32px;
  font-weight: 700;
  margin: 0 0 var(--spacing-sm);
  color: var(--text-primary);
}

.form-subtitle {
  font-size: 16px;
  color: var(--text-secondary);
  margin: 0;
  line-height: 1.5;
}

.login-form {
  margin-bottom: var(--spacing-xl);
}

.form-item {
  margin-bottom: var(--spacing-lg);
}

.form-input {
  border-radius: var(--radius-lg);
  border: 1px solid var(--border-color);
  padding: var(--spacing-md) var(--spacing-lg);
  font-size: 16px;
  transition: all 0.3s ease;
  background: var(--bg-primary);
}

.form-input:focus,
.form-input:hover {
  border-color: var(--primary-color);
  box-shadow: 0 0 0 3px rgba(99, 102, 241, 0.1);
}

.input-icon {
  font-size: 16px;
  color: var(--text-tertiary);
}

.form-actions {
  margin-bottom: var(--spacing-lg);
}

.submit-btn {
  width: 100%;
  height: 48px;
  border-radius: var(--radius-lg);
  font-size: 16px;
  font-weight: 600;
  background: linear-gradient(135deg, var(--primary-color) 0%, var(--primary-hover) 100%);
  border: none;
  box-shadow: var(--shadow-md);
  transition: all 0.3s ease;
}

.submit-btn:hover {
  transform: translateY(-2px);
  box-shadow: var(--shadow-lg);
}

.form-footer {
  text-align: center;
  margin-bottom: var(--spacing-xl);
}

.register-tip {
  margin: 0;
  color: var(--text-secondary);
  font-size: 14px;
}

.register-link {
  color: var(--primary-color);
  text-decoration: none;
  font-weight: 600;
  transition: color 0.3s ease;
}

.register-link:hover {
  color: var(--primary-hover);
  text-decoration: underline;
}

/* 其他登录方式 */
.other-login {
  text-align: center;
}

.divider {
  position: relative;
  margin: var(--spacing-lg) 0;
}

.divider::before {
  content: '';
  position: absolute;
  top: 50%;
  left: 0;
  right: 0;
  height: 1px;
  background: var(--border-color);
}

.divider-text {
  background: var(--bg-primary);
  padding: 0 var(--spacing-md);
  color: var(--text-tertiary);
  font-size: 14px;
  position: relative;
  z-index: 1;
}

.social-login {
  margin-top: var(--spacing-lg);
}

.social-btn {
  width: 100%;
  height: 44px;
  border: 1px solid var(--border-color);
  border-radius: var(--radius-lg);
  background: var(--bg-primary);
  color: var(--text-primary);
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: var(--spacing-sm);
}

.social-btn:hover {
  border-color: var(--primary-color);
  background: rgba(99, 102, 241, 0.05);
  color: var(--primary-color);
}

.social-icon {
  font-size: 18px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .login-container {
    flex-direction: column;
    min-height: auto;
  }
  
  .login-decoration {
    padding: var(--spacing-xl);
    min-height: 300px;
  }
  
  .decoration-title {
    font-size: 24px;
  }
  
  .decoration-subtitle {
    font-size: 14px;
  }
  
  .login-form-container {
    padding: var(--spacing-xl);
  }
  
  .form-title {
    font-size: 28px;
  }
  
  .form-subtitle {
    font-size: 14px;
  }
}

@media (max-width: 480px) {
  #userLoginPage {
    padding: var(--spacing-sm);
  }
  
  .login-container {
    border-radius: var(--radius-xl);
  }
  
  .login-decoration {
    padding: var(--spacing-lg);
    min-height: 250px;
  }
  
  .decoration-icon {
    font-size: 48px;
  }
  
  .decoration-title {
    font-size: 20px;
  }
  
  .decoration-subtitle {
    font-size: 13px;
  }
  
  .login-form-container {
    padding: var(--spacing-lg);
  }
  
  .form-title {
    font-size: 24px;
  }
  
  .form-input {
    font-size: 14px;
    padding: var(--spacing-sm) var(--spacing-md);
  }
  
  .submit-btn {
    height: 44px;
    font-size: 15px;
  }
}
</style>
