export interface RouteInfo {
    name: string;
    layout: string;
    url: string;
}

export const userRouteInfo: RouteInfo[] = [
    {
        name: "계좌 관리",
        layout: "user",
        url: "account",
    },
];
