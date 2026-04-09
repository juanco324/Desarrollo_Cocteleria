<template>
  <component :is="currentView" @navigate="navigate" />
</template>

<script setup>
import { ref, shallowRef } from 'vue'
import LoginView from './views/LoginView.vue'
import DashboardView from './views/DashboardView.vue'

const views = {
  login: LoginView,
  dashboard: DashboardView,
}

const currentView = shallowRef(LoginView)

function navigate(route) {
  if (views[route]) {
    currentView.value = views[route]
  }
}
</script>
