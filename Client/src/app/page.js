"use client";

import { useRouter } from "next/navigation";

const Page = () => {
  const router = useRouter();
  router.push("/dashboard/storefront/home");
  return null;
};

export default Page;
