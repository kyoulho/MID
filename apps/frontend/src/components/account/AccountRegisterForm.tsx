"use client";

import { FC } from "react";
import { Button, Input, Select, Text, VStack } from "@chakra-ui/react";
import { AccountType } from "@mid/shared";
import { useAccountForm } from "@/hooks/UseAccountForm";

const AccountRegisterForm: FC = () => {
  const { formState, handleChange, handleSelectChange, isFormValid } =
    useAccountForm({
      institution: "",
      type: null,
      name: "",
      number: "",
    });

  const createAccount = (): void => {};

  return (
    <VStack align="stretch" spacing={4}>
      <Text as={"b"}>기관명</Text>
      <Input
        placeholder="기관명을 입력하세요"
        value={formState.institution}
        onChange={handleChange("institution")}
      />
      <Text as={"b"}>계좌명</Text>
      <Input
        placeholder="계좌명을 입력하세요"
        value={formState.name}
        onChange={handleChange("name")}
      />
      <Text as={"b"}>계좌 번호</Text>
      <Input
        placeholder="계좌번호를 입력하세요"
        value={formState.number}
        onChange={handleChange("number")}
      />
      <Text as={"b"}>계좌 종류</Text>
      <Select
        placeholder="계좌 종류를 선택하세요"
        value={formState.type || undefined}
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
        onClick={createAccount}
        isDisabled={!isFormValid}
      >
        등록
      </Button>
    </VStack>
  );
};

export default AccountRegisterForm;
