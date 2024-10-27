import React, { FC, PropsWithChildren } from "react";

const Footer: FC<PropsWithChildren> = ({ children }) => {
    return (
        <div className="footer bg-base-100 shadow-md fixed top-0 left-0 w-full z-222">
            <div className="container mx-auto flex justify-center">
                <nav className="flex space-x-8 text-gray-600">{children}</nav>
            </div>
        </div>
    );
};

export default Footer;
