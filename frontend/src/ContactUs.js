import React from "react";
import { Link, useNavigate } from "react-router-dom";

const ContactUs = () => {
  // Check if the user is logged in by retrieving the login state from local storage
  const isLoggedIn = localStorage.getItem("isLoggedIn") === "true";

  const navigate = useNavigate();

  const handleLogout = () => {
    // Clear the login state in local storage
    localStorage.removeItem("isLoggedIn");
    localStorage.removeItem("userName");

    // Redirect to the login page
    navigate("/");
  };

  const imagePath = process.env.PUBLIC_URL + "/black_and_white_airplane.png";

  return (
    <div className="flex flex-col h-screen">
      <header className="bg-black text-white p-4 z-10">
        <h1 className="text-4xl font-bold text-center">
          My Flight Booking Website
        </h1>
      </header>

      <nav className="bg-black text-white px-4 py-2 flex justify-between z-10">
        <Link to="/" className="font-bold italic text-lg hover:text-gray-300">
          Home Page
        </Link>
        <Link
          to="/about_us"
          className="font-bold italic text-lg hover:text-gray-300"
        >
          About us
        </Link>
        {/* Conditionally render the Flight link based on isLoggedIn */}
        {isLoggedIn && (
          <Link
            to="/flights"
            className="font-bold italic text-lg hover:text-gray-300"
          >
            Flight
          </Link>
        )}
        <div className="flex">
          {isLoggedIn ? (
            <>
              <span className="font-bold italic text-lg mr-4 hover:text-gray-300">
                Manage Your Booking
              </span>
              <button
                onClick={handleLogout}
                className="font-bold italic text-lg hover:text-gray-300"
              >
                Logout
              </button>
            </>
          ) : (
            <>
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
            </>
          )}
        </div>
      </nav>

      <main className="flex-grow relative z-10">
        {/* Overlay with reduced opacity */}
        <div
          className="absolute inset-0 bg-black opacity-50"
          style={{
            backgroundImage: `url("${imagePath}")`,
            backgroundSize: "cover",
            backgroundPosition: "center",
            backgroundRepeat: "no-repeat",
          }}
        ></div>

        {/* Content box with full opacity */}
        <div className="relative flex items-center justify-center h-full">
          <div className="bg-white p-8 rounded shadow-md text-center max-w-md">
            <h2 className="text-2xl font-bold mb-4">Contact Information</h2>
            <p className="mb-4">
              <strong>Address:</strong> 123 Flight Avenue, Airplane City, SKY123
            </p>
            <p className="mb-4">
              <strong>Email:</strong> contact@myflightbookingsite.com
            </p>
            <p className="mb-4">
              <strong>Phone:</strong> +1 234 567 8900
            </p>
            <Link
              to="/"
              className="font-bold text-blue-500 hover:text-blue-900"
            >
              Return to Home Page
            </Link>
          </div>
        </div>
      </main>

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

export default ContactUs;
