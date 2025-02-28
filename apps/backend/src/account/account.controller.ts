import {Body, Controller, Delete, Get, Param, Patch, Post,} from "@nestjs/common";
import {AccountService} from "./account.service";
import type {CreateAccountDTO, GetAccountDTO, UpdateAccountDTO, UUID} from "@mid/shared";

@Controller("/api/accounts")
export class AccountController {
    constructor(private readonly investmentAccountService: AccountService) {
    }

    @Post()
    create(@Body() dto: CreateAccountDTO): Promise<GetAccountDTO> {
        return this.investmentAccountService.create(dto);
    }

    @Get()
    findAll(): Promise<GetAccountDTO[]> {
        return this.investmentAccountService.findAll();
    }

    @Get(":id")
    findOne(@Param("id") id: UUID): Promise<GetAccountDTO> {
        return this.investmentAccountService.findOne(id);
    }

    @Patch(":id")
    update(@Param("id") id: UUID, @Body() dto: UpdateAccountDTO): Promise<GetAccountDTO> {
        return this.investmentAccountService.update(id, dto);
    }

    @Delete(":id")
    remove(@Param("id") id: UUID): Promise<void> {
        return this.investmentAccountService.remove(id);
    }
}
