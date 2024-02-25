import React from "react";
import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { useNavigate, useLocation } from "react-router-dom";

const FlightResultReturning = () => {
  // Assuming you will fetch this data from your backend or service
  const [flightResults, setFlightResults] = useState([]);

  // Check if the user is logged in by retrieving the login state from local storage
  const isLoggedIn = localStorage.getItem("isLoggedIn") === "true";

  const handleLogout = () => {
    // Clear the login state in local storage
    localStorage.removeItem("isLoggedIn");
    localStorage.removeItem("userName");
    localStorage.removeItem("idToken");

    // Redirect to the login page
    navigate("/");
  };

  const [selectedReturningFlight, setSelectedReturningFlight] = useState({
    id: null,
    fareType: null,
    flightNumber: null,
    aircraftType: null,
    departureTime: null,
    arrivalTime: null,
    departureAirportCity: null,
    arrivalAirportCity: null,
    economyClassFare: null,
    businessClassFare: null,
    firstClassFare: null,
  });
  const navigate = useNavigate(); // Initialize useNavigate
  const location = useLocation();
  const {
    fromCity,
    toCity,
    departureDate,
    returningDate,
    passengers,
    tripType,
  } = location.state;

  // Function to handle the selection of a flight
  const selectFlight = (flight) => {
    // Check if the currently selected flight is the same as the one being clicked
    if (
      selectedReturningFlight.id === flight.flightId &&
      selectedReturningFlight.fareType === flight.fareType
    ) {
      // If it's the same flight, unselect it by setting state to null values
      setSelectedReturningFlight({
        id: null,
        fareType: null,
        // Other properties set to null or default values
        flightNumber: null,
        aircraftType: null,
        departureTime: null,
        arrivalTime: null,
        departureAirportCity: null,
        arrivalAirportCity: null,
        economyClassFare: null,
        businessClassFare: null,
        firstClassFare: null,
      });
      console.log("Flight unselected");
    } else {
      // If it's a different flight, select it and update state with new flight details
      const newSelectedFlight = {
        id: flight.flightId,
        fareType: flight.fareType, // Ensure this is being passed correctly
        flightNumber: flight.flightNumber,
        aircraftType: flight.aircraftType,
        departureTime: flight.departureTime,
        arrivalTime: flight.arrivalTime,
        departureAirportCity: flight.departureAirportCity,
        arrivalAirportCity: flight.arrivalAirportCity,
        economyClassFare: flight.economyClassFare,
        businessClassFare: flight.businessClassFare,
        firstClassFare: flight.firstClassFare,
        // Add more fields as necessary
      };

      setSelectedReturningFlight(newSelectedFlight);
      console.log("Selected Flight:", newSelectedFlight);
    }
  };

  // Check if a flight is selected to enable the proceed button
  const isSelected = () => {
    return (
      selectedReturningFlight.id !== null &&
      selectedReturningFlight.fareType !== null
    );
  };

  // Converts Unix timestamp to a human-readable string.
  const formatTime = (departureTimestamp, arrivalTimestamp) => {
    const departureDate = new Date(departureTimestamp);
    const arrivalDate = new Date(arrivalTimestamp);

    // Check if the arrival is on the next day
    const nextDayIndicator =
      arrivalDate.getDate() > departureDate.getDate() ? " (+1 day)" : "";

    // Format the time
    const timeStr = arrivalDate.toLocaleTimeString("en-US", {
      hour: "2-digit",
      minute: "2-digit",
      hour12: true,
    });

    return timeStr + nextDayIndicator;
  };

  // Function to perform GET request
  async function getData(url = "") {
    // Retrieve the idToken from local storage
    const idToken = localStorage.getItem("idToken");
    // Check if the token exists
    if (!idToken) {
      console.error("Authorization token is missing");
      return;
    }
    const response = await fetch(url, {
      headers: {
        "Content-Type": "application/json",
        // Use the retrieved token for authorization
        Authorization: `Bearer ${idToken}`,
      },
    });
    const jsonResponse = await response.json(); // parses JSON response into native JavaScript objects
    console.log(jsonResponse);
    return jsonResponse;
  }

  useEffect(() => {
    if (!location.state) {
      console.error("Search data is missing");
      return;
    }
    // 1. extract searchData from react router

    const { fromCity, toCity, departureDate, returningDate } = location.state;
    // 2. set searchData into URLSearchParams

    const params = new URLSearchParams();
    if (fromCity) params.append("departureAirportCity", location.state.toCity);
    if (toCity) params.append("arrivalAirportCity", location.state.fromCity);
    // if (departureDate)
    //   params.append("departureDate", location.state.departureDate);
    // if (returningDate)
    //   params.append("returningDate", location.state.returningDate);
    // 3. get data by url with URLSearchParams
    const url = `http://localhost:8080/flights?${params.toString()}`;

    // transform search data from flights page into search Params in the url below
    getData(url)
      .then((response) => {
        if (response.data.length === 0) {
          // Handle the case where no flights are found
          console.log("No flights available for the selected cities");
          setFlightResults([]);
        } else {
          setFlightResults(response.data);
        }
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });
  }, []);

  const proceed = () => {
    if (isSelected()) {
      navigate("/passengers_info", {
        state: { ...location.state, selectedReturningFlight },
      });
    } else {
      // If no flight is selected, show an alert
      alert("Please select a flight before proceeding.");
    }
  };

  const imagePath = process.env.PUBLIC_URL + "/black_and_white_airplane.png"; // Ensure you have this image in your public folder
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

      {/* Main content container */}
      <main className="flex-grow p-8 z-10">
        <div className="max-w-6xl mx-auto bg-white rounded-lg shadow py-4 px-6">
          <h2 className="text-2xl font-bold text-center mb-4">
            Returning Flights Search Result Shown:
          </h2>

          <table className="w-full text-left">
            <thead>
              <tr>
                <th>Flight Number</th>
                <th>Airplane Name</th>
                <th>Departure Time</th>
                <th>Arrival Time</th>
                <th>Departure City</th>
                <th>Departure Airport</th>
                <th>Arrival City</th>
                <th>Arrival Airport</th>
                <th>Economy Class Fare</th>
                <th>Business Class Fare</th>
                <th>First Class Fare</th>
              </tr>
            </thead>
            <tbody>
              {Array.isArray(flightResults) &&
                flightResults.map((flight) => (
                  <tr key={flight.id} className="border-b">
                    <td>{flight.flightNumber}</td>
                    <td>{flight.aircraftType}</td>
                    <td>
                      {formatTime(flight.departureTime, flight.departureTime)}
                    </td>
                    <td>
                      {formatTime(flight.departureTime, flight.arrivalTime)}
                    </td>
                    <td>{flight.departureAirportCity}</td>
                    <td>{flight.departureAirportName}</td>
                    <td>{flight.arrivalAirportCity}</td>
                    <td>{flight.arrivalAirportName}</td>
                    <td>
                      ${flight.economyClassFare}{" "}
                      <button
                        onClick={() =>
                          selectFlight({ ...flight, fareType: "economy" })
                        }
                        className={`ml-2 ${
                          selectedReturningFlight?.id === flight.flightId &&
                          selectedReturningFlight?.fareType === "economy"
                            ? "bg-yellow-600"
                            : "bg-yellow-300"
                        } hover:bg-yellow-400 text-black font-bold py-1 px-2 rounded`}
                      >
                        Select
                      </button>
                    </td>
                    <td>
                      ${flight.businessClassFare}{" "}
                      <button
                        onClick={() =>
                          selectFlight({ ...flight, fareType: "business" })
                        }
                        className={`ml-2 ${
                          selectedReturningFlight?.id === flight.flightId &&
                          selectedReturningFlight?.fareType === "business"
                            ? "bg-yellow-600"
                            : "bg-yellow-300"
                        } hover:bg-yellow-400 text-black font-bold py-1 px-2 rounded`}
                      >
                        Select
                      </button>
                    </td>
                    <td>
                      ${flight.firstClassFare}{" "}
                      <button
                        onClick={() =>
                          selectFlight({ ...flight, fareType: "firstClass" })
                        }
                        className={`ml-2 ${
                          selectedReturningFlight?.id === flight.flightId &&
                          selectedReturningFlight?.fareType === "firstClass"
                            ? "bg-yellow-600"
                            : "bg-yellow-300"
                        } hover:bg-yellow-400 text-black font-bold py-1 px-2 rounded`}
                      >
                        Select
                      </button>
                    </td>
                  </tr>
                ))}
            </tbody>
          </table>

          {flightResults.length === 0 && (
            <div className="text-center my-6">
              <p>No flights available for the selected route.</p>
            </div>
          )}

          {flightResults.length > 0 && (
            <div className="text-center my-6">
              <button
                className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded"
                onClick={proceed}
              >
                Proceed
              </button>
            </div>
          )}
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
export default FlightResultReturning;
