import type {NextConfig} from "next";

const nextConfig: NextConfig = {
    reactStrictMode: true,                // React Strict 모드 활성화
    swcMinify: true,                      // SWC를 사용하여 JavaScript 최소화
    pageExtensions: ["tsx", "ts"],        // 페이지 확장자로 tsx와 ts 사용
    compiler: {
        styledComponents: true              // styled-components 사용 시 활성화
    },
    images: {},
    async headers() {
        return [
            {
                source: "/(.*)",
                headers: [
                    {
                        key: "X-Content-Type-Options",
                        value: "nosniff"
                    },
                    {
                        key: "X-Frame-Options",
                        value: "DENY"
                    }
                ]
            }
        ];
    }
};

module.exports = nextConfig;
