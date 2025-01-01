import {Module} from "@nestjs/common";
import {AccountService} from "./account.service";
import {AccountController} from "./account.controller";
import {Account} from "@/account/entities/account.entity";
import {TypeOrmModule} from "@nestjs/typeorm";

@Module({
  imports: [TypeOrmModule.forFeature([Account])], // 엔티티 등록
  controllers: [AccountController],
  providers: [AccountService],
})
export class AccountModule {}
