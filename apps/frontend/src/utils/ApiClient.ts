import axios, { AxiosError, AxiosInstance } from "axios";
import { createStandaloneToast } from "@chakra-ui/react";
import { ErrorDTO } from "@mid/shared";

// Toast 설정
const { toast } = createStandaloneToast();

const apiClient: AxiosInstance = axios.create({
  baseURL: "/api", // Next.js 리버스 프록시를 통해 처리
  timeout: 5000,
});

// 인터셉터 설정 함수
apiClient.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

apiClient.interceptors.response.use(
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

export default apiClient;
