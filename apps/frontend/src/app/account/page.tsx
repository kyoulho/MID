"use client";

import React, { FC, useCallback, useEffect, useState } from "react";
import {
  Box,
  Button,
  HStack,
  Modal,
  ModalBody,
  ModalCloseButton,
  ModalContent,
  ModalFooter,
  ModalHeader,
  ModalOverlay,
  Spinner,
  Text,
  useDisclosure,
} from "@chakra-ui/react";
import { GetAccountDTO } from "@mid/shared";
import AccountRegisterForm from "@/components/account/AccountRegisterForm";
import AccountDetail from "@/components/account/AccountDetail";
import AccountTable from "@/components/account/AccountTable";
import { useAccounts } from "@/hooks/useAccount";
import { usePagination } from "@/hooks/usePagination";
import Pagination from "@/components/Pagination";

const AccountPage: FC = () => {
  const {
    accounts,
    isLoading,
    fetchAccounts,
    createAccount,
    deleteAccount,
    updateAccount,
  } = useAccounts();

  const { isOpen, onOpen, onClose } = useDisclosure();

  const [selectedAccount, setSelectedAccount] = useState<GetAccountDTO | null>(
    null,
  );

  const [isRegisterMode, setIsRegisterMode] = useState(false);

  const { currentPage, totalPages, paginatedData, setPage } = usePagination(
    accounts,
    10,
  );

  const handleRegisterClick = useCallback(() => {
    setSelectedAccount(null);
    setIsRegisterMode(true);
    onOpen();
  }, [setSelectedAccount, setIsRegisterMode, onOpen]);

  const handleAccountClick = useCallback(
    (account: GetAccountDTO) => {
      setSelectedAccount(account);
      setIsRegisterMode(false);
      onOpen();
    },
    [setSelectedAccount, setIsRegisterMode, onOpen],
  );

  useEffect(() => {
    void fetchAccounts();
  }, []);

  return (
    <Box display="flex" p={6} height="100vh">
      <Box flex="1" p={4} bg="white" borderRadius="md" boxShadow="sm">
        <HStack justifyContent="space-between" mb={6} ml={6} mr={6}>
          <Text fontSize="2xl">계좌 목록</Text>
          <Button colorScheme="blue" onClick={handleRegisterClick}>
            계좌 등록
          </Button>
        </HStack>
        {isLoading ? (
          <Spinner size="lg" />
        ) : (
          <>
            <AccountTable
              accounts={paginatedData}
              onSelectAccount={handleAccountClick}
            />
            <Pagination
              currentPage={currentPage}
              totalPages={totalPages}
              onPageChange={setPage}
            />
          </>
        )}
      </Box>

      <Modal isOpen={isOpen} onClose={onClose} size="lg">
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>
            {isRegisterMode ? "계좌 등록" : "계좌 상세"}
          </ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            {isRegisterMode ? (
              <AccountRegisterForm onCreateAccount={createAccount} />
            ) : (
              selectedAccount && (
                <AccountDetail
                  account={selectedAccount}
                  onUpdateAccount={updateAccount}
                  onDeleteAccount={deleteAccount}
                />
              )
            )}
          </ModalBody>
          <ModalFooter></ModalFooter>
        </ModalContent>
      </Modal>
    </Box>
  );
};

export default AccountPage;
