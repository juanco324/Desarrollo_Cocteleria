<template>
  <div class="page-login">
    <div class="card">

      <!-- ══ LEFT PANEL ══ -->
      <div class="left">
        <div class="logo">
          <div class="logo-icon">
            <svg width="18" height="18" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24">
              <polygon points="12 2 22 8.5 22 15.5 12 22 2 15.5 2 8.5"/>
            </svg>
          </div>
          <div class="logo-text">
            <h2>COCTEL</h2>
            <span>Cocteleria</span>
          </div>
        </div>

        <div class="signal">
          <svg width="14" height="14" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24">
            <polyline points="22 12 18 12 15 21 9 3 6 12 2 12"/>
          </svg>
        </div>
        <div class="bolt">
          <svg width="13" height="13" fill="currentColor" viewBox="0 0 24 24">
            <polygon points="13 2 3 14 12 14 11 22 21 10 12 10 13 2"/>
          </svg>
        </div>
        <div class="frame-icon">
          <svg width="15" height="15" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24">
            <rect x="3" y="3" width="18" height="18" rx="2"/><circle cx="12" cy="12" r="4"/>
          </svg>
        </div>

        <div class="illustration">
          <svg class="glass-stem" viewBox="0 0 120 160" fill="none" xmlns="http://www.w3.org/2000/svg">
            <circle cx="60" cy="72" r="54" stroke="rgba(0,188,212,0.15)" stroke-width="1.5" stroke-dasharray="4 4"/>
            <circle cx="60" cy="72" r="42" stroke="rgba(0,188,212,0.12)" stroke-width="1"/>
            <path d="M32 28 Q32 72 60 85 Q88 72 88 28 Z" stroke="#00bcd4" stroke-width="2" fill="rgba(0,188,212,0.08)"/>
            <line x1="60" y1="85" x2="60" y2="130" stroke="#00bcd4" stroke-width="2"/>
            <line x1="42" y1="130" x2="78" y2="130" stroke="#00bcd4" stroke-width="2" stroke-linecap="round"/>
            <path d="M40 40 Q42 60 46 70" stroke="rgba(255,255,255,0.5)" stroke-width="1.5" stroke-linecap="round"/>
          </svg>
        </div>
        <div class="platform"></div>

        <div class="env-info">
          <div class="env-label">Environment</div>
          <div class="env-value">STABILIZED / 22°C</div>
        </div>

        <div class="secure-badge">
          <svg width="12" height="12" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24">
            <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"/>
          </svg>
          Zona segura
        </div>
      </div>

      <!-- ══ RIGHT PANEL ══ -->
      <div class="right">
        <div class="status-bar">
          <div class="sys-online">
            <div class="dot"></div>
            Coctel Online
          </div>
          <div class="node-id">Bienvenido usuari@</div>
        </div>

        <div class="auth-tabs">
          <button class="auth-tab" :class="{ active: tab === 'login' }" @click="tab = 'login'">Iniciar Sesión</button>
          <button class="auth-tab" :class="{ active: tab === 'register' }" @click="tab = 'register'">Registrarse</button>
        </div>

        <!-- ── LOGIN ── -->
        <div v-if="tab === 'login'">
          <h1 class="heading">Bienvenido al <span>Portal</span></h1>
          <p class="subtitle">Digita tus credenciales para iniciar sesión</p>

          <div class="input-group">
            <span class="icon">
              <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="1.8" viewBox="0 0 24 24">
                <rect x="2" y="4" width="20" height="16" rx="2"/><polyline points="2,4 12,13 22,4"/>
              </svg>
            </span>
            <input type="email" placeholder="Usuario / Email" v-model="login.email" @keyup.enter="doLogin">
          </div>
          <div v-if="loginError.email" class="error-msg">{{ loginError.email }}</div>

          <div class="input-group">
            <span class="icon">
              <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="1.8" viewBox="0 0 24 24">
                <rect x="3" y="11" width="18" height="11" rx="2"/><path d="M7 11V7a5 5 0 0 1 10 0v4"/>
              </svg>
            </span>
            <input :type="showLoginPwd ? 'text' : 'password'" placeholder="Contraseña" v-model="login.password" @keyup.enter="doLogin">
            <span class="icon-right" @click="showLoginPwd = !showLoginPwd">
              <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="1.8" viewBox="0 0 24 24">
                <path v-if="showLoginPwd" d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/>
                <circle v-if="showLoginPwd" cx="12" cy="12" r="3"/>
                <path v-if="!showLoginPwd" d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94"/>
                <path v-if="!showLoginPwd" d="M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19"/>
                <line v-if="!showLoginPwd" x1="1" y1="1" x2="23" y2="23"/>
              </svg>
            </span>
          </div>
          <div v-if="loginError.password" class="error-msg">{{ loginError.password }}</div>
          <div v-if="loginError.general"  class="error-msg">{{ loginError.general }}</div>

          <div class="actions-row">
            <button class="scan-btn">
              <svg width="14" height="14" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24">
                <rect x="3" y="3" width="5" height="5"/><rect x="16" y="3" width="5" height="5"/>
                <rect x="3" y="16" width="5" height="5"/>
                <path d="M21 16h-3v3m0-3v3h-3M16 21v-3M21 21v-1"/>
              </svg>
              Escanear QR
            </button>
            <button class="forgot-btn">¿Olvidaste tu contraseña?</button>
          </div>

          <button class="auth-btn" @click="doLogin" :disabled="loading">
            <svg width="15" height="15" fill="none" stroke="currentColor" stroke-width="2.5" viewBox="0 0 24 24">
              <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"/>
              <polyline points="9 12 11 14 15 10"/>
            </svg>
            {{ loading ? 'Verificando...' : 'Verificar e Iniciar' }}
            <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="2.5" viewBox="0 0 24 24">
              <line x1="5" y1="12" x2="19" y2="12"/><polyline points="12 5 19 12 12 19"/>
            </svg>
          </button>
        </div>

        <!-- ── REGISTRO ── -->
        <div v-else>
          <h1 class="heading">Crear <span>Cuenta</span></h1>
          <p class="subtitle">Completa el formulario para registrarte en el sistema</p>

          <div class="input-group">
            <span class="icon">
              <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="1.8" viewBox="0 0 24 24">
                <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/>
              </svg>
            </span>
            <input type="text" placeholder="Nombre completo" v-model="register.nombre">
          </div>
          <div v-if="registerError.nombre" class="error-msg">{{ registerError.nombre }}</div>

          <div class="input-group">
            <span class="icon">
              <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="1.8" viewBox="0 0 24 24">
                <rect x="2" y="4" width="20" height="16" rx="2"/><polyline points="2,4 12,13 22,4"/>
              </svg>
            </span>
            <input type="email" placeholder="Email" v-model="register.email">
          </div>
          <div v-if="registerError.email" class="error-msg">{{ registerError.email }}</div>

          <div class="input-group">
            <span class="icon">
              <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="1.8" viewBox="0 0 24 24">
                <rect x="3" y="11" width="18" height="11" rx="2"/><path d="M7 11V7a5 5 0 0 1 10 0v4"/>
              </svg>
            </span>
            <input :type="showRegPwd ? 'text' : 'password'" placeholder="Contraseña" v-model="register.password">
            <span class="icon-right" @click="showRegPwd = !showRegPwd">
              <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="1.8" viewBox="0 0 24 24">
                <path v-if="showRegPwd" d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/>
                <circle v-if="showRegPwd" cx="12" cy="12" r="3"/>
                <path v-if="!showRegPwd" d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94"/>
                <path v-if="!showRegPwd" d="M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19"/>
                <line v-if="!showRegPwd" x1="1" y1="1" x2="23" y2="23"/>
              </svg>
            </span>
          </div>
          <div v-if="registerError.password" class="error-msg">{{ registerError.password }}</div>
          <div v-if="registerError.general"  class="error-msg">{{ registerError.general }}</div>

          <button class="auth-btn" @click="doRegister" :disabled="loading" style="margin-top:16px">
            <svg width="15" height="15" fill="none" stroke="currentColor" stroke-width="2.5" viewBox="0 0 24 24">
              <path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2"/>
              <circle cx="9" cy="7" r="4"/>
              <line x1="19" y1="8" x2="19" y2="14"/><line x1="22" y1="11" x2="16" y2="11"/>
            </svg>
            {{ loading ? 'Registrando...' : 'Crear Cuenta' }}
            <svg width="16" height="16" fill="none" stroke="currentColor" stroke-width="2.5" viewBox="0 0 24 24">
              <line x1="5" y1="12" x2="19" y2="12"/><polyline points="12 5 19 12 12 19"/>
            </svg>
          </button>
        </div>

      </div><!-- /right -->

      <!-- Toast -->
      <div class="card-toast" :class="{ show: toast.visible }">{{ toast.message }}</div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'

