import {Injectable, NotFoundException} from "@nestjs/common";
import {InjectRepository} from "@nestjs/typeorm";
import {Repository} from "typeorm";
import {CreateAccountDTO, GetAccountDTO, UpdateAccountDTO, UUID} from "@shared/shared";
import {Account} from "./entities/account.entity";

@Injectable()
export class AccountService {
    constructor(
        @InjectRepository(Account)
        private readonly accountRepository: Repository<Account>,
    ) {
    }

    async create(createAccountDTO: CreateAccountDTO): Promise<GetAccountDTO> {
        const newAccount = this.accountRepository.create(createAccountDTO);
        return await this.accountRepository.save(newAccount);
    }

    async findAll(): Promise<GetAccountDTO[]> {
        return await this.accountRepository.find();
    }

    async findOne(id: UUID): Promise<GetAccountDTO> {
        const account = await this.accountRepository.findOneBy({id});
        if (!account) {
            throw new NotFoundException(`계좌 아이디 ${id} 찾을 수 없음.`);
        }
        return account;
    }

    async update(id: UUID, updateAccountDTO: UpdateAccountDTO): Promise<Account> {
        const account = await this.findOne(id);
        Object.assign(account, updateAccountDTO);
        return await this.accountRepository.save(account);
    }

    async remove(id: UUID): Promise<void> {
        const account = await this.findOne(id);
        await this.accountRepository.remove(account);
    }
}
