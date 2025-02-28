// utils/internalAxios.ts
import axios, { AxiosError, AxiosInstance } from "axios";
import { createStandaloneToast } from "@chakra-ui/react";
import { ErrorDTO } from "@mid/shared";

const { toast } = createStandaloneToast();

const internalAxios: AxiosInstance = axios.create({
  baseURL: "/api",
  responseType: "json",
  timeout: 5000,
  validateStatus(status) {
    return [200].includes(status);
  },
});

// 내부 API 전용 인터셉터 (필요한 경우)
internalAxios.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// 내부 API 전역 인터셉터 (기본 에러 처리: onError 콜백이 없을 경우)
internalAxios.interceptors.response.use(
  (response) => response,
  (error: AxiosError<ErrorDTO>) => {
    const status = error.response?.status || "상태 없음";
    const errorData = error.response?.data;
    toast({
      title: errorData?.message || "알 수 없는 예외 발생",
      description: `상태 코드: ${status}`,
      colorScheme: "red",
      position: "top-right",
      status: "error",
      duration: 5000,
      isClosable: true,
    });
    return Promise.reject(error);
  },
);
export default internalAxios;
