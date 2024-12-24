import {AccountType, UUID} from "@shared/shared";

export class CreateAccountDTO {
    company!: string;
    type!: AccountType;
    alias?: string;
}
export class UpdateAccountDTO {
    company!: string;
    type!: AccountType;
    alias?: string;
}
export class GetAccountDTO {
    id!: UUID;
    company!: string;
    type!: AccountType;
    alias?: string;
}