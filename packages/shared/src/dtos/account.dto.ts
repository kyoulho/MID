import { AccountType } from "../enums";
import { UUID } from "../types";

export class BaseAccountDTO {
  institution: string;
  type: AccountType;
  name: string;
  number: string;
}

export class CreateAccountDTO extends BaseAccountDTO {}

export class UpdateAccountDTO extends BaseAccountDTO {}

export class GetAccountDTO extends BaseAccountDTO {
  id: UUID;
  createdAt: Date;
  updatedAt: Date;
}
