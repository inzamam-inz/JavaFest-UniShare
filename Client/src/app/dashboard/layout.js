
import ProtectedRoute from "@/authGaurd/ProtectedRoutes";
import React from "react";

const Layout = ({ children }) => {
  return (
    <div>
      <ProtectedRoute>{children}</ProtectedRoute>
    </div>
  );
};

export default Layout;
