import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import CssBaseline from "@mui/material/CssBaseline";
import Grid from "@mui/material/Grid";

const Login = () => {
  const [loginData, setLoginData] = useState({
    email: "",
    // userName: "",
    password: "",
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

  const navigate = useNavigate();

  const handleChange = (e) => {
    setLoginData({ ...loginData, [e.target.name]: e.target.value });
  };

  const handleLogin = async (event) => {
    event.preventDefault();
    // Replace with your Firebase API key
    const firebaseApiKey = process.env.REACT_APP_FIREBASE_API_KEY;
    const signInUrl = `https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=${firebaseApiKey}`;

    try {
      const response = await fetch(signInUrl, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          email: loginData.email,
          password: loginData.password,
          returnSecureToken: true,
        }),
      });

      const userData = await response.json();

      if (response.ok) {
        console.log("Login successful:", userData);
        // Store the login state and user's name in local storage
        localStorage.setItem("isLoggedIn", "true");
        localStorage.setItem("userName", loginData.userName);
        // Store the idToken in local storage
        localStorage.setItem("idToken", userData.idToken);

        // Redirect to home page with user name state
        navigate("/", { state: { userName: loginData.userName } });
      } else {
        console.log("Login failed:", userData.error.message);
        // Handle errors, for example:
        alert("Login failed: " + userData.error.message);
      }
    } catch (error) {
      console.error("Error during fetch:", error);
    }
  };

  const [hoverForgetPassword, setHoverForgetPassword] = useState(false);
  const [hoverSignUp, setHoverSignUp] = useState(false);

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
                marginTop: 4,
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
              }}
            >
              <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
                <LockOutlinedIcon />
              </Avatar>
              <Typography component="h1" variant="h5">
                Login
              </Typography>
              <Box
                component="form"
                noValidate
                onSubmit={handleLogin}
                sx={{ mt: 1 }}
              >
                <TextField
                  margin="normal"
                  required
                  fullWidth
                  id="email"
                  label="Email Address"
                  name="email"
                  autoComplete="email"
                  autoFocus
                  value={loginData.email}
                  onChange={handleChange}
                />
                <TextField
                  margin="normal"
                  required
                  fullWidth
                  name="password"
                  label="Password"
                  type="password"
                  id="password"
                  autoComplete="current-password"
                  value={loginData.password}
                  onChange={handleChange}
                />
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
                  Login
                </Button>
                <Grid container justifyContent="space-between">
                  <Grid item style={{ flexGrow: 1 }}>
                    {/* <Link
                      to="/forget_password"
                      style={{
                        color: hoverForgetPassword ? "#1E3A8A" : "#3B82F6",
                        fontWeight: "bold",
                        fontSize: "0.875rem",
                      }}
                      onMouseEnter={() => setHoverForgetPassword(true)}
                      onMouseLeave={() => setHoverForgetPassword(false)}
                    >
                      Forget password?
                    </Link> */}
                  </Grid>
                  <Grid item>
                    <Link
                      to="/signup"
                      style={{
                        color: hoverSignUp ? "#1E3A8A" : "#3B82F6",
                        fontWeight: "bold",
                        fontSize: "0.875rem",
                      }}
                      onMouseEnter={() => setHoverSignUp(true)}
                      onMouseLeave={() => setHoverSignUp(false)}
                    >
                      Don't have an account? Sign up
                    </Link>
                  </Grid>
                </Grid>
              </Box>
            </Box>
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

export default Login;
