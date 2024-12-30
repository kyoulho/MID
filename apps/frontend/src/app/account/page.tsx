"use client";

import React, { FC, useEffect, useState } from "react";
import { Box, Button, HStack, Spinner, Text } from "@chakra-ui/react";
import { GetAccountDTO } from "@mid/shared";
import AccountRegisterForm from "@/components/account/AccountRegisterForm";
import AccountDetail from "@/components/account/AccountDetail";
import AccountTable from "@/components/account/AccountTable";
import { useAccounts } from "@/hooks/useAccount";

const AccountPage: FC = () => {
  const {
    accounts,
    isLoading,
    fetchAccounts,
    createAccount,
    deleteAccount,
    updateAccount,
  } = useAccounts();
  const [selectedAccount, setSelectedAccount] = useState<GetAccountDTO | null>(
    null,
  );
  const [isRegisterMode, setIsRegisterMode] = useState(false);

  const handleAccountClick = (account: GetAccountDTO): void => {
    setSelectedAccount(account);
    setIsRegisterMode(false);
  };

  useEffect(() => {
    void fetchAccounts();
  }, []);

  return (
    <Box display="flex" p={6} height="100vh">
      <Box flex="1" p={4} bg="white" borderRadius="md" boxShadow="sm" mr={4}>
        <HStack justifyContent="space-between" mb={6}>
          <Text fontSize="2xl">계좌 목록</Text>
          <Button colorScheme="teal" onClick={() => setIsRegisterMode(true)}>
            계좌 등록
          </Button>
        </HStack>
        {isLoading ? (
          <Spinner size="lg" />
        ) : (
          <AccountTable
            accounts={accounts}
            onSelectAccount={handleAccountClick}
          />
        )}
      </Box>
      <Box flex="1" p={4} bg="white" borderRadius="md" boxShadow="sm">
        <HStack justifyContent="space-between" mb={6}>
          <Text fontSize="2xl">
            {isRegisterMode ? "계좌 등록" : "계좌 상세"}
          </Text>
        </HStack>
        {isRegisterMode ? (
          <AccountRegisterForm onCreateAccount={createAccount} />
        ) : (
          <AccountDetail
            account={selectedAccount}
            onUpdateAccount={updateAccount}
            onDeleteAccount={deleteAccount}
          />
        )}
      </Box>
    </Box>
  );
};

export default AccountPage;
