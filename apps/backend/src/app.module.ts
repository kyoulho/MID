import {Module} from "@nestjs/common";
import {TypeOrmModule} from "@nestjs/typeorm";
import {typeOrmConfig} from "./typeorm.config";
import {AccountModule} from "./account/account.module";

@Module({
  imports: [TypeOrmModule.forRoot(typeOrmConfig), AccountModule],
})
export class AppModule {}
