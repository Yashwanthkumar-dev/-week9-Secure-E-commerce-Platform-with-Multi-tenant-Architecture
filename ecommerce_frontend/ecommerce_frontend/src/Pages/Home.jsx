import { ArrowRight, CheckCircle } from "lucide-react";
import heroImage from "../assets/image_banner.png";
import { useEffect, useState } from "react";
import { fetchProducts } from "../api/ProductsApi";
import iphoneImage from "../assets/iphoneHand.png";
import hangingHeadPhone from "../assets/boomheadband.png";
// import Testimonials from "../Components/Testimonials";
const Home = () => {
  const [allProducts, setAllProducts] = useState();

  // ========= get product api from product api =======
  useEffect(() => {
    const fetchProduct = async () => {
      try {
        const response = await fetchProducts();
        setAllProducts(response);
      } catch (error) {
        console.log("error message :", error);
        throw error;
      }
    };

    fetchProduct();
  }, []);
  const ChooseUs = [
    {
      title: "superior quality",
      description: "we give only the best . no compromises",
    },
    {
      title: "Fast & Free shipping",
      description: "Enjoy free shipping orders over $50",
    },
    {
      title: "30-Days Return",
      description:
        "Not satisfied ? Get a full refund or replacement within 30 days",
    },
    {
      title: "24/7 support",
      description: "Our dedicated support team is available anytime",
    },
  ];

  const categories = [
    "Cables",
    "Headphones",
    "Smartwatch",
    "screen projector",
    "monitor",
    "mouse",
  ];

  return (
    <div>
      {/*  ==================  hero section ====================== */}
      <section className="bg-gray-200 m-8 p-9 rounded-lg overflow-hidden pb-0 relative">
        <div className="flex justify-between">
          <div className="ml-4 space-y-5 ">
            <p className="text-6xl capitalize font-semibold pl-30 tracking-normal">
              transform your tech,
            </p>
            <p className="text-6xl capitalize font-semibold tracking-normal">
              boost digital journey
            </p>
          </div>

          <div className="space-y-5 pr-60">
            <p className="w-120 capitalize leading-relaxed text-lg text-gray-400 tracking-wide">
              from ultra-fast charges to cutting-edge gaming accessories, find
              everything you need to power your devices with style and
              performance
            </p>

            <button className="flex gap-x-2 items-center bg-white rounded-lg px-5 py-2 text-black">
              shop
              <i>
                <ArrowRight size={20} />
              </i>
            </button>
          </div>
        </div>

        {/* banner image  */}
        <div className=" relative z-20 w-full ">
          <img
            src={heroImage}
            alt="techcart hero image banner "
            className=" mx-auto w-100 h-140"
          />
        </div>

        {/* ======================== background text ====================  */}

        <div className="absolute bottom-3 left-1/2 -translate-x-1/2 z-0">
          <p className="text-[20rem] font-bold text-gray-800/5 select-none">
            Techcart
          </p>
        </div>
      </section>

      {/* =================  why u choose us ================= */}
      <section className="flex m-20 items-center justify-center gap-10 ">
        {ChooseUs.map((data, index) => (
          <div
            key={index}
            className="flex gap-5 leading-relaxed items-center rounded-xl p-5 bg-gray-50"
          >
            <i className="rounded-full p-2 bg-gray-200">
              <CheckCircle size={30} />
            </i>
            <div className="space-y-1">
              <h2 className="text-2xl capitalize font-semibold">
                {data.title}
              </h2>
              <p className="text-sm  text-gray-600 capitalize w-60 leading-relaxed">
                {data.description}
              </p>
            </div>
          </div>
        ))}
      </section>

      {/* =================== best selling ========================= */}
      <section className="px-10 py-20">
        <div>
          <h2 className="text-3xl capitalize font-semibold tracking-wide py-9 px-3">
            best sellers
          </h2>
        </div>
        <div className="flex gap-5 justify-evenly">
          {allProducts &&
            allProducts.map((products, index) => (
              <div
                className=" overflow-hidden rounded-lg shadow-lg w-100 leading-relaxed relative group "
                key={index}
              >
                <img
                  src={`${import.meta.env.VITE_API_URL}/${products.image}`}
                  alt={products.name}
                  className="w-100 h-100 rounded-t-lg group-hover:scale-110 duration-700 object-fill"
                />
                <div className="p-5 absolute bottom-2">
                  <div className="flex  justify-between items-center py-2">
                    <h2 className="text-2xl capitalize font-bold text-gray-300">
                      {products.name}
                    </h2>

                    <p className="text-lg font-bold text-gray-200/40 bg-gray-800 px-4 rounded-full ">
                      {products.stock}
                    </p>
                  </div>
                  <p className="font-semibold text-gray-200 py-1">
                    ${products.price}
                  </p>
                  <p className="w-90  truncate text-gray-400 capitalize ">
                    {products.description}
                  </p>
                </div>
              </div>
            ))}
        </div>
      </section>

      {/* ============= shop by category ==================== */}
      <section className="px-10 py-20">
        <div className="space-y-10">
          <h2 className="text-2xl font-bold text-center">Shop by category</h2>
          <div className="flex items-center justify-evenly  py-5  rounded-xl">
            {categories.map((cat, index) => (
              <div
                key={index}
                className="flex flex-col items-center font-bold gap-4"
              >
                <i className=" uppercase bg-gray-400 p-2  w-10 rounded-full text-center text-white">
                  {cat[0]}
                </i>
                <p className="capitalize font-medium text-gray-700 hover:cursor-pointer">
                  {cat}
                </p>
              </div>
            ))}
          </div>
        </div>
      </section>

      <section className="grid grid-cols-2   px-10 py-20 gap-8">
        {/* left section */}
        <div className="bg-gray-600/60 rounded-xl flex justify-between p-15 pb-0">
          <div className="space-y-4 content-center">
            <h2 className="text-3xl capitalize font-bold text-white">
              iphone 16 Pro max
            </h2>
            <p className="font-semibold text-slate-100">
              Greate the smartest iphone 16 Pro max with affordable price
            </p>
            <button className="px-5 py-3 flex bg-white rounded-lg gap-x-4 font-bold capitalize">
              Shop collection
              <i>
                <ArrowRight />
              </i>
            </button>
          </div>

          <div>
            <img
              src={iphoneImage}
              alt="techcart home page iphone image"
              className=""
            />
          </div>
        </div>

        {/* right section */}
        <div className="shadow-2xl rounded-xl p-8 pt-0 text-center relative">
          <img
            src={hangingHeadPhone}
            alt="techcart hanging headphone"
            className="text-center mx-auto"
          />
          <h2 className="text-3xl capitalize font-bold absolute bottom-50 left-50">
            premium headphone
          </h2>
          <p className="font-semibold text-slate-900 absolute bottom-40 left-70">
            Grave the premium Headphone made to happy
          </p>
          <button className="px-5 py-3 flex bg-black rounded-lg gap-x-4 font-bold capitalize text-white absolute bottom-20 left-120">
            shop collection
            <i>
              <ArrowRight size={20} />
            </i>
          </button>
        </div>
      </section>

      {/*  =========================== testimonial ============== */}
      {/* <section>
        <Testimonials />
      </section> */}
    </div>
  );
};

export default Home;