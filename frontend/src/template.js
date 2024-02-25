import React from "react";
import { Link } from "react-router-dom";

const template = () => {
  const imagePath = process.env.PUBLIC_URL + "/black_and_white_airplane.png";

  return (
    <div className="relative flex flex-col h-screen">
      {/* Overlay with reduced opacity */}
      <div
        className="absolute inset-0 bg-black opacity-50 z-0"
        style={{
          backgroundImage: `url("${imagePath}")`,
          backgroundSize: "cover",
          backgroundPosition: "center",
          backgroundRepeat: "no-repeat",
        }}
      ></div>

      <header className="bg-black text-white p-4 z-10">
        <h1 className="text-4xl font-bold text-center">
          My Flight Booking Website
        </h1>
      </header>

      <nav className="bg-black text-white px-4 py-2 flex justify-between z-10">
        <Link to="/" className="font-bold italic text-lg">
          Home Page
        </Link>
        <Link
          to="/about_us"
          className="font-bold italic text-lg hover:text-gray-300"
        >
          About us
        </Link>
        <Link
          to="/flights"
          className="font-bold italic text-lg hover:text-gray-300"
        >
          Flight
        </Link>
        <div className="flex">
          <Link
            to="/signup"
            className="font-bold italic text-lg mr-4 hover:text-gray-300"
          >
            Sign Up
          </Link>
          <Link
            to="/login"
            className="font-bold italic text-lg hover:text-gray-300"
          >
            Login
          </Link>
        </div>
      </nav>

      <main className="flex-grow p-8 z-10"></main>

      <footer className="bg-black text-white p-4 text-center z-10">
        <Link
          to="/contact_us"
          className="font-bold italic text-lg hover:text-gray-300"
        >
          Contact us
        </Link>
      </footer>
    </div>
  );
};

export default template;
