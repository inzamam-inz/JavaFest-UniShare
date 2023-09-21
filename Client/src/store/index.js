import { configureStore } from "@reduxjs/toolkit";
// Import your reducers
import categorySlice from "./Slices/categorySlice";
import userSlice from "./Slices/userSlice";
// import Reducer from "./Slices";
const store = configureStore({
  reducer: {
    user: userSlice,
    category: categorySlice,
    // Reducer,
    // Add more reducers as needed
  },
});
export default store;
