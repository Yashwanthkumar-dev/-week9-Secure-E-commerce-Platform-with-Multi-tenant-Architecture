import { ShoppingCart, UserCircle } from "lucide-react";
import { NavLink } from "react-router-dom";

const Nav = () => {
  return (
    <header className="flex  px-30 justify-between py-5 bg-slate-50 sticky">
      {/* company logo */}
      <div>
        <h2 className="font-semibold text-xl">TechCart</h2>
      </div>

      {/* nav menu */}
      <ul className="flex gap-x-10">
        <NavLink
          to="/homepage"
          className={({ isActive }) => (isActive ? "font-bold" : "")}
        >
          Home
        </NavLink>
        <NavLink
          to="/products"
          className={({ isActive }) => (isActive ? "font-bold" : "")}
        >
          products
        </NavLink>
        <NavLink
          to="/deals"
          className={({ isActive }) => (isActive ? "font-bold" : "")}
        >
          Deals
        </NavLink>
        <NavLink
          to="/about-us"
          className={({ isActive }) => (isActive ? "font-bold" : "")}
        >
          Aboutus
        </NavLink>
      </ul>

      {/* icon images  */}

      <div className="flex gap-10">
        {/* cart */}

        <div>
          <i>
            <ShoppingCart />
          </i>
        </div>

        {/* user profile */}

        <div>
          <i>
            <UserCircle />
          </i>
        </div>
      </div>
    </header>
  );
};

export default Nav;
