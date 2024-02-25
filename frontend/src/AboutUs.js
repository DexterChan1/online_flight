import React from "react";
import { Link, useNavigate } from "react-router-dom";

const AboutUs = () => {
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

      <main className="flex-grow p-8 z-10">
        <div className="max-w-4xl mx-auto bg-white rounded-lg shadow p-6">
          {/* Company Overview */}
          <p className="text-lg mb-6">
            My Flight Booking Website has been a leader in travel convenience
            since 2010. With a focus on user experience, we've helped millions
            of travelers find their perfect flights without the hassle
            traditionally associated with booking travel.
          </p>

          {/* Mission Statement */}
          <section className="mb-6">
            <h2 className="text-3xl font-bold mb-4">Our Mission</h2>
            <p>
              Our mission is to make travel accessible and straightforward for
              everyone, everywhere. We work tirelessly to provide our users with
              the most efficient, cost-effective, and user-friendly flight
              booking experience possible, accompanied by top-notch customer
              service.
            </p>
          </section>

          {/* Company History */}
          <section className="mb-6">
            <h2 className="text-3xl font-bold mb-4">Our Story</h2>
            <p>
              Founded by travel enthusiast and tech innovator, Kirby , My Flight
              Booking Website began as a simple tool to bypass the complexities
              of the travel industry. Since then, we've grown into a trusted
              resource for travelers worldwide.
            </p>
          </section>

          {/* Team Members */}
          <section className="mb-6">
            <h2 className="text-3xl font-bold mb-4">Meet the Team</h2>
            {/* Replace with actual images and bios of your team members */}
            <div className="flex flex-wrap justify-center items-center">
              <div className="p-4">
                <img
                  src={process.env.PUBLIC_URL + "kirby.jpg"}
                  alt="Cookie Monster"
                  className="rounded-full w-32 h-32 object-cover"
                />
                <h3 className="text-xl font-bold">Kirby</h3>
                <p>CEO & Co-founder</p>
              </div>
              {/* Repeat for other team members */}
            </div>
          </section>

          {/* Contact Information */}
          <section className="mb-6">
            <h2 className="text-3xl font-bold mb-4">Get in Touch</h2>
            <p>Have questions or feedback? Reach out to us:</p>
            <ul>
              <li>
                <strong>Email:</strong> contact@myflightbookingsite.com
              </li>
              <li>
                <strong>Phone:</strong> +1 234 567 8900
              </li>
            </ul>
          </section>

          {/* Return to Home Link */}
          <div className="text-center mt-6">
            <Link
              to="/"
              className="font-bold text-blue-500 hover:text-blue-900 "
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

export default AboutUs;
