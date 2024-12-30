import { AccountType } from "../enums";
import { UUID } from "../types";

export interface CreateAccountDTO {
  institution: string;
  type: AccountType | null;
  name: string;
  number: string;
}

export interface UpdateAccountDTO {
  institution: string;
  type: AccountType | null;
  name: string;
  number: string;
}

export interface GetAccountDTO {
  id: UUID;
  institution: string;
  type: AccountType;
  name: string;
  number: string;
  createdAt: Date;
  updatedAt: Date;
}
