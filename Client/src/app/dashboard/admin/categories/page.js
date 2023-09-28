"use client";
import CommonTable from "@/components/GlobalComponents/CommonTable";
import Pagination from "@/components/GlobalComponents/Pagination";
import PageHeader from "@/components/OwnerComponents/PageHeader";
import CategoryService from "@/lib/services/categoryService";
import { setCategory } from "@/store/Slices/categorySlice";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { toast } from "react-toastify";

const Page = () => {
  const { category } = useSelector((state) => state.category);
  const router = useRouter();
  const dispatch = useDispatch();
  const [loading, setLoading] = useState(false);
  const [currentPage, setCurrentPage] = useState(1);
  const [postsPerPage] = useState(10);
  // Get current posts
  const indexOfLastPost = currentPage * postsPerPage;
  const indexOfFirstPost = indexOfLastPost - postsPerPage;
  const currentPosts = category?.slice(indexOfFirstPost, indexOfLastPost);

  const paginateFront = () => {
    const totalPages = Math.ceil(category?.length / postsPerPage);
    if (currentPage < totalPages) {
      setCurrentPage(currentPage + 1);
    }
  };

  const paginateBack = () => {
    if (currentPage > 1) {
      setCurrentPage(currentPage - 1);
    }
  };

  useEffect(() => {
    if (!category) {
      setLoading(true);
      CategoryService.getAll()
        .then((res) => {
          console.log(res);
          dispatch(setCategory(res));
          setLoading(false);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }, [category, dispatch]);

  return (
    <div>
      <PageHeader
        title="Categories"
        description={`Total ${category && category.length} categories`}
        actions={[
          {
            name: "Add New",
            type: "addNew",
            href: "/dashboard/admin/categories/add",
          },
        ]}
      />
      <CommonTable
        columns={["id", "categoryName", "description"]}
        data={
          category &&
          category.map((item) => {
            return {
              id: item.id,
              categoryName: item.categoryName,
              description: item.description,
            };
          })
        }
        actions={[
          {
            name: "Edit",
            type: "edit",
            onClick: (e, id) => {
              console.log("Edit");
              router.push(`/dashboard/admin/categories/${id}`);
            },
          },
          {
            name: "Delete",
            type: "delete",
            onClick: (e, id) => {
              CategoryService.delete(id)
                .then((res) => {
                  toast.success("Category deleted successfully");
                  CategoryService.getAll()
                    .then((e) => {
                      dispatch(setCategory(e));
                    })
                    .catch((err) => {
                      console.log(err);
                      toast.error("Something went wrong");
                    });
                })
                .catch((err) => {
                  console.log(err);
                  toast.error("Something went wrong");
                });
            },
          },
        ]}
      />
      <Pagination
        postsPerPage={postsPerPage}
        totalPosts={category?.length}
        paginateBack={paginateBack}
        paginateFront={paginateFront}
        currentPage={currentPage}
      />
    </div>
  );
};
export default Page;
