import api from "../api";

const BookingService = {
  getAll: () => {
    return api.getAsync("/bookings");
  },

  createBooking: (booking) => {
    return api.postAsync("/bookings", booking);
  },

  updateBooking: (booking) => {
    return api.putAsync("/bookings", booking);
  },

  deleteBooking: (id) => {
    return api.deleteAsync(`/bookings/${id}`);
  },
};

export default BookingService;
