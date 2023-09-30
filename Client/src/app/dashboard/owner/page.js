"use client";
import {
  CurrencyBangladeshiIcon,
  EyeIcon,
  ShoppingBagIcon,
} from "@heroicons/react/24/outline";
import { Check } from "@mui/icons-material";
import { useState } from "react";

const Page = () => {
  const [sidebarOpen, setSidebarOpen] = useState(false);

  return (
    <>
      {/* dashboard analytics */}
      <>
        <div className="grid grid-cols-1 gap-4 md:grid-cols-2 md:gap-6 xl:grid-cols-4 2xl:gap-7.5 p-4">
          <div className="rounded-sm border border-stroke py-6 px-7.5 shadow-default border-strokedark bg-boxdark p-3">
            <div className="flex h-11.5 w-11.5  rounded-full bg-meta-2 ">
              <EyeIcon className="h-6 w-6" />
            </div>
            <div className="mt-4 flex items-end justify-between">
              <div>
                <h4 className="text-title-md font-bold text-black">$3.456K</h4>
                <span className="text-sm font-medium">Total products</span>
              </div>
            </div>
          </div>
          <div className="rounded-sm border border-stroke py-6 px-7.5 shadow-default border-strokedark bg-boxdark p-3">
            <div className="flex h-11.5 w-11.5  rounded-full bg-meta-2 ">
              <ShoppingBagIcon className="h-6 w-6" />
            </div>

            <div className="mt-4 flex items-end justify-between">
              <div>
                <h4 className="text-title-md font-bold text-black">456</h4>
                <span className="text-sm font-medium">Total rents</span>
              </div>
            </div>
          </div>
          <div className="rounded-sm border border-stroke py-6 px-7.5 shadow-default border-strokedark bg-boxdark p-3">
            <div className="flex h-11.5 w-11.5  rounded-full bg-meta-2 ">
              <CurrencyBangladeshiIcon className="h-6 w-6" />
            </div>

            <div className="mt-4 flex items-end justify-between">
              <div>
                <h4 className="text-title-md font-bold text-black">Tk. 3043</h4>
                <span className="text-sm font-medium">Total income</span>
              </div>
            </div>
          </div>
          <div className="rounded-sm border border-stroke py-6 px-7.5 shadow-default border-strokedark bg-boxdark p-3">
            <div className="flex h-11.5 w-11.5  rounded-full bg-meta-2 ">
              <Check className="h-6 w-6" />
            </div>

            <div className="mt-4 flex items-end justify-between">
              <div>
                <h4 className="text-title-md font-bold text-green">90%</h4>
                <span className="text-sm font-medium">Completion Rate</span>
              </div>
            </div>
          </div>
          {/* <CardTwo />
          <CardThree />
          <CardFour /> */}
        </div>

        {/* <div className="mt-4 grid grid-cols-12 gap-4 md:mt-6 md:gap-6 2xl:mt-7.5 2xl:gap-7.5">
          <ChartOne />
          <ChartTwo />
          <ChartThree />
          <MapOne />
          <div className="col-span-12 xl:col-span-8">
            <TableOne />
          </div>
          <ChatCard />
        </div> */}
      </>
    </>
  );
};

export default Page;
