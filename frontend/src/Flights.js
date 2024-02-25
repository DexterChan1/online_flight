import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";

const Flight = () => {
  const [searchData, setSearchData] = useState({
    fromCity: "",
    toCity: "",
    departureDate: "",
    returningDate: "",
    tripType: "",
  });

  // Check if the user is logged in by retrieving the login state from local storage
  const isLoggedIn = localStorage.getItem("isLoggedIn") === "true";

  const handleLogout = () => {
    // Clear the login state in local storage
    localStorage.removeItem("isLoggedIn");
    localStorage.removeItem("userName");

    // Redirect to the login page
    navigate("/");
  };

  const [fromCity, setFromCity] = useState("");
  const [toCity, setToCity] = useState("");
  const [departureFlights, setDepartureFlights] = useState([]);
  const [returningFlights, setReturningFlights] = useState([]);

  // Add state for trip type
  const [tripType, setTripType] = useState("");

  const navigate = useNavigate();

  // Function to format the current date as YYYY-MM-DD
  const formatDate = (date) => {
    return date.toISOString().split("T")[0];
  };

  // Get today's date in YYYY-MM-DD format
  const today = formatDate(new Date());

  const cities = ["New York", "Los Angeles", "London", "Hong Kong"];

  const handleChange = (e) => {
    setSearchData({ ...searchData, [e.target.name]: e.target.value });
  };

  const handleSearch = (e) => {
    e.preventDefault();

    // Validation: Check if all required fields are filled
    if (
      !searchData.fromCity ||
      !searchData.toCity ||
      !searchData.departureDate ||
      (searchData.tripType === "round_trip" && !searchData.returningDate) ||
      // !searchData.passengers ||
      !searchData.tripType
    ) {
      alert("Please fill in all the required fields.");
      return; // Stop the function if validation fails
    }

    // Check if 'From' and 'To' cities are the same
    if (searchData.fromCity === searchData.toCity) {
      alert("The departure and destination cities cannot be the same.");
      return; // Stop the function if validation fails
    }

    // If all validations pass, implement the search logic
    console.log(searchData);

    // Navigate to the FlightResult component with the search data
    navigate("/flights_result_departure", { state: { ...searchData } });
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
      {/* ...header and nav */}
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
          <form onSubmit={handleSearch}>
            <h2 className="text-2xl font-bold mb-4">
              Please enter the following information for flight search:
            </h2>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mb-4">
              {/* From and To City Input */}
              <div>
                <label
                  htmlFor="fromCity"
                  className="block text-gray-700 text-sm font-bold mb-2"
                >
                  From (City)
                </label>
                <select
                  id="fromCity"
                  name="fromCity"
                  value={searchData.fromCity}
                  onChange={handleChange}
                  className="block appearance-none w-full bg-gray-200 border border-gray-300 text-gray-700 py-3 px-4 pr-8 rounded"
                >
                  <option value="">Select a city</option>
                  {cities.map((city) => (
                    <option key={city} value={city}>
                      {city}
                    </option>
                  ))}
                </select>
              </div>

              <div>
                <label
                  htmlFor="toCity"
                  className="block text-gray-700 text-sm font-bold mb-2"
                >
                  To (City)
                </label>
                <select
                  id="toCity"
                  name="toCity"
                  value={searchData.toCity}
                  onChange={handleChange}
                  className="block appearance-none w-full bg-gray-200 border border-gray-300 text-gray-700 py-3 px-4 pr-8 rounded"
                >
                  <option value="">Select a city</option>
                  {cities.map((city) => (
                    <option key={city} value={city}>
                      {city}
                    </option>
                  ))}
                </select>
              </div>

              {/* Departure Date-Time */}
              <div>
                <label
                  htmlFor="departureDate"
                  className="block text-gray-700 text-sm font-bold mb-2"
                >
                  Departure Date
                </label>
                <input
                  id="departureDate"
                  name="departureDate"
                  type="date"
                  min={today}
                  value={searchData.departureDate}
                  onChange={handleChange}
                  className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-300 rounded py-3 px-4"
                />
              </div>

              {/* Conditional Returning Date-Time */}
              {searchData.tripType === "round_trip" && (
                <div>
                  <label
                    htmlFor="returningDate"
                    className="block text-gray-700 text-sm font-bold mb-2"
                  >
                    Returning Date
                  </label>
                  <input
                    id="returningDate"
                    name="returningDate"
                    type="date"
                    min={searchData.departureDate || today}
                    value={searchData.returningDate}
                    onChange={handleChange}
                    className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-300 rounded py-3 px-4"
                  />
                </div>
              )}

              {/* Trip Type */}
              <div>
                <label
                  htmlFor="tripType"
                  className="block text-gray-700 text-sm font-bold mb-2"
                >
                  Trip Type
                </label>
                <select
                  id="tripType"
                  name="tripType"
                  value={searchData.tripType}
                  onChange={handleChange}
                  className="block appearance-none w-full bg-gray-200 border border-gray-300 text-gray-700 py-3 px-4 pr-8 rounded"
                >
                  <option value="">Select Trip Type</option>
                  <option value="one_way">One-Way</option>
                  <option value="round_trip">Round-Trip</option>
                </select>
              </div>
            </div>

            <div className="text-center">
              <button
                className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                type="submit"
              >
                Search
              </button>
            </div>
          </form>
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

export default Flight;
