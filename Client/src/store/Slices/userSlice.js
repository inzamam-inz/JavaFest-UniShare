import { createSlice } from "@reduxjs/toolkit";

// Define the initial state for the user slice
const initialState = {
  isAuthenticated: true, // You can initialize this to false
  user: null, // You can initialize this to the default user data or null
};

// Create the user slice
const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    // Action to set the user data
    setUser: (state, action) => {
      state.user = action.payload;
    },

    // Action to update the user data
    updateUser: (state, action) => {
      // Assuming action.payload contains the updated user data
      state.user = action.payload;
    },

    // Action to delete the user data
    deleteUser: (state) => {
      state.user = null;
    },
  },
});

// Export the actions and reducer
export const { setUser, updateUser, deleteUser } = userSlice.actions;
export default userSlice.reducer;
