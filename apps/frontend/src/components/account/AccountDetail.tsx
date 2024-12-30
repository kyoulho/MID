import { FC } from "react";
import { GetAccountDTO } from "@mid/shared";
import { Box, Text } from "@chakra-ui/react";

const AccountDetail: FC<{ account: GetAccountDTO | null }> = ({ account }) => {
  if (!account) return <Text>계좌를 선택하세요.</Text>;

  return (
    <Box>
      <Text>기관명: {account.company}</Text>
      <Text>계좌 번호: {account.number}</Text>
      <Text>계좌명: {account.name}</Text>
      <Text>생성일: {new Date(account.createdAt).toISOString()}</Text>
    </Box>
  );
};

export default AccountDetail;
