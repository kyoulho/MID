"use client";

import { Flex, Spacer, Text } from "@chakra-ui/react";
import { usePathname } from "next/navigation";
import { routes } from "@/routes";
import { Avatar } from "@/components/ui/avatar";

const Header = () => {
  const pathname = usePathname();

  // 현재 경로에 해당하는 라벨 찾기
  const currentRoute = routes.find((route) => route.href === pathname);
  const currentLabel = currentRoute ? currentRoute.label : "알 수 없는 페이지";

  return (
    <Flex
      as="header"
      w="calc(100% - 240px)"
      h="60px"
      bg="gray.100"
      px="4"
      align="center"
      ml="240px"
      position="fixed"
      top="0"
      boxShadow="sm"
    >
      <Text fontSize="lg" fontWeight="bold">
        {currentLabel}
      </Text>
      <Spacer />
      <Avatar name="John Doe" size="sm" />
    </Flex>
  );
};

export default Header;
