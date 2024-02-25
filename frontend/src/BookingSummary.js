import React from "react";
import { Link, useNavigate, useLocation } from "react-router-dom";

const BookingSummary = () => {
  const navigate = useNavigate();
  const location = useLocation();

  // Check if the user is logged in by retrieving the login state from local storage
  const isLoggedIn = localStorage.getItem("isLoggedIn") === "true";

  const handleLogout = () => {
    // Clear the login state in local storage
    localStorage.removeItem("isLoggedIn");
    localStorage.removeItem("userName");
    localStorage.removeItem("userId");

    // Redirect to the login page
    navigate("/");
  };

  // Assume this data is coming from the previous pages via location.state
  const {
    departureDate,
    returningDate,
    selectedDepartureFlight,
    selectedReturningFlight,
    passengers,
    contactPersons,
  } = location.state || {};

  // Function to format dates
  const formatDate = (dateString) => {
    if (!dateString) return "N/A";
    return new Date(dateString).toLocaleDateString("en-US", {
      year: "numeric",
      month: "long",
      day: "numeric",
    });
  };

  // Function to format timestamp into a human-readable date and time
  const formatTimestamp = (timestamp) => {
    const date = new Date(timestamp);
    const options = {
      hour: "2-digit",
      minute: "2-digit",
      hour12: true,
    };
    return date.toLocaleString("en-US", options); // Converts to a readable format in English
  };

  // Determine the trip type based on the presence of a returning flight
  const tripType = selectedReturningFlight ? "round-trip" : "one-way";

  // Function to get fare based on selected class
  const getFareBySelectedClass = (flight) => {
    switch (flight.fareType) {
      case "economy":
        return flight.economyClassFare;
      case "business":
        return flight.businessClassFare;
      case "firstClass":
        return flight.firstClassFare;
      default:
        return 0; // or throw an error
    }
  };

  // Calculate total price based on the trip type and number of passengers
  const calculatedTotalPrice =
    passengers.length * getFareBySelectedClass(selectedDepartureFlight) +
    (tripType === "round-trip"
      ? passengers.length * getFareBySelectedClass(selectedReturningFlight)
      : 0);

  // const calculatedTotalPrice = totalPrice;

  const handleProceed = () => {
    if (window.confirm("Are you sure you want to proceed to payment?")) {
      navigate("/payments", {
        state: {
          departureDate: departureDate,
          returningDate: returningDate,
          selectedDepartureFlight: selectedDepartureFlight, // replace with your actual state or props
          selectedReturningFlight: selectedReturningFlight, // replace with your actual state or props
          passengers: passengers, // replace with your actual state or props
          contactPersons: contactPersons, // replace with your actual state or props
          totalPrice: calculatedTotalPrice, // replace with your actual state or props or calculation
        },
      });
    }
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
        {/* Booking Summary Details */}
        <div className="booking-summary bg-white p-5 rounded shadow-lg">
          <h2 className="text-5xl font-bold mb-4">
            {/* Changed from text-2xl to text-3xl */}
            <b>
              <u>Booking Summary</u>
            </b>
          </h2>

          {selectedDepartureFlight && (
            <div className="flight-information">
              <h3 className="text-2xl font-bold mb-2">
                {/* Increased font size */}
                <b>Departure Flight Information:</b>
              </h3>
              <ul className="text-lg">
                {/* Increased text size */}
                <li>
                  Flight Number : {selectedDepartureFlight.flightNumber} |
                  Aircraft : {selectedDepartureFlight.aircraftType} | Airline :{" "}
                  {selectedDepartureFlight.airline} | From :{" "}
                  {selectedDepartureFlight.departureAirportCity} | To :{" "}
                  {selectedDepartureFlight.arrivalAirportCity} | Departing on :{" "}
                  {formatDate(departureDate)} | {/* Modified */}
                  Departing at :{" "}
                  {formatTimestamp(
                    selectedDepartureFlight.departureTime
                  )} | <br />
                  Arriving on : {""} {formatDate(departureDate)} |{" "}
                  {/* Modified */}
                  Arriving at :{" "}
                  {formatTimestamp(selectedDepartureFlight.arrivalTime)} | Class
                  :{" "}
                  {selectedDepartureFlight.fareType.charAt(0).toUpperCase() +
                    selectedDepartureFlight.fareType.slice(1)}
                </li>
              </ul>
            </div>
          )}

          {/* Returning Flight Information */}
          {selectedReturningFlight && (
            <div className="flight-information">
              <h3 className="text-2xl font-bold mb-2">
                <b>Returning Flight Information:</b>
              </h3>
              <ul className="text-lg">
                <li>
                  Flight Number : {selectedReturningFlight.flightNumber} |
                  Aircraft : {selectedReturningFlight.aircraftType} | Airline :{" "}
                  {selectedReturningFlight.airline} | From :{" "}
                  {selectedReturningFlight.departureAirportCity} | To :{" "}
                  {selectedReturningFlight.arrivalAirportCity} | Departing on :{" "}
                  {formatDate(returningDate)} | {/* Modified */}
                  Departing at :{" "}
                  {formatTimestamp(
                    selectedReturningFlight.departureTime
                  )} | <br />
                  Arriving on : {""} {formatDate(returningDate)} |{" "}
                  {/* Modified */}
                  Arriving at :{" "}
                  {formatTimestamp(selectedReturningFlight.arrivalTime)} | Class
                  :{" "}
                  {selectedReturningFlight.fareType.charAt(0).toUpperCase() +
                    selectedReturningFlight.fareType.slice(1)}
                </li>
              </ul>
            </div>
          )}

          {/* Passenger Information */}
          {passengers && (
            <div className="passenger-information">
              <h3 className="text-2xl font-bold mb-2">
                <b>Passengers Information:</b>
              </h3>
              <ul className="text-lg">
                {passengers.map((passenger, index) => (
                  <li key={index}>
                    Name : {passenger.firstName} {passenger.lastName} | Date of
                    Birth : {passenger.dateOfBirth} | Nationality :{" "}
                    {passenger.nationality} | Passport Number :{" "}
                    {passenger.passportNumber}
                  </li>
                ))}
              </ul>
            </div>
          )}

          {/* Contact Person Information */}
          {contactPersons && (
            <div className="contact-person-information">
              <h3 className="text-2xl font-bold mb-2">
                <b>Contact Person Information:</b>
              </h3>
              <ul className="text-lg">
                {contactPersons.map((contactPerson, index) => (
                  <li key={index}>
                    Name : {contactPerson.firstName} {contactPerson.lastName} |
                    Nationality : {contactPerson.nationality} | Phone Number :{" "}
                    {contactPerson.phoneNumber} | Email Address:{" "}
                    {contactPerson.emailAddress} <br />{" "}
                    {/* Line break added here */}
                    Address: {contactPerson.address}
                  </li>
                ))}
              </ul>
            </div>
          )}

          {/* Total Amount */}
          <div className="total-amount">
            <p className="text-xl font-bold">
              {" "}
              {/* Increased text size */}
              <b>Total Amount of Payment :</b> HKD ${calculatedTotalPrice}
            </p>
          </div>

          {/* Proceed Button */}
          <button
            type="button"
            className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded mt-4"
            onClick={handleProceed}
          >
            Proceed to Payment
          </button>
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

export default BookingSummary;
