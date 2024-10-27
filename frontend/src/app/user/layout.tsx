import React, { FC, PropsWithChildren } from "react";

import NavigationBar from "src/components/navigation";
import { userRouteInfo } from "src/data/route";

export const UserLayout: FC<PropsWithChildren> = ({ children }) => {
    return (
        <div>
            <NavigationBar routeInfos={userRouteInfo} />
            {children}
        </div>
    );
};

export default UserLayout;
