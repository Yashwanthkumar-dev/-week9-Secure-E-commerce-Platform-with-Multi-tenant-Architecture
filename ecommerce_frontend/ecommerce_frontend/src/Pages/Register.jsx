import { useNavigate } from "react-router-dom";
import { register } from "../api/AuthApi";
import { useState } from "react";

const Register = () => {
  const login = useNavigate();
  const [registerData, setRegisterData] = useState({
    firstname: "",
    lastname: "",
    email: "",
    password: "",
  });

  //  ======= handle from data =========

  const handleFormData = (e) => {
    const { value, name } = e.target;
    setRegisterData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  //  ======= handle form submit ======

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      await register(registerData);
      alert("register successfully");
      login("/");
    } catch (error) {
      console.log("error", error);
    }
  };

  return (
    <div className="bg-white/80 flex  justify-between w-[70%] m-auto h-200 overflow-hidden mt-6 shadow-md rounded-lg">
      {/* image content */}
      <div>
        <img
          src="https://i.pinimg.com/736x/af/20/78/af20782520da20cb660b2b97b128c6e9.jpg"
          alt="techcart register page image"
        />
      </div>

      {/* main content */}
      <div className="px-60 pt-40 outline-none">
        <h1 className="text-3xl font-bold">Register</h1>
        <p className="w-60 text-sm text-gray-500 mt-2">
          Already have account ?
          <span onClick={() => login("/")} className="underline cursor-pointer">
            Login
          </span>
        </p>
        {/* first name and last name */}

        <form className="space-y-5 mt-5 outline-none" onSubmit={handleSubmit}>
          <div>
            <label htmlFor="" className="capitalize font-bold">
              first name :
            </label>
            <input
              type="text"
              className="px-8 py-2.5 rounded bg-gray-400/30"
              onChange={handleFormData}
              name="firstname"
              value={registerData.firstname}
            />
          </div>
          <div>
            <label htmlFor="" className="capitalize font-bold">
              last name :
            </label>
            <input
              type="text"
              name="lastname"
              className="px-8 py-2.5 rounded bg-gray-400/30"
              onChange={handleFormData}
              value={registerData.lastname}
            />
          </div>

          {/* email and password */}
          <div>
            <label htmlFor="" className="capitalize font-bold">
              email :
            </label>
            <input
              type="email"
              required
              className="px-8 py-2.5 rounded bg-gray-400/30"
              value={registerData.email}
              name="email"
              onChange={handleFormData}
            />
          </div>

          <div>
            <label htmlFor="" className="capitalize font-bold">
              password :
            </label>
            <input
              type="password"
              required
              className="px-8 py-2.5 rounded bg-gray-400/30"
              value={registerData.value}
              name="password"
              onChange={handleFormData}
            />
          </div>

          <button
            className="border w-full py-2 rounded-xl font-semibold bg-black text-white"
            type="submit"
          >
            sign up
          </button>
        </form>
      </div>
    </div>
  );
};

export default Register;
