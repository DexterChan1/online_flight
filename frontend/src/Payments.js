import { useState, useEffect } from "react";
import { useLocation, Link, useNavigate } from "react-router-dom";

const Payments = () => {
  const navigate = useNavigate();
  const location = useLocation();

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

  const userId = localStorage.getItem("userId");

  // Assume totalPrice is provided via location.state
  const {
    departureDate,
    returningDate,
    selectedDepartureFlight,
    selectedReturningFlight,
    passengers,
    contactPersons,
    totalPrice,
  } = location.state || {};

  const [cardDetails, setCardDetails] = useState({
    cardNumber: "",
    cardholderName: "",
    expiryDate: "",
    securityCode: "",
    // cardType: "visa", // Default card type set to 'visa'
  });

  const [isSubmitting, setIsSubmitting] = useState(false);

  const postData = async (url, data) => {
    // Retrieve the idToken from local storage
    const idToken = localStorage.getItem("idToken");
    // Check if the token exists
    if (!idToken) {
      console.error("Authorization token is missing");
      return;
    }
    const response = await fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        // Use the retrieved token for authorization
        Authorization: `Bearer ${idToken}`,
      },
      body: JSON.stringify(data),
    });
    return response.json();
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    if (name === "cardholderName") {
      // Allow only letters and spaces
      if (!/^[a-zA-Z\s]*$/.test(value)) return;
    }

    setCardDetails({ ...cardDetails, [name]: value });
  };

  // Validate the expiry date format (MM/YY)
  const isExpiryDateValid = () => {
    const regex = /^(0[1-9]|1[0-2])\/([0-9]{2})$/;
    const currentDate = new Date();
    const currentYear = currentDate.getFullYear() % 100; // Last two digits of the year
    const currentMonth = currentDate.getMonth() + 1; // Month (0-11), so adding 1

    if (regex.test(cardDetails.expiryDate)) {
      const [month, year] = cardDetails.expiryDate.split("/").map(Number);
      return (
        year > currentYear || (year === currentYear && month >= currentMonth)
      );
    } else {
      return false;
    }
  };

  // Extract fareType from the selectedDepartureFlight and selectedReturningFlight
  const departureClassType =
    selectedDepartureFlight?.fareType
      ?.toUpperCase()
      .replace("FIRSTCLASS", "FIRST_CLASS") || "ECONOMY";
  const returningClassType =
    selectedReturningFlight?.fareType
      ?.toUpperCase()
      .replace("FIRSTCLASS", "FIRST_CLASS") || "ECONOMY";

  // Add a function to format the date
  const formatDate = (date) => {
    if (!date) return null; // If date is not provided or invalid, return null

    // Assuming date is in JavaScript Date format or a valid date string
    const d = new Date(date);
    const year = d.getFullYear();
    const month = `${d.getMonth() + 1}`.padStart(2, "0"); // Adding 1 as getMonth() returns 0-11
    const day = `${d.getDate()}`.padStart(2, "0");
    return `${year}-${month}-${day}`;
  };

  const requestData = {
    // userId: userId,
    departureFlightId: selectedDepartureFlight?.id,
    returningFlightId: selectedReturningFlight?.id || null,
    departureDate: formatDate(departureDate), // Use formatDate function
    returningDate: formatDate(returningDate), // Use formatDate function
    numberOfPassengers: passengers.length,
    tripType: selectedReturningFlight ? "ROUND_TRIP" : "ONE_WAY",
    departureClassType: departureClassType, // set from selected fare type
    returningClassType: returningClassType, // set from selected fare type
    paymentStatus: "PENDING", // or "PAID", "FAILED" based on the application logic
  };

  const handleFormSubmit = async (e) => {
    e.preventDefault();
    if (!isExpiryDateValid()) {
      alert("Invalid expiry date format. Please use MM/YY format.");
      return;
    }

    if (window.confirm("Confirm payment?")) {
      setIsSubmitting(true);

      try {
        // Create Trip
        const tripResponse = await postData(
          "http://localhost:8080/trips",
          requestData
        );
        console.log("Trip response:", tripResponse); // Log the trip response

        const tripId = tripResponse?.data?.tripId;

        if (!tripId) {
          throw new Error("Failed to create trip.");
        }

        // Create Passengers
        for (const passenger of passengers) {
          const passengerData = { ...passenger, tripId };
          const passengerResponse = await postData(
            "http://localhost:8080/passengers",
            passengerData
          );
          console.log("Passenger response:", passengerResponse); // Log each passenger response
        }

        // Create Contact Person
        for (const contact of contactPersons) {
          const contactData = { ...contact, tripId };
          const contactResponse = await postData(
            "http://localhost:8080/contact-persons",
            contactData
          );
          console.log("Contact person response:", contactResponse); // Log contact person response
        }

        // Create Payment
        const paymentData = {
          tripId,
          paymentAmount: totalPrice,
          cardNumber: cardDetails.cardNumber,
          cardHolderName: cardDetails.cardholderName,
          expiryDate: cardDetails.expiryDate,
          securityCode: cardDetails.securityCode,
        };
        console.log("Payment data before sending:", paymentData); // Verify payment data before sending

        const paymentResponse = await postData(
          "http://localhost:8080/payments",
          paymentData
        );
        console.log("Payment response:", paymentResponse); // Log payment response

        navigate("/payment_confirmation");
      } catch (error) {
        console.error("Error during form submission:", error);
        alert("There was an error processing your request.");
      } finally {
        setIsSubmitting(false);
      }
    }
  };

  // Allow only numeric input
  const handleNumericInput = (e) => {
    if (!/[0-9]/.test(e.key)) {
      e.preventDefault();
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
        <div className="max-w-4xl mx-auto bg-white rounded-lg shadow p-6">
          <form onSubmit={handleFormSubmit}>
            <h2 className="text-2xl font-bold mb-4">Payment Details:</h2>

            <div className="mb-4">
              <label
                htmlFor="paymentAmount"
                className="block text-gray-700 text-sm font-bold mb-2"
              >
                Payment Amount (HKD):
              </label>
              <input
                id="paymentAmount"
                type="text"
                name="paymentAmount"
                value={totalPrice}
                readOnly
                className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-300 rounded py-3 px-4"
              />
            </div>
            {/* <div className="mb-4">
              <label
                htmlFor="cardType"
                className="block text-gray-700 text-sm font-bold mb-2"
              >
                Card Type:
              </label>
              <select
                id="cardType"
                name="cardType"
                value={cardDetails.cardType}
                onChange={handleInputChange}
                className="block appearance-none w-full bg-gray-200 border border-gray-300 text-gray-700 py-3 px-4 pr-8 rounded"
              >
                <option value="visa">Visa</option>
                <option value="master">MasterCard</option>
                <option value="ae">American Express</option>
              </select>
            </div> */}

            {/* Card Details Input Fields */}
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mb-4">
              <div>
                <label
                  htmlFor="cardNumber"
                  className="block text-gray-700 text-sm font-bold mb-2"
                >
                  Card Number:
                </label>
                <input
                  id="cardNumber"
                  type="text"
                  name="cardNumber"
                  placeholder="Card Number"
                  value={cardDetails.cardNumber}
                  onChange={handleInputChange}
                  onKeyPress={handleNumericInput}
                  required
                  className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-300 rounded py-3 px-4"
                />
              </div>
              <div>
                <label
                  htmlFor="cardholderName"
                  className="block text-gray-700 text-sm font-bold mb-2"
                >
                  Name of Cardholder:
                </label>
                <input
                  id="cardholderName"
                  type="text"
                  name="cardholderName"
                  placeholder="Name of Cardholder"
                  value={cardDetails.cardholderName}
                  onChange={handleInputChange}
                  required
                  className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-300 rounded py-3 px-4"
                />
              </div>
              <div>
                <label
                  htmlFor="expiryDate"
                  className="block text-gray-700 text-sm font-bold mb-2"
                >
                  Expiry Date (MM/YY):
                </label>
                <input
                  id="expiryDate"
                  type="text"
                  name="expiryDate"
                  placeholder="Expiry Date (MM/YY)"
                  value={cardDetails.expiryDate}
                  onChange={handleInputChange}
                  required
                  className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-300 rounded py-3 px-4"
                />
              </div>
              <div>
                <label
                  htmlFor="securityCode"
                  className="block text-gray-700 text-sm font-bold mb-2"
                >
                  Security Code:
                </label>
                <input
                  id="securityCode"
                  type="text"
                  name="securityCode"
                  placeholder="Security Code"
                  value={cardDetails.securityCode}
                  onChange={handleInputChange}
                  onKeyPress={handleNumericInput}
                  required
                  maxLength="4"
                  className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-300 rounded py-3 px-4"
                />
              </div>
            </div>

            <button
              type="submit"
              disabled={isSubmitting}
              className={`bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline ${
                isSubmitting ? "opacity-50 cursor-not-allowed" : ""
              }`}
            >
              {isSubmitting ? "Processing..." : "Confirm Payment"}
            </button>
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

export default Payments;
