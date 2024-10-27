// nuxt.config.ts
export default defineNuxtConfig({
  devtools: { enabled: true },
  css: ["@/assets/tailwind.css"],
  postcss: {
    plugins: {
      tailwindcss: {},
      autoprefixer: {},
    },
  },
  routeRules: {
    "/": { redirect: "/user/dashboard" },
  },
});
