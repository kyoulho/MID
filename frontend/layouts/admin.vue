<template>
  <div class="min-h-screen flex flex-col">
    <!-- Header -->
    <header class="flex justify-center border-b border-gray-300 p-4 shadow-sm">
      <Navigation :menu="adminMenu" />
    </header>

    <!-- Main Content -->
    <main class="flex-grow px-16 w-full">
      <NuxtPage />
    </main>

    <!-- Footer -->
    <footer class="bg-gray-200 text-center p-4">
      © My Investment Diary - Admin
    </footer>
  </div>
</template>

<script setup lang="ts">
import Navigation from '@/components/Navigation.vue'
import type { MenuItem } from '@/types/menu'

// 관리자용 메뉴 항목 정의
const adminMenu: MenuItem[] = [
  { name: '홈', path: '/' },
  { name: '전략 관리', path: '/admin/strategy' },
  { name: '사용자 관리', path: '/admin/users' },
]
</script>

<style scoped>
/* 추가적인 스타일이 필요하면 여기에 작성 */
</style>