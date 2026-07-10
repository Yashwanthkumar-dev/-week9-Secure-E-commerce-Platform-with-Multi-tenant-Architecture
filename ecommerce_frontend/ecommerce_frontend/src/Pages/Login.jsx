import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { login } from "../api/AuthApi";

const Login = () => {
  const register = useNavigate();
  const homePage = useNavigate();
  const [loginData, setLoginData] = useState({
    email: "",
    password: "",
  });

  // ========= handle form data changes ========
  const handleFormDataChanges = (e) => {
    const { name, value } = e.target;
    setLoginData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };
  // ============== handle submit ============
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await login(loginData);
      alert("login was successfully");
homePage("/homepage")
    } catch (error) {
      console.log(error);
    }
  };

  // =============== handleGoogleLogin ============
  function handleGoogleLogin() {
    window.location.href = "http://localhost:8081/oauth2/authorization/google";
  }

  return (
    <div className="bg-white/80 flex  justify-between w-[70%] m-auto h-200 overflow-hidden mt-6 shadow-md rounded-lg">
      {/* login content */}
      <div className="p-40 text-center space-y-4">
        <i className="text-xl font-bold ">Techcart</i>
        <h1 className="text-4xl  w-full py-3 font-bold tracking-wide">
          Welcome back
        </h1>

        <p className="text-gray-500">please enter your details</p>

        <form className="space-y-5" onSubmit={handleSubmit}>
          {/* email */}
          <div className=" text-left flex flex-col gap-2">
            <label className="font-semibold">Email Address:</label>
            <input
              type="text"
              placeholder="youremail@gmail.com"
              className="px-8 py-2.5 rounded bg-gray-400/30"
              value={loginData.email}
              onChange={handleFormDataChanges}
              name="email"
            />
          </div>
          {/* password */}
          <div className=" text-left flex flex-col gap-2">
            <label className="font-semibold">Password</label>
            <input
              type="password"
              placeholder="password"
              className="px-8 py-2.5 rounded bg-gray-400/30"
              value={loginData.password}
              onChange={handleFormDataChanges}
              name="password"
            />
          </div>
          {/* Forgot password */}
          <div>
            <button className=" w-full text-right text-blue-800 font-semibold">
              Forgot password?
            </button>
          </div>

          {/* submit button */}
          <button
            type="submit"
            className="border w-full py-2 rounded-xl font-semibold bg-black text-white"
          >
            Sign in
          </button>
          {/* navigate to register */}
          <div>
            <p className="text-gray-400">
              you don't have account ?{" "}
              <span
                className="underline cursor-pointer"
                onClick={() => register("/register")}
              >
                register
              </span>
            </p>
          </div>
        </form>

        <div>
          <p className="text-gray-700">or</p>
        </div>
        {/* google login button */}
        <div>
          <button
            className=" w-full capitalize font-semibold text-xl text-gray-500 py-2 rounded-lg shadow cursor-pointer"
            onClick={handleGoogleLogin}
          >
            google
          </button>
        </div>
      </div>

      {/* login image content */}
      <div>
        <img
          src="https://i.pinimg.com/736x/af/20/78/af20782520da20cb660b2b97b128c6e9.jpg"
          alt="techcart login page image"
        />
      </div>
    </div>
  );
};

export default Login;
