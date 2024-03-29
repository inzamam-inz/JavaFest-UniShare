"use client";
import { Popover } from "@headlessui/react";
import {
  Bars3Icon,
  BellAlertIcon,
  UserCircleIcon,
} from "@heroicons/react/24/outline";
import { Tooltip } from "@material-tailwind/react";
import { ArrowCircleRightOutlined } from "@mui/icons-material";
import Link from "next/link";
import { useState } from "react";
import { useSelector } from "react-redux";
import { toast } from "react-toastify";
function classNames(...classes) {
  return classes.filter(Boolean).join(" ");
}

const Navbar = () => {
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);
  const [active, setActive] = useState("/");
  const isAuthenticated = useSelector((state) => state.user.isAuthenticated);
  const navbarItems = [
    { name: "Home", key: "/", href: "/dashboard/storefront/home" },
    {
      name: "Products",
      key: "products",
      href: "/dashboard/storefront/products",
    },
    {
      name: "My Request",
      key: "myrequests",
      href: "/dashboard/storefront/myrequests",
    },
    { name: "Orders", key: "history", href: "/dashboard/storefront/history" },
  ];
  return (
    <div className="relative bg-gray-600">
      {/* Navigation */}
      <header className="relative z-10">
        <nav aria-label="Top">
          {/* Top navigation */}
          {!isAuthenticated && (
            <div className="bg-gray-900">
              <div className="mx-auto flex h-10 max-w-7xl items-center justify-end px-4 sm:px-6 lg:px-8">
                <div className="flex items-right space-x-6">
                  {/* sign in route */}
                  <Link
                    href="/login"
                    className="text-sm font-medium text-white hover:text-gray-100"
                  >
                    Sign in
                  </Link>
                  <Link
                    href="/signup"
                    className="text-sm font-medium text-white hover:text-gray-100"
                  >
                    Create an account
                  </Link>
                </div>
              </div>
            </div>
          )}

          {/* Secondary navigation */}
          <div className="bg-white bg-opacity-10 backdrop-blur-md backdrop-filter">
            <div className="  px-4 sm:px-6 lg:px-">
              <div className=" ">
                <div className="flex h-16 ">
                  {/* Logo (lg+) */}
                  <div className="hidden lg:flex lg:flex-1 lg:items-center">
                    <Link
                      href="/dashboard/storefront/home"
                      className=" flex items-center"
                    >
                      <img
                        className="h-10 w-auto"
                        src="https://res.cloudinary.com/unishare/image/upload/v1694949615/Logo/Unishare_Logo.png"
                        alt="UniShare"
                      />
                      <h2 className=" text-2xl font-bold tracking-tight text-white ml-2">
                        UniShare
                      </h2>
                    </Link>
                  </div>

                  <div className="hidden h-full lg:flex">
                    {/* Flyout menus */}
                    <Popover.Group className="inset-x-0 bottom-0 px-4">
                      <div className="flex h-full justify-center space-x-8">
                        {navbarItems.map((page) => (
                          <Link
                            key={page.name}
                            href={page.href}
                            className="flex items-center text-sm font-medium text-white"
                          >
                            {page.name}
                          </Link>
                        ))}
                      </div>
                    </Popover.Group>
                  </div>

                  {/* Mobile menu and search (lg-) */}
                  <div className="flex flex-1 items-center lg:hidden">
                    <button
                      type="button"
                      className="-ml-2 p-2 text-white"
                      onClick={() => setMobileMenuOpen(true)}
                    >
                      <span className="sr-only">Open menu</span>
                      <Bars3Icon className="h-6 w-6" aria-hidden="true" />
                    </button>

                    {/* Search */}
                  </div>

                  {/* Logo (lg-) */}
                  <Link
                    href="/dashboard/storefront/home"
                    className=" flex items-center lg:hidden"
                  >
                    <img
                      className="h-10 w-auto"
                      src="https://res.cloudinary.com/unishare/image/upload/v1694949615/Logo/Unishare_Logo.png"
                      alt="UniShare"
                    />
                    <h2 className=" text-2xl font-bold tracking-tight text-white ml-2">
                      UniShare
                    </h2>
                  </Link>

                  <div className="flex flex-1 items-center justify-end">
                    <div className="flex items-center lg:ml-8">
                      {/* Owner Dashboard button, white background */}
                      {isAuthenticated && (
                        <Link
                          href="/dashboard/owner"
                          className="hidden lg:block text-sm font-medium text-gray-100 hover:text-black p-2 hover:bg-white bg-gray-400 rounded-lg mr-5"
                        >
                          Owner Dashboard
                        </Link>
                      )}
                      <div className="ml-4 flow-root lg:ml-8">
                        <p
                          href="#"
                          className="group -m-2 flex items-center p-2"
                        >
                          {/* Logout Icon  */}
                          {isAuthenticated && (
                            <>
                              <BellAlertIcon
                                className="h-6 w-6 flex-shrink-0 mr-4 text-white"
                                aria-hidden="true"
                              />
                              <Link
                                href="/dashboard/storefront/user"
                                className="text-sm font-medium text-white hover:text-gray-100"
                              >
                                <UserCircleIcon
                                  className="flex-shrink-0 h-6 w-6 mr-4 text-white"
                                  aria-hidden="true"
                                />
                              </Link>
                              <button
                                onClick={() => {
                                  localStorage.removeItem("jwt_token");
                                  localStorage.removeItem("refresh_token");
                                  localStorage.removeItem("user");
                                  window.location.href = "/login";
                                  toast.success("Logout successful");
                                }}
                                className="text-sm font-medium text-white hover:text-gray-100"
                              >
                                <Tooltip
                                  content="Logout"
                                  className="text-black z-10 bg-slate-300"
                                  placement="bottom"
                                >
                                  <ArrowCircleRightOutlined
                                    className="flex-shrink-0 h-6 w-6 text-white"
                                    aria-hidden="true"
                                  />
                                </Tooltip>
                              </button>
                            </>
                          )}
                        </p>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </nav>
      </header>
    </div>
  );
};

export default Navbar;
