"use client";

import React, { FC, useEffect, useState } from "react";
import {
  Box,
  Button,
  Flex,
  HStack,
  IconButton,
  Modal,
  ModalBody,
  ModalContent,
  ModalFooter,
  ModalHeader,
  ModalOverlay,
  Spinner,
  Text,
  useDisclosure,
} from "@chakra-ui/react";
import { GetAccountDTO } from "@mid/shared";
import { ChevronLeftIcon, ChevronRightIcon } from "@chakra-ui/icons";
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
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 10;

  const { isOpen, onOpen, onClose } = useDisclosure();

  const handleRegisterClick = (): void => {
    setSelectedAccount(null);
    setIsRegisterMode(true);
    onOpen();
  };

  const handleAccountClick = (account: GetAccountDTO): void => {
    setSelectedAccount(account);
    setIsRegisterMode(false);
    onOpen();
  };

  useEffect(() => {
    void fetchAccounts();
  }, []);

  const paginatedAccounts = accounts.slice(
    (currentPage - 1) * itemsPerPage,
    currentPage * itemsPerPage,
  );

  const totalPages = Math.max(1, Math.ceil(accounts.length / itemsPerPage));

  const handlePreviousPage = (): void => {
    if (currentPage > 1) {
      setCurrentPage(currentPage - 1);
    }
  };

  const handleNextPage = (): void => {
    if (currentPage < totalPages) {
      setCurrentPage(currentPage + 1);
    }
  };

  return (
    <Box display="flex" p={6} height="100vh">
      <Box flex="1" p={4} bg="white" borderRadius="md" boxShadow="sm">
        <HStack justifyContent="space-between" mb={6}>
          <Text fontSize="2xl">계좌 목록</Text>
          <Button colorScheme="teal" onClick={handleRegisterClick}>
            계좌 등록
          </Button>
        </HStack>
        {isLoading ? (
          <Spinner size="lg" />
        ) : (
          <>
            <AccountTable
              accounts={paginatedAccounts}
              onSelectAccount={handleAccountClick}
            />
            <Flex justifyContent="center" mt={4} alignItems="center">
              <IconButton
                icon={<ChevronLeftIcon />}
                aria-label="Previous Page"
                onClick={handlePreviousPage}
                isDisabled={currentPage === 1}
                variant="outline"
                mx={2}
              />
              <Text>
                페이지 {currentPage} / {totalPages}
              </Text>
              <IconButton
                icon={<ChevronRightIcon />}
                aria-label="Next Page"
                onClick={handleNextPage}
                isDisabled={currentPage === totalPages}
                variant="outline"
                mx={2}
              />
            </Flex>
          </>
        )}
      </Box>

      <Modal isOpen={isOpen} onClose={onClose} size="lg">
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>
            {isRegisterMode ? "계좌 등록" : "계좌 상세"}
          </ModalHeader>
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
          <ModalFooter>
            <Button onClick={onClose}>닫기</Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </Box>
  );
};

export default AccountPage;
