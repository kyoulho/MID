import { Box, Link, Text, VStack } from "@chakra-ui/react";
import NextLink from "next/link";
import { routes } from "@/const/routes";
import { FC } from "react";

const Sidebar: FC = () => {
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
        {routes.map((route) => (
          <Link
            key={route.href}
            as={NextLink}
            href={route.href}
            p="2"
            _hover={{ bg: "cyan.400", color: "white" }}
            borderRadius="md"
          >
            <Text fontSize="xl" fontWeight="bold" color="white">
              {route.label}
            </Text>
          </Link>
        ))}
      </VStack>
    </Box>
  );
};

export default Sidebar;
