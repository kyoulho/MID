"use client";

import { ChangeEvent, FC, useState } from "react";
import { Button, Input, Select, Text, VStack } from "@chakra-ui/react";
import { AccountType, CreateAccountDTO } from "@mid/shared";

// 초기 상태 인터페이스
export interface AccountFormState {
  institution: string | null;
  type: AccountType | null;
  name: string | null;
  number: string | null;
}

const AccountRegisterForm: FC<{
  onRegister: (account: CreateAccountDTO) => void;
}> = ({ onRegister }) => {
  const [newAccount, setNewAccount] = useState<AccountFormState>({
    institution: null,
    type: null,
    name: null,
    number: null,
  });

  const handleChange =
    (field: keyof AccountFormState) =>
    (e: ChangeEvent<HTMLInputElement>): void => {
      setNewAccount((prev) => ({
        ...prev,
        [field]: e.target.value,
      }));
    };

  const handleSelectChange = (e: ChangeEvent<HTMLSelectElement>): void => {
    setNewAccount((prev) => ({
      ...prev,
      type: e.target.value as AccountType,
    }));
  };

  const isFormValid =
    newAccount.institution?.trim() &&
    newAccount.name?.trim() &&
    newAccount.number?.trim() &&
    newAccount.type;

  const handleSubmit = (): void => {
    if (isFormValid) {
      onRegister({
        institution: newAccount.institution!,
        type: newAccount.type!,
        name: newAccount.name!,
        number: newAccount.number!,
      });
    }
  };

  return (
    <VStack align="stretch" spacing={4}>
      <Text as={"b"}>기관명</Text>
      <Input
        placeholder="기관명을 입력하세요"
        value={newAccount.institution || ""}
        onChange={handleChange("institution")}
      />
      <Text as={"b"}>계좌명</Text>
      <Input
        placeholder="계좌명을 입력하세요"
        value={newAccount.name || ""}
        onChange={handleChange("name")}
      />
      <Text as={"b"}>계좌 번호</Text>
      <Input
        placeholder="계좌번호를 입력하세요"
        value={newAccount.number || ""}
        onChange={handleChange("number")}
      />
      <Text as={"b"}>계좌 종류</Text>
      <Select
        placeholder="계좌 종류를 선택하세요"
        value={newAccount.type || ""}
        onChange={handleSelectChange}
      >
        {Object.values(AccountType).map((type) => (
          <option key={type} value={type}>
            {type}
          </option>
        ))}
      </Select>
      <Button
        colorScheme="teal"
        onClick={handleSubmit}
        isDisabled={!isFormValid}
      >
        등록
      </Button>
    </VStack>
  );
};

export default AccountRegisterForm;
