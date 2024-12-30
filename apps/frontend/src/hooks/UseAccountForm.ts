import { ChangeEvent, useState } from "react";
import { AccountType } from "@mid/shared";

export interface AccountFormState {
  institution: string;
  type: AccountType | null;
  name: string;
  number: string;
}

export const useAccountForm = (
  initialState: AccountFormState,
): {
  formState: AccountFormState;
  handleChange: (
    field: keyof AccountFormState,
  ) => (e: ChangeEvent<HTMLInputElement>) => void;
  handleSelectChange: (e: ChangeEvent<HTMLSelectElement>) => void;
  isFormValid: boolean;
} => {
  const [formState, setFormState] = useState<AccountFormState>(initialState);

  // 입력 필드 변경 핸들러
  const handleChange =
    (field: keyof AccountFormState) =>
    (e: ChangeEvent<HTMLInputElement>): void => {
      setFormState((prev) => ({
        ...prev,
        [field]: e.target.value,
      }));
    };

  // Select 필드 변경 핸들러
  const handleSelectChange = (e: ChangeEvent<HTMLSelectElement>): void => {
    setFormState((prev) => ({
      ...prev,
      type: e.target.value as AccountType,
    }));
  };

  // 유효성 검사
  const isFormValid =
    !!formState.institution.trim() &&
    !!formState.name.trim() &&
    !!formState.number.trim() &&
    !!formState.type;

  return {
    formState,
    handleChange,
    handleSelectChange,
    isFormValid,
  };
};
