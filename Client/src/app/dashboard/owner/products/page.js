"use client";
import CommonTable from "@/components/GlobalComponents/CommonTable";
import PageHeader from "@/components/OwnerComponents/PageHeader";
import ProductService from "@/lib/services/productService";
import { setMyProducts } from "@/store/Slices/productSlice";
import { useRouter } from "next/navigation";
import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";

const Page = () => {
  const router = useRouter();
  const products = useSelector((state) => state.product.myProducts);
  const dispatch = useDispatch();
  const user = useSelector((state) => state.user.currentUser);

  useEffect(() => {
    if (!products && user) {
      ProductService.getByUser(user?.id).then((res) => {
        dispatch(setMyProducts(res));
      });
    }
  }, [products, user, dispatch]);

  return (
    <>
      <PageHeader
        title="Products"
        description="Manage your products here"
        actions={[
          {
            name: "Add New",
            type: "addNew",
            href: "/dashboard/owner/products/add",
          },
        ]}
      />
      {/* Products Table */}
      <CommonTable
        columns={[
          "image",
          "name",
          "basePrice",
          "perDayPrice",
          "status",
          "description",
        ]}
        data={
          products &&
          products.map((category) => {
            return {
              image: category.image,
              name: category.name,
              basePrice: category.basePrice,
              perDayPrice: category.perDayPrice,
              status: category.status,
              description: category.description,
            };
          })
        }
        actions={[
          {
            name: "Delete",
            type: "delete",
            onClick: () => {},
          },
        ]}
      />
    </>
  );
};

export default Page;
