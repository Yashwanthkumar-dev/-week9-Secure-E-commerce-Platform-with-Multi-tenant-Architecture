import { Route, Routes } from "react-router-dom";
import MainLayout from "./Layout/MainLayout";
import AuthLayout from "./Layout/AuthLayout";
import Login from "./Pages/Login";
import Register from "./Pages/Register";
import Home from "./Pages/Home";

function App() {
  return (
    <>
      <Routes>


        {/* main layout */}
        <Route element={<MainLayout />}>
          <Route path="/homepage" element={<Home />} />
        </Route>


        {/* Auth layout */}
        <Route element={<AuthLayout />}>
          <Route path="/" element={<Login />} />
          <Route path="/register" element={<Register />} />
        </Route>

        
      </Routes>
    </>
  );
}

export default App;
