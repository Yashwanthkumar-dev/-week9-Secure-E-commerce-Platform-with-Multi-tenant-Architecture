import { ArrowRight, CheckCircle } from "lucide-react";
import heroImage from "../assets/image_banner.png";
import { useEffect, useState } from "react";
import { fetchProducts } from "../api/ProductsApi";

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

  return (
    <div>
      {/* hero section */}
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

        {/* background text  */}

        <div className="absolute bottom-3 left-1/2 -translate-x-1/2 z-0">
          <p className="text-[20rem] font-bold text-gray-800/5 select-none">
            Techcart
          </p>
        </div>
      </section>

      {/* why u choose us */}
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

      {/* best selling */}
      <section>
        <div>
          <h2>best selling</h2>
        </div>
        {allProducts &&
          allProducts.map((products) => (
            <div>
              <img src={products.image} alt={products.name} />
            </div>
          ))}
      </section>
    </div>
  );
};

export default Home;

//
