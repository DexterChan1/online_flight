import { useState, useEffect } from "react";
import { Link, useNavigate, useLocation } from "react-router-dom";

const PassengerInfo = () => {
  // Initialize state for multiple passengers' details
  const [passengers, setPassengers] = useState([
    {
      firstName: "",
      lastName: "",
      dateOfBirth: "",
      nationality: "",
      passportNumber: "",
    },
  ]);

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

  const navigate = useNavigate();
  const location = useLocation();

  const addPassenger = () => {
    setPassengers([
      ...passengers,
      {
        firstName: "",
        lastName: "",
        dateOfBirth: "",
        nationality: "",
        passportNumber: "",
      },
    ]);
  };

  // Get today's date in YYYY-MM-DD format
  const today = new Date().toISOString().split("T")[0];

  // Function to remove a passenger from the passengers array
  const removePassenger = (index) => {
    const newPassengers = [...passengers];
    newPassengers.splice(index, 1);
    setPassengers(newPassengers);
  };

  // Function to allow only letter input
  const allowOnlyLetters = (e) => {
    if (!/[a-zA-Z]/.test(e.key)) {
      e.preventDefault();
    }
  };

  const handleChange = (index, e) => {
    const updatedPassengers = [...passengers];
    updatedPassengers[index][e.target.name] = e.target.value;
    setPassengers(updatedPassengers);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    // Create a set for storing unique passport numbers
    const passportNumbers = new Set();

    // Check for unique passport numbers and required fields
    const allDetailsValid = passengers.every((passenger) => {
      const isValidPassport =
        passenger.passportNumber &&
        !passportNumbers.has(passenger.passportNumber);
      const isAllDetailsFilled =
        passenger.firstName &&
        passenger.lastName &&
        passenger.dateOfBirth &&
        passenger.nationality;

      if (isValidPassport) {
        passportNumbers.add(passenger.passportNumber);
      }

      return isValidPassport && isAllDetailsFilled;
    });

    if (allDetailsValid) {
      navigate("/contact_person_info", {
        state: {
          ...location.state, // Include selected flights
          passengers, // Include passenger details
        },
      });
    } else {
      alert(
        "Please ensure all fields are filled and all passport numbers are unique."
      );
    }
  };

  const imagePath = process.env.PUBLIC_URL + "/black_and_white_airplane.png";

  // Render form fields for the passenger
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
        <form onSubmit={handleSubmit}>
          <h2 className="text-2xl font-bold mb-2">Passenger Details</h2>
          {Array.isArray(passengers) &&
            passengers.map((passenger, index) => (
              <div key={index} className="mb-6">
                <h3 className="text-lg font-semibold mb-4">
                  Passenger {index + 1}
                </h3>
                {/* Input fields for passenger details */}
                <InputField
                  id={`firstName-${index}`}
                  name="firstName"
                  label="First Name"
                  value={passenger.firstName}
                  onChange={(e) => handleChange(index, e)}
                  onKeyPress={allowOnlyLetters} // Attach the validation function here for first name
                />
                <InputField
                  id={`lastName-${index}`}
                  name="lastName"
                  label="Last Name"
                  value={passenger.lastName}
                  onChange={(e) => handleChange(index, e)}
                  onKeyPress={allowOnlyLetters} // Attach the validation function here for last name
                />
                <InputField
                  id={`dateOfBirth-${index}`}
                  name="dateOfBirth"
                  label="Date of Birth"
                  type="date"
                  maxDate={today} // Pass today's date as maxDate
                  value={passenger.dateOfBirth}
                  onChange={(e) => handleChange(index, e)}
                />
                <InputField
                  id={`nationality-${index}`}
                  name="nationality"
                  label="Nationality"
                  value={passenger.nationality}
                  onChange={(e) => handleChange(index, e)}
                  onKeyPress={allowOnlyLetters} // Attach the validation function here for nationality
                />
                <InputField
                  id={`passportNumber-${index}`}
                  name="passportNumber"
                  label="Passport Number"
                  value={passenger.passportNumber}
                  onChange={(e) => handleChange(index, e)}
                />
                {passengers.length > 1 && ( // Show delete button only if there is more than one passenger
                  <button
                    type="button"
                    onClick={() => removePassenger(index)}
                    className="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded"
                  >
                    Delete
                  </button>
                )}
              </div>
            ))}
          <button
            type="button"
            onClick={addPassenger}
            disabled={passengers.length >= 9} // Disable button when passenger count is 9 or more
            className={`bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded ${
              passengers.length >= 9 ? "opacity-50 cursor-not-allowed" : ""
            }`}
          >
            Add Passenger
          </button>
          <button
            type="submit"
            className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded mt-4"
          >
            Proceed
          </button>
        </form>
      </main>
      {/* ...footer */}
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

const InputField = ({
  id,
  name,
  label,
  type = "text",
  value,
  onChange,
  maxDate,
  onKeyPress, // Include onKeyPress in the props
}) => (
  <div>
    <label htmlFor={id} className="block text-gray-700 text-sm font-bold mb-2">
      {label}
    </label>
    <input
      id={id}
      name={name}
      type={type}
      placeholder={label}
      value={value}
      onChange={onChange}
      onKeyPress={onKeyPress} // Use the onKeyPress event for validation
      max={type === "date" ? maxDate : undefined} // Use max attribute for date type
      className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-300 rounded py-3 px-4 leading-tight focus:outline-none focus:border-gray-500"
    />
  </div>
);

export default PassengerInfo;
