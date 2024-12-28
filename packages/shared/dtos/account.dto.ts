import { AccountType } from "../enums";
import { UUID } from "../types";

export class CreateAccountDTO {
  company!: string;
  type!: AccountType;
  alias?: string;
  number!: string;
}
export class UpdateAccountDTO {
  company!: string;
  type!: AccountType;
  alias?: string;
  number!: string;
}
export class GetAccountDTO {
  id!: UUID;
  company!: string;
  type!: AccountType;
  alias?: string;
  number!: string;
  createdAt!: Date;
}
