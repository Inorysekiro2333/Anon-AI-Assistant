<template>
  <div id="userRegisterPage">
    <div class="register-container">
      <!-- 左侧装饰区域 -->
      <div class="register-decoration">
        <div class="decoration-content">
          <div class="decoration-icon">🎉</div>
          <h2 class="decoration-title">加入 AI 应用生成平台</h2>
          <p class="decoration-subtitle">开始您的创意之旅，让 AI 帮您实现想法</p>
          <div class="decoration-features">
            <div class="feature-item">
              <span class="feature-icon">🚀</span>
              <span>快速上手</span>
            </div>
            <div class="feature-item">
              <span class="feature-icon">💡</span>
              <span>创意无限</span>
            </div>
            <div class="feature-item">
              <span class="feature-icon">🌟</span>
              <span>专业品质</span>
            </div>
          </div>
        </div>
      </div>
      
      <!-- 右侧注册表单 -->
      <div class="register-form-container">
        <div class="register-form-wrapper">
          <div class="form-header">
            <h1 class="form-title">创建账户</h1>
            <p class="form-subtitle">注册新账户，开始创建您的 AI 应用</p>
          </div>
          
          <a-form 
            :model="formState" 
            name="register" 
            autocomplete="off" 
            @finish="handleSubmit"
            class="register-form"
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
            
            <a-form-item
              name="checkPassword"
              :rules="[
                { required: true, message: '请确认密码' },
                { min: 8, message: '密码长度不能小于 8 位' },
                { validator: validateCheckPassword },
              ]"
              class="form-item"
            >
              <a-input-password 
                v-model:value="formState.checkPassword" 
                placeholder="请确认密码"
                size="large"
                class="form-input"
              >
                <template #prefix>
                  <span class="input-icon">✅</span>
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
                {{ submitting ? '注册中...' : '创建账户' }}
              </a-button>
            </div>
          </a-form>
          
          <div class="form-footer">
            <p class="login-tip">
              已有账户？
              <RouterLink to="/user/login" class="login-link">
                立即登录
              </RouterLink>
            </p>
          </div>
          
          <!-- 注册协议 -->
          <div class="register-agreement">
            <p class="agreement-text">
              注册即表示您同意我们的
              <a href="#" class="agreement-link">服务条款</a>
              和
              <a href="#" class="agreement-link">隐私政策</a>
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { userRegister } from '@/api/userController.ts'
import { message } from 'ant-design-vue'
import { reactive, ref } from 'vue'

const router = useRouter()
const submitting = ref(false)

const formState = reactive<API.UserRegisterRequest>({
  userAccount: '',
  userPassword: '',
  checkPassword: '',
})

/**
 * 验证确认密码
 * @param rule
 * @param value
 * @param callback
 */
const validateCheckPassword = (rule: unknown, value: string, callback: (error?: Error) => void) => {
  if (value && value !== formState.userPassword) {
    callback(new Error('两次输入密码不一致'))
  } else {
    callback()
  }
}

/**
 * 提交表单
 * @param values
 */
const handleSubmit = async (values: API.UserRegisterRequest) => {
  submitting.value = true
  
  try {
    const res = await userRegister(values)
    // 注册成功，跳转到登录页面
    if (res.data.code === 0) {
      message.success('注册成功，请登录')
      router.push({
        path: '/user/login',
        replace: true,
      })
    } else {
      message.error('注册失败，' + res.data.message)
    }
  } catch (error) {
    console.error('注册失败：', error)
    message.error('注册失败，请重试')
  } finally {
    submitting.value = false
  }
}
</script>

<style scoped>
#userRegisterPage {
  min-height: 100vh;
  background: linear-gradient(135deg, var(--bg-secondary) 0%, var(--bg-tertiary) 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: var(--spacing-lg);
}

.register-container {
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
.register-decoration {
  flex: 1;
  background: linear-gradient(135deg, var(--success-color) 0%, #059669 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: var(--spacing-2xl);
  position: relative;
  overflow: hidden;
}

.register-decoration::before {
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

/* 右侧注册表单 */
.register-form-container {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: var(--spacing-2xl);
}

.register-form-wrapper {
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

.register-form {
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
  border-color: var(--success-color);
  box-shadow: 0 0 0 3px rgba(16, 185, 129, 0.1);
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
  background: linear-gradient(135deg, var(--success-color) 0%, #059669 100%);
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
  margin-bottom: var(--spacing-lg);
}

.login-tip {
  margin: 0;
  color: var(--text-secondary);
  font-size: 14px;
}

.login-link {
  color: var(--success-color);
  text-decoration: none;
  font-weight: 600;
  transition: color 0.3s ease;
}

.login-link:hover {
  color: #059669;
  text-decoration: underline;
}

/* 注册协议 */
.register-agreement {
  text-align: center;
  padding-top: var(--spacing-lg);
  border-top: 1px solid var(--border-light);
}

.agreement-text {
  margin: 0;
  color: var(--text-tertiary);
  font-size: 12px;
  line-height: 1.5;
}

.agreement-link {
  color: var(--success-color);
  text-decoration: none;
  transition: color 0.3s ease;
}

.agreement-link:hover {
  color: #059669;
  text-decoration: underline;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .register-container {
    flex-direction: column;
    min-height: auto;
  }
  
  .register-decoration {
    padding: var(--spacing-xl);
    min-height: 300px;
  }
  
  .decoration-title {
    font-size: 24px;
  }
  
  .decoration-subtitle {
    font-size: 14px;
  }
  
  .register-form-container {
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
  #userRegisterPage {
    padding: var(--spacing-sm);
  }
  
  .register-container {
    border-radius: var(--radius-xl);
  }
  
  .register-decoration {
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
  
  .register-form-container {
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
