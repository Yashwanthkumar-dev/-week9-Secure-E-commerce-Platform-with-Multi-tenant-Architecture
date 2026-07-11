import { motion } from "framer-motion";
import { Swiper, SwiperSlide } from "swiper/react";
import { Autoplay } from "swiper/modules";
import { Quote, Star, BadgeCheck } from "lucide-react";

import "swiper/css";
import { testimonials } from "../Carousel";

const Testimonials = () => {
  return (
    <section className="py-28 relative overflow-hidden">
      {/* Subtle background accent */}
      <div className="absolute top-0 left-1/2 -translate-x-1/2 w-[600px] h-[600px] bg-black/[0.02] rounded-full blur-3xl pointer-events-none" />

      {/* Heading */}
      <div className="text-center mb-20 relative">
        <div className="flex items-center justify-center gap-3 mb-4">
          <span className="h-[1px] w-10 bg-black" />
          <p className="uppercase tracking-[6px] text-xs font-semibold text-black">
            Testimonials
          </p>
          <span className="h-[1px] w-10 bg-black" />
        </div>

        <h2 className="text-5xl md:text-6xl font-bold mt-3 text-black tracking-tight">
          What Our Customers Say
        </h2>

        <p className="text-gray-500 mt-6 max-w-2xl mx-auto leading-8 text-lg">
          Thousands of customers trust TechCart for premium electronics,
          lightning-fast delivery, and world-class customer support.
        </p>
      </div>

      <Swiper
        modules={[Autoplay]}
        slidesPerView={3}
        centeredSlides={true}
        loop={true}
        spaceBetween={30}
        autoplay={{
          delay: 3000,
          disableOnInteraction: false,
        }}
        breakpoints={{
          320: {
            slidesPerView: 1,
          },
          768: {
            slidesPerView: 2,
          },
          1200: {
            slidesPerView: 3,
          },
        }}
        className="px-10"
      >
        {testimonials.map((item) => (
          <SwiperSlide key={item.id}>
            <motion.div
              whileHover={{
                y: -14,
                scale: 1.02,
              }}
              transition={{
                duration: 0.35,
                ease: "easeOut",
              }}
              className="
              h-[440px]
              rounded-[28px]
              border
              border-black/10
              bg-white
              shadow-[0_8px_30px_rgba(0,0,0,0.06)]
              hover:shadow-[0_20px_50px_rgba(0,0,0,0.12)]
              hover:border-black/20
              transition-shadow
              duration-300
              p-9
              flex
              flex-col
              justify-between
              relative
              "
            >
              {/* Quote mark - top right, oversized, faint */}
              <Quote
                className="absolute top-6 right-7 text-black/[0.06]"
                size={72}
                strokeWidth={1.5}
              />

              <div className="relative">
                {/* Icon badge */}
                <div className="w-14 h-14 rounded-2xl bg-black text-white flex items-center justify-center shadow-lg">
                  <Quote size={22} />
                </div>

                {/* Stars - monochrome */}
                <div className="flex gap-1 mt-8">
                  {Array.from({ length: Math.min(Math.max(item.rating, 0), 5) }).map((_, index) => (
                    <Star
                      key={index}
                      size={18}
                      className="fill-black text-black"
                    />
                  ))}
                </div>

                {/* Review */}
                <p className="mt-7 text-gray-700 leading-8 text-[17px] font-medium">
                  "{item.review}"
                </p>
              </div>

              {/* User */}
              <div className="flex items-center gap-4 pt-6 border-t border-black/10 relative">
                <img
                  src={item.image}
                  alt={item.name}
                  onError={(e) => {
                    e.target.onerror = null;
                    e.target.src = `https://ui-avatars.com/api/?name=${encodeURIComponent(
                      item.name
                    )}&background=000000&color=ffffff&bold=true`;
                  }}
                  className="w-16 h-16 rounded-full object-cover ring-4 ring-black/5"
                />

                <div>
                  <h3 className="font-bold text-lg text-black">
                    {item.name}
                  </h3>

                  <p className="text-gray-500 text-sm">
                    {item.role}
                  </p>

                  <span className="flex items-center gap-1 text-black text-xs font-semibold mt-1">
                    <BadgeCheck size={14} className="fill-black text-white" />
                    Verified Purchase
                  </span>
                </div>
              </div>
            </motion.div>
          </SwiperSlide>
        ))}
      </Swiper>
    </section>
  );
};

export default Testimonials;