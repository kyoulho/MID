import {
  Table,
  TableContainer,
  Tbody,
  Td,
  Th,
  Thead,
  Tr,
} from "@chakra-ui/react";
import { GetAccountDTO } from "@mid/shared";
import { FC } from "react";
import { formatDatetime } from "@/utils/DateUtil";

const AccountTable: FC<{
  accounts: GetAccountDTO[];
  onSelectAccount: (account: GetAccountDTO) => void;
}> = ({ accounts, onSelectAccount }) => {
  const itemsPerPage = 10;
  return (
    <TableContainer>
      <Table variant={"simple"} size={"lg"}>
        <Thead>
          <Tr>
            <Th>기관명</Th>
            <Th>종류</Th>
            <Th>계좌명</Th>
            <Th>계좌번호</Th>
            <Th>생성일</Th>
            <Th>변경일</Th>
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
              <Td>{formatDatetime(account.createdAt)}</Td>
              <Td>{formatDatetime(account.updatedAt)}</Td>
            </Tr>
          ))}
          {Array.from(
            { length: itemsPerPage - accounts.length },
            (_, index) => (
              <Tr key={`empty-${index}`}>
                <Td colSpan={6}>&nbsp;</Td>
              </Tr>
            ),
          )}
        </Tbody>
      </Table>
    </TableContainer>
  );
};
export default AccountTable;
