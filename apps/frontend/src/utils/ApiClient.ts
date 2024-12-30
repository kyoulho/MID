import axios, { AxiosError, AxiosInstance } from "axios";
import { createStandaloneToast } from "@chakra-ui/react";
import { ErrorDTO } from "@mid/shared";

// Toast 설정
const { toast } = createStandaloneToast();

// 인터셉터 설정 함수
const setupInterceptors = (client: AxiosInstance): void => {
  client.interceptors.request.use((config) => {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  });

  client.interceptors.response.use(
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
};

// 클라이언트 생성 함수
const createApiClient = (baseURL: string): AxiosInstance => {
  const client = axios.create({ baseURL });
  setupInterceptors(client);
  return client;
};

// 클라이언트 생성
export default {
  midApi: createApiClient("http://localhost:8080"),
  host2: createApiClient("https://host2.example.com"),
};
