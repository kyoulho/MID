import { FC } from "react";
import { AccountType, GetAccountDTO } from "@mid/shared";
import { Button, HStack, Input, Select, Text, VStack } from "@chakra-ui/react";
import { useAccountForm } from "@/hooks/UseAccountForm";

export interface AccountDetailProps {
  account: GetAccountDTO | null;
  onUpdate: () => void;
  onDelete: () => void;
}

const AccountDetail: FC<AccountDetailProps> = ({
  account,
  onUpdate,
  onDelete,
}) => {
  const { formState, handleChange, handleSelectChange, isFormValid } =
    useAccountForm({
      institution: "",
      type: null,
      name: "",
      number: "",
    });

  if (!account) return <Text>계좌를 선택하세요.</Text>;

  return (
    <VStack align="stretch" spacing={4}>
      <Text as={"b"}>기관명</Text>
      <Input
        placeholder="기관명을 입력하세요"
        value={account.institution}
        onChange={handleChange("institution")}
      />
      <Text as={"b"}>계좌명</Text>
      <Input
        placeholder="계좌명을 입력하세요"
        value={account.name}
        onChange={handleChange("name")}
      />
      <Text as={"b"}>계좌 번호</Text>
      <Input
        placeholder="계좌번호를 입력하세요"
        value={account.number}
        onChange={handleChange("number")}
      />
      <Text as={"b"}>계좌 종류</Text>
      <Select
        placeholder="계좌 종류를 선택하세요"
        value={account.type}
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
        value={new Intl.DateTimeFormat("ko-KR", {
          year: "numeric",
          month: "long",
          day: "numeric",
          hour: "numeric",
          minute: "numeric",
        }).format(new Date(account.createdAt))}
        isReadOnly
        focusBorderColor="transparent" // 포커스 테두리 제거
        variant="filled" // 채워진 스타일로 변경
      />

      <Text as="b">변경일</Text>
      <Input
        value={new Intl.DateTimeFormat("ko-KR", {
          year: "numeric",
          month: "long",
          day: "numeric",
          hour: "numeric",
          minute: "numeric",
        }).format(new Date(account.updatedAt))}
        isReadOnly
        focusBorderColor="transparent" // 포커스 테두리 제거
        variant="filled" // 채워진 스타일로 변경
      />
      <HStack justifyContent={"flex-end"} spacing={4} width="100%">
        <Button
          colorScheme="teal"
          onClick={onUpdate}
          isDisabled={!isFormValid}
          width="120px"
        >
          수정
        </Button>
        <Button colorScheme="red" onClick={onDelete} width="120px">
          삭제
        </Button>
      </HStack>
    </VStack>
  );
};

export default AccountDetail;
