import { configureStore } from "@reduxjs/toolkit";
// Import your reducers
import userSlice from "./Slices/userSlice";
// import Reducer from "./Slices";
const store = configureStore({
  reducer: {
    user: userSlice,
    // Reducer,
    // Add more reducers as needed
  },
});
export default store;
