import type {NextConfig} from "next";
import "reflect-metadata";

const nextConfig: NextConfig = {
    experimental: {
        optimizePackageImports: ["@chakra-ui/react"],
    },
    rewrites: async ()=> [
      {source: '/api/:path*', destination:'http://localhost:8080/api/:path*'},
    ]
};

export default nextConfig;
