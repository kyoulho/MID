import { Box, Text } from "@chakra-ui/react";
import { FC } from "react";

const InvestmentPage: FC = () => {
  return (
    <Box>
      <Text fontSize="2xl" mb="4">
        Welcome to the Investment Dashboard!
      </Text>
      <Text>
        This is your main dashboard area where you can add widgets, charts, or
        any relevant information.
      </Text>
    </Box>
  );
};

export default InvestmentPage;
