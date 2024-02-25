import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import reportWebVitals from "./reportWebVitals";

import { createBrowserRouter, RouterProvider, Route } from "react-router-dom";

import Home from "./Home";
import ContactUs from "./ContactUs";
import AboutUs from "./AboutUs";
import SignUp from "./SignUp";
import Flights from "./Flights";
import Login from "./Login";
import FlightResultDeparture from "./FlightResultDeparture";
import FlightResultReturning from "./FlightResultReturning";
import PassengerInfo from "./PassengerInfo";
import ContactPersonInfo from "./ContactPersonInfo";
import BookingSummary from "./BookingSummary";
import Payments from "./Payments";
import PaymentConfirmation from "./PaymentConfirmation";
import SignupSuccessful from "./SignupSuccessful";

//import { getFirestore } from "firebase/firestore";
//import { app as firebaseApp, auth } from "./firebase";

// Setup Firestore database
//const db = getFirestore(firebaseApp);

// Define your routes here as per the first pattern
const router = createBrowserRouter([
  {
    path: "/",
    element: <Home />,
    // Add your loader and action functions as needed, similar to the first pattern
  },
  {
    path: "/contact_us",
    element: <ContactUs />,
  },
  {
    path: "/about_us",
    element: <AboutUs />,
  },
  {
    path: "/signup",
    element: <SignUp />,
  },
  {
    path: "/signup_successful",
    element: <SignupSuccessful />,
  },
  {
    path: "/flights",
    element: <Flights />,
  },
  {
    path: "/flights_result_departure",
    element: <FlightResultDeparture />,
  },
  {
    path: "/flights_result_returning",
    element: <FlightResultReturning />,
  },
  {
    path: "/passengers_info",
    element: <PassengerInfo />,
  },
  {
    path: "/contact_person_info",
    element: <ContactPersonInfo />,
  },
  {
    path: "/booking_summary",
    element: <BookingSummary />,
  },
  {
    path: "/payments",
    element: <Payments />,
  },
  {
    path: "/payment_confirmation",
    element: <PaymentConfirmation />,
  },
  {
    path: "/login",
    element: <Login />,
  },

  // ...other routes
]);

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);

reportWebVitals();
