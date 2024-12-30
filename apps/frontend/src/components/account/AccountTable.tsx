import { Table, Tbody, Td, Th, Thead, Tr } from "@chakra-ui/react";
import { GetAccountDTO } from "@mid/shared";
import { FC } from "react";

const AccountTable: FC<{
  accounts: GetAccountDTO[];
  onSelectAccount: (account: GetAccountDTO) => void;
}> = ({ accounts, onSelectAccount }) => (
  <Table>
    <Thead>
      <Tr>
        <Th>기관명</Th>
        <Th>종류</Th>
        <Th>계좌명</Th>
        <Th>계좌번호</Th>
      </Tr>
    </Thead>
    <Tbody>
      {accounts.map((account) => (
        <Tr
          key={account.id}
          onClick={() => onSelectAccount(account)}
          style={{ cursor: "pointer" }}
        >
          <Td>{account.institution}</Td>
          <Td>{account.type}</Td>
          <Td>{account.name}</Td>
          <Td>{account.number}</Td>
        </Tr>
      ))}
    </Tbody>
  </Table>
);

export default AccountTable;
