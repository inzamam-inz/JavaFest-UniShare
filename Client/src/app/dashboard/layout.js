"use client";
import { setUser } from "@/store/Slices/userSlice";
import { useEffect } from "react";
import { useDispatch } from "react-redux";
const Layout = ({ children }) => {
  const dispatch = useDispatch();
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
  return <div>{children}</div>;
};

export default Layout;
