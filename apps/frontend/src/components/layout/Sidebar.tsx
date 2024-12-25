import {Box, VStack, Link, Text} from "@chakra-ui/react";
import NextLink from "next/link";

const Sidebar = () => {
    return (
        <Box
            as="nav"
            w="240px"
            h="100vh"
            bg="blue.600"
            color="white"
            position="fixed"
            top="0"
            left="0"
            p="5"
            boxShadow="lg" // 입체감을 위한 그림자 추가
        >
            <Text fontSize="xl" fontWeight="bold" mb="6">
                My Investment Diary
            </Text>
            <VStack gap={4} align="stretch">
                <Link
                    as={NextLink}
                    href="/dashboard"
                    p="2"
                    _hover={{bg: "cyan.400", color: "white"}}
                    borderRadius="md"
                >
                    <Text fontSize="xl" fontWeight="bold" color="white">
                        대시보드
                    </Text>
                </Link>
                <Link
                    as={NextLink}
                    href="/account"
                    p="2"
                    _hover={{bg: "cyan.400", color: "white"}}
                    borderRadius="md"
                >
                    <Text fontSize="xl" fontWeight="bold" color="white">
                        계좌 관리
                    </Text>
                </Link>
                <Link
                    as={NextLink}
                    href="/investment"
                    p="2"
                    _hover={{bg: "cyan.400", color: "white"}}
                    borderRadius="md"
                >
                    <Text fontSize="xl" fontWeight="bold" color="white">
                        자산 관리
                    </Text>
                </Link>
            </VStack>
        </Box>
    );
};

export default Sidebar;
