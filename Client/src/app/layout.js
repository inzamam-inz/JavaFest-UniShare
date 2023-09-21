"use client"; // Because we're inside a server component
import { Provider } from "react-redux";
import store from "../store";

import "./globals.css";
const RootLayout = ({ children }) => {
  return (
    <html lang="en">
      <head>
        <meta charSet="utf-8" />
        <title>Unishare</title>
      </head>
      <body>
        <Provider store={store}>{children}</Provider>
      </body>
    </html>
  );
};

export default RootLayout;
