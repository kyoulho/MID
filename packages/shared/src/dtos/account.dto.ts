import { AccountType } from "../enums";
import { UUID } from "../types";

export interface BaseAccountDTO {
  institution: string;
  type: AccountType;
  name: string;
  number: string;
}
export type CreateAccountDTO = BaseAccountDTO;
export type UpdateAccountDTO = BaseAccountDTO;
export interface GetAccountDTO extends BaseAccountDTO {
  id: UUID;
  createdAt: Date;
  updatedAt: Date;
}
