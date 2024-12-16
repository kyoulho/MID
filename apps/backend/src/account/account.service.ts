import {Injectable} from "@nestjs/common";
import {CreateAccountDTO, UpdateAccountDTO} from "@shared/shared";

@Injectable()
export class AccountService {
    create(CreateAccountDTO: CreateAccountDTO) {
        return "This action adds a new investmentAccount";
    }

    findAll() {
        return "This action returns all investmentAccount";
    }

    findOne(id: number) {
        return `This action returns a #${id} investmentAccount`;
    }

    update(id: number, UpdateAccountDTO: UpdateAccountDTO) {
        return `This action updates a #${id} investmentAccount`;
    }

    remove(id: number) {
        return `This action removes a #${id} investmentAccount`;
    }
}
