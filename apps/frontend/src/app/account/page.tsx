"use client";

import React, { FC, useEffect, useState } from "react";
import {
  Box,
  Button,
  Input,
  Modal,
  ModalBody,
  ModalCloseButton,
  ModalContent,
  ModalFooter,
  ModalHeader,
  ModalOverlay,
  Spinner,
  Table,
  Text,
  useDisclosure,
} from "@chakra-ui/react";
import { AccountType, GetAccountDTO } from "@mid/shared";

function fetchAccounts(): GetAccountDTO[] {
  return [
    {
      id: "1111-222-3333-4444-555",
      company: "Toss",
      type: AccountType.STOCK,
      alias: "Savings Account",
      number: "123-456-789",
      createdAt: new Date("2023-01-15"),
    },
  ];
}

const AccountPage: FC = () => {
  const [accounts, setAccounts] = useState<GetAccountDTO[]>([]);
  const [loading, setLoading] = useState(true);
  const [selectedAccount, setSelectedAccount] = useState<GetAccountDTO | null>(
    null,
  );
  const {
    isOpen: isRegisterOpen,
    onOpen: onRegisterOpen,
    onClose: onRegisterClose,
  } = useDisclosure();

  const {
    isOpen: isDetailOpen,
    onOpen: onDetailOpen,
    onClose: onDetailClose,
  } = useDisclosure();

  const [newAccountName, setNewAccountName] = useState("");

  useEffect(() => {
    const data = fetchAccounts();
    setAccounts(data);
    setLoading(false);
  }, []);

  return (
    <Box p={6}>
      <Text fontSize="2xl" mb={4}>
        계좌 관리
      </Text>
      <Text mb={6}>계좌를 선택하거나, 새로운 계좌를 등록할 수 있습니다.</Text>

      <Button colorScheme="teal" onClick={onRegisterOpen} mb={6}>
        계좌 등록
      </Button>

      {loading ? (
        <Spinner size="lg" />
      ) : (
        <Box overflowX="auto" bg="white" p={4} borderRadius="md" boxShadow="sm">
          <Table>
            <thead>
              <tr>
                <th>계좌 회사</th>
                <th>계좌 종류</th>
                <th>계좌 별명</th>
                <th>계좌 번호</th>
                <th>계좌 생성일</th>
              </tr>
            </thead>
            <tbody>
              {accounts.map((account: GetAccountDTO) => (
                <tr
                  key={account.id}
                  style={{ cursor: "pointer" }}
                  onClick={() => {
                    setSelectedAccount(account);
                    onDetailOpen();
                  }}
                >
                  <td>{account.company}</td>
                  <td>{account.type}</td>
                  <td>{account.alias}</td>
                  <td>{account.number}</td>
                  <td>{new Date(account.createdAt).toISOString()}</td>
                </tr>
              ))}
            </tbody>
          </Table>
        </Box>
      )}

      {/* 계좌 등록 모달 */}
      <Modal isOpen={isRegisterOpen} onClose={onRegisterClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>새로운 계좌 등록</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <Input
              placeholder="계좌 이름을 입력하세요"
              value={newAccountName}
              onChange={(e) => setNewAccountName(e.target.value)}
            />
          </ModalBody>
          <ModalFooter>
            <Button colorScheme="teal" onClick={() => {}} mr={4}>
              등록
            </Button>
            <Button onClick={onRegisterClose}>취소</Button>
          </ModalFooter>
        </ModalContent>
      </Modal>

      {/* 계좌 상세 모달 */}
      <Modal isOpen={isDetailOpen} onClose={onDetailClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>계좌 상세 정보</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            {selectedAccount && (
              <Box>
                <Text>계좌 회사: {selectedAccount.company}</Text>
                <Text>계좌 번호: {selectedAccount.number}</Text>
                <Text>계좌 별명: {selectedAccount.alias}</Text>
                <Text>
                  계좌 생성일:{" "}
                  {new Date(selectedAccount.createdAt).toISOString()}
                </Text>
              </Box>
            )}
          </ModalBody>
          <ModalFooter>
            <Button colorScheme="teal" onClick={onDetailClose}>
              닫기
            </Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </Box>
  );
};

export default AccountPage;