// ⚙️ Cambia esta URL por la IP de tu backend si no están en la misma máquina
const API_URL = 'http://localhost:8080/auth'

const emit = defineEmits(['navigate'])

const tab          = ref('login')
const loading      = ref(false)
const showLoginPwd = ref(false)
const showRegPwd   = ref(false)

const login    = reactive({ email: '', password: '' })
const register = reactive({ nombre: '', email: '', password: '' })

const loginError    = reactive({ email: '', password: '', general: '' })
const registerError = reactive({ nombre: '', email: '', password: '', general: '' })
const toast         = reactive({ visible: false, message: '' })

function showToast(msg) {
  toast.message = msg
  toast.visible = true
  setTimeout(() => { toast.visible = false }, 3000)
}

async function doLogin() {
  loginError.email = ''; loginError.password = ''; loginError.general = ''
  let valid = true
  if (!login.email) { loginError.email = 'El email es requerido.'; valid = false }
  else if (!/\S+@\S+\.\S+/.test(login.email)) { loginError.email = 'Email inválido.'; valid = false }
  if (!login.password) { loginError.password = 'La contraseña es requerida.'; valid = false }
  if (!valid) return

  loading.value = true
  try {
    const res = await fetch(`${API_URL}/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      // Campos que espera el backend Spring Boot
      body: JSON.stringify({ correo: login.email, contraseña: login.password })
    })
    if (res.ok) {
      const data = await res.json()
      localStorage.setItem('coctel_session', JSON.stringify(data))
      showToast('Inicio de sesión exitoso 🎉')
      setTimeout(() => emit('navigate', 'dashboard'), 1200)
    } else {
      loginError.general = 'Credenciales incorrectas. Verifica tu email y contraseña.'
    }
  } catch {
    loginError.general = 'No se pudo conectar al servidor. Intenta más tarde.'
  } finally {
    loading.value = false
  }
}

async function doRegister() {
  registerError.nombre = ''; registerError.email = ''; registerError.password = ''; registerError.general = ''
  let valid = true
  if (!register.nombre.trim()) { registerError.nombre = 'El nombre es requerido.'; valid = false }
  if (!register.email) { registerError.email = 'El email es requerido.'; valid = false }
  else if (!/\S+@\S+\.\S+/.test(register.email)) { registerError.email = 'Email inválido.'; valid = false }
  if (!register.password || register.password.length < 6) { registerError.password = 'Mínimo 6 caracteres.'; valid = false }
  if (!valid) return

  loading.value = true
  try {
    const res = await fetch(`${API_URL}/register`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      // Campos que espera el backend Spring Boot
      body: JSON.stringify({
        nombre:     register.nombre,
        apellido:   '',
        correo:     register.email,
        contraseña: register.password
      })
    })
    if (res.ok) {
      showToast('Cuenta creada exitosamente ✅')
      tab.value = 'login'
    } else {
      const msg = await res.text()
      registerError.general = msg || 'No se pudo crear la cuenta. Intenta de nuevo.'
    }
  } catch {
    registerError.general = 'No se pudo conectar al servidor. Intenta más tarde.'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Syne:wght@400;600;700&family=DM+Sans:wght@300;400;500&display=swap');

.page-login {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #e8f4f8;
  padding: 20px;
}

.card {
  width: 900px;
  max-width: 100%;
  min-height: 520px;
  background: #fff;
  border-radius: 20px;
  box-shadow: 0 20px 60px rgba(0,100,130,0.12), 0 4px 16px rgba(0,188,212,0.08);
  display: flex;
  overflow: hidden;
  position: relative;
}

.left {
  width: 42%;
  background: linear-gradient(150deg, #dff2f8 0%, #c8eaf4 50%, #d8f0f8 100%);
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 36px 32px 28px;
  overflow: hidden;
}
.left::before {
  content: '';
  position: absolute;
  width: 340px; height: 340px;
  border-radius: 50%;
  border: 1.5px dashed rgba(0,188,212,0.3);
  top: 50%; left: 50%;
  transform: translate(-50%, -38%);
}
.left::after {
  content: '';
  position: absolute;
  width: 260px; height: 260px;
  border-radius: 50%;
  border: 1.5px dashed rgba(0,188,212,0.2);
  top: 50%; left: 50%;
  transform: translate(-50%, -38%);
}

.logo { display: flex; align-items: center; gap: 12px; position: relative; z-index: 2; }
.logo-icon {
  width: 40px; height: 40px;
  border: 2px solid #00bcd4;
  border-radius: 8px;
  display: flex; align-items: center; justify-content: center;
  color: #00bcd4;
}
.logo-text h2 {
  font-family: 'Syne', sans-serif;
  font-size: 15px; font-weight: 700;
  letter-spacing: 0.12em; color: #1a2a35;
}
.logo-text span {
  display: block; font-size: 9px;
  letter-spacing: 0.22em; color: #00bcd4;
  font-weight: 500; margin-top: 1px;
}

.illustration {
  position: absolute; bottom: 60px; left: 50%;
  transform: translateX(-50%); width: 200px; z-index: 2;
}
.glass-stem { animation: float 4s ease-in-out infinite; }
@keyframes float {
  0%, 100% { transform: translateY(0); }
  50%       { transform: translateY(-8px); }
}
.platform {
  position: absolute; bottom: 50px; left: 50%;
  transform: translateX(-50%); width: 180px; height: 12px;
  background: radial-gradient(ellipse, rgba(0,188,212,0.4) 0%, transparent 70%);
  z-index: 1;
}

.signal { position: absolute; top: 48%; right: 60px; z-index: 3; color: #00bcd4; opacity: 0.6; }
.bolt   { position: absolute; bottom: 35%; left: 58px; z-index: 3; color: #00bcd4; opacity: 0.5; }
.frame-icon { position: absolute; top: 44%; left: 52px; z-index: 3; color: #00bcd4; opacity: 0.45; }

.env-info { position: relative; z-index: 2; }
.env-label { font-size: 9px; letter-spacing: 0.2em; color: #8faab5; text-transform: uppercase; margin-bottom: 3px; }
.env-value { font-size: 12px; color: #5a7080; font-weight: 500; }
.secure-badge {
  position: absolute; bottom: 28px; right: 28px;
  display: flex; align-items: center; gap: 6px;
  font-size: 9px; letter-spacing: 0.18em;
  text-transform: uppercase; z-index: 2; color: #00bcd4;
}

.right {
  width: 58%;
  padding: 44px 48px 40px;
  display: flex; flex-direction: column; justify-content: center;
  background: #fff;
}

.auth-tabs {
  display: flex; gap: 0; margin-bottom: 28px;
  border-bottom: 2px solid rgba(0,188,212,0.2);
}
.auth-tab {
  padding: 8px 20px 10px; font-size: 13px; font-weight: 600;
  color: #8faab5; cursor: pointer; border: none; background: none;
  font-family: 'DM Sans', sans-serif; position: relative;
  transition: color 0.2s;
}
.auth-tab.active { color: #00bcd4; }
.auth-tab.active::after {
  content: ''; position: absolute; bottom: -2px; left: 0; right: 0;
  height: 2px; background: #00bcd4; border-radius: 2px;
}

.status-bar {
  display: flex; align-items: center;
  justify-content: space-between; margin-bottom: 20px;
}
.sys-online {
  display: flex; align-items: center; gap: 7px;
  font-size: 10px; letter-spacing: 0.18em;
  color: #00bcd4; font-weight: 600; text-transform: uppercase;
}
.dot {
  width: 8px; height: 8px; border-radius: 50%;
  background: #00bcd4; box-shadow: 0 0 0 3px rgba(0,188,212,0.2);
  animation: pulse 2s ease-in-out infinite;
}
@keyframes pulse {
  0%, 100% { box-shadow: 0 0 0 3px rgba(0,188,212,0.2); }
  50%       { box-shadow: 0 0 0 6px rgba(0,188,212,0.08); }
}
.node-id { font-size: 10px; letter-spacing: 0.12em; color: #8faab5; font-weight: 500; }

.heading {
  font-family: 'Syne', sans-serif; font-size: 34px;
  font-weight: 400; color: #1a2a35;
  line-height: 1.1; margin-bottom: 8px;
}
.heading span { color: #00bcd4; font-weight: 700; }
.subtitle {
  font-size: 13px; color: #5a7080; line-height: 1.55;
  margin-bottom: 28px; max-width: 280px;
}

.input-group { position: relative; margin-bottom: 18px; }
.input-group .icon {
  position: absolute; left: 0; top: 50%;
  transform: translateY(-50%); color: #8faab5;
}
.input-group .icon-right {
  position: absolute; right: 0; top: 50%;
  transform: translateY(-50%); color: #8faab5;
  cursor: pointer; transition: color 0.2s;
}
.input-group .icon-right:hover { color: #00bcd4; }
.input-group input {
  width: 100%; border: none;
  border-bottom: 1.5px solid rgba(0,188,212,0.2);
  background: transparent;
  padding: 10px 30px 10px 28px;
  font-size: 14px; font-family: 'DM Sans', sans-serif;
  color: #1a2a35; outline: none; transition: border-color 0.25s;
}
.input-group input::placeholder { color: #8faab5; }
.input-group input:focus { border-bottom-color: #00bcd4; }

.error-msg {
  font-size: 11px; color: #e53e3e; margin-top: -12px;
  margin-bottom: 10px; padding-left: 4px;
}

.actions-row {
  display: flex; justify-content: space-between;
  align-items: center; margin-bottom: 26px; margin-top: 4px;
}
.scan-btn {
  display: flex; align-items: center; gap: 7px;
  font-size: 10px; letter-spacing: 0.16em; color: #00bcd4;
  cursor: pointer; text-transform: uppercase; font-weight: 600;
  border: none; background: none; transition: opacity 0.2s;
}
.scan-btn:hover { opacity: 0.7; }
.forgot-btn {
  font-size: 10px; letter-spacing: 0.14em; color: #8faab5;
  cursor: pointer; text-transform: uppercase; font-weight: 500;
  border: none; background: none; transition: color 0.2s;
}
.forgot-btn:hover { color: #00bcd4; }

.auth-btn {
  width: 100%; padding: 18px 24px;
  background: #00bcd4; border: none; border-radius: 10px;
  color: #fff; font-size: 12px; font-family: 'Syne', sans-serif;
  font-weight: 700; letter-spacing: 0.22em; text-transform: uppercase;
  cursor: pointer; display: flex; align-items: center;
  justify-content: center; gap: 10px;
  transition: background 0.25s, box-shadow 0.25s, transform 0.15s;
  box-shadow: 0 6px 24px rgba(0,188,212,0.35);
}
.auth-btn:hover {
  background: #00acc1;
  box-shadow: 0 8px 32px rgba(0,188,212,0.45);
  transform: translateY(-1px);
}
.auth-btn:disabled { opacity: 0.6; cursor: not-allowed; transform: none; }

.card-toast {
  position: absolute; bottom: 20px; left: 50%; transform: translateX(-50%);
  background: #1a202c; color: white; padding: 10px 18px;
  border-radius: 8px; font-size: 12px; font-weight: 500;
  white-space: nowrap; opacity: 0; transition: opacity 0.3s;
  pointer-events: none; z-index: 10;
}
.card-toast.show { opacity: 1; }
</style>
