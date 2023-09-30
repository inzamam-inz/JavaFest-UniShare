"use client";
import BookingService from "@/lib/services/bookingService";
import { Cancel } from "@mui/icons-material";
import { useEffect, useState } from "react";

const orders = [
  {
    number: "WU88191111",
    href: "#",
    invoiceHref: "#",
    createdDate: "Jul 6, 2021",
    createdDatetime: "2021-07-06",
    deliveredDate: "July 12, 2021",
    deliveredDatetime: "2021-07-12",
    total: "$160.00",
    products: [
      {
        id: 1,
        name: "Micro Backpack",
        description:
          "Are you a minimalist looking for a compact carry option? The Micro Backpack is the perfect size for your essential everyday carry items. Wear it like a backpack or carry it like a satchel for all-day use.",
        href: "#",
        price: "$70.00",
        imageSrc:
          "https://tailwindui.com/img/ecommerce-images/order-history-page-03-product-01.jpg",
        imageAlt:
          "Moss green canvas compact backpack with double top zipper, zipper front pouch, and matching carry handle and backpack straps.",
      },

      {
        id: 1,
        name: "Micro Backpack",
        description:
          "Are you a minimalist looking for a compact carry option? The Micro Backpack is the perfect size for your essential everyday carry items. Wear it like a backpack or carry it like a satchel for all-day use.",
        href: "#",
        price: "$70.00",
        imageSrc:
          "https://tailwindui.com/img/ecommerce-images/order-history-page-03-product-01.jpg",
        imageAlt:
          "Moss green canvas compact backpack with double top zipper, zipper front pouch, and matching carry handle and backpack straps.",
      },

      {
        id: 1,
        name: "Micro Backpack",
        description:
          "Are you a minimalist looking for a compact carry option? The Micro Backpack is the perfect size for your essential everyday carry items. Wear it like a backpack or carry it like a satchel for all-day use.",
        href: "#",
        price: "$70.00",
        imageSrc:
          "https://tailwindui.com/img/ecommerce-images/order-history-page-03-product-01.jpg",
        imageAlt:
          "Moss green canvas compact backpack with double top zipper, zipper front pouch, and matching carry handle and backpack straps.",
      },
      {
        id: 1,
        name: "Micro Backpack",
        description:
          "Are you a minimalist looking for a compact carry option? The Micro Backpack is the perfect size for your essential everyday carry items. Wear it like a backpack or carry it like a satchel for all-day use.",
        href: "#",
        price: "$70.00",
        imageSrc:
          "https://tailwindui.com/img/ecommerce-images/order-history-page-03-product-01.jpg",
        imageAlt:
          "Moss green canvas compact backpack with double top zipper, zipper front pouch, and matching carry handle and backpack straps.",
      },

      {
        id: 1,
        name: "Micro Backpack",
        description:
          "Are you a minimalist looking for a compact carry option? The Micro Backpack is the perfect size for your essential everyday carry items. Wear it like a backpack or carry it like a satchel for all-day use.",
        href: "#",
        price: "$70.00",
        imageSrc:
          "https://tailwindui.com/img/ecommerce-images/order-history-page-03-product-01.jpg",
        imageAlt:
          "Moss green canvas compact backpack with double top zipper, zipper front pouch, and matching carry handle and backpack straps.",
      },
      // More products...
    ],
  },
  // More orders...
];

export default function page() {
  const [bookings, setBookings] = useState([]);

  useEffect(() => {
    BookingService.getAll()
      .then((res) => {
        res.map((booking) => {
          booking.rentFrom = booking.rentFrom.slice(0, 10);
          booking.rentTo = booking.rentTo.slice(0, 10);
          booking.productName = booking.productResponse.name;
          booking.description = booking.productResponse.description;
          booking.image = booking.productResponse.image;
          booking.borrowerName = booking.borrower.fullName;
          booking.address = booking.borrower.address;
        });
        setBookings(res);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);
  return (
    <div className="bg-white">
      <div className="py-16 sm:py-24">
        <div className="mx-auto max-w-7xl sm:px-2 lg:px-8">
          <div className="mx-auto max-w-2xl px-4 lg:max-w-4xl lg:px-0">
            <h1 className="text-2xl font-bold tracking-tight text-gray-900 sm:text-3xl">
              My Booking Requests
            </h1>
            <p className="mt-2 text-sm text-gray-500">
              Check the status of your booking requests.
            </p>
          </div>
        </div>
        <div className="mt-3">
          <div className="mx-auto max-w-7xl sm:px-2 lg:px-8">
            <div className="mx-auto max-w-2xl space-y-8 sm:px-4 lg:max-w-4xl lg:px-0">
              {orders.map((order) => (
                <div
                  key={order.number}
                  className="border-b border-t border-gray-200 bg-white shadow-sm sm:rounded-lg sm:border"
                >
                  {/* Products */}
                  <h4 className="sr-only">Items</h4>
                  <ul role="list" className="divide-y divide-gray-200">
                    {bookings &&
                      bookings.map((booking) => (
                        <li key={booking.id} className="p-4 sm:p-6">
                          <div className="flex items-center sm:items-start">
                            <div className="h-10 w-20 flex-shrink-0 overflow-hidden rounded-lg bg-gray-200 sm:h-20 sm:w-20">
                              <img
                                src={booking.imageSrc}
                                alt={booking.imageAlt}
                                className="h-full w-full object-cover object-center"
                              />
                            </div>
                            <div className="ml-6 flex-1 text-sm">
                              <div className="font-medium text-gray-900 sm:flex sm:justify-between">
                                <h5>{booking.productName}</h5>
                                <p className="mt-2 sm:mt-0">{booking.total}</p>
                              </div>
                              <p className="hidden text-gray-500 sm:mt-2 sm:block">
                                {booking.description}
                              </p>
                              {/* order number, date placed, total bill */}
                              <div className="mt-2 flex sm:mt-4 sm:space-x-4">
                                <p className="text-gray-500">
                                  <span className=" font-semibold">From </span>{" "}
                                  {booking.rentFrom}
                                </p>
                                <p className="text-gray-500">
                                  <span className=" font-semibold">To </span>{" "}
                                  {booking.rentFrom}
                                </p>
                                <p className="text-gray-500">{booking.price}</p>
                              </div>
                            </div>
                          </div>

                          <div className="mt-6 sm:flex sm:justify-between">
                            <button className="flex items-center">
                              <Cancel
                                className="h-5 w-5 text-red-400"
                                aria-hidden="true"
                              />
                              <p className="ml-2 text-sm font-medium text-gray-500">
                                Cancel
                              </p>
                            </button>

                            <div className="mt-6 flex items-center space-x-4 divide-x divide-gray-200 border-t border-gray-200 pt-4 text-sm font-medium sm:ml-4 sm:mt-0 sm:border-none sm:pt-0">
                              <div className="flex flex-1 justify-center pl-4">
                                {/* rent again button */}
                                <button
                                  type="button"
                                  className="text-indigo-600 hover:text-indigo-500"
                                >
                                  Owner details
                                </button>
                              </div>
                              <div className="flex flex-1 justify-center pl-4">
                                {/* rent again button */}
                                <button
                                  type="button"
                                  className="text-green-600 hover:text-indigo-500"
                                >
                                  Mark as Received
                                </button>
                              </div>
                            </div>
                          </div>
                        </li>
                      ))}
                  </ul>
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
