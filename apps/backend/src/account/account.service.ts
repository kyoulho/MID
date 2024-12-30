import { Injectable, NotFoundException } from "@nestjs/common";
import { InjectRepository } from "@nestjs/typeorm";
import { Repository } from "typeorm";
import {
  CreateAccountDTO,
  GetAccountDTO,
  UpdateAccountDTO,
  type UUID,
} from "@mid/shared";
import { Account } from "./entities/account.entity";
import { plainToInstance } from "class-transformer";

@Injectable()
export class AccountService {
  constructor(
    @InjectRepository(Account)
    private readonly accountRepository: Repository<Account>,
  ) {}

  async create(dto: CreateAccountDTO): Promise<GetAccountDTO> {
    const newAccount = plainToInstance(Account, dto);
    this.accountRepository.create(newAccount);
    return plainToInstance(GetAccountDTO, newAccount);
  }

  async findAll(): Promise<GetAccountDTO[]> {
    const accounts = await this.accountRepository.find();
    return accounts.map((account) => plainToInstance(GetAccountDTO, account));
  }

  async findOne(id: UUID): Promise<GetAccountDTO> {
    const account = await this.accountRepository.findOneBy({ id });
    if (!account) {
      throw new NotFoundException(`계좌 아이디 ${id} 찾을 수 없음.`);
    }
    return plainToInstance(GetAccountDTO, account);
  }

  async update(id: UUID, dto: UpdateAccountDTO): Promise<GetAccountDTO> {
    const account = await this.accountRepository.findOneBy({ id });
    if (!account) {
      throw new NotFoundException(`계좌 아이디 ${id} 찾을 수 없음.`);
    }

    const updatedAccount = this.accountRepository.merge(
      account,
      plainToInstance(Account, dto),
    );
    const savedAccount = await this.accountRepository.save(updatedAccount);

    return plainToInstance(GetAccountDTO, savedAccount);
  }

  async remove(id: UUID): Promise<{ message: string }> {
    const account = await this.accountRepository.findOneBy({ id });
    if (!account) {
      throw new NotFoundException(`계좌 아이디 ${id} 찾을 수 없음.`);
    }
    await this.accountRepository.remove(account);
    return { message: `계좌 아이디 ${id}가 성공적으로 삭제되었습니다.` };
  }
}
