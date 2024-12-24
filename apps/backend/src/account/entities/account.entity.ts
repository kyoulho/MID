import {Entity, Column, PrimaryGeneratedColumn} from "typeorm";
import {AccountType, UUID} from "@shared/shared";

@Entity("account")
export class Account {
    @PrimaryGeneratedColumn("uuid")
    id!: UUID;

    @Column({length: 50})
    company!: string;

    @Column({
        type: "enum",
        enum: AccountType,
    })
    type!: AccountType;

    @Column({length: 100, nullable: true})
    alias?: string;
}