export interface Routes {
  href: string;
  label: string;
}

export const routes: Routes[] = [
  { href: "/dashboard", label: "대시보드" },
  { href: "/account", label: "계좌 관리" },
  { href: "/investment", label: "자산 관리" },
];
