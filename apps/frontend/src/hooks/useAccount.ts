import { useCallback, useState } from "react";
import {
  CreateAccountDTO,
  GetAccountDTO,
  UpdateAccountDTO,
  UUID,
} from "@mid/shared";
import apiClient from "@/utils/ApiClient";

export interface UseAccountReturn {
  accounts: GetAccountDTO[];
  isLoading: boolean;
  fetchAccounts: () => Promise<void>;
  createAccount: (newAccount: CreateAccountDTO) => Promise<void>;
  updateAccount: (id: UUID, updatedAccount: UpdateAccountDTO) => Promise<void>;
  deleteAccount: (id: UUID) => Promise<void>;
}

export const useAccounts = (): UseAccountReturn => {
  const [accounts, setAccounts] = useState<GetAccountDTO[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(false);

  const fetchAccounts = useCallback(async () => {
    setIsLoading(true);
    try {
      const response = await apiClient.get<GetAccountDTO[]>("/accounts");
      setAccounts(response.data);
    } finally {
      setIsLoading(false);
    }
  }, []);

  const createAccount = useCallback(
    async (newAccount: CreateAccountDTO): Promise<void> => {
      await apiClient.post("/accounts", newAccount);
      await fetchAccounts();
    },
    [fetchAccounts],
  );

  const updateAccount = useCallback(
    async (id: UUID, updatedAccount: UpdateAccountDTO): Promise<void> => {
      await apiClient.patch(`/accounts/${id}`, updatedAccount);
      await fetchAccounts();
    },
    [fetchAccounts],
  );

  const deleteAccount = useCallback(
    async (id: UUID): Promise<void> => {
      await apiClient.delete(`/accounts/${id}`);
      await fetchAccounts();
    },
    [fetchAccounts],
  );

  return {
    accounts,
    isLoading,
    fetchAccounts,
    createAccount,
    updateAccount,
    deleteAccount,
  };
};
