"use client";
import { useRouter } from "next/navigation";
import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { setUser } from "../store/Slices/userSlice";

const Page = () => {
  const router = useRouter();
  const dispatch = useDispatch();
  router.push("/dashboard/owner");
  useEffect(() => {
    if (localStorage.getItem("jwt_token")) {
      dispatch(setUser({}));
      // UserService.getOne()
      //   .then((res) => {
      //     dispatch(setUser(res));
      //     router.push("/dashboard/storefront/home");
      //   })
      //   .catch((err) => {
      //     console.log(err);
      //   });
    }
  }, []);
  return null;
};

export default Page;
