import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

// import * as React from "react";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
// import Link from "@mui/material/Link";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";

const SignUp = () => {
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    userName: "",
    password: "",
    email: "",
    phoneNumber: "",
    address: "",
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

  const [hover, setHover] = useState(false);
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Validation Logic
    if (
      !formData.firstName ||
      !formData.lastName ||
      !formData.email ||
      !formData.password ||
      !formData.userName ||
      !formData.phoneNumber ||
      !formData.address
    ) {
      alert("Please fill in all fields.");
      return;
    }

    if (
      !/^[A-Za-z]+$/.test(formData.firstName) ||
      !/^[A-Za-z]+$/.test(formData.lastName)
    ) {
      alert("First and Last Name must only contain letters.");
      return;
    }

    if (!/^[\d+()\-]+$/.test(formData.phoneNumber)) {
      alert("Phone number can only contain numbers, +, -, (, and ).");
      return;
    }

    if (formData.password.length < 8) {
      alert("Password must be at least 8 characters long.");
      return;
    }

    const requestData = {
      firstName: formData.firstName,
      lastName: formData.lastName,
      email: formData.email,
      password: formData.password,
      userName: formData.userName,
      phoneNumber: formData.phoneNumber,
      address: formData.address,
    };

    try {
      const response = await fetch("http://localhost:8080/users/signup", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(requestData),
      });

      const responseData = await response.json();
      if (response.ok) {
        console.log("Registration successful", responseData);

        // Store the username in local storage after successful signup
        localStorage.setItem("recentlySignedUpUserName", formData.userName);

        navigate("/signup_successful", {
          state: { userName: formData.userName },
        });
        // } else if (responseData.message === "Email already exists") {
        //   alert("Email already exists.");
      } else {
        alert("Registration failed. Please try again.");
      }
    } catch (error) {
      alert("An error occurred during registration. Please try again.");
      console.error("Error during registration:", error);
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

      {/* Your header, nav, and other content go here */}
      {/* Header */}
      <header className="bg-black text-white p-4 z-10">
        <h1 className="text-4xl font-bold text-center">
          My Flight Booking Website
        </h1>
      </header>

      {/* Navigation */}
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
        {isLoggedIn && (
          <Link
            to="/flights"
            className="font-bold italic text-lg hover:text-gray-300"
          >
            Flight
          </Link>
        )}
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

      <main className="flex-grow p-8 z-10">
        <div className="max-w-2xl mx-auto bg-white rounded-lg shadow p-6">
          <Container component="main" maxWidth="xs">
            <CssBaseline />
            <Box
              sx={{
                marginTop: "8px", // Reduced top margin to move everything up
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
              }}
            >
              <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
                <LockOutlinedIcon />
              </Avatar>
              <Typography component="h1" variant="h5">
                Sign up
              </Typography>
              <Box
                component="form"
                noValidate
                onSubmit={handleSubmit}
                sx={{ mt: 3 }}
              >
                <Grid container spacing={2}>
                  <Grid item xs={12} sm={6}>
                    <TextField
                      autoComplete="given-name"
                      name="firstName"
                      required
                      fullWidth
                      id="firstName"
                      label="First Name"
                      value={formData.firstName}
                      onChange={handleChange}
                      autoFocus
                    />
                  </Grid>
                  <Grid item xs={12} sm={6}>
                    <TextField
                      required
                      fullWidth
                      id="lastName"
                      label="Last Name"
                      name="lastName"
                      value={formData.lastName}
                      onChange={handleChange}
                      autoComplete="family-name"
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <TextField
                      required
                      fullWidth
                      id="email"
                      label="Email Address"
                      name="email"
                      value={formData.email}
                      onChange={handleChange}
                      autoComplete="email"
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <TextField
                      required
                      fullWidth
                      id="userName"
                      label="User Name"
                      name="userName"
                      value={formData.userName}
                      onChange={handleChange}
                      autoComplete="username"
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <TextField
                      required
                      fullWidth
                      id="phoneNumber"
                      label="Phone Number"
                      name="phoneNumber"
                      value={formData.phoneNumber}
                      onChange={handleChange}
                      autoComplete="tel"
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <TextField
                      required
                      fullWidth
                      id="address"
                      label="Address"
                      name="address"
                      value={formData.address}
                      onChange={handleChange}
                      autoComplete="street-address"
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <TextField
                      required
                      fullWidth
                      name="password"
                      label="Password"
                      type="password"
                      id="password"
                      value={formData.password}
                      onChange={handleChange}
                      autoComplete="new-password"
                    />
                  </Grid>
                </Grid>
                <Button
                  type="submit"
                  fullWidth
                  variant="contained"
                  sx={{
                    mt: 3,
                    mb: 2,
                    bgcolor: "#22c55e",
                    "&:hover": { bgcolor: "#15803d" },
                  }} // Green button
                >
                  Sign Up
                </Button>
                <Grid container justifyContent="flex-end">
                  <Grid item>
                    <Link
                      to="/login"
                      style={{
                        color: hover ? "#1E3A8A" : "#3B82F6", // Change the color when hovered
                        fontWeight: "bold", // Assuming you want the link bold as well
                        fontSize: "0.875rem", // Assuming you want to mimic text-sm size
                      }}
                      onMouseEnter={() => setHover(true)}
                      onMouseLeave={() => setHover(false)}
                    >
                      Already have an account? Login
                    </Link>
                  </Grid>
                </Grid>
              </Box>
            </Box>
            {/* <Copyright sx={{ mt: 5 }} /> */}
          </Container>
        </div>
      </main>

      {/* Footer */}
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

export default SignUp;
