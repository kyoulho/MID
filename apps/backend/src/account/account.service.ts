import { Injectable, NotFoundException } from "@nestjs/common";
import { InjectRepository } from "@nestjs/typeorm";
import { Repository } from "typeorm";
import { Account } from "./entities/account.entity";
import { plainToInstance } from "class-transformer";
import {
  CreateAccountDTO,
  GetAccountDTO,
  UpdateAccountDTO,
  UUID,
} from "@mid/shared";

@Injectable()
export class AccountService {
  constructor(
    @InjectRepository(Account)
    private readonly accountRepository: Repository<Account>,
  ) {}

  async create(dto: CreateAccountDTO): Promise<GetAccountDTO> {
    const newAccount = plainToInstance(Account, dto);
    const savedAccount = await this.accountRepository.save(newAccount);
    return plainToInstance(GetAccountDTO, savedAccount);
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
    const updatedAccount = plainToInstance(Account, { id, ...dto });
    const savedAccount = await this.accountRepository.save(updatedAccount);
    return plainToInstance(GetAccountDTO, savedAccount);
  }

  async remove(id: UUID): Promise<void> {
    const result = await this.accountRepository.delete(id);
    if (result.affected === 0) {
      throw new NotFoundException(`계좌 아이디 ${id} 찾을 수 없음.`);
    }
  }
}
