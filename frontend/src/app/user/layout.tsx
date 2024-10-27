import React, { FC, PropsWithChildren } from "react";

import NavigationBar from "src/components/navigation";
import { userRouteInfo } from "src/data/route";

export const UserLayout: FC<PropsWithChildren> = ({ children }) => {
    return (
        <div className="min-h-screen">
            <NavigationBar routeInfos={userRouteInfo} />
            <main className="container mx-auto pt-24 pb-8 max-w-4xl">
                <h1 className="text-2xl font-bold text-center mb-8">DGA</h1>

                {/* Tab Navigation */}
                <div className="tabs mb-8 flex justify-around bg-base-100 rounded-lg shadow">
                    <a className="tab tab-bordered">수익률 차트</a>
                    <a className="tab tab-bordered">손실폭</a>
                    <a className="tab tab-bordered">포트폴리오 요약</a>
                    <a className="tab tab-bordered">자산 세부내역</a>
                    <a className="tab tab-active text-primary">전략 정보</a>
                </div>

                <div className="flex flex-col md:flex-row gap-6">
                    {/* Left Column */}
                    <div className="flex-1 space-y-6">
                        <div className="card bg-base-100 shadow-md p-4">
                            <h2 className="card-title">전략 설명</h2>
                            <p>
                                Paul Cho의 "Balance Between Growth and Dividend"
                                전략...
                            </p>
                        </div>

                        <div className="card bg-base-100 shadow-md p-4">
                            <h2 className="card-title">전략 규칙</h2>
                            <ul className="list-disc list-inside space-y-1 text-gray-600">
                                <li>
                                    현재 자산: 미국 나스닥 (QQQ), 미국 배당주
                                    (SCHD)
                                </li>
                                <li>
                                    채권자산: 미국 국채 (TLT), 물가연동국채
                                    (TIP)
                                </li>
                                {/* 다른 규칙 추가 가능 */}
                            </ul>
                        </div>
                    </div>

                    {/* Right Column */}
                    <div className="flex-1">
                        <div className="card bg-base-100 shadow-md p-4">
                            <h2 className="card-title">전략 특징</h2>
                            <p className="text-gray-600">
                                이 전략은 성장주와 배당주를 균형 있게 선정해
                                두개의 주식 ETF로 구성됩니다...
                            </p>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    );
};

export default UserLayout;
