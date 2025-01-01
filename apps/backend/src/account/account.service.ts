import {Injectable, NotFoundException} from "@nestjs/common";
import {InjectRepository} from "@nestjs/typeorm";
import {Repository} from "typeorm";
import {Account} from "./entities/account.entity";
import {CreateAccountDTO, GetAccountDTO, UpdateAccountDTO, UUID,} from "@mid/shared";

@Injectable()
export class AccountService {
  constructor(
    @InjectRepository(Account)
    private readonly accountRepository: Repository<Account>,
  ) {}

  async create(dto: CreateAccountDTO): Promise<GetAccountDTO> {
    const newAccount = this.accountRepository.create(dto);
    const savedAccount = await this.accountRepository.save(newAccount);
    return { ...savedAccount } as GetAccountDTO; // 직접 변환
  }

  async findAll(): Promise<GetAccountDTO[]> {
    const accounts = await this.accountRepository.find();
    return accounts.map((account) => ({ ...account }) as GetAccountDTO);
  }

  async findOne(id: UUID): Promise<GetAccountDTO> {
    const account = await this.accountRepository.findOneBy({ id });
    if (!account) {
      throw new NotFoundException(`계좌 아이디 ${id} 찾을 수 없음.`);
    }
    return { ...account } as GetAccountDTO;
  }

  async update(id: UUID, dto: UpdateAccountDTO): Promise<GetAccountDTO> {
    const updatedAccount = { id, ...dto }; // DTO와 ID를 병합
    const savedAccount = await this.accountRepository.save(updatedAccount);
    return { ...savedAccount } as GetAccountDTO;
  }

  async remove(id: UUID): Promise<void> {
    const result = await this.accountRepository.delete(id);
    if (result.affected === 0) {
      throw new NotFoundException(`계좌 아이디 ${id} 찾을 수 없음.`);
    }
  }
}
