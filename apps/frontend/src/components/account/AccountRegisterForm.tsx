import { FC, useCallback } from "react";
import { Button, Input, Select, Text, VStack } from "@chakra-ui/react";
import { AccountType, CreateAccountDTO } from "@mid/shared";
import { useAccountForm } from "@/hooks/useAccountForm";

const AccountRegisterForm: FC<{
  onCreateAccount: (newAccount: CreateAccountDTO) => Promise<void>;
}> = ({ onCreateAccount }) => {
  const { formState, handleChange, handleSelectChange, isFormValid } =
    useAccountForm({
      institution: "",
      type: null,
      name: "",
      number: "",
    });

  const onSubmit = useCallback(async () => {
    const newAccount: CreateAccountDTO = {
      institution: formState.institution,
      type: formState.type!,
      name: formState.name,
      number: formState.number,
    };
    await onCreateAccount(newAccount); // 비동기 호출
  }, [formState, onCreateAccount]);

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
        onClick={void onSubmit}
        isDisabled={!isFormValid}
      >
        등록
      </Button>
    </VStack>
  );
};

export default AccountRegisterForm;
