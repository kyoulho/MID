import {ReactNode} from "react";
import {Provider} from "@/components/ui/provider";
import Sidebar from "@/components/layout/Sidebar";
import Header from "@/components/layout/Header";

export const metadata = {
    title: "My Investment Diary",
    description: "An app using Chakra UI with Next.js",
};

export default function RootLayout({children}: { children: ReactNode }) {
    return (
        <html lang="ko" suppressHydrationWarning={true}>
        <body>
        <Provider>
            <Sidebar/>
            <Header/>
            <main
                style={{
                    marginLeft: "240px",
                    marginTop: "60px",
                    padding: "20px",
                }}
            >
                {children}
            </main>
        </Provider>
        </body>
        </html>
    );
}
