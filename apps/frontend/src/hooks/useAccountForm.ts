import { ChangeEvent, useCallback, useState } from "react";
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
  changed: boolean;
} => {
  const [formState, setFormState] = useState<AccountFormState>(initialState);

  // 입력 필드 변경 핸들러
  const handleChange = useCallback(
    (field: keyof AccountFormState) =>
      (e: ChangeEvent<HTMLInputElement>): void => {
        const value = e.target.value;
        setFormState((prev) => ({
          ...prev,
          [field]: value,
        }));
      },
    [],
  );

  // Select 필드 변경 핸들러
  const handleSelectChange = useCallback(
    (e: ChangeEvent<HTMLSelectElement>): void => {
      const value = e.target.value as AccountType;
      setFormState((prev) => ({
        ...prev,
        type: value,
      }));
    },
    [],
  );

  // 유효성 검사
  const isFormValid =
    formState.institution.trim().length > 0 &&
    formState.name.trim().length > 0 &&
    formState.number.trim().length > 0 &&
    formState.type != null;

  // 변경 여부 계산
  const changed = useCallback((): boolean => {
    return (
      formState.institution !== initialState.institution ||
      formState.name !== initialState.name ||
      formState.number !== initialState.number ||
      formState.type !== initialState.type
    );
  }, [formState, initialState]);

  return {
    formState,
    handleChange,
    handleSelectChange,
    isFormValid,
    changed: changed(),
  };
};
