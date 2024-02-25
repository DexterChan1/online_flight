import { useState, useEffect } from "react";
import { Link, useNavigate, useLocation } from "react-router-dom";

const ContactPersonInfo = () => {
  const [contactPersons, setContactPersons] = useState([
    {
      firstName: "",
      lastName: "",
      nationality: "",
      phoneNumber: "",
      emailAddress: "",
      address: "",
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

  const handleChange = (index, e) => {
    const { name, value } = e.target;

    if (name === "firstName" || name === "lastName" || name === "nationality") {
      // Allow only letters for first name, last name, and nationality
      if (!/^[a-zA-Z]*$/g.test(value)) {
        return; // Skip updating the state if non-letter characters are entered
      }
    }

    if (name === "phoneNumber") {
      // Allow only numbers, parentheses, plus, hyphen, and spaces in the phone number
      if (!/^[\d()+-\s]*$/g.test(value)) {
        return; // Skip updating the state if invalid characters are entered
      }
    }

    const newContactPersons = [...contactPersons];
    newContactPersons[index][name] = value;
    setContactPersons(newContactPersons);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    for (const person of contactPersons) {
      const {
        firstName,
        lastName,
        nationality,
        phoneNumber,
        emailAddress,
        address,
      } = person;
      if (
        !firstName ||
        !lastName ||
        !nationality ||
        !phoneNumber ||
        !emailAddress ||
        !address
      ) {
        alert("Please fill in all the required fields.");
        return;
      }
    }
    console.log("Contact Persons Details:", contactPersons);
    navigate("/booking_summary", {
      state: { ...location.state, contactPersons },
    });
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
        <form onSubmit={handleSubmit} className="mb-6">
          <h2 className="text-2xl font-bold mb-2">Contact Person Details</h2>
          {contactPersons.map((contactPerson, index) => (
            <div key={index} className="mb-6">
              <InputField
                id={`firstName-${index}`}
                name="firstName"
                label="First Name"
                value={contactPerson.firstName}
                onChange={(e) => handleChange(index, e)}
                autocomplete="given-name"
              />
              <InputField
                id={`lastName-${index}`}
                name="lastName"
                label="Last Name"
                value={contactPerson.lastName}
                onChange={(e) => handleChange(index, e)}
                autocomplete="family-name"
              />
              <InputField
                id={`nationality-${index}`}
                name="nationality"
                label="Nationality"
                value={contactPerson.nationality}
                onChange={(e) => handleChange(index, e)}
              />
              <InputField
                id={`phoneNumber-${index}`}
                name="phoneNumber"
                label="Phone Number"
                type="tel"
                value={contactPerson.phoneNumber}
                onChange={(e) => handleChange(index, e)}
                autocomplete="tel"
              />
              <InputField
                id={`emailAddress-${index}`}
                name="emailAddress"
                label="Email Address"
                type="email"
                value={contactPerson.emailAddress}
                onChange={(e) => handleChange(index, e)}
                autocomplete="email"
              />
              <InputField
                id={`address-${index}`}
                name="address"
                label="Address"
                value={contactPerson.address}
                onChange={(e) => handleChange(index, e)}
                autocomplete="street-address"
              />
            </div>
          ))}
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
  autocomplete,
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
      autoComplete={autocomplete}
      className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-300 rounded py-3 px-4 leading-tight focus:outline-none focus:border-gray-500"
    />
  </div>
);

export default ContactPersonInfo;
