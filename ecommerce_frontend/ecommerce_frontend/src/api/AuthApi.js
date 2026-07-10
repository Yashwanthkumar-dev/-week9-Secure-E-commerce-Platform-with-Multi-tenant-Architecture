import axios from "axios";

const BASE_URL = "http://localhost:8081/api";

// =========== login api =================
export const login = async (loginData) => {
  try {
    const response = await axios.post(`${BASE_URL}/auth/login`, loginData, {
      withCredentials: true,
    });

    console.log(response);
    return response.data;
  } catch (error) {
    console.log(error);
    throw error;
  }
};

// ============== register api ============
export const register = async (registerData) => {
  try {
    const response = await axios.post(
      `${BASE_URL}/auth/register`,
      registerData,
      {
        withCredentials: true,
      },
    );
    console.log(response);
    return response.data;
  } catch (error) {
    console.log(error);
    throw error;
  }
};
