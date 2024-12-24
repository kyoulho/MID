import {TypeOrmModuleOptions} from "@nestjs/typeorm";
import {Account} from "./account/entities/account.entity";

export const typeOrmConfig: TypeOrmModuleOptions = {
    type: "postgres",
    host: "localhost",
    port: 5432,
    username: "root",
    password: "password",
    database: "mid",
    entities: [Account],
    synchronize: true, // 개발 환경에서만 사용. 운영 환경에서는 비활성화하세요.
};
