import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";
import React, { FC } from "react";

const inter = Inter({ subsets: ["latin"] });

export const metadata: Metadata = {
    title: "My Investment Diary",
    description: "My Investment Diary",
    keywords: "Investment, Stock",
};

interface RootLayoutProps {
    children: React.ReactNode;
}

const RootLayout: FC = ({ children }: RootLayoutProps) => (
    <html lang="kr">
        <body className={inter.className}>{children}</body>
    </html>
);

export default RootLayout;
