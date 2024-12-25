"use client";

import React, { useState, useEffect } from "react";
import {
  Box,
  Text,
  Table,
  Thead,
  Tbody,
  Tr,
  Th,
  Td,
  Button,
  useDisclosure,
  Modal,
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalCloseButton,
  ModalBody,
  ModalFooter,
  Input,
  Spinner,
} from "@chakra-ui/react";

// Mock API for demonstration
const fetchAccounts = async () => {
  return [
    {
      id: 1,
      name: "Savings Account",
      balance: 500000,
      createdAt: "2023-01-15",
    },
    {
      id: 2,
      name: "Checking Account",
      balance: 1200000,
      createdAt: "2023-05-20",
    },
    {
      id: 3,
      name: "Investment Account",
      balance: 3000000,
      createdAt: "2023-07-10",
    },
  ];
};

export default function AccountPage() {
  const [accounts, setAccounts] = useState([]); // 계좌 리스트 상태
  const [loading, setLoading] = useState(true); // 로딩 상태
  const [selectedAccount, setSelectedAccount] = useState(null); // 선택된 계좌 상태
  const { isOpen, onOpen, onClose } = useDisclosure(); // 계좌 상세 팝업 상태
  const {
    isOpen: isRegisterOpen,
    onOpen: onRegisterOpen,
    onClose: onRegisterClose,
  } = useDisclosure(); // 계좌 등록 팝업 상태
  const [newAccountName, setNewAccountName] = useState(""); // 새 계좌 이름 상태

  // API에서 계좌 데이터 가져오기
  useEffect(() => {
    const fetchData = async () => {
      const data = await fetchAccounts();
      setAccounts(data);
      setLoading(false);
    };
    fetchData();
  }, []);

  // 계좌 등록 함수
  const registerAccount = () => {
    if (newAccountName.trim() !== "") {
      setAccounts((prev) => [
        ...prev,
        {
          id: prev.length + 1,
          name: newAccountName,
          balance: 0,
          createdAt: new Date().toISOString().split("T")[0],
        },
      ]);
      setNewAccountName("");
      onRegisterClose();
    }
  };

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
          <Table variant="simple" size="md">
            <Thead bg="gray.100">
              <Tr>
                <Th>계좌 번호</Th>
                <Th>계좌 이름</Th>
                <Th>잔액 (₩)</Th>
                <Th>생성 날짜</Th>
              </Tr>
            </Thead>
            <Tbody>
              {accounts.map((account) => (
                <Tr
                  key={account.id}
                  cursor="pointer"
                  _hover={{ bg: "gray.50" }}
                  onClick={() => {
                    setSelectedAccount(account);
                    onOpen();
                  }}
                >
                  <Td>{account.id}</Td>
                  <Td>{account.name}</Td>
                  <Td>{account.balance.toLocaleString("ko-KR")}</Td>
                  <Td>{account.createdAt}</Td>
                </Tr>
              ))}
            </Tbody>
          </Table>
        </Box>
      )}

      {/* 계좌 상세 팝업 */}
      {selectedAccount && (
        <Modal isOpen={isOpen} onClose={onClose}>
          <ModalOverlay />
          <ModalContent>
            <ModalHeader>계좌 정보</ModalHeader>
            <ModalCloseButton />
            <ModalBody>
              <Text>계좌 번호: {selectedAccount.id}</Text>
              <Text>계좌 이름: {selectedAccount.name}</Text>
              <Text>
                잔액: {selectedAccount.balance.toLocaleString("ko-KR")} ₩
              </Text>
              <Text>생성 날짜: {selectedAccount.createdAt}</Text>
            </ModalBody>
            <ModalFooter>
              <Button onClick={onClose} colorScheme="teal">
                닫기
              </Button>
            </ModalFooter>
          </ModalContent>
        </Modal>
      )}

      {/* 계좌 등록 팝업 */}
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
            <Button colorScheme="teal" onClick={registerAccount} mr={4}>
              등록
            </Button>
            <Button onClick={onRegisterClose}>취소</Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </Box>
  );
}
