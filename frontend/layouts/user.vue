<template>
  <div class="min-h-screen flex flex-col">
    <!-- Header -->
    <header class="flex justify-center border-b border-gray-300 p-4 shadow-sm">
        <Navigation :menu="userMenu" />
    </header>

    <!-- Main Content -->
    <main class="flex-grow mx-auto px-4 md:px-8 lg:px-16 w-full max-w-7xl">
      <NuxtPage />
    </main>

    <!-- Footer -->
    <footer class="bg-gray-200 text-center p-4">
      © My Investment Diary
    </footer>
  </div>
</template>

<script setup lang="ts">
import Navigation from '@/components/Navigation.vue'
import type { MenuItem } from '@/types/menu'

// 유저용 메뉴 항목 정의
const userMenu: MenuItem[] = [
  { name: '계좌 관리', path: '/user/account' },
]
</script>

<style scoped>
/* 추가적인 스타일이 필요하면 여기에 작성 */
</style>