"use client";

import React, { FC, useEffect, useState } from "react";
import { Box, Button, HStack, Spinner, Text } from "@chakra-ui/react";
import { AccountType, GetAccountDTO } from "@mid/shared";
import AccountRegisterForm from "@/components/account/AccountRegisterForm";
import AccountDetail from "@/components/account/AccountDetail";
import AccountTable from "@/components/account/AccountTable";

function fetchAccounts(): GetAccountDTO[] {
  return [
    {
      id: "1111-222-3333-4444-555",
      institution: "Toss",
      type: AccountType.STOCK,
      name: "Savings Account",
      number: "123-456-789",
      createdAt: new Date("2023-01-11"),
      updatedAt: new Date("2021-01-15"),
    },
  ];
}

const AccountPage: FC = () => {
  const [accounts, setAccounts] = useState<GetAccountDTO[]>([]);
  const [loading, setLoading] = useState(true);
  const [selectedAccount, setSelectedAccount] = useState<GetAccountDTO | null>(
    null,
  );
  const [isRegisterMode, setIsRegisterMode] = useState(false);

  const handleAccountClick = (account: GetAccountDTO): void => {
    setSelectedAccount(account);
    setIsRegisterMode(false);
  };

  const handleAccountRegister = (): void => {
    console.log("Account Register");
    setIsRegisterMode(false);
  };

  const handleAccountUpdater = (): void => {
    console.log("Account Updater");
  };
  const handleAccountDeleter = (): void => {
    console.log("Account deleter");
  };

  useEffect(() => {
    const data = fetchAccounts();
    setAccounts(data);
    setLoading(false);
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
        {loading ? (
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
          <AccountRegisterForm onRegister={handleAccountRegister} />
        ) : (
          <AccountDetail
            account={selectedAccount}
            onUpdate={handleAccountUpdater}
            onDelete={handleAccountDeleter}
          />
        )}
      </Box>
    </Box>
  );
};

export default AccountPage;
