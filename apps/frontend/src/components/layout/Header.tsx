import {Flex, Spacer, Text} from "@chakra-ui/react";
import {Avatar} from "../ui/avatar";

const Header = () => {
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
        Dashboard
      </Text>
      <Spacer />
      <Avatar name="John Doe" size="sm" />
    </Flex>
  );
};

export default Header;
