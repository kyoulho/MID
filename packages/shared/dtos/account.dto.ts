import { AccountType } from "../enums";
import { UUID } from "../types";

interface BaseAccountDTO {
  institution: string;
  type: AccountType;
  name: string;
  number: string;
}

export class CreateAccountDTO implements BaseAccountDTO {
  institution!: string;
  type!: AccountType;
  name!: string;
  number!: string;
}

export class UpdateAccountDTO implements BaseAccountDTO {
  institution!: string;
  type!: AccountType;
  name!: string;
  number!: string;
}

export class GetAccountDTO extends CreateAccountDTO {
  id!: UUID;
  createdAt!: Date;
  updatedAt!: Date;
}
