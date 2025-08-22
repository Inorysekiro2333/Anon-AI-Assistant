<template>
  <a-layout-footer class="footer">
    <div class="footer-container">
      <div class="footer-content">
        <div class="footer-section">
          <h3 class="footer-title">AI 应用生成平台</h3>
          <p class="footer-description">
            让每个人都能轻松创建专业的网站应用，无需编程知识
          </p>
        </div>
        
        <div class="footer-section">
          <h4 class="footer-subtitle">快速开始</h4>
          <ul class="footer-links">
            <li><RouterLink to="/">创建应用</RouterLink></li>
            <li><RouterLink to="/user/register">注册账号</RouterLink></li>
            <li><a href="https://github.com/Inorysekiro2333" target="_blank" rel="noopener noreferrer">查看源码</a></li>
          </ul>
        </div>
        
        <div class="footer-section">
          <h4 class="footer-subtitle">支持</h4>
          <ul class="footer-links">
            <li><a href="#" @click.prevent>使用文档</a></li>
            <li><a href="#" @click.prevent>常见问题</a></li>
            <li><a href="#" @click.prevent>联系我们</a></li>
          </ul>
        </div>
        
        <div class="footer-section">
          <h4 class="footer-subtitle">关注我们</h4>
          <div class="social-links">
            <a href="https://github.com/Inorysekiro2333" target="_blank" rel="noopener noreferrer" class="social-link">
              <span class="social-icon">🐙</span>
              GitHub
            </a>
          </div>
        </div>
      </div>
      
      <div class="footer-bottom">
        <div class="footer-divider"></div>
        <p class="copyright">
          © 2024 AI 应用生成平台. 
          <a
            href="https://github.com/Inorysekiro2333"
            target="_blank"
            rel="noopener noreferrer"
            class="author-link"
          >
            由程序员爱音开发
          </a>
        </p>
      </div>
    </div>
  </a-layout-footer>
</template>

<script setup lang="ts">
// 无需额外的响应式数据
</script>

<style scoped>
.footer {
  background: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(20px);
  border-top: 1px solid rgba(255, 255, 255, 0.2);
  margin-top: var(--spacing-2xl);
  position: relative;
}

.footer::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 1px;
  background: linear-gradient(90deg, transparent, rgba(99, 102, 241, 0.3), transparent);
}

.footer-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: var(--spacing-2xl) var(--spacing-lg);
}

.footer-content {
  display: grid;
  grid-template-columns: 2fr 1fr 1fr 1fr;
  gap: var(--spacing-2xl);
  margin-bottom: var(--spacing-2xl);
}

.footer-section {
  display: flex;
  flex-direction: column;
}

.footer-title {
  font-size: 20px;
  font-weight: 700;
  margin: 0 0 var(--spacing-md);
  background: linear-gradient(135deg, var(--primary-color) 0%, var(--info-color) 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.footer-description {
  color: var(--text-secondary);
  line-height: 1.6;
  margin: 0;
  font-size: 14px;
}

.footer-subtitle {
  font-size: 16px;
  font-weight: 600;
  margin: 0 0 var(--spacing-md);
  color: var(--text-primary);
}

.footer-links {
  list-style: none;
  padding: 0;
  margin: 0;
}

.footer-links li {
  margin-bottom: var(--spacing-sm);
}

.footer-links a {
  color: var(--text-secondary);
  text-decoration: none;
  font-size: 14px;
  transition: all 0.3s ease;
  display: inline-block;
}

.footer-links a:hover {
  color: var(--primary-color);
  transform: translateX(4px);
}

.social-links {
  display: flex;
  flex-direction: column;
  gap: var(--spacing-sm);
}

.social-link {
  display: flex;
  align-items: center;
  gap: var(--spacing-sm);
  color: var(--text-secondary);
  text-decoration: none;
  font-size: 14px;
  padding: var(--spacing-sm);
  border-radius: var(--radius-lg);
  transition: all 0.3s ease;
}

.social-link:hover {
  color: var(--primary-color);
  background: rgba(99, 102, 241, 0.1);
  transform: translateY(-2px);
}

.social-icon {
  font-size: 18px;
}

.footer-bottom {
  text-align: center;
}

.footer-divider {
  height: 1px;
  background: linear-gradient(90deg, transparent, var(--border-color), transparent);
  margin-bottom: var(--spacing-lg);
}

.copyright {
  margin: 0;
  color: var(--text-tertiary);
  font-size: 14px;
  line-height: 1.6;
}

.author-link {
  color: var(--primary-color);
  text-decoration: none;
  font-weight: 500;
  transition: color 0.3s ease;
}

.author-link:hover {
  color: var(--primary-hover);
  text-decoration: underline;
}

/* 响应式设计 */
@media (max-width: 1024px) {
  .footer-content {
    grid-template-columns: 1fr 1fr;
    gap: var(--spacing-xl);
  }
  
  .footer-section:first-child {
    grid-column: 1 / -1;
  }
}

@media (max-width: 768px) {
  .footer-container {
    padding: var(--spacing-xl) var(--spacing-md);
  }
  
  .footer-content {
    grid-template-columns: 1fr;
    gap: var(--spacing-lg);
    text-align: center;
  }
  
  .footer-title {
    font-size: 18px;
  }
  
  .footer-subtitle {
    font-size: 15px;
  }
  
  .social-links {
    align-items: center;
  }
}

@media (max-width: 480px) {
  .footer-container {
    padding: var(--spacing-lg) var(--spacing-sm);
  }
  
  .footer-content {
    gap: var(--spacing-md);
  }
  
  .footer-title {
    font-size: 16px;
  }
  
  .footer-description {
    font-size: 13px;
  }
}
</style>
