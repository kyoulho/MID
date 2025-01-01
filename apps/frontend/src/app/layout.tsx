import React, { FC, PropsWithChildren } from "react";
import Sidebar from "@/components/layout/Sidebar";
import Header from "@/components/layout/Header";
import { Box, ChakraProvider } from "@chakra-ui/react";
import { plainToInstance } from "class-transformer";
import { GetAccountDTO } from "@mid/shared";

export const metadata = {
  title: "My Investment Diary",
};

const RootLayout: FC<PropsWithChildren> = ({ children }) => {
  const instance = plainToInstance(GetAccountDTO, {
    id: "",
    createdAt: "2024-01-01T00:00:00Z",
  });
  console.log(instance instanceof GetAccountDTO);
  console.log(instance);
  console.log(instance.createdAt instanceof Date);

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
