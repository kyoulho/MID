import { CreateAccountDTO, UpdateAccountDTO } from "@mid/shared";

// useAccountAPI 반환 타입 정의
interface UseAccountAPIReturn {
  createAccount: (data: CreateAccountDTO) => Promise<void>;
  updateAccount: (id: string, data: UpdateAccountDTO) => Promise<void>;
}

export const useAccountAPI = (): UseAccountAPIReturn => {
  const createAccount = async (data: UpdateAccountDTO): Promise<void> => {
    const response = await fetch("/api/accounts", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data),
    });

    if (!response.ok) {
      throw new Error("계좌 생성 실패");
    }
  };

  const updateAccount = async (
    id: string,
    data: UpdateAccountDTO,
  ): Promise<void> => {
    const response = await fetch(`/api/accounts/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data),
    });

    if (!response.ok) {
      throw new Error("계좌 수정 실패");
    }
  };

  return { createAccount, updateAccount };
};
