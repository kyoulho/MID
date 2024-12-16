import {Controller, Get, Post, Body, Patch, Param, Delete} from "@nestjs/common";
import {AccountService} from "./account.service";
import {CreateAccountDTO, UpdateAccountDTO} from "@shared/shared";

@Controller("investment-account")
export class AccountController {
    constructor(private readonly investmentAccountService: AccountService) {
    }

    @Post()
    create(@Body() createInvestmentAccountDto: CreateAccountDTO) {
        return this.investmentAccountService.create(createInvestmentAccountDto);
    }

    @Get()
    findAll() {
        return this.investmentAccountService.findAll();
    }

    @Get(":id")
    findOne(@Param("id") id: string) {
        return this.investmentAccountService.findOne(+id);
    }

    @Patch(":id")
    update(@Param("id") id: string, @Body() updateInvestmentAccountDto: UpdateAccountDTO) {
        return this.investmentAccountService.update(+id, updateInvestmentAccountDto);
    }

    @Delete(":id")
    remove(@Param("id") id: string) {
        return this.investmentAccountService.remove(+id);
    }
}
