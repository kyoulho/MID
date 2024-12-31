import { AccountType } from "../enums";
import { UUID } from "../types";

export class CreateAccountDTO {
  institution: string;
  type: AccountType;
  name: string;
  number: string;
}

export class UpdateAccountDTO {
  institution: string;
  type: AccountType;
  name: string;
  number: string;
}

export class GetAccountDTO {
  id: UUID;
  institution: string;
  type: AccountType;
  name: string;
  number: string;
  createdAt: Date;
  updatedAt: Date;
}
