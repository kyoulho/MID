import { create } from "zustand";
import $axios from "@/utils/internalAxios";
import {
  CreateAccountDTO,
  GetAccountDTO,
  UpdateAccountDTO,
  UUID,
} from "@mid/shared";

interface AccountState {
  accounts: GetAccountDTO[];
  isLoading: boolean;
  fetchAccounts: () => Promise<void>;
  createAccount: (newAccount: CreateAccountDTO) => Promise<void>;
  updateAccount: (id: UUID, updatedAccount: UpdateAccountDTO) => Promise<void>;
  deleteAccount: (id: UUID) => Promise<void>;
}

export const useAccountStore = create<AccountState>((set, get) => ({
  accountsForm: {},
  accounts: [],
  isLoading: false,

  fetchAccounts: async (): Promise<void> => {
    set({ isLoading: true });
    try {
      const response = await $axios.get<GetAccountDTO[]>("/accounts").finally();
      set({ accounts: response.data });
    } finally {
      set({ isLoading: false });
    }
  },

  createAccount: async (newAccount: CreateAccountDTO): Promise<void> => {
    await $axios.post("/accounts", newAccount);
    await get().fetchAccounts();
  },

  updateAccount: async (
    id: UUID,
    updatedAccount: UpdateAccountDTO,
  ): Promise<void> => {
    await $axios.put(`/accounts/${id}`, updatedAccount);
    await get().fetchAccounts();
  },

  deleteAccount: async (id: UUID): Promise<void> => {
    await $axios.delete(`/accounts/${id}`);
    await get().fetchAccounts();
  },
}));
