import React, { FC, PropsWithChildren } from "react";
import Sidebar from "@/components/layout/Sidebar";
import Header from "@/components/layout/Header";
import { Box, ChakraProvider } from "@chakra-ui/react";

export const metadata = {
  title: "My Investment Diary",
};

const RootLayout: FC<PropsWithChildren> = ({ children }) => {
  return (
    <html lang="ko" suppressHydrationWarning={true}>
      <body>
        <ChakraProvider>
          <Sidebar />
          <Header />
          <Box ml="240px" mt="60px" p="20px">
            {children}
          </Box>
        </ChakraProvider>
      </body>
    </html>
  );
};

export default RootLayout;
