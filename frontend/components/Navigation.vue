<template>
  <nav>
    <ul class="flex space-x-4">
      <li v-for="item in menu" :key="item.name">
        <NuxtLink :to="item.path" class="hover:underline">
          {{ item.name }}
        </NuxtLink>
      </li>
    </ul>
  </nav>
</template>

<script setup lang="ts">
import { defineProps } from 'vue'
import type { MenuItem } from '../types/menu';

// Props 정의
const props = defineProps<{
  menu: MenuItem[]
}>()
</script>

<style scoped>
/* 네비게이션 스타일링 */
nav {
  /* 필요에 따라 추가 스타일링 */
}
</style>