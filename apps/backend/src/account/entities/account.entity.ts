import {Column, CreateDateColumn, Entity, PrimaryGeneratedColumn, UpdateDateColumn,} from "typeorm";
import {AccountType, type UUID} from "@mid/shared";

@Entity("account")
export class Account {
  @PrimaryGeneratedColumn("uuid")
  id?: UUID;

  @Column({ length: 50 })
  institution!: string;

  @Column({
    type: "enum",
    enum: AccountType,
  })
  type!: AccountType;

  @Column({ length: 100, nullable: true })
  name!: string;

  @Column({ length: 100, nullable: true })
  number!: string;

  @CreateDateColumn()
  createdAt?: Date;

  @UpdateDateColumn()
  updatedAt?: Date;
}
