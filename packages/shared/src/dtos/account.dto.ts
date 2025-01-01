import { AccountType } from "../enums";
import { UUID } from "../types";
import { IsDate, IsEnum, IsString, IsUUID } from "class-validator";

export class BaseAccountDTO {
  @IsString()
  institution: string;

  @IsEnum(AccountType)
  type: AccountType;

  @IsString()
  name: string;

  @IsString()
  number: string;
}

export class CreateAccountDTO extends BaseAccountDTO {}

export class UpdateAccountDTO extends BaseAccountDTO {}

export class GetAccountDTO extends BaseAccountDTO {
  @IsUUID()
  id: UUID;

  @IsDate()
  createdAt: Date;

  @IsDate()
  updatedAt: Date;
}
