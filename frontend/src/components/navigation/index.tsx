import Link from "next/link";
import React, { FC } from "react";

import { RouteInfo } from "../../data/route";

export interface NavigationBarProps {
    routeInfos: RouteInfo[];
}

const NavigationBar: FC<NavigationBarProps> = ({ routeInfos }) => {
    const children = routeInfos.map((info, index) => (
        <Link key={index} href={info.layout + "/" + info.url}>
            {info.name}
        </Link>
    ));

    return (
        <header className="navbar bg-base-100 shadow-md fixed top-0 left-0 w-full z-222">
            <div className="container mx-auto flex justify-center">g</div>
            <div className="container mx-auto flex justify-center">
                <nav className="flex space-x-8 text-gray-600">{children}</nav>
            </div>
            <div className="container mx-auto flex justify-center">g</div>
        </header>
    );
};

export default NavigationBar;
