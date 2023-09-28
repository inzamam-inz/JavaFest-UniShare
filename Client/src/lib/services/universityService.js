import api from "../api";

const UniversityService = {
  getAll: () => {
    return api.getAsync("/university");
  },

  create: (university) => {
    return api.postAsync("/university", university);
  },

  update: (university) => {
    return api.putAsync(`/university/${university.id}`, university);
  },

  getOne: (id) => {
    return api.getAsync(`/university/${id}`);
  },

  delete: (id) => {
    return api.deleteAsync(`/university/${id}`);
  },
};

export default UniversityService;
