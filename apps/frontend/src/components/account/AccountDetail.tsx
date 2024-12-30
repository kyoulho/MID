import { FC } from "react";
import { AccountType, GetAccountDTO } from "@mid/shared";
import { Button, HStack, Input, Select, Text, VStack } from "@chakra-ui/react";
import { useAccountForm } from "@/hooks/UseAccountForm";
import { formatDatetime } from "@/utils/DateUtil";

export interface AccountDetailProps {
  account: GetAccountDTO | null;
}

const AccountDetail: FC<AccountDetailProps> = ({ account }) => {
  if (!account) return <Text>계좌를 선택해 주세요.</Text>;

  const { formState, handleChange, handleSelectChange, isFormValid, changed } =
    useAccountForm({
      institution: account.institution,
      type: account.type,
      name: account.name,
      number: account.number,
    });

  const updateAccount = (): void => {};
  const deleteAccount = (): void => {};

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
      <Text as="b">생성일</Text>
      <Input
        value={formatDatetime(account?.createdAt)}
        isReadOnly
        focusBorderColor="transparent"
        variant="filled"
      />

      <Text as="b">변경일</Text>
      <Input
        value={formatDatetime(account?.updatedAt)}
        isReadOnly
        focusBorderColor="transparent"
        variant="filled"
      />
      <HStack justifyContent={"flex-end"} spacing={4} width="100%">
        <Button
          colorScheme="teal"
          onClick={updateAccount}
          isDisabled={!changed || !isFormValid}
          width="120px"
        >
          수정
        </Button>
        <Button colorScheme="red" onClick={deleteAccount} width="120px">
          삭제
        </Button>
      </HStack>
    </VStack>
  );
};

export default AccountDetail;
