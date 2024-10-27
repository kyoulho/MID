"use client";

import { redirect } from "next/navigation";
import { FC } from "react";

export const Home: FC = () => {
    redirect("user/dashboard");
};

export default Home;
