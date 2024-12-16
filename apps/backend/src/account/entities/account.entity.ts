import {Entity, Column, PrimaryGeneratedColumn} from "typeorm";
import {AccountType} from "@shared/shared/src/enums/account-type.enum";

@Entity("investment_account")
export class Account {
    @PrimaryGeneratedColumn()
    id!: number; // 고유 ID

    @Column({length: 50})
    accountType!: AccountType;

    @Column({length: 100})
    accountName!: string; // 계좌 이름: '메인 계좌', '암호화폐 계좌'

    @Column()
    balance!: number; // 계좌 잔액

    @Column({default: true})
    isActive!: boolean; // 활성화 여부
}