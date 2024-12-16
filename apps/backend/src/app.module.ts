import { Module } from "@nestjs/common";
import { TypeOrmModule } from "@nestjs/typeorm";
import { typeOrmConfig } from "./typeorm.config";
import { AccountModule } from "./account/account.module";

@Module({
  imports: [
    TypeOrmModule.forRoot(typeOrmConfig),
    AccountModule, // Account 모듈 등록
  ],
})
export class AppModule {}
