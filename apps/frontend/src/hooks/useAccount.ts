// src/hooks/useAccounts.ts
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

  // Fetch all accounts
  const fetchAccounts = useCallback(async () => {
    setIsLoading(true);
    try {
      const response =
        await apiClient.midApi.get<GetAccountDTO[]>("/api/accounts");
      setAccounts(response.data);
    } finally {
      setIsLoading(false);
    }
  }, []);

  // Create a new account
  const createAccount = useCallback(
    async (newAccount: CreateAccountDTO): Promise<void> => {
      await apiClient.midApi.post("/api/accounts", newAccount);
      await fetchAccounts();
    },
    [fetchAccounts],
  );

  // Update an account
  const updateAccount = useCallback(
    async (id: UUID, updatedAccount: UpdateAccountDTO): Promise<void> => {
      await apiClient.midApi.put(`/api/accounts/${id}`, updatedAccount);
      await fetchAccounts();
    },
    [fetchAccounts],
  );

  // Delete an account
  const deleteAccount = useCallback(
    async (id: UUID): Promise<void> => {
      await apiClient.midApi.delete(`/api/accounts/${id}`);
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
