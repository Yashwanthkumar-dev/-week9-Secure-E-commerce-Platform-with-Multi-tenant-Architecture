import axios from "axios";

const BASE_URL = "http://localhost:8081/api";

// =========== get all products =========

export const fetchProducts = async () => {
  const response = await axios.get(`${BASE_URL}/product`, {
    withCredentials: true,
  });
  console.log(response.data);
  return response.data;
};
